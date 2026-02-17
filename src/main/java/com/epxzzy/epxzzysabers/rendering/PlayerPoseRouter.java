package com.epxzzy.epxzzysabers.rendering;

import com.epxzzy.epxzzysabers.item.Protosaber;
import com.epxzzy.epxzzysabers.item.types.RotarySaber;
import com.epxzzy.epxzzysabers.item.types.SingleBladed;
import com.epxzzy.epxzzysabers.rendering.playerposerenderers.PlayerAttackRenderer;
import com.epxzzy.epxzzysabers.rendering.playerposerenderers.PlayerBlockRenderer;
import com.epxzzy.epxzzysabers.rendering.playerposerenderers.PlayerStanceRenderer;
import com.epxzzy.epxzzysabers.rendering.parry.heavy.HeavyArmRenderer;
import com.epxzzy.epxzzysabers.rendering.parry.light.LightArmRenderer;
import com.epxzzy.epxzzysabers.util.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;

import static com.epxzzy.epxzzysabers.rendering.parry.rotary.RotaryPoseRenderer.setRotaryFlyPose;


public class PlayerPoseRouter {


    public static void beforeSetupAnim(Player player, HumanoidModel<?> model) {
        boolean[] bbc = RotarySaber.checkForSaberFly(player);

        //if(player.getMainHandItem().is(SaberTags.Items.LIGHTSABER)) {
        model.head.resetPose();
        model.hat.resetPose();
        model.body.resetPose();
        model.leftArm.resetPose();
        model.rightArm.resetPose();
        model.leftLeg.resetPose();
        model.rightLeg.resetPose();
        //}

        if (Protosaber.checkForSaberEquipment(player, true) && player.swingTime > 0) {
            model.leftArm.resetPose();
            model.rightArm.resetPose();
        }

        if (SingleBladed.checkForSaberEquipment(player, true) && player.swingTime > 0) {
            model.rightArm.resetPose();
            model.rightArm.resetPose();
        }

        if ((Protosaber.checkForSaberBlock(player) || SingleBladed.checkForSaberBlock(player)) && player.isShiftKeyDown()) {
            model.rightArm.resetPose();
            model.leftArm.resetPose();
            model.head.resetPose();
            model.body.resetPose();
            model.leftLeg.resetPose();
            model.rightLeg.resetPose();
        }

        if (bbc[0]) {
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
        int block = player.getMainHandItem().getOrCreateTag().getCompound("display").getInt("blk");
        int attack = player.getMainHandItem().getOrCreateTag().getCompound("display").getInt("atk");
        float SaberSwingAnim = ((PlayerHelperLmao) player).getSaberAttackAnim();
        float SaberDefAnim = ((PlayerHelperLmao) player).getSaberDefendAnim();
        //[ flight, mainhand ]
        boolean[] isInFlight = RotarySaber.checkForSaberFly(player);

        //((PlayerHelperLmao) player).LogFlightDetails();
        //epxzzySabers.LOGGER.debug("");


        //debug purposes
        /*
        if (player.swingTime > 0 || player.getAttackAnim(1) > 0) {
            epxzzySabers.LOGGER.debug("swing time is: " + player.swingTime + " and attack time is: " + player.getAttackAnim(1));
        }
         */

        //attack hit
        if ((TagHelper.checkMainhandActiveLightWeapon(player) && attack > 0 && SaberSwingAnim > 0)) {
            setBladedAttack(attack, model, SaberSwingAnim);
            return;
        }

        //block hit
        if (TagHelper.checkMainhandActiveLightWeapon(player) && block > 0 && SaberDefAnim > 0) {
            setBladedBlock(block, model, SaberDefAnim);
            return;
        }

        //stance pose
        if ((TagHelper.checkMainhandPoseableWeapon(player) && player.isShiftKeyDown())) {
            setBladedStance(player, model);
            return;
        }

        //flight pose
        if (isInFlight[0]) {
            setRotaryFlyPose(player, model, isInFlight[1]);
            return;
        }

        //attack miss//empty swing
        if(player.swingTime > 0){
            //heavy weapon mainhand
            if (TagHelper.checkMainhandActiveHeavyWeapon(player)) {
                setDualSaberPose(player.getMainArm() == HumanoidArm.LEFT, false, model, flourish);
                return;
            }

            //heavy weapon jarr kai type shit
            if (TagHelper.checkActiveHeavyWeapon(player, false)) {
                setDualSaberPose(player.getMainArm() != HumanoidArm.LEFT, true, model, flourish);
                return;
            }

            //light weapon mainhand
            if (TagHelper.checkMainhandActiveLightWeapon(player)) {
                setSingleBladedSaberPose(player.getMainArm() != HumanoidArm.LEFT, false, model, flourish);
                return;
            }

            //light weapon jarr jai type shit
            if (TagHelper.checkActiveLightWeapon(player, false)) {
                setSingleBladedSaberPose(player.getMainArm() != HumanoidArm.LEFT, true, model, flourish);
            }

        }
    }

    private static void setDualSaberPose(boolean Lefty, boolean both, HumanoidModel<?> model, int flourish) {
        if (Minecraft.getInstance().isPaused())
            return;

        ModelPart MainArm = Lefty ? model.leftArm : model.rightArm;
        ModelPart otherArm = Lefty ? model.rightArm : model.leftArm;

        HeavyArmRenderer.setArmPose(flourish, Lefty, both, model);

    }

    private static void setSingleBladedSaberPose(boolean Lefty, boolean both, HumanoidModel<?> model, int flourish) {
        if (Minecraft.getInstance().isPaused())
            return;

        LightArmRenderer.setArmPose(flourish, Lefty, both, model);
    }

    private static void setBladedStance(Player player, HumanoidModel<?> model) {
        PlayerStanceRenderer.setPose(Protosaber.getStance(player), false, model);
    }

    private static void setBladedAttack(int attack, HumanoidModel<?> model, float lerper) {
        PlayerAttackRenderer.setPose(attack, false, model, lerper);
    }

    private static void setBladedBlock(int block, HumanoidModel<?> model, float lerper) {
        PlayerBlockRenderer.setPose(block, false, model, lerper);
    }


}
