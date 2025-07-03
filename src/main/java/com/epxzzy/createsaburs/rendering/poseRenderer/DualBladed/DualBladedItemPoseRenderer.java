package com.epxzzy.createsaburs.rendering.poseRenderer.DualBladed;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueHandler;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModel;
import com.simibubi.create.foundation.item.render.PartialItemModelRenderer;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.createmod.catnip.math.AngleHelper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;


public class DualBladedItemPoseRenderer {
    public static void setItemPose( ItemStack stack, CustomRenderedItemModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay, LivingEntity entity){
        DualBladedFlourish flourish = DualBladedFlourish.fromTagID(stack.getOrCreateTag().getCompound("display").getInt("flourish"));
        switch (flourish) {
            case SKIPCATCH -> SetFlourishSKIPCATCH(stack, model, renderer, transformType, ms, buffer, light, overlay, entity);
            //front neeeeooommm
            case BEHINDTHEBACK -> SetFlourishBEHINDTHEBACK(stack, model, renderer, transformType, ms, buffer, light, overlay, entity);
            //circling neeeeoommm
            case FIGUREEIGHT -> SetFlourishFIGUREEIGHT(stack, model, renderer, transformType, ms, buffer, light, overlay, entity);
            //side neeeeooommm

        }
    }
    public static void SetFlourishSKIPCATCH(ItemStack stack, CustomRenderedItemModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay, LivingEntity entity){
        float time = AnimationTickHolder.getTicks(false);

        float movement = Mth.sin(((float) ((time) * 2 / Math.PI)));
        float movement2 = Mth.sin(((float) ((time) * 4/ Math.PI)));
        //ItemStack.isSameItemSameTags(entity.getOffhandItem())

        //ms.mulPose(Axis.XN.rotation(ScrollValueHandler.getScroll((AnimationTickHolder.getPartialTicks() * 10)*multiplier)));
        ms.mulPose(Axis.YP.rotationDegrees(-27));
        ms.translate(-0.1,0,0);
        ms.mulPose(Axis.ZP.rotation((float) (ScrollValueHandler.getScroll((AnimationTickHolder.getPartialTicks())) * 2.5)));

    }

    public static void SetFlourishBEHINDTHEBACK(ItemStack stack, CustomRenderedItemModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay, LivingEntity entity){
        float time = AnimationTickHolder.getTicks(false);

        float movement = Mth.sin(((float) ((time) * 3.3 / Math.PI)));
        float movementreverse = Mth.cos(((float) ((time) * 3.3 / Math.PI)));

        float armXmovement = Mth.sin(((float) ((time) * 3.3 / Math.PI)));
        float armZmovement = Mth.sin(((float) ((time+Mth.PI/2) * 3.3 / Math.PI)));


        float movement3 = Mth.sin(((float) ((time) * 4 / Math.PI)));



        //ms.mulPose(Axis.XP.rotation((float) (ScrollValueHandler.getScroll(AnimationTickHolder.getPartialTicks()) * (10)) - (45 * movement)));
        //ms.mulPose(Axis.XP.rotation(AngleHelper.rad(-90*armXmovement)));
        ms.mulPose(Axis.XP.rotation(AngleHelper.rad(90+armXmovement*-45.04)));

        //ms.mulPose(Axis.YP.rotationDegrees(-27));

        //ms.mulPose(Axis.XP.rotationDegrees(-145));
        //ms.mulPose(Axis.XP.rotationDegrees((float) (45.04*movementARM)));
        //ms.translate(-0.3 +movement*0.7,0,-0+movementARM*0.5*1.2);

        ms.translate(-0.3 +movementreverse*0.6,0.2,0+movement*-0.3);
        //ms.mulPose(Axis.ZP.rotation(AngleHelper.rad(-30*-armXmovement)));//- ScrollValueHandler.getScroll(AnimationTickHolder.getPartialTicks() *3)));

        ms.mulPose(Axis.ZP.rotation(ScrollValueHandler.getScroll((AnimationTickHolder.getPartialTicks()))* 3));


        //ms.mulPose(Axis.ZN.rotation(AngleHelper.rad(30)));
        ms.pushPose();
        ms.popPose();
    }

    public static void SetFlourishFIGUREEIGHT(ItemStack stack, CustomRenderedItemModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay, LivingEntity entity){
        float time = AnimationTickHolder.getTicks(false);
        float timeT = AnimationTickHolder.getPartialTicks();
        float squaremovement = (Mth.sin((float) (time*5)) >= 0)? 1:-1;
        float desquaremovement = (Mth.sin((float) (time*1)) >= 0)? 1:-1;
        float unsquaremovement = Mth.sin((float) (time*1));


        float movement = Mth.sin(((float) ((time) * 2 / Math.PI)));
        float movement2 = Mth.sin(((float) ((time) * 4/ Math.PI)));
        //ItemStack.isSameItemSameTags(entity.getOffhandItem())

        //ms.mulPose(Axis.XN.rotation(ScrollValueHandler.getScroll((AnimationTickHolder.getPartialTicks() * 10)*multiplier)));
        //ms.mulPose(Axis.YP.rotationDegrees(27));
        ms.mulPose(Axis.YN.rotation((desquaremovement*AngleHelper.rad(45))+AngleHelper.rad(120)));
        //ms.translate(0.1,0,0);
        //ms.mulPose(Axis.ZP.rotationDegrees(90));

        ms.mulPose(Axis.ZP.rotation((float) (ScrollValueHandler.getScroll((AnimationTickHolder.getPartialTicks())) * 4)));

    }

}
