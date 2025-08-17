package com.epxzzy.epxzzysabers.rendering.poseRenderer.LightWeapon;

import com.google.common.collect.ImmutableList;

import java.util.List;

public enum LightWeaponFlourish {
    NOFLOURISH("none", 0),
    XCROSS("xcross", 1),
    CIRCULAR("circular", 2),
    SPIN("spin", 3);


    List<Flourish> flourishes;
    private final String str;
    private final int tagID;
    private LightWeaponFlourish(String str, int value) {
        this.str = str;
        this.tagID = value;
        this.flourishes = ImmutableList.copyOf(new Flourish[]{new Flourish(str, value)});
    }


    public static List<LightWeaponFlourish> getCategories() {
        List list;
        list = ImmutableList.of(NOFLOURISH,XCROSS, CIRCULAR,SPIN);

        return list;
    }
    public static LightWeaponFlourish fromTagID(int tagID) {
        for (LightWeaponFlourish flourish : values()) {
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
