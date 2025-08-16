package com.epxzzy.epxzzysabers.rendering.poseRenderer.DualBladed;

import com.epxzzy.epxzzysabers.utils.AngleHelper;
import com.epxzzy.epxzzysabers.utils.AnimationTickHolder;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;


public class DualBladedArmPoseRenderer {
    public static void setArmPose(int flouriseh, boolean Lefty, boolean both, HumanoidModel<?> model) {
        DualBladedFlourish flourish = DualBladedFlourish.fromTagID(flouriseh);
        switch (flourish) {
            case SKIPCATCH -> SetArmFlourishSKIPCATCH(Lefty, both, model);
            case BEHINDTHEBACK -> SetArmFlourishBEHINDTHEBACK(Lefty, both, model);

            case FIGUREEIGHT -> SetArmFlourishFIGUREEIGHT(Lefty, both, model);

        }
    }



    public static void SetArmFlourishSKIPCATCH(boolean Lefty, boolean both, HumanoidModel<?> model){
        ModelPart MainArm = Lefty ? model.leftArm: model.rightArm;
        ModelPart otherArm = Lefty ? model.rightArm: model.leftArm;

        //model.body.resetPose();
        model.leftArm.resetPose();
        model.rightArm.resetPose();


        float movement = Mth.sin((float) ((AnimationTickHolder.getTicks(false))*4/Math.PI));

        MainArm.xRot = (float) (Mth.clamp(0f, -1.2F, 1.2F) - 1.4835298F +AngleHelper.rad(movement*3));
        MainArm.yRot = AngleHelper.rad(-27);
        otherArm.xRot = (float)(Mth.clamp(0f, -1.2F, 1.2F) - 1.4835298F +AngleHelper.rad(-movement*3));
        otherArm.yRot = AngleHelper.rad(27);
    }
    public static void SetArmFlourishBEHINDTHEBACK(boolean Lefty, boolean both, HumanoidModel<?> model){
        ModelPart MainArm = Lefty ? model.leftArm: model.rightArm;
        ModelPart otherArm = Lefty ? model.rightArm: model.leftArm;

        model.leftArm.resetPose();
        model.rightArm.resetPose();


        float time = AnimationTickHolder.getTicks(true) + AnimationTickHolder.getPartialTicks();


        float movement = Mth.sin((float) ((AnimationTickHolder.getTicks(false))*4/Math.PI));

        float movement2 = Mth.sin(((float) ((time) * 3.3 / Math.PI)));
        float movement3 = Mth.sin(((float) ((time+Mth.PI/-2) * 3.3 / Math.PI)));


        MainArm.xRot = AngleHelper.rad(-45.04 * movement2);
        otherArm.xRot = AngleHelper.rad(-45.04 * movement3);

        MainArm.zRot = AngleHelper.rad(movement2 * -30);
        otherArm.zRot = AngleHelper.rad(movement3 * 30);

    }
    public static void SetArmFlourishUNTITLED(boolean Lefty, boolean both, HumanoidModel<?> model){
        ModelPart MainArm = Lefty ? model.leftArm: model.rightArm;
        ModelPart otherArm = Lefty ? model.rightArm: model.leftArm;

        float time = AnimationTickHolder.getTicks(true) + AnimationTickHolder.getPartialTicks();


        float movement = Mth.sin((float) ((AnimationTickHolder.getTicks(false))*2/Math.PI));

        MainArm.xRot = (float) (Mth.clamp(0f, -1.2F, 1.2F) - 1.4835298F +(-movement*1.1));
        MainArm.yRot = AngleHelper.rad(-30);
        otherArm.xRot = (float)(Mth.clamp(0f, -1.2F, 1.2F) - 1.4835298F +(movement*1.1));
        otherArm.yRot = AngleHelper.rad(30);

    }
    public static void SetArmFlourishFIGUREEIGHT(boolean Lefty, boolean both, HumanoidModel<?> model){
        ModelPart MainArm = Lefty ? model.leftArm: model.rightArm;
        ModelPart otherArm = Lefty ? model.rightArm: model.leftArm;

        model.leftArm.resetPose();
        model.rightArm.resetPose();



        float movement = Mth.sin((float) ((AnimationTickHolder.getTicks(false))*4/Math.PI));

        MainArm.xRot = (float) (Mth.clamp(0f, -1.2F, 1.2F) - 1.4835298F +AngleHelper.rad(movement*3));
        MainArm.yRot = AngleHelper.rad(-30);
        otherArm.xRot = (float)(Mth.clamp(0f, -1.2F, 1.2F) - 1.4835298F +AngleHelper.rad(-movement*3));
        otherArm.yRot = AngleHelper.rad(30);
    }


}
