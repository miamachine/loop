package com.example.mia.loop;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by mia on 3/26/15.
 */
public class Loops {
    private static Loops sLoops;
    private Context mAppContext;
    private ArrayList<Loop> mLoops;

    private Loops(Context appContext) {
        // context parameter allows the singleton to start activities,
        // access project resources, etc.
        mAppContext = appContext;
        mLoops = new ArrayList<Loop>();
    }

    public static Loops get(Context c) {
        if(sLoops == null) {
            sLoops = new Loops(c.getApplicationContext());
        }
        return sLoops;
    }

    public void addLoop(Loop l) {
        mLoops.add(l);
    }

    public ArrayList<Loop> getLoops() {
        return mLoops;
    }

    public Loop getLoop(UUID id) {
        for(Loop l : mLoops) {
            if(l.getId().equals(id))
                return l;
        }
        return null;
    }
}
