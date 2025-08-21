package com.epxzzy.epxzzysabers.rendering.playerposerenderers;

import com.google.common.collect.ImmutableList;

import java.util.List;

public enum BladeStance {
    FORM0(0,"nada "),
    FORM1(1,"shii-cho"),
    // kit fisto, luke?
    FORM2(2,"makashi"),
    // darth tyranus, grand inquisitor
    FORM3(3,"soresu"),
    // hello there
    FORM4(4,"ataru"),
    // yoda, qui gon jinn
    FORM5(5,"shien","djem-so"),
    // skywanker
    FORM6(6,"niman"),
    // maul
    FORM7(7,"juyo","vaapad");
    // sidious, windu

    List<String> stances;
    private BladeStance(int order, String... str) {
        this.stances= ImmutableList.copyOf(str);
    }


    public static List<BladeStance> getStances() {
        List list;
        list = ImmutableList.of(FORM1,FORM2,FORM3,FORM4,FORM5,FORM6,FORM7);

        return list;
    }
}
