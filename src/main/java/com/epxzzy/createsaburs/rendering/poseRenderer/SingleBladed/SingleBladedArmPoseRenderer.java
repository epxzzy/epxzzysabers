package com.epxzzy.createsaburs.rendering.poseRenderer.SingleBladed;

import com.epxzzy.createsaburs.utils.AngleHelper;
import com.epxzzy.createsaburs.utils.AnimationTickHolder;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;


public class SingleBladedArmPoseRenderer {
    public static void setArmPose(int flouriseh, boolean Lefty, boolean both, HumanoidModel<?> model) {
        SingleBladedFlourish flourish = SingleBladedFlourish.fromTagID(flouriseh);
        switch (flourish) {
            case XCROSS -> SetArmFlourishXCROSS(Lefty, both, model);
            //side to front ro side
            case CIRCULAR -> SetArmFlourishCIRCULAR(Lefty, both, model);
            //the "obi-ani"
        }
    }



    public static void SetArmFlourishXCROSS(boolean Lefty, boolean both, HumanoidModel<?> model){
        ModelPart MainArm = Lefty ? model.rightArm : model.leftArm;
        ModelPart otherArm = Lefty ? model.leftArm : model.rightArm;

        model.rightArm.resetPose();

        //the x-cross
        float time = AnimationTickHolder.getTicks(true) + AnimationTickHolder.getPartialTicks();
        float mainCycle = Mth.sin(((float) ((time + 10) * 0.3f / Math.PI)));


        MainArm.xRot = Mth.clamp(0f, -1.2F, 1.2F) - 1.4835298F;
        MainArm.yRot = AngleHelper.rad(-20);

        if (both) {
            model.leftArm.resetPose();

            //otherArm.xRot = -AngleHelper.rad(bodySwing + 150);
            otherArm.xRot = Mth.clamp(model.head.xRot, -1.2F, 1.2F) - 1.4835298F;
            MainArm.yRot = AngleHelper.rad(0);
            //otherArm.zRot = (Lefty ? 1 : -1) * AngleHelper.rad(15);
        }
    }
    public static void SetArmFlourishCIRCULAR(boolean Lefty, boolean both, HumanoidModel<?> model){
        ModelPart MainArm = Lefty ? model.rightArm : model.leftArm;
        ModelPart otherArm = Lefty ? model.leftArm : model.rightArm;

        model.rightArm.resetPose();

        //the circular
        float time = AnimationTickHolder.getTicks(true) + AnimationTickHolder.getPartialTicks();
        float movement = Mth.sin(((float) ((time) * 3.3 / Math.PI)));
        float movement2 = Mth.sin(((float) ((time) * 4 / Math.PI)));

        MainArm.xRot = AngleHelper.rad(-45.04 * movement - 10);
        MainArm.zRot = AngleHelper.rad(movement2 * -30);

        if (both) {
            model.leftArm.resetPose();

            otherArm.xRot = Mth.cos(0.6662F + (float) Math.PI) * 2.0F * 0.5F;
            otherArm.zRot = -AngleHelper.rad(20);
        }

        if (!both) {
            //otherArm.zRot = (Lefty ? -1 : 1) * (-AngleHelper.rad(20)) + 0.5f * bodySwing + limbSwing;
        }


    }

}
