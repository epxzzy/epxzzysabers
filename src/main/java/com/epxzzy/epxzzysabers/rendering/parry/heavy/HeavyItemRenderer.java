package com.epxzzy.epxzzysabers.rendering.parry.heavy;

import com.epxzzy.epxzzysabers.rendering.foundation.PartialItemModelRenderer;
import com.epxzzy.epxzzysabers.rendering.parry.light.LightFlourish;
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


public class HeavyItemRenderer {
    public static void setItemPose(ItemStack stack, BakedModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay, LivingEntity entity){
        if (entity instanceof Player pPlayer) {
            HeavyFlourish flourish = HeavyFlourish.fromTagID(((PlayerHelperLmao) pPlayer).getSaberFlourishId());
            boolean lefty = pPlayer.getMainArm() != HumanoidArm.LEFT;
            switch (flourish) {
                case FIGUREEIGHT -> SetFlourishFIGUREEIGHT(stack, model, renderer, transformType, ms, buffer, light, overlay, entity, lefty);
                //side neeeeooommm
                case BEHINDTHEBACK -> SetFlourishBEHINDTHEBACK(stack, model, renderer, transformType, ms, buffer, light, overlay, entity, lefty);
                //circling neeeeoommm
                case SKIPCATCH -> SetFlourishSKIPCATCH(stack, model, renderer, transformType, ms, buffer, light, overlay, entity, lefty);
                //front neeeeooommm
            }
        }
    }
    public static void SetFlourishSKIPCATCH(ItemStack stack, BakedModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay, LivingEntity entity, boolean Lefty){
        float time = AnimationTickHolder.getTicks(false);

        ms.mulPose(Axis.YP.rotationDegrees(Lefty?-27:27));

        ms.translate(-0.1* (Lefty?1:-1),0,0);

        ms.mulPose(Axis.ZP.rotation((float) (ScrollValueHandler.getScroll((AnimationTickHolder.getPartialTicks())) * 3)));

    }

    public static void SetFlourishBEHINDTHEBACK(ItemStack stack, BakedModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay, LivingEntity entity, boolean Lefty){
        float time = AnimationTickHolder.getTicks(false);

        float movement = Mth.sin(((float) ((time) * 3.3 / Math.PI)));
        float movementreverse = Mth.cos(((float) ((time) * 3.3 / Math.PI)));

        float armXmovement = Mth.sin(((float) ((time) * 3.3 / Math.PI)));

        ms.mulPose(Axis.XP.rotation(AngleHelper.rad(90+armXmovement*-45.04)));

        ms.translate((-0.3 +movementreverse*0.6)* (Lefty?1:-1),0.2,0+movement*-0.3);

        ms.mulPose(Axis.ZP.rotation(ScrollValueHandler.getScroll((AnimationTickHolder.getPartialTicks()))* 3));

        ms.pushPose();
        ms.popPose();
    }

    public static void SetFlourishFIGUREEIGHT(ItemStack stack, BakedModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay, LivingEntity entity, boolean Lefty){
        float time = AnimationTickHolder.getTicks(false);

        float motion = Mth.sin((float) (time*2/Math.PI));
        float motionsmooooooth = (float) AnimationHelper.squareInterpolation(motion);

        ms.translate(Lefty?-0.02:0.02,0,0);

        ms.mulPose(Axis.YN.rotationDegrees(5-((motionsmooooooth* 30) + (30* (Lefty?-1:1)))));

        ms.mulPose(Axis.XN.rotation((float) (ScrollValueHandler.getScroll((AnimationTickHolder.getPartialTicks())) * 2)));

    }
}
