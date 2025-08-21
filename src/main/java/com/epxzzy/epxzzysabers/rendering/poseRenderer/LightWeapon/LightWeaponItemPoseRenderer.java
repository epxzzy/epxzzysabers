package com.epxzzy.epxzzysabers.rendering.poseRenderer.LightWeapon;

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
        float motion = Mth.sin((float) (time * 5F/Math.PI));
        float motionsmooooooth = (float) AnimationHelper.squareInterpolation(motion);


        ms.mulPose(Axis.YN.rotationDegrees( (motionsmooooooth* -30) + 30 ) );

        ms.mulPose(Axis.XN.rotation((ScrollValueHandler.getScroll((AnimationTickHolder.getPartialTicks())) * -5)));

        ms.pushPose();
        ms.popPose();

    }

    public static void SetFlourishCIRCULAR(ItemStack stack, BakedModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay, LivingEntity entity){
        float time = AnimationTickHolder.getTicks(false);
        float movement = Mth.sin(((float) ((time) * 5/ Math.PI)));

        ms.mulPose(Axis.XP.rotation((float) (ScrollValueHandler.getScroll(AnimationTickHolder.getPartialTicks())) * (10)-(45*movement)));
        ms.mulPose(Axis.ZP.rotationDegrees(movement * 50));

        ms.pushPose();
        ms.popPose();
    }
    public static void SetFlourishSPIN(ItemStack stack, BakedModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay, LivingEntity entity){
        float time = AnimationTickHolder.getTicks(false);
        float movement = Mth.sin(((float) ((time+10) * 5f /Math.PI)));

        ms.mulPose(Axis.XP.rotation((float) (ScrollValueHandler.getScroll(AnimationTickHolder.getPartialTicks()) * (5))));

        ms.mulPose(Axis.ZN.rotation(AngleHelper.rad(movement)));

        ms.pushPose();
        ms.popPose();
    }

}
