package com.example.zhz256.ringtone;

import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.widget.Toast;

import java.util.Date;

/**
 * Created by zhz256 on 5/26/2016.
 */
public class Ring {
    private Uri ringtone;
    static boolean vibrate;
    static boolean mute;
    static boolean sound;



    public Ring (){
        ringtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    }

    public Uri getRingtone(){
        return ringtone;
    }

    public void setRingtone(Uri r){
        ringtone = r;
    }

    public static boolean isMute(){
        return mute;
    }

    public static boolean isVibrate(){
        return vibrate;
    }

    public static boolean isSound(){
        return sound;
    }

    public static void setSound(boolean flag) {
        if (flag) {
            sound = true;
        }else{
            sound=false;
        }
    }

    public static void setVibrate(boolean flag){
        if (flag) {
            vibrate=true;
        }else{
            vibrate=false;
        }

    }

    public static void setMute(boolean flag){
        if(flag){
            vibrate = false;
            sound = false;
            mute = true;
        }else{
            mute=false;
        }
    }

}
