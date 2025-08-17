package com.epxzzy.epxzzysabers.rendering;

import com.epxzzy.epxzzysabers.epxzzySabers;
import com.epxzzy.epxzzysabers.rendering.foundation.CustomRenderedItemModelRenderer;
import com.epxzzy.epxzzysabers.rendering.foundation.PartialItemModelRenderer;
import com.epxzzy.epxzzysabers.rendering.foundation.PartialModel;
import com.epxzzy.epxzzysabers.rendering.poseRenderer.HeavyWeapon.HeavyWeaponItemPoseRenderer;
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


public class ProtosaberItemRenderer extends CustomRenderedItemModelRenderer {
    protected static final PartialModel HILT_BIT = PartialModel.of(epxzzySabers.asResource("item/hilt/dual_hilt"));
    protected static final PartialModel GLOWLY_BIT = PartialModel.of(epxzzySabers.asResource("item/additive/blade"));

    @Override
    protected void render(ItemStack stack, BakedModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType,
                          PoseStack ms, MultiBufferSource buffer, int light, int overlay) {

        boolean leftHand = transformType == ItemDisplayContext.FIRST_PERSON_LEFT_HAND;
        /*if(stack.getOrCreateTag().getBoolean("BlockBoiii")){
            renderer.render(BLOCING_BIT.get(), light);
        }
        else {
        */

        List<LivingEntity> allEntities = getEntitiesHoldingItemRightOrBoth(stack);
        for (LivingEntity entity : allEntities) {
            if(transformType.firstPerson() && entity.isUsingItem()){
                int modifier = leftHand ? -1 : 1;
                ms.mulPose(Axis.ZP.rotationDegrees(modifier * 30));
                ms.pushPose();
                ms.popPose();
            }
        }

        if (transformType != ItemDisplayContext.GUI && transformType != ItemDisplayContext.FIXED) {
            for (LivingEntity entity : allEntities) {
                if ((entity.swingTime > 0 || entity.swinging) && stack.getOrCreateTag().getBoolean("ActiveBoiii")) {
                    HeavyWeaponItemPoseRenderer.setItemPose(stack, model, renderer, transformType, ms, buffer, light, overlay, entity);
                }
            }
        }
        renderer.render(HILT_BIT.get(), light);
        //}


        if (stack.getOrCreateTag().getBoolean("ActiveBoiii")) {
            //stack.getUseAnimation()
            ms.pushPose();
            renderer.renderGlowing(GLOWLY_BIT.get(),  LightTexture.FULL_BRIGHT);
            ms.popPose();
        }


    }
}
