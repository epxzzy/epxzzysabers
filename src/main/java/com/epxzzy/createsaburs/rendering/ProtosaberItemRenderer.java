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
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ProtosaberItemRenderer extends CustomRenderedItemModelRenderer {

    protected static final PartialModel GEAR_BIT = PartialModel.of(createsaburs.asResource("item/geur"));
    protected static final PartialModel GLOWLY_BIT = PartialModel.of(createsaburs.asResource("item/blade"));

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
            if(transformType.firstPerson() && entity.isUsingItem()){
                int modifier = leftHand ? -1 : 1;
                ms.mulPose(Axis.ZP.rotationDegrees(modifier * 30));
                ms.pushPose();
                ms.popPose();
            }
        }

        if (transformType != ItemDisplayContext.GUI && transformType != ItemDisplayContext.FIXED) {
            float time = AnimationTickHolder.getTicks(false);

            for (LivingEntity entity : allEntities) {
                if (entity.swingTime > 0 || entity.swinging) {
                    if (stack.getOrCreateTag().getCompound("display").getInt("flourish") == 2) {
                        //float movement = Mth.sin(((float) ((time+10) * 5f /Math.PI)));
                        float movement = Mth.sin(((float) ((time) * 5 / Math.PI)));

                        ms.mulPose(Axis.XP.rotation((float) (ScrollValueHandler.getScroll(AnimationTickHolder.getPartialTicks()) * (10)) - (45 * movement)));
                        ms.mulPose(Axis.ZP.rotationDegrees(movement * 50));
                        //ms.mulPose(Axis.YP.rotationDegrees(movement*10-10));

                        //ms.mulPose(Axis.ZN.rotation(AngleHelper.rad(30)));
                        ms.pushPose();
                        ms.popPose();
                    }

                    if (stack.getOrCreateTag().getCompound("display").getInt("flourish") == 1) {
                        float movement = Mth.sin(((float) ((time) * 2 / Math.PI)));
                        float movement2 = Mth.sin(((float) ((time) * 4/ Math.PI)));
                        //ItemStack.isSameItemSameTags(entity.getOffhandItem())

                        //ms.mulPose(Axis.XN.rotation(ScrollValueHandler.getScroll((AnimationTickHolder.getPartialTicks() * 10)*multiplier)));
                        ms.mulPose(Axis.ZP.rotation(ScrollValueHandler.getScroll((AnimationTickHolder.getPartialTicks() * 2))));
                        ms.mulPose(Axis.XN.rotationDegrees(-10));
                        //ms.
                        ms.translate(-0.2,0.2,0);
                        //ms.translate(-0.5,0,0);
                        //ms.mulPose(Axis.ZN.rotation(AngleHelper.rad(movement * 25)));


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
        ms.mulPose(Axis.YP.rotationDegrees(ScrollValueHandler.getScroll(AnimationTickHolder.getPartialTicks()) * (stack.getOrCreateTag().getBoolean("ActiveBoiii")? -30 : 4)));
        //
        //ms.translate(xOffset, 0, 0);
        renderer.render(GEAR_BIT.get(), light);
    }


    public static List<LivingEntity> getEntitiesHoldingItem(ItemStack stack) {
        List<LivingEntity> result = new ArrayList<>();
        Minecraft mc = Minecraft.getInstance();

        Player player = mc.player;
        if (player != null && isHoldingItemAnyHand(player, stack)) {
            result.add(player);
        }

        // Check other entities in the loaded world
        if (mc.level != null) {
            for (Entity entity : mc.level.entitiesForRendering()) {
                if (entity instanceof LivingEntity livingEntity) {
                    // skippity
                    if (livingEntity == player) continue;

                    if (isHoldingItemAnyHand(livingEntity, stack)) {
                        result.add(livingEntity);
                    }
                }
            }
        }

        return result;
    }

    /**
     * Checks if a living entity is holding the specified ItemStack.
     * This is not an exact science - we're trying to determine if the stack
     * we're rendering is the one being held by this entity.
     */
    public static boolean isHoldingItemAnyHand(LivingEntity entity, ItemStack stack) {
        return (ItemStack.isSameItemSameTags(entity.getMainHandItem(), stack) && ItemStack.isSameItemSameTags(entity.getOffhandItem(), stack)) || ItemStack.isSameItemSameTags(entity.getMainHandItem(), stack);
        //return entity.getMainHandItem().is(ModTags.Items.CREATE_LIGHTSABER);
    }
    public static boolean isHoldingItemOffHand(LivingEntity entity, ItemStack stack) {
        return ItemStack.isSameItemSameTags(entity.getOffhandItem(), stack);
        //return entity.getMainHandItem().is(ModTags.Items.CREATE_LIGHTSABER);
    }
    public static boolean isHoldingItemMainHand(LivingEntity entity, ItemStack stack) {
        return ItemStack.isSameItemSameTags(entity.getMainHandItem(), stack);
        //return entity.getMainHandItem().is(ModTags.Items.CREATE_LIGHTSABER);
    }


}
