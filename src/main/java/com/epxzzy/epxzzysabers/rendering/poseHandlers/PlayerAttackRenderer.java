package com.epxzzy.epxzzysabers.rendering.poseHandlers;

import com.epxzzy.epxzzysabers.epxzzySabers;
import com.epxzzy.epxzzysabers.utils.AngleHelper;
import com.epxzzy.epxzzysabers.utils.ScrollValueHandler;
import net.minecraft.client.model.HumanoidModel;

public class PlayerAttackRenderer {
    public static void setPose(int Attack, boolean Lefty, HumanoidModel<?> model, float lerper) {
        double smooth = 1 - Math.pow(1 - lerper, 5); // ease-out

        switch (Attack) {
            //left shoulder
            case 1 -> SetAttack1(Lefty, model, smooth);
            //right shoulder
            case 2 -> SetAttack2(Lefty, model, smooth);
            //left knee
            case 3 -> SetAttack3(Lefty, model, smooth);
            //right knee
            case 4 -> SetAttack4(Lefty, model, smooth);
            //left abdomen
            case 5 -> SetAttack5(Lefty, model, smooth);
            //right abdomen
            case 6 -> SetAttack6(Lefty, model, smooth);
            //balls
            case 7 -> SetAttack7(Lefty, model, smooth);
            //head
            case 8 -> SetAttack8(Lefty, model, smooth);

        }
    }
    //left shoulder   1
    public static void SetAttack1(Boolean lefty, HumanoidModel<?> model, double lerper){
        double startX = 0,startY = 0,endX = 0,endY = 0, endZ =0, currX = 0, currY = 0, currZ =0;
        if(lefty) {
            startX = 45;
            startY = 30;
            endX = 90;
            endY = 50;
        }



        if(!lefty) {
            startX = -287;
            startY = -15;
            endX = -40;
            endY = -15;
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
    public static void SetAttack2(Boolean lefty, HumanoidModel<?> model, double lerper){
        double startX = 0,startY = 0,endX = 0,endY = 0, endZ =0, currX = 0, currY = 0, currZ =0;
        if(lefty) {
            startX = 45;
            startY = 30;
            endX = 90;
            endY = 50;
        }

        if(!lefty) {
            startX = -330;
            startY = 15;
            endX = -115;
            endY = 15;
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
    public static void SetAttack3(Boolean lefty, HumanoidModel<?> model, double lerper){
        double startX = 0,startY = 0,endX = 0,endY = 0, endZ =0, currX = 0, currY = 0, currZ =0;
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
    public static void SetAttack4(Boolean lefty, HumanoidModel<?> model, double lerper){
        double startX = 0,startY = 0,endX = 0,endY = 0, endZ =0, currX = 0, currY = 0, currZ =0;
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
    public static void SetAttack5(Boolean lefty, HumanoidModel<?> model, double lerper){
        double startX = 0,startY = 0,endX = 0,endY = 0, endZ =0, currX = 0, currY = 0, currZ =0;
        if(lefty) {
            startX = 45;
            startY = 30;
            endX = 90;
            endY = 50;
        }



        if(!lefty) {
            startX = -287;
            startY = 15; endX = -40;
            endY = 15;
        }

        currX = startX + (endX - startX) * lerper;
        currY = startY + (endY - startY) * lerper;

        model.rightArm.setRotation(
                AngleHelper.rad(currX),
                AngleHelper.rad(currY),
                AngleHelper.rad(-90)

        );

    }
    //right abdomen   6
    public static void SetAttack6(Boolean lefty, HumanoidModel<?> model, double lerper){
        double startX = 0,startY = 0,endX = 0,endY = 0, endZ =0, currX = 0, currY = 0, currZ =0;
        if(lefty) {
            startX = 45;
            startY = 30;
            endX = 90;
            endY = 50;
        }

        if(!lefty) {
            startX = -330;
            startY = -15;
            endX = -115;
            endY = -15;
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
    //balls  7
    public static void SetAttack7(Boolean lefty, HumanoidModel<?> model, double lerper){
        double startX = 0,startY = 0,endX = 0,endY = 0, endZ =0, currX = 0, currY = 0, currZ =0;
        if(lefty) {
            startX = 45;
            startY = 30;
            endX = 90;
            endY = 50;
        }



        if(!lefty) {
            startX = -280;
            startY = 30;
            endX = -95;
            endY = 30;
        }

        currX = startX + (endX - startX) * lerper;
        currY = startY + (endY - startY) * lerper;

        model.rightArm.setRotation(
                AngleHelper.rad(currX),
                AngleHelper.rad(currY),
                AngleHelper.rad(-180)

        );
    }
    //head   8
    public static void SetAttack8(Boolean lefty, HumanoidModel<?> model, double lerper){
        double startX = 0,startY = 0,endX = 0,endY = 0, endZ =0, currX = 0, currY = 0, currZ =0;
        if(lefty) {
            startX = 45;
            startY = 30;
            endX = 90;
            endY = 50;
        }



        if(!lefty) {
            startX = -260;
            startY = -30;
            endX = -75;
            endY = -30;
        }

        currX = startX + (endX - startX) * lerper;
        currY = startY + (endY - startY) * lerper;

        model.rightArm.setRotation(
                AngleHelper.rad(currX),
                AngleHelper.rad(currY),
                AngleHelper.rad(0)

        );
    }


}
