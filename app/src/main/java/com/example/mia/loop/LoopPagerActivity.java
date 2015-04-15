package com.example.mia.loop;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by mia on 3/26/15.
 */
public class LoopPagerActivity extends FragmentActivity {
    private ViewPager mViewPager;
    private ArrayList<Loop> mLoops;
    private static final String TAG = "LoopPagerActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);

        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);

        mLoops = Loops.get(this).getLoops();

        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public android.support.v4.app.Fragment getItem(int position) {
                Loop loop = mLoops.get(position);
                return LoopFragment.newInstance(loop.getId());
            }

            @Override
            public int getCount() {
                return mLoops.size();
            }
        });

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected");
                Loop loop = mLoops.get(position);
                if(loop.getTitle() != null) {
                    setTitle(loop.getTitle());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });

        UUID loopId = (UUID)getIntent().getSerializableExtra(LoopFragment.EXTRA_LOOP_ID);
        for(int i = 0; i < mLoops.size(); i++) {
            if(mLoops.get(i).getId().equals(loopId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}