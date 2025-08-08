package com.epxzzy.createsaburs.rendering;

import com.epxzzy.createsaburs.CreateSaburs;
import com.epxzzy.createsaburs.rendering.foundation.CustomRenderedItemModelRenderer;
import com.epxzzy.createsaburs.rendering.foundation.PartialItemModelRenderer;
import com.epxzzy.createsaburs.rendering.foundation.PartialModel;
import com.epxzzy.createsaburs.utils.AnimationTickHolder;
import com.epxzzy.createsaburs.utils.ScrollValueHandler;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import java.util.List;

import static com.epxzzy.createsaburs.utils.StackHelper.getEntitiesHoldingItemRightOrBoth;


public class SaberGauntletItemRenderer extends CustomRenderedItemModelRenderer {
    protected static final PartialModel HILT_BIT = PartialModel.of(CreateSaburs.asResource("item/hilt/gauntlet"));
    protected static final PartialModel GLOWLY_BIT = PartialModel.of(CreateSaburs.asResource("item/additive/katar_blades"));

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
                    //SingleBladedItemPoseRenderer.setItemPose(stack, model, renderer, transformType, ms, buffer, light, overlay, entity);
                }
            }
        }
        renderer.render(HILT_BIT.get(), light);

        //}

        if (stack.getOrCreateTag().getBoolean("ActiveBoiii")) {
            //stack.getUseAnimation()
            ms.pushPose();
            renderer.renderGlowing(GLOWLY_BIT.get(), LightTexture.FULL_BRIGHT);

            ms.popPose();
        }





    }

}
