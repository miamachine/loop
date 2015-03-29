package com.example.mia.loop;

import android.app.Fragment;
import android.app.ListFragment;

import java.util.ArrayList;

/**
 * Created by mia on 3/26/15.
 */
public class LoopListActivity extends SingleFragmentActivity {
    private ArrayList<Loop> mLoops;
    private static final String TAG = "LoopListActivity";

    @Override
    protected Fragment createFragment() {
        return new LoopListFragment();
    }
}
