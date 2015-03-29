package com.example.mia.loop;

import java.util.Date;
import java.util.UUID;

/**
 * Created by mia on 3/26/15.
 */
public class Loop {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mRecurring;

    public Loop() {
        // generate unique identifier
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        this.mDate = date;
    }

    public boolean isRecurring() { return mRecurring; }

    public void setRecurring(boolean recurring) { this.mRecurring = recurring; }

    @Override
    public String toString() {
        return mTitle;
    }
}
