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
        double currRX = 0, currRY = 0, currRZ =0;
        float currPX = 0, currPY = 0, currPZ =0;

        /*
        rotx = 330 + (45 - 330) * (t.lerper)
        roty = 10 * (t.lerper)
        rotz = -90

        posy = left ? -1 : 1
        posz = left ? -1 * lerper : 0
         */
        currRX = 45 + (330 - 45) * lerper;
        currRY = 10 * lerper;
        currRZ = -90;

        currPX = (float) arm.x;
        currPY = (float) ((lefty?3:1) * lerper);
        currPZ = (float) ((lefty?-1:0) * lerper);

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
        double currRX = 0, currRY = 0, currRZ =0;
        float currPX = 0, currPY = 0, currPZ =0;

        /*
                  "rot x": "330 + (45 - 330) * (t.lerper)",
                  "rot y": "-10 * (t.lerper)",
                  "rot z": "90"
                  "pos x": "temp.is_left ? 1 * (t.lerper) : -5 * (t.lerper) ",
                  "pos y": "temp.is_left ? 1 : -1 \n",
                  "pos z": "temp.is_left ? 0 : -1 * (t.lerper)"
         */

        currRX = 45 + (330- 45) * lerper;
        currRY = -10* lerper;
        currRZ = 90;

        currPX = (float) arm.x;
        currPY = (float) ((lefty?1:3) * lerper);
        currPZ = (float) ((lefty?0:-1) * lerper);

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
    //left knee   3
    public static void SetAttack3(Boolean lefty, HumanoidModel<?> model, double lerper){
        ModelPart arm = lefty? model.leftArm:model.rightArm;
        double currRX = 0, currRY = 0, currRZ =0;
        float currPX = 0, currPY = 0, currPZ =0;

        /*
                  "rot x": "330 + ((temp.is_left?60:45) - 330) * (t.lerper)",
                  "rot y": "temp.is_left ? -50 * (t.lerper) : 0",
                  "rot z": "-90"
                  "pos x": "temp.is_left ? 1 * (t.lerper) : -1 * (t.lerper) ",
                  "pos y": "temp.is_left ? -2 : -1 \n",
                  "pos z": "temp.is_left ? -3 * (t.lerper) : -1 * (t.lerper) "
         */

        currRX = (lefty?60:45) + (330 - (lefty?60:45)) * lerper;
        currRY = 50;
        currRZ = -90;

        currPX = (float) arm.x;
        currPY = (float) ((lefty?2:-1) * lerper);
        currPZ = (float) (-1 * lerper);

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
    //right knee   4
    public static void SetAttack4(Boolean lefty, HumanoidModel<?> model, double lerper){
        ModelPart arm = lefty? model.leftArm:model.rightArm;
        double currRX = 0, currRY = 0, currRZ =0;
        float currPX = 0, currPY = 0, currPZ =0;

        /*
                  "rot x": "330 + (45 - 330) * (t.lerper)",
                  "rot y": "-10 * (t.lerper)",
                  "rot z": "90"
                  "pos x": "temp.is_left ? 1 * (t.lerper) : -5 * (t.lerper) ",
                  "pos y": "temp.is_left ? 1 : -1 \n",
                  "pos z": "temp.is_left ? 0 : -1 * (t.lerper)"
         */

        currRX = 45+ (330- 45) * lerper;
        currRY = -50;
        currRZ = 90;

        currPX = (float) arm.x;
        currPY = (float) ((lefty?1:2));
        currPZ = (float) (-1 * lerper);

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
    //left abdomen   5
    public static void SetAttack5(Boolean lefty, HumanoidModel<?> model, double lerper){
        ModelPart arm = lefty? model.leftArm:model.rightArm;
        double currRX = 0, currRY = 0, currRZ =0;
        float currPX = 0, currPY = 0, currPZ =0;

        /*
                  "leftarm rot x": "330 + (temp.is_left?60:45 - 330) * (t.lerper)",
                  "leftarm rot y": "-20 * (t.lerper)",
                  "leftarm rot z": "-90"
                  "leftarm pos x": "(temp.is_left ? 1 : -1) * (t.lerper)",
                  "leftarm pos y": "temp.is_left ? -2 : -1 \n",
                  "leftarm pos z": "-1 * (t.lerper)"
         */
        currRX = (lefty?60:45) + (330 - (lefty?60:45)) * lerper;
        currRY = 20;
        currRZ = -90;

        currPX = (float) arm.x;
        currPY = (float) ((lefty?3:1));
        currPZ = (float) (-1 * lerper);

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
    //right abdomen   6
    public static void SetAttack6(Boolean lefty, HumanoidModel<?> model, double lerper){
        ModelPart arm = lefty? model.leftArm:model.rightArm;
        double currRX = 0, currRY = 0, currRZ =0;
        float currPX = 0, currPY = 0, currPZ =0;

        /*
                        "leftarm rot x": " 330 + ((temp.is_left ? 45 : 60 ) - 330) * (t.lerper)",
                        "leftarm rot y": "20 * (t.lerper)",
                        "leftarm rot z": "90"
                        "leftarm pos x": "-1 * (t.lerper)",
                        "leftarm pos y": "temp.is_left ? -1 : -2 \n",
                        "leftarm pos z": "-1 * (t.lerper)"
         */
        currRX = (lefty?45:60) + (330 - (lefty?45:60)) * lerper;
        currRY = -20;
        currRZ = 90;

        currPX = (float) arm.x;
        currPY = (float) ((lefty?1:3));
        currPZ = (float) (-1 * lerper);

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
    //balls  7
    public static void SetAttack7(Boolean lefty, HumanoidModel<?> model, double lerper){
        ModelPart arm = lefty? model.leftArm:model.rightArm;
        double currRX = 0, currRY = 0, currRZ =0;
        float currPX = 0, currPY = 0, currPZ =0;

        /*
                  "leftarm rot x": "330 + (60 - 330) * (t.lerper)",
                  "leftarm rot y": "(temp.is_left ? 15 : -15) * (t.lerper)",
                  "leftarm rot z": "-180"
                  "leftarm pos x": "(temp.is_left ? -1 : 1) * (t.lerper)",
                  "leftarm pos y": "-2 * (t.lerper)",
                  "lettarm pos z": "-3 * (t.lerper)"
         */
        currRX = 60 + (330 - 60) * lerper;
        currRY = (lefty?-15:15) * lerper;
        currRZ = 180;

        currPX = (float) ((lefty?5:-5) * lerper);
        currPY = (float) arm.y;
        currPZ = (float) (-2 * lerper);

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
    //head   8
    public static void SetAttack8(Boolean lefty, HumanoidModel<?> model, double lerper) {
        ModelPart arm = lefty ? model.leftArm : model.rightArm;
        double currRX = 0, currRY = 0, currRZ = 0;
        float currPX = 0, currPY = 0, currPZ = 0;

        /*
                  "leftarm rot x": "330 + (50 - 330) * (t.lerper)",
                  "leftarm rot y": "(temp.is_left ? -15: 15) * (t.lerper)",
                  "leftarm rot z": "0"
                  "leftarm pos x": "(temp.is_left ? -1 : 1) * (t.lerper) : 0",
                  "leftarm pos y": "-2 * (t.lerper)",
                  "leftarm pos z": "-1 * (t.lerper)"
         */
        currRX = 60 + (330 - 60) * lerper;
        currRY = (lefty?15:-15) * lerper;
        currRZ = 0;

        currPX = (float) ((lefty?5:-5) * lerper);
        currPY = (float) arm.y;
        currPZ = (float) (-2 * lerper);

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
