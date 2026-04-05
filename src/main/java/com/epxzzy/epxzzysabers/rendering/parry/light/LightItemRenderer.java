package com.epxzzy.epxzzysabers.rendering.parry.light;

import com.epxzzy.epxzzysabers.rendering.foundation.PartialItemModelRenderer;
import com.epxzzy.epxzzysabers.util.*;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;


public class LightItemRenderer {
    public static void setItemPose(ItemStack stack, BakedModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay, LivingEntity entity) {
        if (entity instanceof Player pPlayer) {
            LightFlourish flourish = LightFlourish.fromTagID(((PlayerHelperLmao) pPlayer).getSaberFlourishId());
            boolean lefty = pPlayer.getMainArm() != HumanoidArm.LEFT;
            switch (flourish) {
                case XCROSS -> SetFlourishXCROSS(stack, model, renderer, transformType, ms, buffer, light, overlay, entity, lefty);
                //side to side
                case CIRCULAR -> SetFlourishCIRCULAR(stack, model, renderer, transformType, ms, buffer, light, overlay, entity, lefty);
                //the "obi-ani"
                case SPIN -> SetFlourishSPIN(stack, model, renderer, transformType, ms, buffer, light, overlay, entity, lefty);
                //regular onehanded spin
            }
        }

        //((PlayerHelperLmao) entity).LogFlightDetails();
    }

    public static void SetFlourishXCROSS(ItemStack stack, BakedModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay, LivingEntity entity, boolean Lefty) {
        float time = AnimationTickHolder.getTicks(false);
        float motion = Mth.sin((float) (time * 5F / Math.PI));
        float motionsmooooooth = (float) AnimationHelper.squareInterpolation(motion);

        int handMultiplier = Lefty?1:-1;

        ms.mulPose(Axis.YN.rotationDegrees(((motionsmooooooth * -30) + 30) * handMultiplier));

        ms.mulPose(Axis.XN.rotation((ScrollValueHandler.getScroll((AnimationTickHolder.getPartialTicks())) * -5)));

        ms.pushPose();
        ms.popPose();

    }

    public static void SetFlourishCIRCULAR(ItemStack stack, BakedModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay, LivingEntity entity, boolean Lefty) {
        float time = AnimationTickHolder.getTicks(false);
        float movement = Mth.sin(((float) ((time) * 5 / Math.PI)));

        int handMultiplier = Lefty?1:-1;

        ms.mulPose(Axis.XP.rotation((float) ((ScrollValueHandler.getScroll(AnimationTickHolder.getPartialTicks())) * (10) - (45 * movement)) * handMultiplier));
        ms.mulPose(Axis.ZP.rotationDegrees((movement * 50)*handMultiplier));

        ms.pushPose();
        ms.popPose();
    }

    public static void SetFlourishSPIN(ItemStack stack, BakedModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay, LivingEntity entity, boolean Lefty) {
        float time = AnimationTickHolder.getTicks(false);
        float movement = Mth.sin(((float) ((time + 10) * 5f / Math.PI)));
        int handMultiplier = Lefty?-1:1;

        ms.mulPose(Axis.XP.rotation((float) (ScrollValueHandler.getScroll(AnimationTickHolder.getPartialTicks()) * (5))));
        ms.mulPose(Axis.ZN.rotation(AngleHelper.rad(movement)*handMultiplier));

        ms.pushPose();
        ms.popPose();
    }

}
