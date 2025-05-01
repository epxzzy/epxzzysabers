package com.epxzzy.createsaburs.rendering.posehandlers;

import com.google.common.collect.ImmutableList;

import java.util.List;

public enum SingleBladedFlourish implements net.minecraftforge.common.IExtensibleEnum {
    XCROSS("xcross"),
    CIRCULAR("circular");

    List<String> flourishes;
    private SingleBladedFlourish(String str) {
        this.flourishes = ImmutableList.copyOf(new String[]{str});
    }


    public static List<SingleBladedFlourish> getCategories() {
        List list;
        list = ImmutableList.of(XCROSS, CIRCULAR);

        return list;
    }
}
