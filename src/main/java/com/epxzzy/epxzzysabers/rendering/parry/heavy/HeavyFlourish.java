package com.epxzzy.epxzzysabers.rendering.parry.heavy;

import com.google.common.collect.ImmutableList;

import java.util.List;

public enum HeavyFlourish {
    NOFLOURISH("none", 0),
    FIGUREEIGHT("figure-eight", 1),
    BEHINDTHEBACK("behind-the-back", 2),
    SKIPCATCH("skip-catch", 3);



    private final String str;
    private final int tagID;
    List<Flourish> flourishes;
    private HeavyFlourish(String str, int value) {
        this.str = str;
        this.tagID = value;


        this.flourishes = ImmutableList.copyOf(new Flourish[]{new Flourish(str, value)});
    }


    public static List<HeavyFlourish> getCategories() {
        List list;
        list = ImmutableList.of(NOFLOURISH, SKIPCATCH, BEHINDTHEBACK, FIGUREEIGHT);

        return list;
    }

    public static HeavyFlourish fromTagID(int tagID) {
        for (HeavyFlourish flourish : values()) {
            if (flourish.tagID == tagID) {
                return flourish;
            }
        }
        return NOFLOURISH;
    }

        public String getName() {
        return str;
    }

    public int getTagID() {
        return tagID;
    }

    public record Flourish(String name, int TagID){}
}
