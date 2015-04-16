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

    public ArrayList<Loop> getSeedLoops() {
        Loop loop1 = new Loop();
        loop1.setTitle("Study session for CHEM100 final");
        loop1.setCategoryType(LoopCategoryType.ACADEMIC);
        loop1.setRecurring(true);
        Loops.get(mAppContext).addLoop(loop1);

        Loop loop2 = new Loop();
        loop2.setTitle("Running 1000m repeats at Lake Yosemite");
        loop2.setCategoryType(LoopCategoryType.FITNESS);
        loop2.setRecurring(true);
        Loops.get(mAppContext).addLoop(loop2);

        Loop loop3 = new Loop();
        loop3.setTitle("Yosemite Half Dome day trip :D");
        loop3.setCategoryType(LoopCategoryType.OUTDOORS);
        loop3.setRecurring(false);
        Loops.get(mAppContext).addLoop(loop3);

        Loop loop4 = new Loop();
        loop4.setTitle("Trivia and drinks @ the Partisan");
        loop4.setCategoryType(LoopCategoryType.SOCIAL);
        loop4.setRecurring(true);
        Loops.get(mAppContext).addLoop(loop4);

        Loop loop5 = new Loop();
        loop5.setTitle("Prototyping Arduino boards with NeoPixel LEDs");
        loop5.setCategoryType(LoopCategoryType.LEARNING_AND_TECHNOLOGY);
        loop5.setRecurring(false);
        Loops.get(mAppContext).addLoop(loop5);

        Loop loop6 = new Loop();
        loop6.setTitle("Cinco de Cuatro Celebration");
        loop6.setCategoryType(LoopCategoryType.LANGUAGE_AND_CULTURE);
        loop6.setRecurring(false);
        Loops.get(mAppContext).addLoop(loop6);

        Loop loop7 = new Loop();
        loop7.setTitle("Open Mic Night at Coffee Bandits");
        loop7.setCategoryType(LoopCategoryType.MUSIC);
        loop7.setRecurring(true);
        Loops.get(mAppContext).addLoop(loop7);

        Loop loop8 = new Loop();
        loop8.setTitle("Knitting tree sweaters!");
        loop8.setCategoryType(LoopCategoryType.CRAFTS_AND_DIY);
        loop8.setRecurring(true);
        Loops.get(mAppContext).addLoop(loop8);

        Loop loop9 = new Loop();
        loop9.setTitle("Pitch Fest at UC Merced");
        loop9.setCategoryType(LoopCategoryType.ENTREPRENEURSHIP);
        loop9.setRecurring(false);
        Loops.get(mAppContext).addLoop(loop9);

        Loop loop10 = new Loop();
        loop10.setTitle("Game of Thrones Pre-season Binge Watching Party");
        loop10.setCategoryType(LoopCategoryType.SOCIAL);
        loop10.setRecurring(false);
        Loops.get(mAppContext).addLoop(loop10);

        Loop loop11 = new Loop();
        loop11.setTitle("CS:GO");
        loop11.setCategoryType(LoopCategoryType.GAMING);
        loop11.setRecurring(true);
        Loops.get(mAppContext).addLoop(loop11);

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
