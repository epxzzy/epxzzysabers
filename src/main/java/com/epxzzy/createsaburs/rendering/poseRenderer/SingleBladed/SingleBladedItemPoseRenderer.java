package com.epxzzy.createsaburs.rendering.poseRenderer.SingleBladed;

import com.epxzzy.createsaburs.createsaburs;
import com.epxzzy.createsaburs.rendering.foundation.CustomRenderedItemModel;
import com.epxzzy.createsaburs.rendering.foundation.PartialItemModelRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueHandler;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.createmod.catnip.math.AngleHelper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import static com.epxzzy.createsaburs.rendering.ProtosaberItemRenderer.isHoldingItemOffHand;


public class SingleBladedItemPoseRenderer {
    public static void setItemPose(ItemStack stack, CustomRenderedItemModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay, LivingEntity entity){
        SingleBladedFlourish flourish = SingleBladedFlourish.fromTagID(stack.getOrCreateTag().getCompound("display").getInt("flourish"));
        switch (flourish) {
            case XCROSS -> SetFlourishXCROSS(stack, model, renderer, transformType, ms, buffer, light, overlay, entity);
            //side to side
            case CIRCULAR -> SetFlourishCIRCULAR(stack, model, renderer, transformType, ms, buffer, light, overlay, entity);
            //the "obi-ani"
        }
    }
    public static void SetFlourishXCROSS(ItemStack stack, CustomRenderedItemModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay, LivingEntity entity){
        float time = AnimationTickHolder.getTicks(false);
        int multiplier = isHoldingItemOffHand(entity, stack)?-1:1;
        //createsaburs.LOGGER.info("nbt offhand: " + stack.getOrCreateTag().getCompound("display").getBoolean("offhand") + " and thought to be: " + (isHoldingItemOffHand(entity, stack) ? "offhand" : "mainhand") + " and multiplier " + multiplier );
        float movement = Mth.sin(((float) ((time) * 6/ Math.PI)));
        float squaremovement = (Mth.sin(time) >= 0)? 1:-1;
        //ItemStack.isSameItemSameTags(entity.getOffhandItem())

        //ms.mulPose(Axis.XN.rotation(ScrollValueHandler.getScroll((AnimationTickHolder.getPartialTicks() * 10)*multiplier)));
        ms.mulPose(Axis.XN.rotation(ScrollValueHandler.getScroll((AnimationTickHolder.getPartialTicks() * 10))*multiplier));
        ms.mulPose(Axis.ZN.rotation(AngleHelper.rad(squaremovement*25)));
    }

    public static void SetFlourishCIRCULAR(ItemStack stack, CustomRenderedItemModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay, LivingEntity entity){
        float time = AnimationTickHolder.getTicks(false);
        float movement = Mth.sin(((float) ((time) * 5/ Math.PI)));

        ms.mulPose(Axis.XP.rotation((float) (ScrollValueHandler.getScroll(AnimationTickHolder.getPartialTicks())) * (10)-(45*movement)));
        ms.mulPose(Axis.ZP.rotationDegrees(movement * 50));
        //ms.mulPose(Axis.YP.rotationDegrees(movement*10-10));

        //ms.mulPose(Axis.ZN.rotation(AngleHelper.rad(30)));
        ms.pushPose();
        ms.popPose();
    }
}
