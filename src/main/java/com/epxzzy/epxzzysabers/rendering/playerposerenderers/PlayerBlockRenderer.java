package com.epxzzy.epxzzysabers.rendering.playerposerenderers;

import com.epxzzy.epxzzysabers.util.AngleHelper;
import com.epxzzy.epxzzysabers.util.AnimationHelper;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;

public class PlayerBlockRenderer {
    public static void setPose(int Block, boolean Lefty, HumanoidModel<?> model, double lerpusy) {
        double lerper = 1;//AnimationHelper.easeOutBack(lerpusy);

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
        ModelPart arm = lefty? model.leftArm:model.rightArm;
        double currRX = 0, currRY = 0, currRZ =0;
        float currPX = 0, currPY = 0, currPZ =0;

        /*
                  "rightrotx": "60",
                  "rightroty": "56",
                  "rightrotz": "-20"
                  "rightposx": "-2",
                  "rightposy": "0",
                  "rightposz": "-3"
                  "leftrotx": "72",
                  "leftroty": "-1",
                  "leftrotz": "6"
                  "leftposx": "0",
                  "leftposy": "0",
                  "leftposz": "0"
         */
        currRX = (lefty?72:60) * lerper;
        currRY = (lefty?-1:56) * lerper;
        currRZ = (lefty?6:-20) * lerper;

        currPX = (lefty?arm.x:-2);
        currPY = arm.y;
        currPZ = (lefty?arm.z:-3);

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

    //right shoulder
    public static void SetBlock2(Boolean lefty, HumanoidModel<?> model, double lerper) {
        ModelPart arm = lefty? model.leftArm:model.rightArm;
        double currRX = 0, currRY = 0, currRZ =0;
        float currPX = 0, currPY = 0, currPZ =0;

        /*
                  "rightrotx": "72",
                  "rightroty": "-1",
                  "rightrotz": "-6"
                  "rightposx": "0",
                  "rightposy": "0",
                  "rightposz": "0"
                  "leftrotx": "60",
                  "leftroty": "-56",
                  "leftrotz": "20"
                  "leftrotx": "2",
                  "leftroty": "0",
                  "leftrotz": "-3"
         */
        currRX = (!lefty?72:60) * lerper;
        currRY = (!lefty?-1:-56) * lerper;
        currRZ = (!lefty?-6:20) * lerper;

        currPX = (!lefty?arm.x:2);
        currPY = arm.y;
        currPZ = (!lefty?arm.z:-3);

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

    //left knee
    public static void SetBlock3(Boolean lefty, HumanoidModel<?> model, double lerper) {
        ModelPart arm = lefty? model.leftArm:model.rightArm;
        double currRX = 0, currRY = 0, currRZ =0;
        float currPX = 0, currPY = 0, currPZ =0;

        /*
                  "rightrotx": "94",
                  "rightroty": "-56",
                  "rightrotz": "-190"
                  "rightposx": "-2",
                  "rightposy": "0",
                  "rightposz": "-3"
                  "leftrotx": "100",
                  "leftroty": "-1",
                  "leftrotz": "174"
                  "leftposx": "0",
                  "leftposy": "0",
                  "leftposz": "0"
         */

        currRX = (94) * lerper;
        currRY = (lefty?-1:-56) * lerper;
        currRZ = (-190) * lerper;

        currPX = (!lefty?arm.x:-2);
        currPY = arm.y;
        currPZ = (lefty?arm.z:-3);

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

    //right knee
    public static void SetBlock4(Boolean lefty, HumanoidModel<?> model, double lerper) {
        ModelPart arm = lefty? model.leftArm:model.rightArm;
        double currRX = 0, currRY = 0, currRZ =0;
        float currPX = 0, currPY = 0, currPZ =0;

        /*
                  "rightrotx": "94",
                  "rightroty": "-1",
                  "rightrotz": "190"
                  "rightposx": "0",
                  "rightposy": "0",
                  "rightposz": "0"
                  "leftrotx": "94",
                  "leftroty": "56",
                  "leftrotz": "190"
                  "leftposx": "2",
                  "leftposy": "0",
                  "leftposz": "-3"
         */
        currRX = (94) * lerper;
        currRY = (!lefty?-1:-56) * lerper;
        currRZ = (190) * lerper;

        currPX = (!lefty?arm.x:2);
        currPY = arm.y;
        currPZ = (!lefty?arm.z:-3);

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

    //left abdomen
    public static void SetBlock5(Boolean lefty, HumanoidModel<?> model, double lerper) {
        ModelPart arm = lefty? model.leftArm:model.rightArm;
        double currRX = 0, currRY = 0, currRZ =0;
        float currPX = 0, currPY = 0, currPZ =0;

        /*
                  "rightrotx": "70",
                  "rightroty": "-56",
                  "rightrotz": "-190"
                  "rightposx": "-2",
                  "rightposy": "0",
                  "rightposz": "-3"
                  "leftrotx": "70",
                  "leftroty": "-1",
                  "leftrotz": "150"
                  "leftposx": "0",
                  "leftposy": "0",
                  "leftposz": "0"
         */
        currRX = (70) * lerper;
        currRY = (lefty?-1:-56) * lerper;
        currRZ = (lefty?150:-190) * lerper;

        currPX = (lefty?arm.x:-2);
        currPY = arm.y;
        currPZ = (lefty?arm.z:-3);

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

    //right abdomen
    public static void SetBlock6(Boolean lefty, HumanoidModel<?> model, double lerper) {
        ModelPart arm = lefty? model.leftArm:model.rightArm;
        double currRX = 0, currRY = 0, currRZ =0;
        float currPX = 0, currPY = 0, currPZ =0;

        /*
                  "rightrotx": "70",
                  "rightroty": "-1",
                  "rightrotz": "-150"
                  "rightposx": "0",
                  "rightposy": "0",
                  "rightposz": "0"
                  "leftrotx": "70",
                  "leftroty": "56",
                  "leftrotz": "190"
                  "leftposx": "2",
                  "leftposy": "0",
                  "leftposz": "-3"
         */
        currRX = (70) * lerper;
        currRY = (!lefty?-1:56) * lerper;
        currRZ = (!lefty?-150:190) * lerper;

        currPX = (!lefty?arm.x:2);
        currPY = arm.y;
        currPZ = (!lefty?arm.z:-3);

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

    //balls
    public static void SetBlock7(Boolean lefty, HumanoidModel<?> model, double lerper) {
        ModelPart arm = lefty? model.leftArm:model.rightArm;
        double currRX = 0, currRY = 0, currRZ =0;
        float currPX = 0, currPY = 0, currPZ =0;

        /*
                  "rightrotx": "74",
                  "rightroty": "66",
                  "rightrotz": "104"
                  "rightposx": "0",
                  "rightposy": "-2",
                  "rightposz": "0"
                  "leftrotx": "74",
                  "leftroty": "-66",
                  "leftrotz": "-104"
                  "leftposx": "0\n",
                  "leftposy": "-2",
                  "leftposz": "0"
         */
        currRX = (74) * lerper;
        currRY = (lefty?-66:66) * lerper;
        currRZ = (lefty?-104:104) * lerper;

        currPX = (arm.x);
        currPY = (2);
        currPZ = (arm.z);

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

    //head
    public static void SetBlock8(Boolean lefty, HumanoidModel<?> model, double lerper) {
        ModelPart arm = lefty ? model.leftArm : model.rightArm;
        double currRX = 0, currRY = 0, currRZ = 0;
        float currPX = 0, currPY = 0, currPZ = 0;

        /*
                  "rightrotx": "72",
                  "rightroty": "-36",
                  "rightrotz": "72"
                  "rightposx": "0",
                  "rightposy": "2",
                  "rightposz": "0"
                  "leftrotx": "72",
                  "leftroty": "36",
                  "leftrotz": "-72"
                  "leftposx": "0\n",
                  "leftposy": "2",
                  "leftposz": "0"
         */
        currRX = (72) * lerper;
        currRY = (lefty?36:-36) * lerper;
        currRZ = (lefty?-72:72) * lerper;

        currPX = (arm.x);
        currPY = (-2);
        currPZ = (arm.z);

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
}
