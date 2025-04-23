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

public class PlayerSaberRenderer {
    public static boolean IsPlayerStationary(Player player){

        //createsaburs.LOGGER.info("getdeltamovementplayer "+ player.getDeltaMovement().length());
        return !(player.getDeltaMovement().length() > 0.08);

        //return x+y+z;
        //return true;
    }

    public static void beforeSetupAnim(Player player, HumanoidModel<?> model) {
        if (protosaber.checkForSaberEquipment(player, true) && player.swingTime > 0) {
            model.leftArm.resetPose();
            model.rightArm.resetPose();
        }

        if (SingleBladed.checkForSaberEquipment(player, true) && player.swingTime > 0) {
            model.rightArm.resetPose();
            model.rightArm.resetPose();
        }
        if(protosaber.checkForSaberBlock(player)&& IsPlayerStationary(player)){//&& player.isShiftKeyDown()){
            model.rightArm.resetPose();
            model.leftArm.resetPose();
            model.head.resetPose();
            model.body.resetPose();
            model.leftLeg.resetPose();
            model.rightLeg.resetPose();
            
        };
    }

    public static void afterSetupAnim(Player player, HumanoidModel<?> model) {
        int flourish = player.getMainHandItem().getOrCreateTag().getCompound("display").getInt("flourish");
        if (protosaber.checkForSaberEquipment(player, true) && player.swingTime > 0) {
            setDualSaberPose(player.getMainArm() == HumanoidArm.LEFT, false, model, flourish);
        }
        if (protosaber.checkForSaberEquipment(player, false) && protosaber.checkForSaberEquipment(player, true) && player.swingTime > 0) {
            setDualSaberPose(player.getMainArm() != HumanoidArm.LEFT, true, model, flourish);
        }
        if (SingleBladed.checkForSaberEquipment(player, true) && player.swingTime > 0) {
            setSingleBladedSaberPose(player.getMainArm() != HumanoidArm.LEFT, false, model, flourish);
        }
        if (SingleBladed.checkForSaberEquipment(player, false) && SingleBladed.checkForSaberEquipment(player, true) && player.swingTime > 0) {
            setSingleBladedSaberPose(player.getMainArm() != HumanoidArm.LEFT, true, model, flourish);
        }
        if(protosaber.checkForSaberBlock(player)&& IsPlayerStationary(player)){//&& player.isShiftKeyDown()){
            setDualBladedAttackStance(model);
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
    private static void setDualBladedAttackStance(HumanoidModel<?> model){
        //model.head.setPos(1,-1,0);
        model.head.x = 1;
        model.head.y = 0;

        model.body.setPos(0,-1,0);
        model.body.setRotation(AngleHelper.rad( 5), AngleHelper.rad( -57.6),0);

        model.rightArm.setPos(-2.5f,1, -6.5f);
        model.rightArm.setRotation(AngleHelper.rad(-90),0, AngleHelper.rad(90));

        model.leftArm.setPos(4.5f,3,6);
        model.leftArm.setRotation(AngleHelper.rad(-72.5), AngleHelper.rad(-10), 0);

        //model.rightLeg.setPos((float) -1.5,0, (float) -3.5);
        model.rightLeg.x = (float) -1.5;
        model.rightLeg.y = 11;
        model.rightLeg.z = (float) -3.5;
        model.rightLeg.setRotation(AngleHelper.rad(-10), AngleHelper.rad(5),0);

        model.leftLeg.z = 3;
        model.leftLeg.y = 11;
        model.leftLeg.setRotation(AngleHelper.rad(-2), (float) AngleHelper.rad(-67.5),AngleHelper.rad(-10));
    }
}
