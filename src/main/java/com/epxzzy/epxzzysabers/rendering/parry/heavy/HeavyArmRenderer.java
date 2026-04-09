package com.epxzzy.epxzzysabers.rendering.parry.heavy;

import com.epxzzy.epxzzysabers.util.AngleHelper;
import com.epxzzy.epxzzysabers.util.AnimationTickHolder;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;


public class HeavyArmRenderer {
    public static void setArmPose(int flouriseh, boolean Lefty, boolean both, HumanoidModel<?> model) {
        HeavyFlourish flourish = HeavyFlourish.fromTagID(flouriseh);
        switch (flourish) {
            case SKIPCATCH -> SetArmFlourishSKIPCATCH(Lefty, both, model);
            case BEHINDTHEBACK -> SetArmFlourishBEHINDTHEBACK(Lefty, both, model);

            case FIGUREEIGHT -> SetArmFlourishFIGUREEIGHT(Lefty, both, model);

        }
    }



    public static void SetArmFlourishSKIPCATCH(boolean Lefty, boolean both, HumanoidModel<?> model){
        ModelPart MainArm = model.rightArm;
        ModelPart otherArm = model.leftArm;

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

        float movement2 = !Lefty? Mth.sin(((float) ((time) * 3.3 / Math.PI))): Mth.cos(((float) ((time) * 3.3 / Math.PI)));
        float movement3 = !Lefty? Mth.cos(((float) ((time+Mth.PI/-2) * 3.3 / Math.PI))): Mth.sin(((float) ((time+Mth.PI/-2) * 3.3 / Math.PI)));


        MainArm.xRot = AngleHelper.rad(-45.04 * movement2);
        otherArm.xRot = AngleHelper.rad(-45.04 * movement3);

        MainArm.zRot = AngleHelper.rad(movement2 * -30);
        otherArm.zRot = AngleHelper.rad(movement3 * 30);

    }
    public static void SetArmFlourishUNTITLED(boolean Lefty, boolean both, HumanoidModel<?> model){
        ModelPart MainArm = Lefty ? model.leftArm: model.rightArm;
        ModelPart otherArm = Lefty ? model.rightArm: model.leftArm;

        float time = AnimationTickHolder.getTicks(true) + AnimationTickHolder.getPartialTicks();

        int handMultiplier = Lefty?1:-1;

        float movement = Mth.sin((float) ((AnimationTickHolder.getTicks(false))*2/Math.PI));

        MainArm.xRot = (float) (Mth.clamp(0f, -1.2F, 1.2F) - 1.4835298F +(-movement*1.1))*handMultiplier;
        MainArm.yRot = AngleHelper.rad(-30)*handMultiplier;
        otherArm.xRot = (float)(Mth.clamp(0f, -1.2F, 1.2F) - 1.4835298F +(movement*1.1))*handMultiplier;
        otherArm.yRot = AngleHelper.rad(30)*handMultiplier;

    }
    public static void SetArmFlourishFIGUREEIGHT(boolean Lefty, boolean both, HumanoidModel<?> model){
        ModelPart MainArm = model.rightArm;
        ModelPart otherArm = model.leftArm;
        //both arms remain the same even when left-ing
        model.leftArm.resetPose();
        model.rightArm.resetPose();

        //float movement = Mth.sin((float) ((AnimationTickHolder.getTicks(false))*4/Math.PI));
        float movement = 1;

        MainArm.xRot = (float) (Mth.clamp(0f, -1.2F, 1.2F) - 1.4835298F +AngleHelper.rad(movement*3));
        MainArm.yRot = AngleHelper.rad(-30);
        otherArm.xRot = (float)(Mth.clamp(0f, -1.2F, 1.2F) - 1.4835298F +AngleHelper.rad(-movement*3));
        otherArm.yRot = AngleHelper.rad(30);
    }


}
