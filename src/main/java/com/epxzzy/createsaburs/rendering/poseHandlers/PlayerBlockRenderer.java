package com.epxzzy.createsaburs.rendering.poseHandlers;

import com.epxzzy.createsaburs.utils.AngleHelper;
import net.minecraft.client.model.HumanoidModel;

public class PlayerBlockRenderer {
    public static void setPose(int Block, boolean Lefty, HumanoidModel<?> model) {
        switch (Block) {
            //left shoulder
            case 1 -> SetBlock1(Lefty, model);
            //right shoulder
            case 2 -> SetBlock2(Lefty, model);
            //left knee
            case 3 -> SetBlock3(Lefty, model);
            //right knee
            case 4 -> SetBlock4(Lefty, model);
            //left abdomen
            case 5 -> SetBlock5(Lefty, model);
            //right abdomen
            case 6 -> SetBlock6(Lefty, model);
            //balls
            case 7 -> SetBlock7(Lefty, model);
            //head
            case 8 -> SetBlock8(Lefty, model);

        }
    }

    //left shoulder
    public static void SetBlock1(Boolean lefty, HumanoidModel<?> model) {
        model.rightArm.setRotation(AngleHelper.rad(-76), AngleHelper.rad(-50), AngleHelper.rad(18));

    }
    //right shoulder
    public static void SetBlock2(Boolean lefty, HumanoidModel<?> model) {
        model.rightArm.setRotation(AngleHelper.rad(-54), AngleHelper.rad(-20), AngleHelper.rad(-32));
    }

    //left knee
    public static void SetBlock3(Boolean lefty, HumanoidModel<?> model) {
        model.rightArm.setRotation(AngleHelper.rad(-70), AngleHelper.rad(-52), AngleHelper.rad(90));
    }
    //right knee
    public static void SetBlock4(Boolean lefty, HumanoidModel<?> model) {
        model.rightArm.setRotation(AngleHelper.rad(-42), AngleHelper.rad(50), AngleHelper.rad(-80));
    }

    //left abdomen
    public static void SetBlock5(Boolean lefty, HumanoidModel<?> model) {
        model.rightArm.setRotation(AngleHelper.rad(-100), AngleHelper.rad(-65), AngleHelper.rad(0));
    }
    //right abdomen
    public static void SetBlock6(Boolean lefty, HumanoidModel<?> model) {
        model.rightArm.setRotation(AngleHelper.rad(22), AngleHelper.rad(-80), AngleHelper.rad(0));
    }

    //balls
    public static void SetBlock7(Boolean lefty, HumanoidModel<?> model) {
        model.rightArm.setRotation(AngleHelper.rad(-90), AngleHelper.rad(0), AngleHelper.rad(0));
    }
    //head
    public static void SetBlock8(Boolean lefty, HumanoidModel<?> model) {
        model.rightArm.setRotation(AngleHelper.rad(90), AngleHelper.rad(0), AngleHelper.rad(0));
    }


}
