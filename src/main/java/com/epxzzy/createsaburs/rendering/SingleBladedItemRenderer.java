package com.epxzzy.createsaburs.rendering;

import com.epxzzy.createsaburs.createsaburs;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.createmod.catnip.animation.AnimationTickHolder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueHandler;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModel;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModelRenderer;
import com.simibubi.create.foundation.item.render.PartialItemModelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class SingleBladedItemRenderer extends CustomRenderedItemModelRenderer {
    protected static final PartialModel GEAR_BIT = PartialModel.of(createsaburs.asResource("item/geur"));
    protected static final PartialModel GLOWLY_BIT = PartialModel.of(createsaburs.asResource("item/blade_single"));

    @Override
    protected void render(ItemStack stack, CustomRenderedItemModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType,
                          PoseStack ms, MultiBufferSource buffer, int light, int overlay) {

        boolean leftHand = transformType == ItemDisplayContext.FIRST_PERSON_LEFT_HAND;
        /*if(stack.getOrCreateTag().getBoolean("BlockBoiii")){
            renderer.render(BLOCING_BIT.get(), light);
        }
        else {

         */


        if(transformType.firstPerson() && stack.getOrCreateTag().getBoolean("BlockBoiii")){
            int modifier = leftHand ? -1 : 1;
            //
            ms.mulPose(Axis.ZP.rotationDegrees(modifier * 60));
            ms.pushPose();
            ms.popPose();
        }
        renderer.render(model.getOriginalModel(), light);

        //}

        if (stack.getOrCreateTag().getBoolean("ActiveBoiii")) {
            //stack.getUseAnimation()
            ms.pushPose();
            renderer.renderGlowing(GLOWLY_BIT.get(),  LightTexture.FULL_BRIGHT);

            ms.popPose();
        }


        float xOffset = -1 / 16f;
        ms.translate(0, xOffset * -3, 0);
       /*
        if (stack.getOrCreateTag().getBoolean("ActiveBoiii")) {
            ms.mulPose(Axis.YP.rotationDegrees(ScrollValueHandler.getScroll(AnimationTickHolder.getPartialTicks()) * 20));
        }
        else {
        */
        ms.mulPose(Axis.YP.rotationDegrees(ScrollValueHandler.getScroll(AnimationTickHolder.getPartialTicks()) * (stack.getOrCreateTag().getBoolean("ActiveBoiii")? 30 : 4)));

        //
        //ms.translate(xOffset, 0, 0);


        renderer.render(GEAR_BIT.get(), light);

    }

}
