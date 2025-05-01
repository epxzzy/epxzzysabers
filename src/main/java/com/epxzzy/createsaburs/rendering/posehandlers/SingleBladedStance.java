package com.epxzzy.createsaburs.rendering.posehandlers;

import com.google.common.collect.ImmutableList;

import java.util.List;

public enum SingleBladedStance implements net.minecraftforge.common.IExtensibleEnum {
    FORM1("shii-cho"),
    // kit fisto, luke?
    FORM2("makashi"),
    // darth tyranus, grand inquisitor
    FORM3("soresu"),
    // hello there
    FORM4("ataru"),
    // yoda, qui gon jinn
    FORM5("shien","djem-so"),
    // skywanker
    FORM6("niman"),
    // maul
    FORM7("juyo","vaapad");
    // sidious, windu

    List<String> flourishes;
    private SingleBladedStance(String... str) {
        this.flourishes = ImmutableList.copyOf(str);
    }


    public static List<SingleBladedStance> getStances() {
        List list;
        list = ImmutableList.of(FORM1,FORM2,FORM3,FORM4,FORM5,FORM6,FORM7);

        return list;
    }
}
