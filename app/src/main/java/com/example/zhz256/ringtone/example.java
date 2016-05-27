package com.example.zhz256.ringtone;

import android.media.RingtoneManager;
import android.net.Uri;

/**
 * Created by zhz256 on 5/26/2016.
 */
public class example {
    private String name;
    private Uri sound;
    private int priority;

    public example(String n, int p){
        name = n;
        priority = p;
        sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    }

    public String getName(){
        return name;
    }

    public Uri getSound(){
        return sound;
    }

    public void setSound(Uri newSound){
        sound = newSound;
    }

    public int getPriority(){
        return priority;
    }
}
