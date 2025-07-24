package com.epxzzy.createsaburs.rendering;

import com.epxzzy.createsaburs.createsaburs;

import com.epxzzy.createsaburs.rendering.foundation.CustomRenderedItemModelRenderer;
import com.epxzzy.createsaburs.rendering.foundation.PartialItemModelRenderer;
import com.epxzzy.createsaburs.rendering.foundation.PartialModel;

//import com.simibubi.create.foundation.item.render.CustomRenderedItemModel;
//import com.simibubi.create.foundation.item.render.CustomRenderedItemModelRenderer;
//import com.simibubi.create.foundation.item.render.PartialItemModelRenderer;
//import dev.engine_room.flywheel.lib.model.baked.PartialModel;

import com.epxzzy.createsaburs.rendering.poseRenderer.SingleBladed.SingleBladedItemPoseRenderer;
import com.epxzzy.createsaburs.utils.ScrollValueHandler;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import java.util.List;

import static com.epxzzy.createsaburs.utils.StackHelper.getEntitiesHoldingItem;


public class SingleBladedItemRenderer extends CustomRenderedItemModelRenderer {
    protected static final PartialModel HILTBIT = PartialModel.of(createsaburs.asResource("item/hilt/mono_hilt"));
    protected static final PartialModel GEAR_BIT = PartialModel.of(createsaburs.asResource("item/additive/gear"));
    protected static final PartialModel GLOWLY_BIT = PartialModel.of(createsaburs.asResource("item/additive/blade_single"));
    public boolean lock;

    @Override
    protected void render(ItemStack stack, BakedModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType,
                          PoseStack ms, MultiBufferSource buffer, int light, int overlay) {

        boolean leftHand = transformType == ItemDisplayContext.FIRST_PERSON_LEFT_HAND;
        /*if(stack.getOrCreateTag().getBoolean("BlockBoiii")){
            renderer.render(BLOCING_BIT.get(), light);
        }
        else {

         */

        List<LivingEntity> allEntities = getEntitiesHoldingItem(stack);
        for (LivingEntity entity : allEntities) {
            if (transformType.firstPerson() && entity.isUsingItem()) {
                int modifier = leftHand ? -1 : 1;
                ms.mulPose(Axis.ZP.rotationDegrees(modifier * 60));
                ms.pushPose();
                ms.popPose();
            }
        }
        if (transformType != ItemDisplayContext.GUI && transformType != ItemDisplayContext.FIXED) {
            float time = AnimationTickHolder.getTicks(false);

            for (LivingEntity entity : allEntities) {
                if ((entity.swingTime > 0 || entity.swinging) && stack.getOrCreateTag().getBoolean("ActiveBoiii")){
                    SingleBladedItemPoseRenderer.setItemPose(stack, model, renderer, transformType, ms, buffer, light, overlay, entity);
                }
            }
        }
        if(!lock) {
            //renderer.render(model, light);
            lock = true;
        }
        //}
        renderer.render(HILTBIT.get(), light);

        if (stack.getOrCreateTag().getBoolean("ActiveBoiii")) {
            //stack.getUseAnimation()
            ms.pushPose();
            renderer.renderGlowing(GLOWLY_BIT.get(), LightTexture.FULL_BRIGHT);

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
        ms.mulPose(Axis.YP.rotationDegrees(ScrollValueHandler.getScroll(AnimationTickHolder.getPartialTicks()) * (stack.getOrCreateTag().getBoolean("ActiveBoiii") ? 30 : 4)));

        //
        //ms.translate(xOffset, 0, 0);


        renderer.render(GEAR_BIT.get(), light);

    }

}
