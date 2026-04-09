package com.epxzzy.epxzzysabers.rendering;

import com.epxzzy.epxzzysabers.epxzzySabers;
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
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;

import static com.epxzzy.epxzzysabers.rendering.playerposerenderers.PlayerMiscRenderer.setRotaryFlyPose;


public class PlayerPoseRouter {
    public static int tempDebugVar = 0;

    public static void beforeSetupAnim(Player player, HumanoidModel<?> model) {
        if (Minecraft.getInstance().isPaused())
            return;

        boolean stancing = ((PlayerHelperLmao) player).getSaberStanceDown();

        //fix rotation bug #3, uses magic number cooldown
        //actaully listened to my own advice for once going against optimising the fuck out of it
        if (true) {
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
        /*
        int flourish = player.getMainHandItem().getOrCreateTag().getCompound("display").getInt("flourish");
        int block = player.getMainHandItem().getOrCreateTag().getCompound("display").getInt("blk");
        int attack = player.getMainHandItem().getOrCreateTag().getCompound("display").getInt("atk");
        */
        int flourish = ((PlayerHelperLmao) player).getSaberFlourishId();
        int block = ((PlayerHelperLmao) player).getSaberBlockForm();
        int attack = ((PlayerHelperLmao) player).getSaberAttackForm();


        float SaberSwingAnim = ((PlayerHelperLmao) player).getSaberAttackAnim();
        float SaberDefAnim = ((PlayerHelperLmao) player).getSaberDefendAnim();
        boolean stancing = ((PlayerHelperLmao) player).getSaberStanceDown();
        int stanceform = ((PlayerHelperLmao) player).getStancePreference();

        boolean isInFlight = RotarySaber.checkForSaberFly(player);

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
            setBladedAttack(attack, model, SaberSwingAnim, player.getMainArm() == HumanoidArm.LEFT);
            return;
        }

        //block hit
        if (TagHelper.checkMainhandActiveLightWeapon(player) && block > 0 && SaberDefAnim > 0) {
            setBladedBlock(block, model, SaberDefAnim,player.getMainArm() == HumanoidArm.LEFT);
            return;
        }

        //held stance pose
        if (stancing) {
            setBladedStance(player, model, stanceform);
            return;
        }

        //flight pose
        if (isInFlight) {
            setRotaryFlyPose(player, model);
            return;
        }

        //attack miss//empty swing
        if (player.swingTime > 0 && !(SaberSwingAnim > 0)) {
            //heavy weapon mainhand
            if (TagHelper.checkMainhandActiveHeavyWeapon(player)) {
                setDualSaberPose(player.getMainArm() == HumanoidArm.LEFT, false, model, flourish);
                return;
            }

            //heavy weapon offhand todo- check mainhand
            if (TagHelper.checkActiveHeavyWeapon(player, false)) {
                //setDualSaberPose(player.getMainArm() == HumanoidArm.LEFT, false, model, flourish);
                //return;
            }

            //light weapon mainhand
            if (TagHelper.checkMainhandActiveLightWeapon(player)) {
                setSingleBladedSaberPose(player.getMainArm() == HumanoidArm.LEFT, false, model, flourish);
                return;
            }

            //light weapon offhand todo- check mainhand
            if (TagHelper.checkActiveLightWeapon(player, false)) {
                //setSingleBladedSaberPose(player.getMainArm() == HumanoidArm.LEFT, false, model, flourish);
            }
        }
    }

    private static void setDualSaberPose(boolean Lefty, boolean both, HumanoidModel<?> model, int flourish) {
        HeavyArmRenderer.setArmPose(flourish, Lefty, both, model);

        if(tempDebugVar != flourish){
            //epxzzySabers.LOGGER.info("heavy flourish given: " + flourish);
        }
        tempDebugVar = flourish == tempDebugVar?tempDebugVar:flourish;

    }

    private static void setSingleBladedSaberPose(boolean Lefty, boolean both, HumanoidModel<?> model, int flourish) {
        LightArmRenderer.setArmPose(flourish, Lefty, both, model);

        if(tempDebugVar != flourish){
            //epxzzySabers.LOGGER.info("light flourish given: " + flourish);
        }
        tempDebugVar = flourish == tempDebugVar?tempDebugVar:flourish;
    }

    private static void setBladedStance(Player player, HumanoidModel<?> model, int form) {
        PlayerStanceRenderer.setStance(form, false, model);
    }

    private static void setBladedAttack(int attack, HumanoidModel<?> model, float lerper, boolean lefty) {
        PlayerAttackRenderer.setPose(attack, lefty, model, lerper);
    }

    private static void setBladedBlock(int block, HumanoidModel<?> model, float lerper, boolean lefty) {
        PlayerBlockRenderer.setPose(block, lefty, model, lerper);
    }


}
