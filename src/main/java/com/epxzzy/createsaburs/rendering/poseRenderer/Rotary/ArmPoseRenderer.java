package com.epxzzy.createsaburs.rendering.poseRenderer.Rotary;

import com.epxzzy.createsaburs.utils.AngleHelper;
import com.epxzzy.createsaburs.utils.AnimationTickHolder;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;

public class ArmPoseRenderer {

    public static void setRotaryFlyPose(Player player, HumanoidModel<?> model, boolean offhand){
        model.head.x = 0;
        model.hat.x = 0;
        model.body.resetPose();
        model.leftArm.resetPose();
        model.rightArm.resetPose();
        model.leftLeg.resetPose();
        model.rightLeg.resetPose();
        boolean isLeftArmMain = (player.getMainArm() == HumanoidArm.LEFT);
        isLeftArmMain = offhand?!isLeftArmMain: isLeftArmMain;

        float time = (AnimationTickHolder.getTicks(true) + AnimationTickHolder.getPartialTicks())/2;
        float mainCycle = Mth.sin(((float) ((time + 10) * 0.3f / Math.PI)));
        float limbCycle = Mth.sin(((float) (time * 0.3f / Math.PI)));
        float bodySwing = AngleHelper.rad(15 + (mainCycle * 10));
        float limbSwing = AngleHelper.rad(limbCycle * 15);
        if (isLeftArmMain) bodySwing = -bodySwing;
        model.body.zRot = bodySwing;
        model.head.zRot = bodySwing;
        model.hat.zRot = bodySwing;

        //CreateSaburs.LOGGER.info("offhand is "+offhand+" isLeftArm is " + isLeftArmMain);

        ModelPart hangingArm = (isLeftArmMain ? model.leftArm : model.rightArm);
        ModelPart otherArm = (isLeftArmMain ? model.rightArm : model.leftArm);
        hangingArm.y -= 3;

        float offsetX = hangingArm.x;
        float offsetY = hangingArm.y;
//		model.rightArm.x = offsetX * Mth.cos(bodySwing) - offsetY * Mth.sin(bodySwing);
//		model.rightArm.y = offsetX * Mth.sin(bodySwing) + offsetY * Mth.cos(bodySwing);
        float armPivotX = offsetX * Mth.cos(bodySwing) - offsetY * Mth.sin(bodySwing) + (isLeftArmMain ? -1 : 1) * 4.5f;
        float armPivotY = offsetX * Mth.sin(bodySwing) + offsetY * Mth.cos(bodySwing) + 2;
        hangingArm.xRot = -AngleHelper.rad(180);

        offsetX = otherArm.x;
        offsetY = otherArm.y;
        otherArm.x = offsetX * Mth.cos(bodySwing) - offsetY * Mth.sin(bodySwing);
        otherArm.y = offsetX * Mth.sin(bodySwing) + offsetY * Mth.cos(bodySwing);
        otherArm.zRot = (isLeftArmMain ? -1 : 1) * (-AngleHelper.rad(20)) + 0.5f * bodySwing + limbSwing;

        ModelPart leadingLeg = isLeftArmMain ? model.leftLeg : model.rightLeg;
        ModelPart trailingLeg = isLeftArmMain ? model.rightLeg : model.leftLeg;

        leadingLeg.y -= 0.2f;
        offsetX = leadingLeg.x;
        offsetY = leadingLeg.y;
        leadingLeg.x = offsetX * Mth.cos(bodySwing) - offsetY * Mth.sin(bodySwing);
        leadingLeg.y = offsetX * Mth.sin(bodySwing) + offsetY * Mth.cos(bodySwing);
        leadingLeg.xRot = -AngleHelper.rad(25);
        leadingLeg.zRot = (isLeftArmMain ? -1 : 1) * (AngleHelper.rad(10)) + 0.5f * bodySwing + limbSwing;
        trailingLeg.y -= 0.8f;
        offsetX = trailingLeg.x;
        offsetY = trailingLeg.y;
        trailingLeg.x = offsetX * Mth.cos(bodySwing) - offsetY * Mth.sin(bodySwing);
        trailingLeg.y = offsetX * Mth.sin(bodySwing) + offsetY * Mth.cos(bodySwing);
        trailingLeg.xRot = AngleHelper.rad(10);
        trailingLeg.zRot = (isLeftArmMain ? -1 : 1) * (-AngleHelper.rad(10)) + 0.5f * bodySwing + limbSwing;
        model.hat.x -= armPivotX;
        model.head.x -= armPivotX;
        model.body.x -= armPivotX;
        otherArm.x -= armPivotX;
        trailingLeg.x -= armPivotX;
        leadingLeg.x -= armPivotX;

        model.hat.y -= armPivotY;
        model.head.y -= armPivotY;
        model.body.y -= armPivotY;
        otherArm.y -= armPivotY;
        trailingLeg.y -= armPivotY;
        leadingLeg.y -= armPivotY;
    }

    public static void setRotaryBlockPose(Player player, HumanoidModel<?> model){
        ModelPart modelpart =  model.leftArm;
        ModelPart modelpart1 = model.rightArm;
        modelpart.yRot = 0.6F + model.head.yRot;
        modelpart1.yRot = -0.6F + model.head.yRot;
        modelpart.xRot = (-(float)Math.PI / 2F) + model.head.xRot + 0.1F;
        modelpart1.xRot = -1.5F + model.head.xRot;
    }
}
