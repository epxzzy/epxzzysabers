package com.epxzzy.epxzzysabers.rendering.poseRenderer.LightWeapon;

import com.epxzzy.epxzzysabers.rendering.foundation.PartialItemModelRenderer;
import com.epxzzy.epxzzysabers.utils.AngleHelper;
import com.epxzzy.epxzzysabers.utils.AnimationTickHolder;
import com.epxzzy.epxzzysabers.utils.ScrollValueHandler;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import static com.epxzzy.epxzzysabers.utils.StackHelper.isHoldingItemOffHand;


public class LightWeaponItemPoseRenderer {
    public static void setItemPose(ItemStack stack, BakedModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay, LivingEntity entity){
        LightWeaponFlourish flourish = LightWeaponFlourish.fromTagID(stack.getOrCreateTag().getCompound("display").getInt("flourish"));
        switch (flourish) {
            case XCROSS -> SetFlourishXCROSS(stack, model, renderer, transformType, ms, buffer, light, overlay, entity);
            //side to side
            case CIRCULAR -> SetFlourishCIRCULAR(stack, model, renderer, transformType, ms, buffer, light, overlay, entity);
            //the "obi-ani"
            case SPIN -> SetFlourishSPIN(stack, model, renderer, transformType, ms, buffer, light, overlay, entity);
            //regular onehanded spin

        }
        //((PlayerHelperLmao) entity).LogFlightDetails();
    }
    public static void SetFlourishXCROSS(ItemStack stack, BakedModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay, LivingEntity entity){
        float time = AnimationTickHolder.getTicks(false);
        int multiplier = isHoldingItemOffHand(entity, stack)?-1:1;
        //epxzzySabers.LOGGER.info("nbt offhand: " + stack.getOrCreateTag().getCompound("display").getBoolean("offhand") + " and thought to be: " + (isHoldingItemOffHand(entity, stack) ? "offhand" : "mainhand") + " and multiplier " + multiplier );
        float movement = Mth.sin(((float) ((time) * 6/ Math.PI)));
        float squaremovement = (Mth.sin(time) >= 0)? 1:-1;
        //ItemStack.isSameItemSameTags(entity.getOffhandItem())

        //ms.mulPose(Axis.XN.rotation(ScrollValueHandler.getScroll((AnimationTickHolder.getPartialTicks() * 10)*multiplier)));
        ms.mulPose(Axis.XN.rotation(ScrollValueHandler.getScroll((AnimationTickHolder.getPartialTicks() * 10))*multiplier));
        ms.mulPose(Axis.ZN.rotation(AngleHelper.rad(squaremovement*25)));

        ms.pushPose();
        ms.popPose();

    }

    public static void SetFlourishCIRCULAR(ItemStack stack, BakedModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay, LivingEntity entity){
        float time = AnimationTickHolder.getTicks(false);
        float movement = Mth.sin(((float) ((time) * 5/ Math.PI)));

        ms.mulPose(Axis.XP.rotation((float) (ScrollValueHandler.getScroll(AnimationTickHolder.getPartialTicks())) * (10)-(45*movement)));
        ms.mulPose(Axis.ZP.rotationDegrees(movement * 50));
        //ms.mulPose(Axis.YP.rotationDegrees(movement*10-10));

        //ms.mulPose(Axis.ZN.rotation(AngleHelper.rad(30)));
        ms.pushPose();
        ms.popPose();
    }
    public static void SetFlourishSPIN(ItemStack stack, BakedModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay, LivingEntity entity){
        float time = AnimationTickHolder.getTicks(false);
        float movement = Mth.sin(((float) ((time+10) * 5f /Math.PI)));
        ms.mulPose(Axis.XP.rotation((float) (ScrollValueHandler.getScroll(AnimationTickHolder.getPartialTicks()) * (5))));
        //ms.mulPose(Axis.YP.rotation(AngleHelper.rad(movement * 5)));
        ms.mulPose(Axis.ZN.rotation(AngleHelper.rad(movement)));
        //ms.mulPose(Axis.ZN.rotation(AngleHelper.rad(30)));

        ms.pushPose();
        ms.popPose();
    }

}
