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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    RingVib ringVib;
    Thread vibThread;

    Switch mute;
    Switch vibrate;
    Switch sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        spinner = (Spinner)findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.Patterns, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ringVib.getVibration().selectPattern(position);
                //Toast.makeText(getBaseContext(), parent.getItemAtPosition(position)+" selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ringVib = new RingVib();
        mute = (Switch)findViewById(R.id.mute);
        vibrate = (Switch)findViewById(R.id.vibrate);
        sound = (Switch)findViewById(R.id.sound);
        RingVib.setMute(false);
        RingVib.setSound(true);
        RingVib.setVibrate(false);
        mute.setChecked(RingVib.isMute());
        sound.setChecked(RingVib.isSound());
        vibrate.setChecked(RingVib.isVibrate());

        mute.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    RingVib.setMute(true);
                    RingVib.setSound(false);
                    RingVib.setVibrate(false);
                } else {
                    RingVib.setMute(false);
                    RingVib.setSound(true);
                }
                sound.setChecked(RingVib.isSound());
                vibrate.setChecked(RingVib.isVibrate());
            }
        });

        vibrate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    RingVib.setVibrate(true);
                    RingVib.setMute(false);
                }else{
                    RingVib.setVibrate(false);
                }
                mute.setChecked(RingVib.isMute());
                sound.setChecked(RingVib.isSound());
                vibrate.setChecked(RingVib.isVibrate());
            }
        });

        sound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    RingVib.setMute(false);
                    RingVib.setSound(true);
                } else {
                    RingVib.setSound(false);
                }
                mute.setChecked(RingVib.isMute());
                vibrate.setChecked(RingVib.isVibrate());
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
        Vibrator vib;
        public VibrateThread(){
        }
        @Override
        public void run(){
            synchronized (this){
                vib = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                if(vib==null){
                    //Toast.makeText(MainActivity.this, "No vibration service", Toast.LENGTH_SHORT).show();
                }else {
                    vib.vibrate(ringVib.getVibration().getPattern(),-1);
                    //Toast.makeText(MainActivity.this, "Vibrating", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void Play(View view){
        if(!RingVib.isMute()){
            if(RingVib.isVibrate()){
                vibThread = new Thread(new VibrateThread());
                vibThread.start();
            }
            if(RingVib.isSound()){
                Uri notification = ringVib.getRing().getRingtone();
                Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), notification);
                ringtone.play();
            }
            //Toast.makeText(MainActivity.this, "Notification", Toast.LENGTH_SHORT).show();
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
        if (resultCode == Activity.RESULT_OK && requestCode == 5 && uri !=null) {
            ringVib.getRing().setRingtone(uri);
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
