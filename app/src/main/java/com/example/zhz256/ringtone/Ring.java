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

    public Ring (){
        ringtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    }

    public Uri getRingtone(){
        return ringtone;
    }

    public void setRingtone(Uri r){
        ringtone = r;
    }

}
