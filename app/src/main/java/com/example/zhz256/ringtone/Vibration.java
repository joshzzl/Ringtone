package com.example.zhz256.ringtone;

import android.os.Vibrator;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by zhz256 on 5/31/2016.
 */
public class Vibration {

    final ArrayList<long[]> patterns = new ArrayList<>(Arrays.asList(new long[]{0, 300, 200,300},
                                                                     new long[]{0, 500,250, 500},
                                                                     new long[]{0, 400, 300, 400},
                                                                     new long[]{0, 300, 100, 500}));
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
