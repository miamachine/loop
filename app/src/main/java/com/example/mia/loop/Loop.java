package com.example.mia.loop;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Created by mia on 3/26/15.
 */
public class Loop {
    // attributes shared by all Loop types
    private UUID mId;
    private String mTitle;
    private Date mDateAndTime;
    private boolean mRecurring;
    private LoopCategoryType mCategoryType;
    private ArrayList<Detail> mDetails;
    private Detail mCurrent;
    private static final String TAG = "Loop";

    // Loop constructor
    Loop() {
        Log.d(TAG, "Loop constructor");
        // generate unique identifier
        mId = UUID.randomUUID();
        mDateAndTime = new Date();
        mDetails = new ArrayList<Detail>();
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

    // get and set next Loop month, day, year, hour and minutes
    public Date getDateAndTime() {
        return mDateAndTime;
    }
    public void setDateAndTime(Date date) {
        this.mDateAndTime = date;
    }

    // get and set whether the Loop is recurring
    public boolean isRecurring() { return mRecurring; }
    public void setRecurring(boolean recurring) { this.mRecurring = recurring; }

    // get and set the Loop category (should only be changed once when created)
    public String getCategoryType() { return mCategoryType.toString(); }
    public void setCategoryType(LoopCategoryType category) { this.mCategoryType = category; }
    public int getCategoryColor() { return mCategoryType.color(); }

    public void setDetailText(String text) { mCurrent.setText(text); }
    public String getDetailText(String text) { return mCurrent.getText(); }
    public void setDetailType(Detail type) { this.mCurrent = type; }
    public String getDetailType() { return mCurrent.toString(); }
    public void addDetail(Detail itemToAdd) { mDetails.add(itemToAdd); }
    public void removeDetail(Detail itemToRemove) { mDetails.remove(itemToRemove); }
    public ArrayList<Detail> getDetails() { return mDetails; }

    @Override
    public String toString() {
        return mTitle;
    }

    // Detail class to add fields dynamically
    public enum Detail {
        TRANSPORTATION("Transportation"){
            @Override
            public int getIconColor() { return R.color.primary; }
            @Override
            public String getHint() {
                return "Biking and carpooling are good for the Earth!";
            }
        },
        MONEY("Money"){
            @Override
            public int getIconColor() {
                return R.color.primary;
            }
            @Override
            public String getHint() {
                return "Any associated costs?";
            }
        },
        WHAT_TO_WEAR("What to Wear") {
            @Override
            public int getIconColor() {
                return R.color.primary;
            }
            @Override
            public String getHint() {
                return "Flip flops or hiking boots?";
            }
        },
        OTHER("Other") {
            @Override
            public int getIconColor() {
                return R.color.primary;
            }
            @Override
            public String getHint() {
                return "Anything and/or everything!";
            }
        },
        READ_FIRST("Read First") {
            @Override
            public int getIconColor() {
                return R.color.primary;
            }
            @Override
            public String getHint() {
                return "Stuff users should familiarize with beforehand...";
            }
        },
        EQUIPMENT_AND_TOOLS("Equipment & Tools") {
            @Override
            public int getIconColor() {
                return R.color.primary;
            }
            @Override
            public String getHint() {
                return "Don't forget your hammer!";
            }
        },
        MATERIALS("Materials") {
            @Override
            public int getIconColor() {
                return R.color.Teal_50;
            }
            @Override
            public String getHint() {
                return "Any required materials?";
            }
        },
        FITNESS("Fitness") {
            @Override
            public int getIconColor() {
                return R.color.primary;
            }
            @Override
            public String getHint() {
                return "Goal pace, distance, max reps, etc.";
            }
        };

        private String mType;
        private String mText;
        private Detail(String type) {
            this.mType = type;
        }
        private void setText(String text) {
            this.mText = text;
        }
        private String getText() {
            return this.mText;
        }
        @Override
        public String toString() {
            return mType;
        }
        public abstract int getIconColor();
        public abstract String getHint();

    }
}
