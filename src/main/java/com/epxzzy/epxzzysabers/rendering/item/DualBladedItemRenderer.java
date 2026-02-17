package com.epxzzy.epxzzysabers.rendering.item;

import com.epxzzy.epxzzysabers.epxzzySabers;
import com.epxzzy.epxzzysabers.rendering.ItemTransformRouter;
import com.epxzzy.epxzzysabers.rendering.foundation.CustomRenderedItemModelRenderer;
import com.epxzzy.epxzzysabers.rendering.foundation.PartialItemModelRenderer;
import com.epxzzy.epxzzysabers.rendering.foundation.PartialModel;
import com.epxzzy.epxzzysabers.rendering.parry.heavy.HeavyItemRenderer;
import com.epxzzy.epxzzysabers.util.PlayerHelperLmao;
import com.epxzzy.epxzzysabers.util.StackHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import java.util.List;

import static com.epxzzy.epxzzysabers.util.StackHelper.getPlayersHoldingItemRightOrBoth;


public class DualBladedItemRenderer extends CustomRenderedItemModelRenderer {
    protected static final PartialModel HILT_BIT = PartialModel.of(epxzzySabers.asResource("item/hilt/dual_hilt"));
    protected static final PartialModel GLOWLY_BIT = PartialModel.of(epxzzySabers.asResource("item/additive/blade"));

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
                if ((entity.swinging) && stack.getOrCreateTag().getBoolean("ActiveBoiii") && !(((PlayerHelperLmao) entity).getSaberAttackAnim() > 0)){
                    ItemTransformRouter.transform(stack, model, renderer, transformType, ms, buffer, light, overlay, entity);
                }
            }
        }
        renderer.render(HILT_BIT.get(), light);
        //}
        if (StackHelper.isActive(stack)) {
            //stack.getUseAnimation()
            ms.pushPose();
            renderer.renderGlowing(GLOWLY_BIT.get(),  LightTexture.FULL_BRIGHT);
            ms.popPose();
        }


    }
}
