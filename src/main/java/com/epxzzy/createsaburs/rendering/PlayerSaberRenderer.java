package com.epxzzy.createsaburs.rendering;

import com.epxzzy.createsaburs.createsaburs;
import com.epxzzy.createsaburs.item.Protosaber;
import com.epxzzy.createsaburs.item.saburtypes.RotarySaber;
import com.epxzzy.createsaburs.item.saburtypes.SingleBladed;
import com.epxzzy.createsaburs.rendering.posehandlers.SaberPoseHandler;
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
        if(RotarySaber.checkForSaberFly(player)){
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
        else if (RotarySaber.checkForSaberFly(player)){
            setSaberFlyPose(player, model);
        }
    }

    private static void setDualSaberPose(boolean Lefty, boolean both, HumanoidModel<?> model, int flourish) {
        if (Minecraft.getInstance().isPaused())
            return;

        ModelPart MainArm = Lefty ? model.leftArm : model.rightArm;
        ModelPart otherArm = Lefty ? model.rightArm : model.leftArm;

        //model.hat.y -= armPivotY;
        //model.head.y -= armPivotY;
        //otherArm.y -= armPivotY;
        if(flourish == 1){
            //skip catch
            model.body.resetPose();
            MainArm.resetPose();
            otherArm.resetPose();


            float movement = Mth.sin((float) ((AnimationTickHolder.getTicks(false))*4/Math.PI));

            MainArm.xRot = (float) (Mth.clamp(0f, -1.2F, 1.2F) - 1.4835298F +AngleHelper.rad(movement*3));
            MainArm.yRot = AngleHelper.rad(-27);
            otherArm.xRot = (float)(Mth.clamp(0f, -1.2F, 1.2F) - 1.4835298F +AngleHelper.rad(-movement*3));
            otherArm.yRot = AngleHelper.rad(27);



        }
        if (flourish == 2) {
            MainArm.resetPose();
            otherArm.resetPose();
            //spin behind the back
            float time = AnimationTickHolder.getTicks(true) + AnimationTickHolder.getPartialTicks();
            float movement = Mth.sin((float) ((AnimationTickHolder.getTicks(false))*4/Math.PI));

            float movement2 = Mth.sin(((float) ((time) * 3.3 / Math.PI)));
            float movement3 = Mth.sin(((float) ((time+Mth.PI/-2) * 3.3 / Math.PI)));


            MainArm.xRot = AngleHelper.rad(-45.04 * movement2);
            otherArm.xRot = AngleHelper.rad(-45.04 * movement3);

            MainArm.zRot = AngleHelper.rad(movement2 * -30);
            otherArm.zRot = AngleHelper.rad(movement3 * 30);



            //MainArm.yRot = AngleHelper.rad(-27);
            //otherArm.xRot = (float)(Mth.clamp(0f, -1.2F, 1.2F) - 1.4835298F +AngleHelper.rad(-movement*3));
            //otherArm.yRot = AngleHelper.rad(27);

        }
        if(flourish == 3){
            //heli behind the back
            MainArm.resetPose();
            otherArm.resetPose();

            float movement = Mth.sin((float) ((AnimationTickHolder.getTicks(false))*2/Math.PI));

            MainArm.xRot = (float) (Mth.clamp(0f, -1.2F, 1.2F) - 1.4835298F +(-movement*1.1));
            MainArm.yRot = AngleHelper.rad(-30);
            otherArm.xRot = (float)(Mth.clamp(0f, -1.2F, 1.2F) - 1.4835298F +(movement*1.1));
            otherArm.yRot = AngleHelper.rad(30);

        }
    }

    private static void setSingleBladedSaberPose(boolean Lefty, boolean both, HumanoidModel<?> model, int flourish) {
        if (Minecraft.getInstance().isPaused())
            return;
        ModelPart MainArm = Lefty ? model.rightArm : model.leftArm;
        ModelPart otherArm = Lefty ? model.leftArm : model.rightArm;


        if (flourish == 1) {
            createsaburs.LOGGER.info("the xcross");
            //the x-cross
            float time = AnimationTickHolder.getTicks(true) + AnimationTickHolder.getPartialTicks();
            float mainCycle = Mth.sin(((float) ((time + 10) * 0.3f / Math.PI)));

            MainArm.resetPose();

            MainArm.xRot = Mth.clamp(0f, -1.2F, 1.2F) - 1.4835298F;
            MainArm.yRot = AngleHelper.rad(-20);
            if (both) {
                otherArm.resetPose();
                //otherArm.xRot = -AngleHelper.rad(bodySwing + 150);
                otherArm.xRot = Mth.clamp(model.head.xRot, -1.2F, 1.2F) - 1.4835298F;
                MainArm.yRot = AngleHelper.rad(0);
                //otherArm.zRot = (Lefty ? 1 : -1) * AngleHelper.rad(15);
            }

        }


        if (flourish == 2) {
            createsaburs.LOGGER.info("the circular");
            //the circular
            float time = AnimationTickHolder.getTicks(true) + AnimationTickHolder.getPartialTicks();
            float movement = Mth.sin(((float) ((time) * 3.3 / Math.PI)));
            float movement2 = Mth.sin(((float) ((time) * 4 / Math.PI)));

            MainArm.resetPose();
            MainArm.xRot = AngleHelper.rad(-45.04 * movement - 10);
            MainArm.zRot = AngleHelper.rad(movement2 * -30);


            if (both) {
                otherArm.resetPose();
                otherArm.xRot = Mth.cos(0.6662F + (float) Math.PI) * 2.0F * 0.5F;
                otherArm.zRot = -AngleHelper.rad(20);
            }
            if (!both) {
                //otherArm.zRot = (Lefty ? -1 : 1) * (-AngleHelper.rad(20)) + 0.5f * bodySwing + limbSwing;
            }

        }

    }
    private static void setBladedStance(Player player,HumanoidModel<?> model){
        SaberPoseHandler.setPose(Protosaber.getStance(player),false, model);
    }

    private static void setSaberFlyPose(Player player,HumanoidModel<?> model){
        model.head.x = 0;
        model.hat.x = 0;
        model.body.resetPose();
        model.leftArm.resetPose();
        model.rightArm.resetPose();
        model.leftLeg.resetPose();
        model.rightLeg.resetPose();
        boolean isLeftArmMain = player.getMainArm() == HumanoidArm.LEFT;

        float time = AnimationTickHolder.getTicks(true) + AnimationTickHolder.getPartialTicks();
        float mainCycle = Mth.sin(((float) ((time + 10) * 0.3f / Math.PI)));
        float limbCycle = Mth.sin(((float) (time * 0.3f / Math.PI)));
        float bodySwing = AngleHelper.rad(15 + (mainCycle * 10));
        float limbSwing = AngleHelper.rad(limbCycle * 15);
        if (isLeftArmMain) bodySwing = -bodySwing;
        model.body.zRot = bodySwing;
        model.head.zRot = bodySwing;
        model.hat.zRot = bodySwing;

        ModelPart hangingArm = isLeftArmMain ? model.leftArm : model.rightArm;
        ModelPart otherArm = isLeftArmMain ? model.rightArm : model.leftArm;
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
