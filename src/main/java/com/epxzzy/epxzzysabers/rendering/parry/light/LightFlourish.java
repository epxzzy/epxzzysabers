package com.epxzzy.epxzzysabers.rendering.parry.light;

import com.google.common.collect.ImmutableList;

import java.util.List;

public enum LightFlourish {
    NOFLOURISH("none", 0),
    XCROSS("xcross", 1),
    CIRCULAR("circular", 2),
    SPIN("spin", 3);


    List<Flourish> flourishes;
    private final String str;
    private final int tagID;
    private LightFlourish(String str, int value) {
        this.str = str;
        this.tagID = value;
        this.flourishes = ImmutableList.copyOf(new Flourish[]{new Flourish(str, value)});
    }


    public static List<LightFlourish> getCategories() {
        List list;
        list = ImmutableList.of(NOFLOURISH,XCROSS, CIRCULAR,SPIN);

        return list;
    }
    public static LightFlourish fromTagID(int tagID) {
        for (LightFlourish flourish : values()) {
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
