package com.epxzzy.createsaburs.rendering;

import com.epxzzy.createsaburs.createsaburs;
import com.epxzzy.createsaburs.item.Protosaber;
import com.epxzzy.createsaburs.item.saburtypes.RotarySaber;
import com.epxzzy.createsaburs.item.saburtypes.SingleBladed;
import com.epxzzy.createsaburs.rendering.poseHandlers.PlayerStanceRenderer;
import com.epxzzy.createsaburs.rendering.poseRenderer.DualBladed.DualBladedArmPoseRenderer;
import com.epxzzy.createsaburs.rendering.poseRenderer.SingleBladed.SingleBladedArmPoseRenderer;
import com.epxzzy.createsaburs.utils.AngleHelper;
import com.epxzzy.createsaburs.utils.AnimationTickHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;

import static com.epxzzy.createsaburs.rendering.poseRenderer.Rotary.ArmPoseRenderer.setRotaryBlockPose;
import static com.epxzzy.createsaburs.rendering.poseRenderer.Rotary.ArmPoseRenderer.setRotaryFlyPose;


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
        if(RotarySaber.checkForSaberBlock(player)){
            setRotaryBlockPose(player, model);
        }
        if (RotarySaber.checkForSaberFly(player)[0]){
            setRotaryFlyPose(player, model, RotarySaber.checkForSaberFly(player)[1]);
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


}
