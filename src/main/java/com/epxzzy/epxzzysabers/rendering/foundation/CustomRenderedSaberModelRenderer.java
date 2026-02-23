package com.epxzzy.epxzzysabers.rendering.foundation;

import com.epxzzy.epxzzysabers.item.Protosaber;
import com.epxzzy.epxzzysabers.rendering.ItemTransformRouter;
import com.epxzzy.epxzzysabers.rendering.parry.light.LightItemRenderer;
import com.epxzzy.epxzzysabers.util.*;
import com.mojang.blaze3d.vertex.PoseStack;

import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import java.util.List;

import static com.epxzzy.epxzzysabers.util.StackHelper.getPlayersHoldingItemRightOrBoth;

//import com.simibubi.create.foundation.item.renderMid.CustomRenderedItemModel;
//import com.simibubi.create.foundation.item.renderMid.PartialItemModelRenderer;

public abstract class CustomRenderedSaberModelRenderer extends CustomRenderedItemModelRenderer {
    private BakedModel mainModel;

    public void render(ItemStack stack, BakedModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType,
             PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        if(isUnsualRenderer()){
            renderCustom(stack, mainModel, renderer, transformType, ms, buffer, light, overlay);
            return;
        }
        List<LivingEntity> allEntities = getPlayersHoldingItemRightOrBoth(stack);

        for (LivingEntity entity : allEntities) {
            if (transformType.firstPerson() && entity.isUsingItem()){
                renderFirstPersonBlock(stack, mainModel, renderer, transformType, ms, buffer, light, overlay);
                ms.pushPose();
                ms.popPose();

                if(entity.getTicksUsingItem() > Protosaber.SOFT_PARRY) {
                    float newdur = entity.getUseItemRemainingTicks() - Protosaber.SOFT_PARRY;
                    float dur = (float) stack.getUseDuration() - newdur;
                    float sinn = Mth.sin((10*dur - 0.1F) * 20F);
                    ms.mulPose(Axis.ZN.rotationDegrees(sinn-15));
                    //ms.translate(f12 * 0.0F, f12 * 0.0F, f12 * 0.04F);
                }
            }
        }


        if (transformType != ItemDisplayContext.GUI && transformType != ItemDisplayContext.FIXED) {
            for (LivingEntity entity : allEntities) {
                renderTransforms(stack, mainModel, renderer, transformType, ms, buffer, light, overlay, entity);

                if ((entity.swinging) && StackHelper.isActive(stack) && !(((PlayerHelperLmao) entity).getSaberAttackAnim() > 0)){
                    ItemTransformRouter.transform(stack, model, renderer, transformType, ms, buffer, light, overlay, entity);
                }
            }
        }

        ms.pushPose();
        renderHilt(stack, mainModel, renderer, transformType, ms, buffer, light, overlay);
        ms.popPose();

        if (StackHelper.isActive(stack)) {
            ms.pushPose();
            renderBlade(stack, mainModel, renderer, transformType, ms, buffer, light, overlay);
            ms.popPose();
        }

    }

    protected void renderTransforms(ItemStack stack, BakedModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType,
                                      PoseStack ms, MultiBufferSource buffer, int light, int overlay, LivingEntity entity){
        if ((entity.swinging) && StackHelper.isActive(stack) && !(((PlayerHelperLmao) entity).getSaberAttackAnim() > 0)){
            ItemTransformRouter.transform(stack, model, renderer, transformType, ms, buffer, light, overlay, entity);
        }
    };
    protected void renderCustom(ItemStack stack, BakedModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType,
                                    PoseStack ms, MultiBufferSource buffer, int light, int overlay){
    };
    protected boolean isUnsualRenderer(){
        return false;
    }

    protected void renderFirstPersonBlock(ItemStack stack, BakedModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType,
                                             PoseStack ms, MultiBufferSource buffer, int light, int overlay){
        boolean leftHand = transformType == ItemDisplayContext.FIRST_PERSON_LEFT_HAND;
        int modifier = leftHand ? -1 : 1;
        ms.mulPose(Axis.ZP.rotationDegrees(modifier * 60));
    };

    protected abstract void renderBlade(ItemStack stack, BakedModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType,
                                   PoseStack ms, MultiBufferSource buffer, int light, int overlay);
    protected abstract void renderHilt(ItemStack stack, BakedModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType,
                                   PoseStack ms, MultiBufferSource buffer, int light, int overlay);

}