package com.example.zhz256.ringtone;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String chosenRingtone;
    example e1, e2;

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

        e1 = new example("e1", 1);
        e2 = new example("e2", 2);
        Toast.makeText(MainActivity.this, "1: " + e1.getSound().toString()+" 2: "+e2.getSound().toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    public void Play1(View view){
        Uri notification = e1.getSound();
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();
        Toast.makeText(MainActivity.this, "Playing 1, name: "+e1.getName(), Toast.LENGTH_SHORT).show();
        Toast.makeText(MainActivity.this, "Priority: "+e1.getPriority(), Toast.LENGTH_SHORT).show();
    }

    public void Set1(View view){
        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select tone");
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, (Uri)null);
        this.startActivityForResult(intent, 5);
    }

    public void Play2(View view){
        Uri notification = e2.getSound();
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();
        Toast.makeText(MainActivity.this, "Playing 2, name: "+e2.getName(), Toast.LENGTH_SHORT).show();
        Toast.makeText(MainActivity.this, "Priority: "+e2.getPriority(), Toast.LENGTH_SHORT).show();
    }

    public void Set2(View view){
        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select tone");
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, (Uri) null);
        this.startActivityForResult(intent, 6);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent intent)
    {
        Uri uri = intent.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
        if (resultCode == Activity.RESULT_OK && requestCode == 5)
        {
            e1.setSound(uri);
        }
        else if (resultCode == Activity.RESULT_OK && requestCode == 6)
        {
            e2.setSound(uri);
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
