package com.epxzzy.epxzzysabers.rendering.poseRenderer.SingleBladed;

import com.google.common.collect.ImmutableList;

import java.util.List;

public enum SingleBladedFlourish {
    NOFLOURISH("none", 0),
    XCROSS("xcross", 1),
    CIRCULAR("circular", 2),
    SPIN("spin", 3);


    List<Flourish> flourishes;
    private final String str;
    private final int tagID;
    private SingleBladedFlourish(String str, int value) {
        this.str = str;
        this.tagID = value;
        this.flourishes = ImmutableList.copyOf(new Flourish[]{new Flourish(str, value)});
    }


    public static List<SingleBladedFlourish> getCategories() {
        List list;
        list = ImmutableList.of(NOFLOURISH,XCROSS, CIRCULAR,SPIN);

        return list;
    }
    public static SingleBladedFlourish fromTagID(int tagID) {
        for (SingleBladedFlourish flourish : values()) {
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
