package com.epxzzy.epxzzysabers.rendering.item;

import com.epxzzy.epxzzysabers.epxzzySabers;
import com.epxzzy.epxzzysabers.rendering.foundation.CustomRenderedItemModelRenderer;
import com.epxzzy.epxzzysabers.rendering.foundation.PartialItemModelRenderer;
import com.epxzzy.epxzzysabers.rendering.foundation.PartialModel;
import com.epxzzy.epxzzysabers.util.AnimationTickHolder;
import com.epxzzy.epxzzysabers.util.ScrollValueHandler;
import com.epxzzy.epxzzysabers.util.StackHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import java.util.List;

import static com.epxzzy.epxzzysabers.rendering.parry.rotary.RotaryItemRenderer.*;
import static com.epxzzy.epxzzysabers.util.StackHelper.*;

public class RotarySaberItemRenderer extends CustomRenderedItemModelRenderer {

    public static final PartialModel GLOWLY_BIT = PartialModel.of(epxzzySabers.asResource("item/additive/rotary_blade"));
    public static final PartialModel SMEAR_BIT = PartialModel.of(epxzzySabers.asResource("item/additive/rotary_swing"));
    public static final PartialModel SPIN_BIT = PartialModel.of(epxzzySabers.asResource("item/hilt/spin_bit"));
    public static final PartialModel GUARD_BIT = PartialModel.of(epxzzySabers.asResource("item/hilt/rotary_guard"));

    @Override
    protected void render(ItemStack stack, BakedModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType,
                             PoseStack ms, MultiBufferSource buffer, int light, int overlay) {

        boolean leftHand = transformType == ItemDisplayContext.FIRST_PERSON_LEFT_HAND;
        /*if(stack.getOrCreateTag().getBoolean("BlockBoiii")){
            renderer.renderMid(BLOCING_BIT.get(), light);
        }
        else {
        */

        List<LivingEntity> allEntities = getPlayersHoldingItemRightOrBoth(stack);

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
        /*
        if(stack.getOrCreateTag().getBoolean("BlockBoiii") && !stack.getOrCreateTag().getBoolean("FlyBoiii")){
            ms.mulPose(Axis.YP.rotationDegrees(-27));
            ms.mulPose(Axis.ZN.rotationDegrees(90));
            ms.translate(-0.1,0,0);
        }

         */

        renderer.render(GUARD_BIT.get(), light);
        if (StackHelper.isActive(stack)) {
            if (stack.getOrCreateTag().getBoolean("FlyBoiii")) {
                ms.mulPose(Axis.ZN.rotation(ScrollValueHandler.getScroll((float) ((float) (AnimationTickHolder.getPartialTicks()) * 5))));
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






            //stack.getUseAnimation()

            //ms.mulPose(Axis.YP.rotationDegrees(-27));
            //ms.mulPose(Axis.XP.rotationDegrees(90));
            //ms.translate(0.05,0.179,0);
            //ms.translate(0.035,-0.175,0);


            //ms.translate(0,-0.175,0);
            //stack.getUseAnimation()

        //renderer.renderMid(model.get(), light);

    }

}