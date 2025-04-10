package com.epxzzy.createsaburs.rendering;

import com.epxzzy.createsaburs.createsaburs;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueHandler;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModel;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModelRenderer;
import com.simibubi.create.foundation.item.render.PartialItemModelRenderer;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.createmod.catnip.math.AngleHelper;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import java.util.List;

import static com.epxzzy.createsaburs.rendering.ProtosaberItemRenderer.getEntitiesHoldingItem;
import static com.epxzzy.createsaburs.rendering.ProtosaberItemRenderer.isHoldingItemOffHand;

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

        List<LivingEntity> allEntities = getEntitiesHoldingItem(stack);
        for (LivingEntity entity : allEntities) {
            if (transformType.firstPerson() && entity.isUsingItem()) {
                int modifier = leftHand ? -1 : 1;
                ms.mulPose(Axis.ZP.rotationDegrees(modifier * 60));
                ms.pushPose();
                ms.popPose();
            }
        }
        if (transformType != ItemDisplayContext.GUI) {
            float time = AnimationTickHolder.getTicks(false);

            for (LivingEntity entity : allEntities) {
                if (entity.swingTime > 0 || entity.swinging) {
                    if (stack.getOrCreateTag().getCompound("display").getInt("flourish") == 2) {
                        //float movement = Mth.sin(((float) ((time+10) * 5f /Math.PI)));
                        float movement = Mth.sin(((float) ((time) * 5/ Math.PI)));

                        ms.mulPose(Axis.XP.rotation((float) (ScrollValueHandler.getScroll(AnimationTickHolder.getPartialTicks()) * (5))-(45*movement)));
                        ms.mulPose(Axis.ZP.rotationDegrees(movement * 50));
                        //ms.mulPose(Axis.YP.rotationDegrees(movement*10-10));

                        //ms.mulPose(Axis.ZN.rotation(AngleHelper.rad(30)));
                        ms.pushPose();
                        ms.popPose();
                    }

                    if (stack.getOrCreateTag().getCompound("display").getInt("flourish") == 1) {
                        int multiplier = isHoldingItemOffHand(entity, stack)?-1:1;
                        createsaburs.LOGGER.info("nbt offhand: " + stack.getOrCreateTag().getCompound("display").getBoolean("offhand") + " and thought to be: " + (isHoldingItemOffHand(entity, stack) ? "offhand" : "mainhand") + " and multiplier " + multiplier );
                        float movement = Mth.sin(((float) ((time) * 5/ Math.PI)));
                        //ItemStack.isSameItemSameTags(entity.getOffhandItem())
                        ms.mulPose(Axis.XN.rotation(ScrollValueHandler.getScroll((AnimationTickHolder.getPartialTicks() * 10)*multiplier)));
                        //ms.mulPose(Axis.YN.rotation(AngleHelper.rad(30)));
                        ms.mulPose(Axis.ZP.rotation(AngleHelper.rad(movement * 40)));

                        //ms.mulPose(Axis.XP.rotation(-AngleHelper.rad(movement * 60)));
                        //System.out.print("I... AM STEEVE\n");
                    }

                }
            }
        }
        renderer.render(model.getOriginalModel(), light);

        //}

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
