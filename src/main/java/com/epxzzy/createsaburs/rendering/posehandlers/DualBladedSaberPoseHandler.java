package com.epxzzy.createsaburs.rendering.posehandlers;

import net.createmod.catnip.math.AngleHelper;
import net.minecraft.client.model.HumanoidModel;

public class DualBladedSaberPoseHandler {
    public static void setPose(BladeStance stance, boolean Lefty, HumanoidModel<?> model) {
        switch (stance) {
            case FORM1 -> SetStanceForm1(Lefty, model);
            case FORM2 -> SetStanceForm2(Lefty, model);
            case FORM3 -> SetStanceForm3(Lefty, model);
            case FORM4 -> SetStanceForm4(Lefty, model);
            case FORM5 -> SetStanceForm5(Lefty, model);
            case FORM6 -> SetStanceForm6(Lefty, model);
            case FORM7 -> SetStanceForm7(Lefty, model);
        }
    }

    public static void SetStanceForm1(Boolean lefty, HumanoidModel<?> model) {

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
