package com.epxzzy.createsaburs.rendering.poseHandlers;

import com.epxzzy.createsaburs.CreateSaburs;
import com.epxzzy.createsaburs.utils.AngleHelper;
import net.minecraft.client.model.HumanoidModel;

public class PlayerAttackRenderer {
    public static void setPose(int Attack, boolean Lefty, HumanoidModel<?> model, float lerper) {
        switch (Attack) {
            //left shoulder
            case 1 -> SetAttack1(Lefty, model, lerper);
            //right shoulder
            case 2 -> SetAttack2(Lefty, model, lerper);
            //left knee
            case 3 -> SetAttack3(Lefty, model, lerper);
            //right knee
            case 4 -> SetAttack4(Lefty, model, lerper);
            //left abdomen
            case 5 -> SetAttack5(Lefty, model, lerper);
            //right abdomen
            case 6 -> SetAttack6(Lefty, model, lerper);
            //balls
            case 7 -> SetAttack7(Lefty, model, lerper);
            //head
            case 8 -> SetAttack8(Lefty, model, lerper);

        }
    }
    //left shoulder   1
    public static void SetAttack1(Boolean lefty, HumanoidModel<?> model, float lerper){
        float startX = 0,startY = 0,endX = 0,endY = 0, endZ =0, currX = 0, currY = 0, currZ =0;
        if(lefty) {
            startX = 45;
            startY = 30;
            endX = 90;
            endY = 50;
        }




        if(!lefty) {
            startX = -287;
            startY = 0;
            endX = -40;
            endY = 0;
        }

        currX = startX + (endX - startX) * lerper;
        currY = startY + (endY - startY) * lerper;

        model.rightArm.setRotation(
                AngleHelper.rad(currX),
                AngleHelper.rad(currY),
                AngleHelper.rad(-90)

        );
    }

    //right shoulder   2
    public static void SetAttack2(Boolean lefty, HumanoidModel<?> model, float lerper){
        float startX = 0,startY = 0,endX = 0,endY = 0, endZ =0, currX = 0, currY = 0, currZ =0;
        if(lefty) {
            startX = 45;
            startY = 30;
            endX = 90;
            endY = 50;
        }

        if(!lefty) {
            startX = -330;
            startY = 0;
            endX = -115;
            endY = 0;
            endZ = 90;
        }

        currX = startX + (endX - startX) * lerper;
        currY = startY + (endY - startY) * lerper;
        currZ = 80 + (endZ - 80) * lerper;


        model.rightArm.setRotation(
                AngleHelper.rad(currX),
                AngleHelper.rad(currY),
                AngleHelper.rad(currZ)

        );
    }
    //left knee   3
    public static void SetAttack3(Boolean lefty, HumanoidModel<?> model, float lerper){
        float startX = 0,startY = 0,endX = 0,endY = 0, endZ =0, currX = 0, currY = 0, currZ =0;
        if(lefty) {
            startX = 45;
            startY = 30;
            endX = 90;
            endY = 50;
        }




        if(!lefty) {
            startX = -260;
            startY = 42;
            endX = -40;
            endY = 42;
        }

        currX = startX + (endX - startX) * lerper;
        currY = startY + (endY - startY) * lerper;

        model.rightArm.setRotation(
                AngleHelper.rad(currX),
                AngleHelper.rad(currY),
                AngleHelper.rad(-90)

        );
    }
    //right knee   4
    public static void SetAttack4(Boolean lefty, HumanoidModel<?> model, float lerper){
        float startX = 0,startY = 0,endX = 0,endY = 0, endZ =0, currX = 0, currY = 0, currZ =0;
        if(lefty) {
            startX = 45;
            startY = 34;
            endX = 90;
            endY = 50;
        }

        if(!lefty) {
            startX = -230;
            startY = -50;
            endX = -45;
            endY = -50;
            endZ = 90;
        }

        currX = startX + (endX - startX) * lerper;
        currY = startY + (endY - startY) * lerper;
        currZ = 80 + (endZ - 80) * lerper;


        model.rightArm.setRotation(
                AngleHelper.rad(currX),
                AngleHelper.rad(currY),
                AngleHelper.rad(currZ)

        );
    }
    //left abdomen   5
    public static void SetAttack5(Boolean lefty, HumanoidModel<?> model, float lerper){
        model.rightArm.setRotation(AngleHelper.rad(10), AngleHelper.rad(-60), AngleHelper.rad(0));
    }
    //right abdomen   6
    public static void SetAttack6(Boolean lefty, HumanoidModel<?> model, float lerper){
        model.rightArm.setRotation(AngleHelper.rad(0), AngleHelper.rad(-5), AngleHelper.rad(0));
    }
    //balls  7
    public static void SetAttack7(Boolean lefty, HumanoidModel<?> model, float lerper){
        model.rightArm.setRotation(AngleHelper.rad(59), AngleHelper.rad(-40), AngleHelper.rad(0));
    }
    //head   8
    public static void SetAttack8(Boolean lefty, HumanoidModel<?> model, float lerper){
        model.rightArm.setRotation(AngleHelper.rad(-50), AngleHelper.rad(-40), AngleHelper.rad(0));
    }


}
