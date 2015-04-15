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

        @Override
        public int details() {
            return R.layout.fragment_category_academic;
        }
    },
    FITNESS("Fitness"){
        @Override
        public int color() {
            return R.color.Teal_100;
        }
        @Override
        public int details() {
            return R.layout.fragment_category_fitness;
        }
    },
    OUTDOORS("Outdoors"){
        @Override
        public int color() {
            return R.color.accent;
        }
        @Override
        public int details() {
            return R.layout.fragment_category_outdoors;
        }
    },
    SOCIAL("Social"){
        @Override
        public int color() {
            return R.color.Teal_300;
        }
        @Override
        public int details() {
            return R.layout.fragment_category_social;
        }
    },
    LEARNING_AND_TECHNOLOGY("Learning & Technology"){
        @Override
        public int color() {
            return R.color.Teal_400;
        }
        @Override
        public int details() {
            return R.layout.fragment_category_learning_and_technology;
        }
    },
    LANGUAGE_AND_CULTURE("Language & Culture"){
        @Override
        public int color() {
            return R.color.primary;
        }
        @Override
        public int details() {
            return R.layout.fragment_category_language_and_culture;
        }
    },
    MUSIC("Music"){
        @Override
        public int color() {
            return R.color.Teal_600;
        }
        @Override
        public int details() {
            return R.layout.fragment_category_music;
        }
    },
    CRAFTS_AND_DIY("Crafts & DIY"){
        @Override
        public int color() {
            return R.color.Teal_700;
        }
        @Override
        public int details() {
            return R.layout.fragment_category_crafts_and_diy;
        }
    },
    ENTREPRENEURSHIP("Entrepreneurship"){
        @Override
        public int color() {
            return R.color.Teal_800;
        }
        @Override
        public int details() {
            return R.layout.fragment_category_entrepreneurship;
        }
    },
    ENTERTAINMENT("Entertainment"){
        @Override
        public int color() {
            return R.color.primary_dark;
        }
        @Override
        public int details() {
            return R.layout.fragment_category_entertainment;
        }
    },
    GAMING("Gaming"){
        @Override
        public int color() {
            return R.color.Teal_A100;
        }
        @Override
        public int details() {
            return R.layout.fragment_category_gaming;
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
    public abstract int details();
}
