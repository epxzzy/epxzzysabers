package com.epxzzy.epxzzysabers.rendering.poseHandlers;

import com.epxzzy.epxzzysabers.epxzzySabers;
import com.epxzzy.epxzzysabers.utils.AngleHelper;
import net.minecraft.client.model.HumanoidModel;

public class PlayerBlockRenderer {
    public static void setPose(int Block, boolean Lefty, HumanoidModel<?> model, double lerpusy) {
        double lerper = 1 - Math.pow(1 - lerpusy, 5); //ease-out
        epxzzySabers.LOGGER.debug("now animating block {}", Block);
        switch (Block) {
            //left shoulder
            case 1 -> SetBlock1(Lefty, model, lerper);
            //right shoulder
            case 2 -> SetBlock2(Lefty, model, lerper);
            //left knee
            case 3 -> SetBlock3(Lefty, model, lerper);
            //right knee
            case 4 -> SetBlock4(Lefty, model, lerper);
            //left abdomen
            case 5 -> SetBlock5(Lefty, model, lerper);
            //right abdomen
            case 6 -> SetBlock6(Lefty, model, lerper);
            //balls
            case 7 -> SetBlock7(Lefty, model, lerper);
            //head
            case 8 -> SetBlock8(Lefty, model, lerper);

        }
    }

    //left shoulder
    public static void SetBlock1(Boolean lefty, HumanoidModel<?> model, double lerper) {
        double endX = 0, endY = 0, endZ = 0;

        if (lefty) {
            endX = 90;
            endY = 50;
            endZ = 0;
        }

        if (!lefty) {
            endX = -76;
            endY = -50;
            endZ = 18;
        }

        ShieldPositionToPos(endX, endY, endZ, lefty, lerper, model);
        //model.rightArm.setRotation(AngleHelper.rad(-76), AngleHelper.rad(-50), AngleHelper.rad(18));
    }

    //right shoulder
    public static void SetBlock2(Boolean lefty, HumanoidModel<?> model, double lerper) {
        double endX = 0, endY = 0, endZ = 0;

        if (lefty) {
            endX = 90;
            endY = 50;
            endZ = 0;
        }

        if (!lefty) {
            endX = -54;
            endY = -20;
            endZ = -32;
        }

        ShieldPositionToPos(endX, endY, endZ, lefty, lerper, model);
        //model.rightArm.setRotation(AngleHelper.rad(-54), AngleHelper.rad(-20), AngleHelper.rad(-32));
    }

    //left knee
    public static void SetBlock3(Boolean lefty, HumanoidModel<?> model, double lerper) {
        double endX = 0, endY = 0, endZ = 0;

        if (lefty) {
            endX = 90;
            endY = 50;
            endZ = 0;
        }

        if (!lefty) {
            endX = -70;
            endY = -52;
            endZ = 90;
        }

        ShieldPositionToPos(endX, endY, endZ, lefty, lerper, model);
        //model.rightArm.setRotation(AngleHelper.rad(-70), AngleHelper.rad(-52), AngleHelper.rad(90));
    }

    //right knee
    public static void SetBlock4(Boolean lefty, HumanoidModel<?> model, double lerper) {
        double endX = 0, endY = 0, endZ = 0;

        if (lefty) {
            endX = 90;
            endY = 50;
            endZ = 0;
        }

        if (!lefty) {
            endX = -42;
            endY = 50;
            endZ = -80;
        }

        ShieldPositionToPos(endX, endY, endZ, lefty, lerper, model);
        //model.rightArm.setRotation(AngleHelper.rad(-42), AngleHelper.rad(50), AngleHelper.rad(-80));
    }

    //left abdomen
    public static void SetBlock5(Boolean lefty, HumanoidModel<?> model, double lerper) {
        double endX = 0, endY = 0, endZ = 0;

        if (lefty) {
            endX = 90;
            endY = 50;
            endZ = 0;
        }

        if (!lefty) {
            endX = -100;
            endY = -65;
            endZ = 64;
        }

        ShieldPositionToPos(endX, endY, endZ, lefty, lerper, model);
        //model.rightArm.setRotation(AngleHelper.rad(-100), AngleHelper.rad(-65), AngleHelper.rad(64));
    }

    //right abdomen
    public static void SetBlock6(Boolean lefty, HumanoidModel<?> model, double lerper) {
        double endX = 0, endY = 0, endZ = 0;

        if (lefty) {
            endX = 90;
            endY = 50;
            endZ = 0;
        }

        if (!lefty) {
            endX = -65;
            endY = -22;
            endZ = -172;
        }

        ShieldPositionToPos(endX, endY, endZ, lefty, lerper, model);
        //model.rightArm.setRotation(AngleHelper.rad(-65), AngleHelper.rad(-22), AngleHelper.rad(-172));
    }

    //balls
    public static void SetBlock7(Boolean lefty, HumanoidModel<?> model, double lerper) {
        double endX = 0, endY = 0, endZ = 0;

        if (lefty) {
            endX = 90;
            endY = 50;
            endZ = 0;
        }

        if (!lefty) {
            endX = -70;
            endY = -65;
            endZ = 85;
        }

        ShieldPositionToPos(endX, endY, endZ, lefty, lerper, model);
        //model.rightArm.setRotation(AngleHelper.rad(-70), AngleHelper.rad(-65), AngleHelper.rad(85));
    }

    //head
    public static void SetBlock8(Boolean lefty, HumanoidModel<?> model, double lerper) {
        double endX = 0, endY = 0, endZ = 0;

        if (lefty) {
            endX = 90;
            endY = 50;
            endZ = 0;
        }

        if (!lefty) {
            endX = -80;
            endY = 42;
            endZ = 85;
        }

        ShieldPositionToPos(endX, endY, endZ, lefty, lerper, model);
        //model.rightArm.setRotation(AngleHelper.rad(-80), AngleHelper.rad(42.5), AngleHelper.rad(85));
    }


    public static void ShieldPositionToPos(double x, double y, double z, boolean lefty, double lerper, HumanoidModel<?> model) {
        double startX = 1, startY = 1, startZ = 1,
                currX = 0, currY = 0, currZ = 0;
        if (lefty) {
            startX = model.leftArm.xRot * 0.5F - 0.9424779F;
            startY = (-(float)Math.PI / 6F);

        }


        if (!lefty) {
            /*

            startX = model.rightArm.xRot;
            startY = model.rightArm.yRot;
            startZ = model.rightArm.zRot;

            */
        }

        currX = startX + (x - startX) * lerper;
        currY = startY + (y - startY) * lerper;
        currZ = startZ + (z - startZ) * lerper;

        epxzzySabers.LOGGER.debug("arm x y z {} {} {}", currX, currY, currZ);

        if (!lefty) {
            model.rightArm.setRotation(
                    AngleHelper.rad(currX),
                    AngleHelper.rad(currY),
                    AngleHelper.rad(currZ)

            );
        }
        if (lefty) {
            model.leftArm.setRotation(
                    AngleHelper.rad(currX),
                    AngleHelper.rad(currY),
                    AngleHelper.rad(currZ)
            );
        }
    }
}
