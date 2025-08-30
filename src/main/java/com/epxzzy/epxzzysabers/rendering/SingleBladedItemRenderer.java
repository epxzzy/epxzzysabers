package com.epxzzy.epxzzysabers.rendering;

import com.epxzzy.epxzzysabers.epxzzySabers;

import com.epxzzy.epxzzysabers.rendering.foundation.CustomRenderedItemModelRenderer;
import com.epxzzy.epxzzysabers.rendering.foundation.PartialItemModelRenderer;
import com.epxzzy.epxzzysabers.rendering.foundation.PartialModel;

//import com.simibubi.create.foundation.item.render.CustomRenderedItemModel;
//import com.simibubi.create.foundation.item.render.CustomRenderedItemModelRenderer;
//import com.simibubi.create.foundation.item.render.PartialItemModelRenderer;
//import dev.engine_room.flywheel.lib.model.baked.PartialModel;

import com.epxzzy.epxzzysabers.rendering.parryrenderers.LightWeapon.LightWeaponItemPoseRenderer;
import com.epxzzy.epxzzysabers.utils.AnimationTickHolder;
import com.epxzzy.epxzzysabers.utils.PlayerHelperLmao;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import java.util.List;

import static com.epxzzy.epxzzysabers.utils.StackHelper.getEntitiesHoldingItemRightOrBoth;
import static com.epxzzy.epxzzysabers.utils.StackHelper.getPlayersHoldingItemRightOrBoth;


public class SingleBladedItemRenderer extends CustomRenderedItemModelRenderer {
    protected static final PartialModel HILTBIT = PartialModel.of(epxzzySabers.asResource("item/hilt/mono_hilt"));
    protected static final PartialModel GLOWLY_BIT = PartialModel.of(epxzzySabers.asResource("item/additive/blade_single"));
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

        List<LivingEntity> allEntities = getPlayersHoldingItemRightOrBoth(stack);
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
                if ((entity.swinging) && stack.getOrCreateTag().getBoolean("ActiveBoiii") && !(((PlayerHelperLmao) entity).getSaberAttackAnim() > 0)){
                    LightWeaponItemPoseRenderer.setItemPose(stack, model, renderer, transformType, ms, buffer, light, overlay, entity);
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


    }

}
