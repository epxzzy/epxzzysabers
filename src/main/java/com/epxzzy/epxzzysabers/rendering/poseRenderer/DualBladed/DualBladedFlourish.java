package com.epxzzy.epxzzysabers.rendering.poseRenderer.DualBladed;

import com.google.common.collect.ImmutableList;

import java.util.List;

public enum DualBladedFlourish {
    NOFLOURISH("none", 0),
    SKIPCATCH("skip-catch", 1),
    BEHINDTHEBACK("behind-the-back", 2),
    FIGUREEIGHT("figure-eight", 3);


    private final String str;
    private final int tagID;
    List<Flourish> flourishes;
    private DualBladedFlourish(String str, int value) {
        this.str = str;
        this.tagID = value;


        this.flourishes = ImmutableList.copyOf(new Flourish[]{new Flourish(str, value)});
    }


    public static List<DualBladedFlourish> getCategories() {
        List list;
        list = ImmutableList.of(NOFLOURISH, SKIPCATCH, BEHINDTHEBACK, FIGUREEIGHT);

        return list;
    }

    public static DualBladedFlourish fromTagID(int tagID) {
        for (DualBladedFlourish flourish : values()) {
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
