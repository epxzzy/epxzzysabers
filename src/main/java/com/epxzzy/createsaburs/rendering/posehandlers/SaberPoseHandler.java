package com.epxzzy.createsaburs.rendering.posehandlers;

import net.createmod.catnip.math.AngleHelper;
import net.minecraft.client.model.HumanoidModel;

public class SaberPoseHandler {
    public static void setPose(BladeStance stance, boolean Lefty, HumanoidModel<?> model) {
        switch (stance) {
            case FORM1 -> SetStanceForm1(Lefty, model);
            //shii-cho
            case FORM2 -> SetStanceForm2(Lefty, model);
            //makashi
            case FORM3 -> SetStanceForm3(Lefty, model);
            //soresu
            case FORM4 -> SetStanceForm4(Lefty, model);
            //ataru
            case FORM5 -> SetStanceForm5(Lefty, model);
            //shien
            case FORM6 -> SetStanceForm6(Lefty, model);
            //niman
            case FORM7 -> SetStanceForm7(Lefty, model);
            //juyo
        }
    }

    public static void SetStanceForm1(Boolean lefty, HumanoidModel<?> model) {
        model.head.setPos(0,2,-3);

        model.body.setPos(0, 2, -2);
        model.body.setRotation(AngleHelper.rad(16), AngleHelper.rad(15), AngleHelper.rad(1));

        model.rightArm.setPos(-4, 4, -1);
        model.rightArm.setRotation(AngleHelper.rad(-48), AngleHelper.rad(-24), AngleHelper.rad(-18));

        model.leftArm.setPos(4, 4, -3);
        model.leftArm.setRotation(AngleHelper.rad(-8), AngleHelper.rad(46), AngleHelper.rad(37));

        model.rightLeg.setPos(-3, 14, 1);
        model.rightLeg.setRotation(AngleHelper.rad(41), AngleHelper.rad(16), AngleHelper.rad(7));

        model.leftLeg.setPos(3, 12, -1);
        model.leftLeg.setRotation(AngleHelper.rad(-23), (float) AngleHelper.rad(17), 0);
    }

    public static void SetStanceForm2(Boolean lefty, HumanoidModel<?> model) {

    }

    public static void SetStanceForm3(Boolean lefty, HumanoidModel<?> model) {

    }

    public static void SetStanceForm4(Boolean lefty, HumanoidModel<?> model) {

    }

    public static void SetStanceForm5(Boolean lefty, HumanoidModel<?> model) {

    }

    public static void SetStanceForm6(Boolean lefty, HumanoidModel<?> model) {
        //model.head.setPos(1,-1,0);
        model.head.x = 1;
        model.head.y = 0;

        model.body.setPos(0, -1, 0);
        model.body.setRotation(AngleHelper.rad(5), AngleHelper.rad(-57.6), 0);

        model.rightArm.setPos(-2.5f, 1, -6.5f);
        model.rightArm.setRotation(AngleHelper.rad(-90), 0, AngleHelper.rad(90));

        model.leftArm.setPos(4.5f, 3, 6);
        model.leftArm.setRotation(AngleHelper.rad(-72.5), AngleHelper.rad(-10), 0);

        //model.rightLeg.setPos((float) -1.5,0, (float) -3.5);
        model.rightLeg.x = (float) -1.5;
        model.rightLeg.y = 11;
        model.rightLeg.z = (float) -3.5;
        model.rightLeg.setRotation(AngleHelper.rad(-10), AngleHelper.rad(5), 0);

        model.leftLeg.z = 3;
        model.leftLeg.y = 11;
        model.leftLeg.setRotation(AngleHelper.rad(-2), (float) AngleHelper.rad(-67.5), AngleHelper.rad(-10));
    }

    public static void SetStanceForm7(Boolean lefty, HumanoidModel<?> model) {

    }


}
