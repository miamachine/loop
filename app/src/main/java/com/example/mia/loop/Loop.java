package com.example.mia.loop;

import android.util.Log;

import java.util.Date;
import java.util.UUID;

/**
 * Created by mia on 3/26/15.
 */
public class Loop {
    // attributes shared by all Loop types
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mRecurring;
    private LoopCategoryType mCategoryType;
    private static final String TAG = "Loop";

    // Loop constructor
    Loop() {
        Log.d(TAG, "Loop constructor");
        // generate unique identifier
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    // methods shared by all Loop types
    public UUID getId() {
        return mId;
    }

    // get and set Loop title
    public String getTitle() {
        return mTitle;
    }
    public void setTitle(String title) {
        this.mTitle = title;
    }

    // get and set next Loop occurrence
    public Date getDate() {
        return mDate;
    }
    public void setDate(Date date) {
        this.mDate = date;
    }

    // get and set whether the Loop is recurring
    public boolean isRecurring() { return mRecurring; }
    public void setRecurring(boolean recurring) { this.mRecurring = recurring; }

    // get and set the Loop category (should only be changed once when created)
    public String getCategoryType() { return mCategoryType.toString(); }
    public void setCategoryType(LoopCategoryType category) { this.mCategoryType = category; }
    public int getCategoryColor() { return mCategoryType.color(); }

    @Override
    public String toString() {
        return mTitle;
    }
}