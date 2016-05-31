package com.example.zhz256.ringtone;

import android.os.Vibrator;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by zhz256 on 5/31/2016.
 */
public class Vibration {

    final ArrayList<long[]> patterns = new ArrayList<>(Arrays.asList(new long[]{100, 0, 100},
                                                                     new long[]{50, 0, 200},
                                                                     new long[]{100, 0, 200, 0, 100},
                                                                     new long[]{50, 0, 50, 0}));
    private long[] pattern;

    public Vibration(){
        pattern = patterns.get(0);
    }

    public void setPattern(long[] P){
        pattern = P;
    }

    public long[] getPattern(){
        return pattern;
    }

    public void selectPattern(int n){
        if(n>=0 && n<4)
            pattern = patterns.get(n);
    }

}
