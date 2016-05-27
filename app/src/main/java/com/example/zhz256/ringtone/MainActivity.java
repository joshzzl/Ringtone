package com.example.zhz256.ringtone;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Ring r;
    Thread vib;

    Switch mute;
    Switch vibrate;
    Switch sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        r = new Ring();
        mute = (Switch)findViewById(R.id.mute);
        vibrate = (Switch)findViewById(R.id.vibrate);
        sound = (Switch)findViewById(R.id.sound);
        Ring.setMute(false);
        Ring.setSound(true);
        Ring.setVibrate(false);
        mute.setChecked(Ring.isMute());
        sound.setChecked(Ring.isSound());
        vibrate.setChecked(Ring.isVibrate());

        mute.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Ring.setMute(true);
                } else {
                    Ring.setMute(false);
                }
                sound.setChecked(Ring.isSound());
                vibrate.setChecked(Ring.isVibrate());
            }
        });

        vibrate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Ring.setVibrate(true);
                }else{
                    Ring.setVibrate(false);
                }
                mute.setChecked(Ring.isMute());
                sound.setChecked(Ring.isSound());
                vibrate.setChecked(Ring.isVibrate());
            }
        });

        sound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Ring.setSound(true);
                } else {
                    Ring.setSound(false);
                }
                mute.setChecked(Ring.isMute());
                vibrate.setChecked(Ring.isVibrate());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    final class VibrateThread implements Runnable{
        Vibrator v;
        public VibrateThread(){
        }
        @Override
        public void run(){
            synchronized (this){
                Vibrator v = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                if(v==null){
                    Toast.makeText(MainActivity.this, "No vibration service", Toast.LENGTH_SHORT).show();
                }else {
                    v.vibrate(500);
                }
            }
        }
    }

    public void Play(View view){
        if(!Ring.isMute()){
            if(Ring.isVibrate()){
                vib = new Thread(new VibrateThread());
                vib.start();
            }
            if(Ring.isSound()){
                Uri notification = r.getRingtone();
                Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), notification);
                ringtone.play();
            }
            Toast.makeText(MainActivity.this, "Notification", Toast.LENGTH_SHORT).show();
        }
    }

    public void Set(View view){
        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select tone");
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, (Uri)null);
        this.startActivityForResult(intent, 5);
    }


    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent intent)
    {
        Uri uri = intent.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
        if (resultCode == Activity.RESULT_OK && requestCode == 5) {
            r.setRingtone(uri);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
