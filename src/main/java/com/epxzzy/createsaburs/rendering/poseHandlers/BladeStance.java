package com.epxzzy.createsaburs.rendering.poseHandlers;

import com.google.common.collect.ImmutableList;

import java.util.List;

public enum BladeStance {
    FORM0("nada "),
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
    private BladeStance(String... str) {
        this.flourishes = ImmutableList.copyOf(str);
    }


    public static List<BladeStance> getStances() {
        List list;
        list = ImmutableList.of(FORM1,FORM2,FORM3,FORM4,FORM5,FORM6,FORM7);

        return list;
    }
}
