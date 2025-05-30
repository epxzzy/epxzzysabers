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

public class InquisitoriusItemRenderer extends CustomRenderedItemModelRenderer {

    protected static final PartialModel GLOWLY_BIT = PartialModel.of(createsaburs.asResource("item/additive/rotary_blade"));
    protected static final PartialModel SPIN_BIT = PartialModel.of(createsaburs.asResource("item/additive/rotary_swing"));

    protected static final PartialModel HANDLE = PartialModel.of(createsaburs.asResource("item/additive/rotary_handle"));

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
                if (((Player) entity).getAbilities().flying) {
                    //stack.getUseAnimation()

                    //ms.mulPose(Axis.YP.rotationDegrees(-27));
                    //ms.mulPose(Axis.XP.rotationDegrees(90));
                    ms.mulPose(Axis.ZP.rotation(-ScrollValueHandler.getScroll((float) (AnimationTickHolder.getPartialTicks() * 5))));

                    ms.pushPose();
                    ms.popPose();
                }
                if ((entity.swingTime > 0 || entity.swinging) && stack.getOrCreateTag().getBoolean("ActiveBoiii")) {

                    if (stack.getOrCreateTag().getCompound("display").getInt("flourish") == 3) {
                        //float movement = Mth.sin(((float) ((time+10) * 5f /Math.PI)));
                        float movement = Mth.cos(((float) ((time) * 3.3 / Math.PI)));
                        float movementARM = Mth.sin(((float) ((time) * 3.3 / Math.PI)));
                        float movementZARM = Mth.sin(((float) ((time+Mth.PI/2) * 3.3 / Math.PI)));
                        float movement3 = Mth.sin(((float) ((time) * 4 / Math.PI)));



                        //ms.mulPose(Axis.XP.rotation((float) (ScrollValueHandler.getScroll(AnimationTickHolder.getPartialTicks()) * (10)) - (45 * movement)));
                        //ms.mulPose(Axis.ZP.rotationDegrees(movement * 50));
                        //ms.mulPose(Axis.YP.rotationDegrees(movement*10-10));
                        ms.mulPose(Axis.YP.rotationDegrees(-27));
                        //ms.mulPose(Axis.XP.rotationDegrees(-145));
                        //ms.mulPose(Axis.XP.rotationDegrees((float) (45.04*movementARM)));
                        //ms.translate(-0.3 +movement*0.7,0,-0+movementARM*0.5*1.2);
                        //ms.mulPose(Axis.ZP.rotation(ScrollValueHandler.getScroll((AnimationTickHolder.getPartialTicks() * 3))));


                        //ms.mulPose(Axis.ZN.rotation(AngleHelper.rad(30)));
                        ms.pushPose();
                        ms.popPose();
                    }

                    if (stack.getOrCreateTag().getCompound("display").getInt("flourish") == 2) {
                        //spinny behind the bacc
                        //float movement = Mth.sin(((float) ((time+10) * 5f /Math.PI)));
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

                        ms.mulPose(Axis.ZP.rotation(ScrollValueHandler.getScroll((AnimationTickHolder.getPartialTicks() * 5))));


                        //ms.mulPose(Axis.ZN.rotation(AngleHelper.rad(30)));
                        ms.pushPose();
                        ms.popPose();
                    }

                    if (stack.getOrCreateTag().getCompound("display").getInt("flourish") == 1) {
                        //skip catch
                        float movement = Mth.sin(((float) ((time) * 2 / Math.PI)));
                        float movement2 = Mth.sin(((float) ((time) * 4/ Math.PI)));
                        //ItemStack.isSameItemSameTags(entity.getOffhandItem())

                        //ms.mulPose(Axis.XN.rotation(ScrollValueHandler.getScroll((AnimationTickHolder.getPartialTicks() * 10)*multiplier)));
                        ms.mulPose(Axis.YP.rotationDegrees(-27));
                        ms.translate(-0.1,0,0);
                        ms.mulPose(Axis.ZP.rotation(ScrollValueHandler.getScroll((AnimationTickHolder.getPartialTicks() * -3))));

                        //ms.mulPose(Axis.XN.rotationDegrees(-90));
                        //ms.mulPose(Axis.XP.rotation(ScrollValueHandler.getScroll((AnimationTickHolder.getPartialTicks()*5))));
                        //ms.translate(-0.2,0,0);
                        //ms.mulPose(Axis.ZN.rotation(AngleHelper.rad(movement * 25)));


                        //ms.mulPose(Axis.XP.rotation(-AngleHelper.rad(movement * 60)));
                        //System.out.print("I... AM STEEVE\n");
                    }
                }
            }
        }
        renderer.render(model.getOriginalModel(), light);
        //}




        float xOffset = -1 / 16f;
        ms.translate(0, xOffset * -3, 0);
        /*
        if (stack.getOrCreateTag().getBoolean("ActiveBoiii")) {
            ms.mulPose(Axis.YP.rotationDegrees(ScrollValueHandler.getScroll(AnimationTickHolder.getPartialTicks()) * 20));
        }
        else {
        */
        //
        //ms.translate(xOffset, 0, 0);



        for (LivingEntity entity : allEntities) {
            if (((Player) entity).getAbilities().flying) {

                //stack.getUseAnimation()

                //ms.mulPose(Axis.YP.rotationDegrees(-27));
                //ms.mulPose(Axis.XP.rotationDegrees(90));
                //ms.translate(0.05,0.179,0);
                ms.pushPose();
                ms.translate(0.035,-0.175,0);
                renderer.render(SPIN_BIT.get(), LightTexture.FULL_BRIGHT);
                ms.popPose();

                ms.translate(0,-0.175,0);
                ms.mulPose(Axis.ZN.rotation(-ScrollValueHandler.getScroll((float) (AnimationTickHolder.getPartialTicks() * 5))));
                continue;
            }
            else if (stack.getOrCreateTag().getBoolean("ActiveBoiii")&& !((Player) entity).getAbilities().flying && transformType != ItemDisplayContext.GUI) {
                //stack.getUseAnimation()
                ms.pushPose();
                renderer.renderGlowing(GLOWLY_BIT.get(),  LightTexture.FULL_BRIGHT);
                ms.popPose();
            }
            else {
                //ms.translate(0,-0.179,0);

            }
        }
        renderer.render(HANDLE.get(), light);

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
