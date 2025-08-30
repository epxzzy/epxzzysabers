package com.epxzzy.epxzzysabers.rendering;

import com.epxzzy.epxzzysabers.item.Protosaber;
import com.epxzzy.epxzzysabers.item.saburtypes.RotarySaber;
import com.epxzzy.epxzzysabers.item.saburtypes.SingleBladed;
import com.epxzzy.epxzzysabers.rendering.playerposerenderers.PlayerAttackRenderer;
import com.epxzzy.epxzzysabers.rendering.playerposerenderers.PlayerBlockRenderer;
import com.epxzzy.epxzzysabers.rendering.playerposerenderers.PlayerStanceRenderer;
import com.epxzzy.epxzzysabers.rendering.parryrenderers.HeavyWeapon.HeavyWeaponArmPoseRenderer;
import com.epxzzy.epxzzysabers.rendering.parryrenderers.LightWeapon.LightWeaponArmPoseRenderer;
import com.epxzzy.epxzzysabers.utils.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;

import static com.epxzzy.epxzzysabers.rendering.parryrenderers.Rotary.ArmPoseRenderer.setRotaryFlyPose;


public class PlayerPoseRouter {


    public static void beforeSetupAnim(Player player, HumanoidModel<?> model) {
        boolean[] bbc = RotarySaber.checkForSaberFly(player);

        //if(player.getMainHandItem().is(ModTags.Items.LIGHTSABER)) {
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

        //((PlayerHelperLmao) player).LogFlightDetails();
        //epxzzySabers.LOGGER.debug("");
        //
        boolean[] bbc = RotarySaber.checkForSaberFly(player);
        //debug purposes
        if (player.swingTime > 0 || player.getAttackAnim(1) > 0) {
            //epxzzySabers.LOGGER.debug("swing time is: " + player.swingTime + " and attack time is: " + player.getAttackAnim(1));
        }
        //attack
        if ((StackHelper.checkHoldingActiveTag(player, true, ModTags.Items.LIGHT_WEAPON)) && attack > 0 && SaberSwingAnim > 0) {
            setBladedAttack(attack, model, SaberSwingAnim);
            return;
        }
        //block
        if ((StackHelper.checkHoldingActiveTag(player, true, ModTags.Items.LIGHT_WEAPON)) && block > 0 && SaberDefAnim > 0) {
            setBladedBlock(block, model, SaberDefAnim);
            return;
        }
        //stancing
        if ((StackHelper.checkHoldingActiveTag(player, true, ModTags.Items.HEAVY_WEAPON) || StackHelper.checkHoldingActiveTag(player, true, ModTags.Items.LIGHT_WEAPON)) && player.isShiftKeyDown()) {
            setBladedStance(player, model);
            return;
        }
        //flight pose
        if (bbc[0]) {
            setRotaryFlyPose(player, model, bbc[1]);
            return;
        }

        //joint hands spin
        /*
        if(RotarySaber.checkForSaberBlock(player)){
            setRotaryBlockPose(player, model);
            return;
        }

         */

        //heavy weapon attack miss mainhand
        if (StackHelper.checkHoldingActiveTag(player, true, ModTags.Items.HEAVY_WEAPON) && player.swingTime > 0) {
            setDualSaberPose(player.getMainArm() == HumanoidArm.LEFT, false, model, flourish);
            return;
        }

        //heavy weapon attack miss jarr kai type shit
        if (StackHelper.checkHoldingActiveTag(player, false, ModTags.Items.HEAVY_WEAPON) && player.swingTime > 0) {
            setDualSaberPose(player.getMainArm() != HumanoidArm.LEFT, true, model, flourish);
            return;
        }

        //light weapon attack miss mainhand
        if (StackHelper.checkHoldingActiveTag(player, true, ModTags.Items.LIGHT_WEAPON) && player.swingTime > 0) {
            setSingleBladedSaberPose(player.getMainArm() != HumanoidArm.LEFT, false, model, flourish);
            return;
        }

        //light weapon attack miss jarr jai type shit
        if (StackHelper.checkHoldingActiveTag(player, false, ModTags.Items.LIGHT_WEAPON) && player.swingTime > 0) {
            setSingleBladedSaberPose(player.getMainArm() != HumanoidArm.LEFT, true, model, flourish);
            return;
        }
    }

    private static void setDualSaberPose(boolean Lefty, boolean both, HumanoidModel<?> model, int flourish) {
        if (Minecraft.getInstance().isPaused())
            return;

        ModelPart MainArm = Lefty ? model.leftArm : model.rightArm;
        ModelPart otherArm = Lefty ? model.rightArm : model.leftArm;

        HeavyWeaponArmPoseRenderer.setArmPose(flourish, Lefty, both, model);

    }

    private static void setSingleBladedSaberPose(boolean Lefty, boolean both, HumanoidModel<?> model, int flourish) {
        if (Minecraft.getInstance().isPaused())
            return;

        LightWeaponArmPoseRenderer.setArmPose(flourish, Lefty, both, model);
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
