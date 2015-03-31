package com.example.mia.loop;

import android.graphics.Color;

/**
 * Created by mia on 3/29/15.
 */
public enum LoopCategoryType {
    ACADEMIC("Academic"){
        @Override
        public int color() {
            return R.color.Teal_50;
        }
    },
    FITNESS("Fitness"){
        @Override
        public int color() {
            return R.color.Teal_100;
        }
    },
    OUTDOORS("Outdoors"){
        @Override
        public int color() {
            return R.color.accent;
        }
    },
    SOCIAL("Social"){
        @Override
        public int color() {
            return R.color.Teal_300;
        }
    },
    LEARNING_AND_TECHNOLOGY("Learning & Technology"){
        @Override
        public int color() {
            return R.color.Teal_400;
        }
    },
    LANGUAGE_AND_CULTURE("Language & Culture"){
        @Override
        public int color() {
            return R.color.primary;
        }
    },
    MUSIC("Music"){
        @Override
        public int color() {
            return R.color.Teal_600;
        }
    },
    CRAFTS("Crafts"){
        @Override
        public int color() {
            return R.color.Teal_700;
        }
    },
    ENTREPRENEURSHIP("Entrepreneurship"){
        @Override
        public int color() {
            return R.color.Teal_800;
        }
    },
    ENTERTAINMENT("Entertainment"){
        @Override
        public int color() {
            return R.color.primary_dark;
        }
    },
    GAMING("Gaming"){
        @Override
        public int color() {
            return R.color.Teal_A100;
        }
    };

    private String mName;
    private LoopCategoryType(String name) {
        this.mName = name;
    }
    @Override
    public String toString() {
        return mName;
    }
    public abstract int color();
}
