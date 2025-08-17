package com.epxzzy.epxzzysabers.rendering.poseRenderer.Rotary;

import com.epxzzy.epxzzysabers.rendering.foundation.PartialItemModelRenderer;
import com.epxzzy.epxzzysabers.rendering.poseRenderer.LightWeapon.LightWeaponFlourish;
import com.epxzzy.epxzzysabers.utils.AngleHelper;
import com.epxzzy.epxzzysabers.utils.AnimationTickHolder;
import com.epxzzy.epxzzysabers.utils.ScrollValueHandler;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import static com.epxzzy.epxzzysabers.rendering.RotarySaberItemRenderer.*;
import static com.epxzzy.epxzzysabers.utils.StackHelper.isHoldingItemOffHand;


public class RotarySaberItemPoseRenderer {
    public static void setItemPose(ItemStack stack, BakedModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay, LivingEntity entity) {
        LightWeaponFlourish flourish = LightWeaponFlourish.fromTagID(stack.getOrCreateTag().getCompound("display").getInt("flourish"));
        if (stack.getOrCreateTag().getBoolean("FlyBoiii")) {
            setSuperSpin(stack, model, renderer, transformType, ms, buffer, light, overlay, entity);
            return;
        }
        switch (flourish) {
            case XCROSS -> SetFlourishXCROSS(stack, model, renderer, transformType, ms, buffer, light, overlay, entity);
            //side to side
            case CIRCULAR ->
                    SetFlourishCIRCULAR(stack, model, renderer, transformType, ms, buffer, light, overlay, entity);
            //the "obi-ani"

            default ->
                setDefault(stack, model, renderer, transformType, ms, buffer, light, overlay, entity);
        }
    }

    public static void SetFlourishXCROSS(ItemStack stack, BakedModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay, LivingEntity entity) {
        float time = AnimationTickHolder.getTicks(false);
        int multiplier = isHoldingItemOffHand(entity, stack) ? -1 : 1;
        //epxzzySabers.LOGGER.info("nbt offhand: " + stack.getOrCreateTag().getCompound("display").getBoolean("offhand") + " and thought to be: " + (isHoldingItemOffHand(entity, stack) ? "offhand" : "mainhand") + " and multiplier " + multiplier );
        float movement = Mth.sin(((float) ((time) * 6 / Math.PI)));
        float squaremovement = (Mth.sin(time) >= 0) ? 1 : -1;
        //ItemStack.isSameItemSameTags(entity.getOffhandItem())

        //ms.mulPose(Axis.XN.rotation(ScrollValueHandler.getScroll((AnimationTickHolder.getPartialTicks() * 10)*multiplier)));
        ms.mulPose(Axis.XN.rotation(ScrollValueHandler.getScroll((AnimationTickHolder.getPartialTicks() * 10)) * multiplier));
        ms.mulPose(Axis.ZN.rotation(AngleHelper.rad(squaremovement * 25)));
    }

    public static void SetFlourishCIRCULAR(ItemStack stack, BakedModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay, LivingEntity entity) {
        float time = AnimationTickHolder.getTicks(false);
        float movement = Mth.sin(((float) ((time) * 5 / Math.PI)));

        ms.mulPose(Axis.XP.rotation((float) (ScrollValueHandler.getScroll(AnimationTickHolder.getPartialTicks())) * (10) - (45 * movement)));
        ms.mulPose(Axis.ZP.rotationDegrees(movement * 50));
        //ms.mulPose(Axis.YP.rotationDegrees(movement*10-10));

        //ms.mulPose(Axis.ZN.rotation(AngleHelper.rad(30)));
        ms.pushPose();
        ms.popPose();
    }

    public static void setSuperSpin(ItemStack stack, BakedModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay, LivingEntity entity) {
        RenderSmearBlade(stack, model, renderer, transformType, ms, buffer, light, overlay);
    }

    public static void setDefault(ItemStack stack, BakedModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay, LivingEntity entity) {
        RenderStaticBlade(stack, model, renderer, transformType, ms, buffer, light, overlay);
    }


    public static void RenderSmearBlade(ItemStack stack, BakedModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        //if (stack.getOrCreateTag().getBoolean("FlyBoiii")) {
            ms.pushPose();
            renderer.renderGlowing(SMEAR_BIT.get(), LightTexture.FULL_BRIGHT);
            ms.popPose();
        //}
    }

    public static void RenderStaticBlade(ItemStack stack, BakedModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        //if (stack.getOrCreateTag().getBoolean("ActiveBoiii") && !stack.getOrCreateTag().getBoolean("FlyBoiii")) {
            ms.pushPose();
            renderer.renderGlowing(GLOWLY_BIT.get(), LightTexture.FULL_BRIGHT);
            ms.popPose();
        //}
    }
}
