package com.epxzzy.createsaburs.rendering.poseHandlers;

import com.epxzzy.createsaburs.CreateSaburs;
import com.epxzzy.createsaburs.utils.AngleHelper;
import net.minecraft.client.model.HumanoidModel;

public class PlayerAttackRenderer {
    public static void setPose(int Attack, boolean Lefty, HumanoidModel<?> model, float lerper) {
        switch (Attack) {
            //left shoulder
            case 1 -> SetAttack1(Lefty, model);
            //right shoulder
            case 2 -> SetAttack2(Lefty, model);
            //left knee
            case 3 -> SetAttack3(Lefty, model);
            //right knee
            case 4 -> SetAttack4(Lefty, model);
            //left abdomen
            case 5 -> SetAttack5(Lefty, model);
            //right abdomen
            case 6 -> SetAttack6(Lefty, model);
            //balls
            case 7 -> SetAttack7(Lefty, model);
            //head
            case 8 -> SetAttack8(Lefty, model);

        }
    }
    //left shoulder   1
    public static void SetAttack1(Boolean lefty, HumanoidModel<?> model) {
        model.rightArm.setRotation(AngleHelper.rad(-50), AngleHelper.rad(-50), AngleHelper.rad(0));
    }

    //right shoulder   2
    public static void SetAttack2(Boolean lefty, HumanoidModel<?> model) {
        model.rightArm.setRotation(AngleHelper.rad(-50), AngleHelper.rad(-10), AngleHelper.rad(0));
    }
    //left knee   3
    public static void SetAttack3(Boolean lefty, HumanoidModel<?> model) {
        model.rightArm.setRotation(AngleHelper.rad(50), AngleHelper.rad(-50), AngleHelper.rad(0));
    }
    //right knee   4
    public static void SetAttack4(Boolean lefty, HumanoidModel<?> model) {
        model.rightArm.setRotation(AngleHelper.rad(50), AngleHelper.rad(-10), AngleHelper.rad(0));
    }
    //left abdomen   5
    public static void SetAttack5(Boolean lefty, HumanoidModel<?> model) {
        model.rightArm.setRotation(AngleHelper.rad(10), AngleHelper.rad(-60), AngleHelper.rad(0));
    }
    //right abdomen   6
    public static void SetAttack6(Boolean lefty, HumanoidModel<?> model) {
        model.rightArm.setRotation(AngleHelper.rad(0), AngleHelper.rad(-5), AngleHelper.rad(0));
    }
    //balls  7
    public static void SetAttack7(Boolean lefty, HumanoidModel<?> model) {
        model.rightArm.setRotation(AngleHelper.rad(59), AngleHelper.rad(-40), AngleHelper.rad(0));
    }
    //head   8
    public static void SetAttack8(Boolean lefty, HumanoidModel<?> model) {
        model.rightArm.setRotation(AngleHelper.rad(-50), AngleHelper.rad(-40), AngleHelper.rad(0));
    }


}
