package com.epxzzy.createsaburs.rendering;

import com.epxzzy.createsaburs.createsaburs;
import com.epxzzy.createsaburs.item.saburtypes.RotarySaber;
import com.epxzzy.createsaburs.rendering.foundation.CustomRenderedItemModelRenderer;
import com.epxzzy.createsaburs.rendering.foundation.PartialItemModelRenderer;
import com.epxzzy.createsaburs.rendering.foundation.PartialModel;
import com.epxzzy.createsaburs.utils.AngleHelper;
import com.epxzzy.createsaburs.utils.AnimationTickHolder;
import com.epxzzy.createsaburs.utils.ScrollValueHandler;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static com.epxzzy.createsaburs.rendering.poseRenderer.Rotary.RotarySaberItemPoseRenderer.*;
import static com.epxzzy.createsaburs.utils.StackHelper.*;

public class RotarySaberItemRenderer extends CustomRenderedItemModelRenderer {

    public static final PartialModel GLOWLY_BIT = PartialModel.of(createsaburs.asResource("item/additive/rotary_blade"));
    public static final PartialModel SMEAR_BIT = PartialModel.of(createsaburs.asResource("item/additive/rotary_swing"));
    public static final PartialModel SPIN_BIT = PartialModel.of(createsaburs.asResource("item/hilt/spin_bit"));
    public static final PartialModel GUARD_BIT = PartialModel.of(createsaburs.asResource("item/hilt/rotary_guard"));

    @Override
    protected void render(ItemStack stack, BakedModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType,
                          PoseStack ms, MultiBufferSource buffer, int light, int overlay) {

        boolean leftHand = transformType == ItemDisplayContext.FIRST_PERSON_LEFT_HAND;
        /*if(stack.getOrCreateTag().getBoolean("BlockBoiii")){
            renderer.render(BLOCING_BIT.get(), light);
        }
        else {
        */

        List<LivingEntity> allEntities = getEntitiesHoldingItemAnyHand(stack);

        /*
        for (LivingEntity entity : allEntities) {
            if (transformType.firstPerson() && entity.isUsingItem()) {
                int modifier = leftHand ? -1 : 1;
                ms.mulPose(Axis.ZP.rotationDegrees(modifier * 30));
                ms.pushPose();
                ms.popPose();
            }
        }

         */

        renderer.render(GUARD_BIT.get(), light);
        if (stack.getOrCreateTag().getBoolean("ActiveBoiii")) {
            if (stack.getOrCreateTag().getBoolean("FlyBoiii")) {
                ms.mulPose(Axis.ZN.rotation(-ScrollValueHandler.getScroll((float) (AnimationTickHolder.getPartialTicks()) * -1)));
                RenderSmearBlade(stack, model, renderer, transformType, ms, buffer, light, overlay);
            }
            else {
                RenderStaticBlade(stack, model, renderer, transformType, ms, buffer, light, overlay);
            }

        }
        renderer.render(SPIN_BIT.get(), light);

        //if (transformType != ItemDisplayContext.GUI && transformType != ItemDisplayContext.FIXED) {
        //    float time = AnimationTickHolder.getTicks(false);

            //for (LivingEntity entity : allEntities) {
                /*
                if ((entity.swingTime > 0 || entity.swinging) && stack.getOrCreateTag().getBoolean("ActiveBoiii")) {
                    //
                }

                 */
            //}
        //}

        //}


        float xOffset = -1 / 16f;
        //ms.translate(0, xOffset * -3, 0);
        /*
        if (stack.getOrCreateTag().getBoolean("ActiveBoiii")) {
            ms.mulPose(Axis.YP.rotationDegrees(ScrollValueHandler.getScroll(AnimationTickHolder.getPartialTicks()) * 20));
        }
        else {
        */
        //
        //ms.translate(xOffset, 0, 0);





            //stack.getUseAnimation()

            //ms.mulPose(Axis.YP.rotationDegrees(-27));
            //ms.mulPose(Axis.XP.rotationDegrees(90));
            //ms.translate(0.05,0.179,0);
            //ms.translate(0.035,-0.175,0);


            //ms.translate(0,-0.175,0);
            //stack.getUseAnimation()

        //renderer.render(model.get(), light);

    }

}