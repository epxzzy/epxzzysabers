package com.epxzzy.epxzzysabers.rendering.playerposerenderers;

import com.epxzzy.epxzzysabers.util.AngleHelper;
import com.epxzzy.epxzzysabers.util.AnimationHelper;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;

public class PlayerAttackRenderer {
    public static void setPose(int Attack, boolean Lefty, HumanoidModel<?> model, float lerper) {
        double smooth = AnimationHelper.easeOut(lerper);
        ModelPart arm = Lefty? model.leftArm:model.rightArm;
        arm.resetPose();

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
        ModelPart arm = lefty? model.leftArm:model.rightArm;
        double startRX = 0, startRY = 0, endRX = 0, endRY = 0, endRZ =0, currRX = 0, currRY = 0, currRZ =0;
        float startPX = 0, startPY = 0, endPX = 0, endPY = 0, endPZ =0, currPX = 0, currPY = 0, currPZ =0;

        /*
        rotx = 330 + (45 - 330) * (t.lerper)
        roty = 10 * (t.lerper)
        rotz = -90

        posx = left ? 5 * lerper : -1 * lerper
        posy = left ? -1 : 1
        posz = left ? -1 * lerper : 0
         */
        startRX = 330;
        endRX = 45;
        endRY = -10;

        endRZ = -90;

        if(!lefty) {
            endPY = 1F;
        }

        if(lefty) {
            endPY = 1F;
            endPZ = -1F;
        }

        currRX = endRX + (startRX - endRX) * lerper;
        currRY = endRY * lerper;
        currRZ = endRZ;

        currPX = (float) ((endPX) * lerper);
        currPY = (float) ((endPY) * lerper);
        currPZ = (float) ((endPZ) * lerper);

        arm.setRotation(
                AngleHelper.rad(currRX),
                AngleHelper.rad(currRY),
                AngleHelper.rad(currRZ)
        );
        arm.setPos(
                currPX,
                currPY,
                currPZ
        );

    }

    //right shoulder   2
    public static void SetAttack2(Boolean lefty, HumanoidModel<?> model, double lerper){
        ModelPart arm = lefty? model.leftArm:model.rightArm;
        double startRX = 0, startRY = 0, endRX = 0, endRY = 0, endRZ =0, currRX = 0, currRY = 0, currRZ =0;
        float startPX = 0, startPY = -5F, endPX = 0, endPY = -5F, endPZ =0, currPX = 0, currPY = 0, currPZ =0;

        if(lefty) {
            startRX = 40;
            endRX = 330;
            endPZ = -0.5F;
        }

        if(!lefty) {
            startRX = 35;
            endRX = 330;
        }
        currRX = startRX + (endRX - startRX) * lerper;
        currRY = startRY + (endRY - startRY) * lerper;
        currRZ = 90;

        currPX = (float) (startPX + (endPX - startPX) * lerper);
        currPZ = (float) ((endPZ) * lerper);


        arm.setRotation(
                AngleHelper.rad(currRX),
//                AngleHelper.rad(currY)*(lefty?-1:1),
                AngleHelper.rad(currRY),
                AngleHelper.rad(currRZ)
        );
        arm.setPos(
                currPX,
                currPY,
                currPZ
        );
    }
    //left knee   3
    public static void SetAttack3(Boolean lefty, HumanoidModel<?> model, double lerper){
        ModelPart arm = lefty? model.leftArm:model.rightArm;
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

        arm.setRotation(
                AngleHelper.rad(currX),
                //AngleHelper.rad(currY)*(lefty?-1:1),
                AngleHelper.rad(currY),
                AngleHelper.rad(-90)

        );
    }
    //right knee   4
    public static void SetAttack4(Boolean lefty, HumanoidModel<?> model, double lerper){
        ModelPart arm = lefty? model.leftArm:model.rightArm;
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


        arm.setRotation(
                AngleHelper.rad(currX),
                //AngleHelper.rad(currY)*(lefty?-1:1),
                AngleHelper.rad(currY),
                AngleHelper.rad(currZ)

        );
    }
    //left abdomen   5
    public static void SetAttack5(Boolean lefty, HumanoidModel<?> model, double lerper){
        ModelPart arm = lefty? model.leftArm:model.rightArm;
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

        arm.setRotation(
                AngleHelper.rad(currX),
                //AngleHelper.rad(currY)*(lefty?-1:1),
                AngleHelper.rad(currY),
                AngleHelper.rad(-90)

        );

    }
    //right abdomen   6
    public static void SetAttack6(Boolean lefty, HumanoidModel<?> model, double lerper){
        ModelPart arm = lefty? model.leftArm:model.rightArm;
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


        arm.setRotation(
                AngleHelper.rad(currX),
                //AngleHelper.rad(currY)*(lefty?-1:1),
                AngleHelper.rad(currY),
                AngleHelper.rad(currZ)

        );
    }
    //balls  7
    public static void SetAttack7(Boolean lefty, HumanoidModel<?> model, double lerper){
        ModelPart arm = lefty? model.leftArm:model.rightArm;
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

        arm.setRotation(
                AngleHelper.rad(currX),
                //AngleHelper.rad(currY)*(lefty?-1:1),
                AngleHelper.rad(currY),
                AngleHelper.rad(-180)
        );
    }
    //head   8
    public static void SetAttack8(Boolean lefty, HumanoidModel<?> model, double lerper){
        ModelPart arm = lefty? model.leftArm:model.rightArm;
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

        arm.setRotation(
                AngleHelper.rad(currX),
                //AngleHelper.rad(currY)*(lefty?-1:1),
                AngleHelper.rad(currY),
                AngleHelper.rad(0)

        );
    }


}
