package com.epxzzy.epxzzysabers.rendering.poseRenderer.HeavyWeapon;

import com.epxzzy.epxzzysabers.rendering.foundation.PartialItemModelRenderer;
import com.epxzzy.epxzzysabers.utils.AngleHelper;
import com.epxzzy.epxzzysabers.utils.AnimationHelper;
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


public class HeavyWeaponItemPoseRenderer {
    public static void setItemPose(ItemStack stack, BakedModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay, LivingEntity entity){
        HeavyWeaponFlourish flourish = HeavyWeaponFlourish.fromTagID(stack.getOrCreateTag().getCompound("display").getInt("flourish"));
        switch (flourish) {
            case FIGUREEIGHT -> SetFlourishFIGUREEIGHT(stack, model, renderer, transformType, ms, buffer, light, overlay, entity);
            //side neeeeooommm
            case BEHINDTHEBACK -> SetFlourishBEHINDTHEBACK(stack, model, renderer, transformType, ms, buffer, light, overlay, entity);
            //circling neeeeoommm
            case SKIPCATCH -> SetFlourishSKIPCATCH(stack, model, renderer, transformType, ms, buffer, light, overlay, entity);
            //front neeeeooommm

        }
    }
    public static void SetFlourishSKIPCATCH(ItemStack stack, BakedModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay, LivingEntity entity){
        float time = AnimationTickHolder.getTicks(false);

        ms.mulPose(Axis.YP.rotationDegrees(-27));

        ms.translate(-0.1,0,0);

        ms.mulPose(Axis.ZP.rotation((float) (ScrollValueHandler.getScroll((AnimationTickHolder.getPartialTicks())) * 2.5)));

    }

    public static void SetFlourishBEHINDTHEBACK(ItemStack stack, BakedModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay, LivingEntity entity){
        float time = AnimationTickHolder.getTicks(false);

        float movement = Mth.sin(((float) ((time) * 3.3 / Math.PI)));
        float movementreverse = Mth.cos(((float) ((time) * 3.3 / Math.PI)));

        float armXmovement = Mth.sin(((float) ((time) * 3.3 / Math.PI)));

        ms.mulPose(Axis.XP.rotation(AngleHelper.rad(90+armXmovement*-45.04)));

        ms.translate(-0.3 +movementreverse*0.6,0.2,0+movement*-0.3);

        ms.mulPose(Axis.ZP.rotation(ScrollValueHandler.getScroll((AnimationTickHolder.getPartialTicks()))* 3));

        ms.pushPose();
        ms.popPose();
    }

    public static void SetFlourishFIGUREEIGHT(ItemStack stack, BakedModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay, LivingEntity entity){
        float time = AnimationTickHolder.getTicks(false);

        float motion = Mth.sin((float) (time * 5F/Math.PI));
        float motionsmooooooth = (float) AnimationHelper.squareInterpolation(motion);

        ms.translate(-0.02,0,-0.1);

        ms.mulPose(Axis.YN.rotationDegrees( (motionsmooooooth* -30) + 30 ) );

        ms.mulPose(Axis.XN.rotation((float) (ScrollValueHandler.getScroll((AnimationTickHolder.getPartialTicks())) * -4)));

    }
}
