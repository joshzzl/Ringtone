package com.example.zhz256.ringtone;

/**
 * Created by zhz256 on 5/31/2016.
 */
public class RingVib {
    static boolean vibrate;
    static boolean mute;
    static boolean sound;

    Vibration vibration;
    Ring ring;

    public RingVib(){
        ring = new Ring();
        vibration = new Vibration();
    }

    public Ring getRing(){
        return ring;
    }

    public Vibration getVibration(){
        return vibration;
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
