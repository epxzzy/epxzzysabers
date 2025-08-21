package com.epxzzy.epxzzysabers.rendering.poseRenderer.HeavyWeapon;

import com.google.common.collect.ImmutableList;

import java.util.List;

public enum HeavyWeaponFlourish {
    NOFLOURISH("none", 0),
    FIGUREEIGHT("figure-eight", 1),
    BEHINDTHEBACK("behind-the-back", 2),
    SKIPCATCH("skip-catch", 3);



    private final String str;
    private final int tagID;
    List<Flourish> flourishes;
    private HeavyWeaponFlourish(String str, int value) {
        this.str = str;
        this.tagID = value;


        this.flourishes = ImmutableList.copyOf(new Flourish[]{new Flourish(str, value)});
    }


    public static List<HeavyWeaponFlourish> getCategories() {
        List list;
        list = ImmutableList.of(NOFLOURISH, SKIPCATCH, BEHINDTHEBACK, FIGUREEIGHT);

        return list;
    }

    public static HeavyWeaponFlourish fromTagID(int tagID) {
        for (HeavyWeaponFlourish flourish : values()) {
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
