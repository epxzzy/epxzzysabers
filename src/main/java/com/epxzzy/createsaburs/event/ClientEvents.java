package com.epxzzy.createsaburs.event;

import com.epxzzy.createsaburs.event.events.EventPosePlayerHand;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientEvents {
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onPoseHand(EventPosePlayerHand event) {
        LivingEntity player = (LivingEntity) event.getEntityIn();
        float f = Minecraft.getInstance().getFrameTime();
        float rightHandRaygunUseProgress = 0.0F;
        float leftHandRaygunUseProgress = 0.0F;

        /*
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof RaygunItem) {
            if (player.getMainArm() == HumanoidArm.RIGHT) {
                rightHandRaygunUseProgress = Math.max(rightHandRaygunUseProgress, RaygunItem.getLerpedUseTime(player.getItemInHand(InteractionHand.MAIN_HAND), f));
            } else {
                leftHandRaygunUseProgress = Math.max(leftHandRaygunUseProgress, RaygunItem.getLerpedUseTime(player.getItemInHand(InteractionHand.MAIN_HAND), f));
            }
        }
        if (player.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof RaygunItem) {
            if (player.getMainArm() == HumanoidArm.RIGHT) {
                leftHandRaygunUseProgress = Math.max(leftHandRaygunUseProgress, RaygunItem.getLerpedUseTime(player.getItemInHand(InteractionHand.OFF_HAND), f));
            } else {
                rightHandRaygunUseProgress = Math.max(rightHandRaygunUseProgress, RaygunItem.getLerpedUseTime(player.getItemInHand(InteractionHand.OFF_HAND), f));
            }
        }
        if (leftHandRaygunUseProgress > 0.0F) {
            float useProgress = Math.min(5F, leftHandRaygunUseProgress) / 5F;
            event.getModel().leftArm.xRot = (event.getModel().head.xRot - (float) Math.toRadians(80F)) * useProgress;
            event.getModel().leftArm.yRot = event.getModel().head.yRot * useProgress;
            event.getModel().leftArm.zRot = 0;
            event.setResult(Event.Result.ALLOW);
        }
        if (rightHandRaygunUseProgress > 0.0F) {
            float useProgress = Math.min(5F, rightHandRaygunUseProgress) / 5F;
            event.getModel().rightArm.xRot = (event.getModel().head.xRot - (float) Math.toRadians(80F)) * useProgress;
            event.getModel().rightArm.yRot = event.getModel().head.yRot * useProgress;
            event.getModel().rightArm.zRot = 0;
            event.setResult(Event.Result.ALLOW);
        }

         */
    }
}
