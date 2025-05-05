package com.epxzzy.createsaburs.rendering.posehandlers;

import com.google.common.collect.ImmutableList;

import java.util.List;

public enum DualBladedFlourish implements net.minecraftforge.common.IExtensibleEnum {
    SKIPCATCH("skip-catch", 1),
    BEHINDTHEBACK("behind-the-back", 2);

    List<Flourish> flourishes;
    private DualBladedFlourish(String str, int value) {
        this.flourishes = ImmutableList.copyOf(new Flourish[]{new Flourish(str, value)});
    }


    public static List<DualBladedFlourish> getCategories() {
        List list;
        list = ImmutableList.of(SKIPCATCH, BEHINDTHEBACK);

        return list;
    }
    public record Flourish(String name, int TagID){}
}
