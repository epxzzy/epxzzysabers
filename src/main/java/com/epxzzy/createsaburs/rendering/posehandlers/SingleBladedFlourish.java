package com.epxzzy.createsaburs.rendering.posehandlers;

import com.google.common.collect.ImmutableList;

import java.util.List;

public enum SingleBladedFlourish implements net.minecraftforge.common.IExtensibleEnum {
    XCROSS("xcross", 1),
    CIRCULAR("circular", 2);

    List<Flourish> flourishes;
    private SingleBladedFlourish(String str, int value) {
        this.flourishes = ImmutableList.copyOf(new Flourish[]{new Flourish(str, value)});
    }


    public static List<SingleBladedFlourish> getCategories() {
        List list;
        list = ImmutableList.of(XCROSS, CIRCULAR);

        return list;
    }
    public record Flourish(String name, int TagID){}
}
