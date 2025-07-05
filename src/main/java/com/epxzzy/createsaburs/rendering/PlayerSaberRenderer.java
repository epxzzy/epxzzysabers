package com.epxzzy.createsaburs.rendering;

import com.epxzzy.createsaburs.createsaburs;
import com.epxzzy.createsaburs.item.Protosaber;
import com.epxzzy.createsaburs.item.saburtypes.RotarySaber;
import com.epxzzy.createsaburs.item.saburtypes.SingleBladed;
import com.epxzzy.createsaburs.rendering.poseHandlers.PlayerStanceRenderer;
import com.epxzzy.createsaburs.rendering.poseRenderer.DualBladed.DualBladedArmPoseRenderer;
import com.epxzzy.createsaburs.rendering.poseRenderer.SingleBladed.SingleBladedArmPoseRenderer;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.createmod.catnip.math.AngleHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;

public class PlayerSaberRenderer {
    public static boolean IsPlayerStationary(Player player){

        //createsaburs.LOGGER.info("getdeltamovementplayer "+ player.getDeltaMovement().length());
        return !(player.getDeltaMovement().length() > 0.08);

        //return x+y+z;
        //return true;
    }

    public static void beforeSetupAnim(Player player, HumanoidModel<?> model) {
        if (Protosaber.checkForSaberEquipment(player, true) && player.swingTime > 0) {
            model.leftArm.resetPose();
            model.rightArm.resetPose();
        }

        if (SingleBladed.checkForSaberEquipment(player, true) && player.swingTime > 0) {
            model.rightArm.resetPose();
            model.rightArm.resetPose();
        }
        if((Protosaber.checkForSaberBlock(player)||SingleBladed.checkForSaberBlock(player))&& IsPlayerStationary(player)){//&& player.isShiftKeyDown()){
            model.rightArm.resetPose();
            model.leftArm.resetPose();
            model.head.resetPose();
            model.body.resetPose();
            model.leftLeg.resetPose();
            model.rightLeg.resetPose();
            
        };
        if(RotarySaber.checkForSaberFly(player)[0]){
            model.head.resetPose();
            model.hat.resetPose();
            model.body.resetPose();
            model.leftArm.resetPose();
            model.rightArm.resetPose();
            model.leftLeg.resetPose();
            model.rightLeg.resetPose();
        }
    }

    public static void afterSetupAnim(Player player, HumanoidModel<?> model) {
        int flourish = player.getMainHandItem().getOrCreateTag().getCompound("display").getInt("flourish");
        if(player.swingTime > 0 || player.getAttackAnim(1) > 0) {
            //createsaburs.LOGGER.warn("swing time is: " + player.swingTime + " and attack time is: " + player.getAttackAnim(1));
        }
        if (Protosaber.checkForSaberEquipment(player, true) && player.swingTime > 0) {
            setDualSaberPose(player.getMainArm() == HumanoidArm.LEFT, false, model, flourish);
        }
        if (Protosaber.checkForSaberEquipment(player, false) && Protosaber.checkForSaberEquipment(player, true) && player.swingTime > 0) {
            setDualSaberPose(player.getMainArm() != HumanoidArm.LEFT, true, model, flourish);
        }
        if (SingleBladed.checkForSaberEquipment(player, true) && player.swingTime > 0) {
            setSingleBladedSaberPose(player.getMainArm() != HumanoidArm.LEFT, false, model, flourish);
        }
        if (SingleBladed.checkForSaberEquipment(player, false) && SingleBladed.checkForSaberEquipment(player, true) && player.swingTime > 0) {
            setSingleBladedSaberPose(player.getMainArm() != HumanoidArm.LEFT, true, model, flourish);
        }
        if((Protosaber.checkForSaberBlock(player)||SingleBladed.checkForSaberBlock(player))&& IsPlayerStationary(player)){//&& player.isShiftKeyDown()){
            setBladedStance(player, model);
        }
        else if (RotarySaber.checkForSaberFly(player)[0]){
            setSaberFlyPose(player, model, RotarySaber.checkForSaberFly(player)[1]);
        }
    }

    private static void setDualSaberPose(boolean Lefty, boolean both, HumanoidModel<?> model, int flourish) {
        if (Minecraft.getInstance().isPaused())
            return;

        ModelPart MainArm = Lefty ? model.leftArm : model.rightArm;
        ModelPart otherArm = Lefty ? model.rightArm : model.leftArm;

        DualBladedArmPoseRenderer.setArmPose(flourish, Lefty, both, model);

    }

    private static void setSingleBladedSaberPose(boolean Lefty, boolean both, HumanoidModel<?> model, int flourish) {
        if (Minecraft.getInstance().isPaused())
            return;

        SingleBladedArmPoseRenderer.setArmPose(flourish, Lefty, both, model);
    }
    private static void setBladedStance(Player player,HumanoidModel<?> model){
        PlayerStanceRenderer.setPose(Protosaber.getStance(player),false, model);
    }

    private static void setSaberFlyPose(Player player,HumanoidModel<?> model, boolean offhand){
        model.head.x = 0;
        model.hat.x = 0;
        model.body.resetPose();
        model.leftArm.resetPose();
        model.rightArm.resetPose();
        model.leftLeg.resetPose();
        model.rightLeg.resetPose();
        boolean isLeftArmMain = (player.getMainArm() == HumanoidArm.LEFT);
        isLeftArmMain = offhand?!isLeftArmMain: isLeftArmMain;

        float time = AnimationTickHolder.getTicks(true) + AnimationTickHolder.getPartialTicks();
        float mainCycle = Mth.sin(((float) ((time + 10) * 0.3f / Math.PI)));
        float limbCycle = Mth.sin(((float) (time * 0.3f / Math.PI)));
        float bodySwing = AngleHelper.rad(15 + (mainCycle * 10));
        float limbSwing = AngleHelper.rad(limbCycle * 15);
        if (isLeftArmMain) bodySwing = -bodySwing;
        model.body.zRot = bodySwing;
        model.head.zRot = bodySwing;
        model.hat.zRot = bodySwing;

        //createsaburs.LOGGER.info("offhand is "+offhand+" isLeftArm is " + isLeftArmMain);

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

}
