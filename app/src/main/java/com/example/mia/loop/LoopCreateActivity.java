package com.example.mia.loop;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import java.util.UUID;

/**
 * Created by mia on 3/29/15.
 */
public class LoopCreateActivity extends SingleFragmentActivity {
    private static final String TAG ="LoopCreateActivity";

    @Override
    protected Fragment createFragment() {
        return new LoopCreateFragment();
    }
}
