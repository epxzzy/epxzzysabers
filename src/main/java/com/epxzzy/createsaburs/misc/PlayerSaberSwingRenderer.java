package com.epxzzy.createsaburs.misc;

import com.epxzzy.createsaburs.createsaburs;
import com.epxzzy.createsaburs.item.protosaber;
import com.epxzzy.createsaburs.item.saburtypes.SingleBladed;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.createmod.catnip.math.AngleHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;

public class PlayerSaberSwingRenderer {

    public static void beforeSetupAnim(Player player, HumanoidModel<?> model) {
        if (protosaber.checkForSaberEquipment(player, true) && player.swingTime > 0){
            model.leftArm.resetPose();
            model.rightArm.resetPose();
        }

        if (SingleBladed.checkForSaberEquipment(player, true) && player.swingTime > 0 ){
            model.rightArm.resetPose();
            model.rightArm.resetPose();
        }
    }

    public static void afterSetupAnim(Player player, HumanoidModel<?> model) {
        int flourish = player.getMainHandItem().getOrCreateTag().getCompound("display").getInt("flourish");
        if (protosaber.checkForSaberEquipment(player, true) && player.swingTime > 0) {
            setDualSaberPose(player.getMainArm() == HumanoidArm.LEFT, false, model);
        }
        if(protosaber.checkForSaberEquipment(player, false) && protosaber.checkForSaberEquipment(player, true) && player.swingTime > 0){
            setDualSaberPose( player.getMainArm() != HumanoidArm.LEFT, true, model);
        }
        if(SingleBladed.checkForSaberEquipment(player, true) && player.swingTime >0){
            setSingleBladedSaberPose(player.getMainArm() != HumanoidArm.LEFT, false, model, flourish);
        }
        if(SingleBladed.checkForSaberEquipment(player, false) && SingleBladed.checkForSaberEquipment(player, true) && player.swingTime > 0){
            setSingleBladedSaberPose( player.getMainArm() != HumanoidArm.LEFT, true, model, flourish);
        }

    }

    private static void setDualSaberPose(boolean Lefty,boolean both, HumanoidModel<?> model) {
        if (Minecraft.getInstance().isPaused())
            return;

        model.leftArm.resetPose();
        model.rightArm.resetPose();

        float time = AnimationTickHolder.getTicks(true) + AnimationTickHolder.getPartialTicks();
        float mainCycle = Mth.sin(((float) ((time + 10) * 0.3f / Math.PI)));
        float limbCycle = Mth.sin(((float) (time * 0.3f / Math.PI)));
        float bodySwing = AngleHelper.rad(15 + (mainCycle * 10));
        float limbSwing = AngleHelper.rad(limbCycle * 15);
        if(Lefty) bodySwing = -bodySwing;
        //model.body.zRot = bodySwing;
        //model.head.zRot = bodySwing;
        //model.hat.zRot = bodySwing;

        ModelPart hangingArm = Lefty ? model.leftArm : model.rightArm;
        ModelPart otherArm = Lefty ? model.rightArm : model.leftArm;
        hangingArm.y -= 3;

        float offsetX = model.rightArm.x;
        float offsetY = model.rightArm.y;
//		model.rightArm.x = offsetX * Mth.cos(bodySwing) - offsetY * Mth.sin(bodySwing);
//		model.rightArm.y = offsetX * Mth.sin(bodySwing) + offsetY * Mth.cos(bodySwing);
        float armPivotX = offsetX * Mth.cos(bodySwing) - offsetY * Mth.sin(bodySwing) + 4.5f;
        float armPivotY = offsetX * Mth.sin(bodySwing) + offsetY * Mth.cos(bodySwing) + 2;

        hangingArm.xRot = -AngleHelper.rad(bodySwing+150);
        hangingArm.zRot = (Lefty? -1 : 1) * AngleHelper.rad(15);

        if(both) {
            otherArm.xRot = -AngleHelper.rad(bodySwing+150);
            otherArm.zRot = (Lefty? 1 : -1) * AngleHelper.rad(15);
        }

        if(!both) {
            offsetX = otherArm.x;
            offsetY = otherArm.y;
            otherArm.x = offsetX * Mth.cos(bodySwing) - offsetY * Mth.sin(bodySwing);
            //otherArm.y = offsetX * Mth.sin(bodySwing) + offsetY * Mth.cos(bodySwing);
            otherArm.zRot = (Lefty ? -1 : 1) * (-AngleHelper.rad(20)) + 0.5f * bodySwing + limbSwing;

            //model.hat.x -= armPivotX;
            //model.head.x -= armPivotX;
            otherArm.x -= armPivotX;
        }
        //model.hat.y -= armPivotY;
        //model.head.y -= armPivotY;
        //otherArm.y -= armPivotY;
    }
    private static void setSingleBladedSaberPose(boolean Lefty,boolean both, HumanoidModel<?> model, int flourish) {
        if (Minecraft.getInstance().isPaused())
            return;


        if(flourish == 1) {
            createsaburs.LOGGER.info("the xcross");
            //the x-cross
            float time = AnimationTickHolder.getTicks(true) + AnimationTickHolder.getPartialTicks();
            float mainCycle = Mth.sin(((float) ((time + 10) * 0.3f / Math.PI)));
            float bodySwing = AngleHelper.rad(15 + (mainCycle * 10));
            if (Lefty) bodySwing = -bodySwing;

            ModelPart MainArm= Lefty ? model.rightArm: model.leftArm;
            ModelPart otherArm = Lefty ? model.leftArm: model.rightArm;
            MainArm.resetPose();

            MainArm.xRot = Mth.clamp(0f, -1.2F, 1.2F) - 1.4835298F;
            MainArm.yRot = AngleHelper.rad(-30);
            //hangingArm.xRot = -AngleHelper.rad(bodySwing+150);
            //hangingArm.zRot = (Lefty? -1 : 1) * AngleHelper.rad(15);
            if (both) {
                otherArm.resetPose();
                //otherArm.xRot = -AngleHelper.rad(bodySwing + 150);
                otherArm.xRot = Mth.clamp(model.head.xRot, -1.2F, 1.2F) - 1.4835298F;
                MainArm.yRot = AngleHelper.rad(0);
                //otherArm.zRot = (Lefty ? 1 : -1) * AngleHelper.rad(15);
            }

        }


        if(flourish == 2){
            createsaburs.LOGGER.info("the circular");
            //the circular
            float time = AnimationTickHolder.getTicks(true) + AnimationTickHolder.getPartialTicks();
            float movement = Mth.sin(((float) ((time) * 3.3 / Math.PI)));
            float movement2 = Mth.sin(((float) ((time) * 4 / Math.PI)));

            ModelPart otherArm = Lefty ? model.leftArm : model.rightArm;
            ModelPart mainArm = Lefty ? model.rightArm : model.leftArm;
            mainArm.resetPose();
            //hangingArm.y -= 3;
            mainArm.xRot = AngleHelper.rad(-45.04*movement-10);
            mainArm.zRot = AngleHelper.rad(movement2*-30);


            //hangingArm.xRot = -AngleHelper.rad(bodySwing+150);
            //hangingArm.zRot = (Lefty? -1 : 1) * AngleHelper.rad(15);
            if(both) {
                otherArm.resetPose();
                otherArm.xRot = Mth.cos(0.6662F + (float) Math.PI) * 2.0F * 0.5F;
                otherArm.zRot = -AngleHelper.rad(20);
            }
            if(!both) {
                //otherArm.zRot = (Lefty ? -1 : 1) * (-AngleHelper.rad(20)) + 0.5f * bodySwing + limbSwing;
            }

        }

    }
}
