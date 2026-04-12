package com.epxzzy.epxzzysabers.rendering.item;

import com.epxzzy.epxzzysabers.epxzzySabers;
import com.epxzzy.epxzzysabers.item.Protosaber;
import com.epxzzy.epxzzysabers.rendering.ItemTransformRouter;
import com.epxzzy.epxzzysabers.rendering.foundation.CustomRenderedItemModelRenderer;
import com.epxzzy.epxzzysabers.rendering.foundation.CustomRenderedSaberModelRenderer;
import com.epxzzy.epxzzysabers.rendering.foundation.PartialItemModelRenderer;
import com.epxzzy.epxzzysabers.rendering.foundation.PartialModel;
import com.epxzzy.epxzzysabers.util.AnimationTickHolder;
import com.epxzzy.epxzzysabers.util.PlayerHelperLmao;
import com.epxzzy.epxzzysabers.util.StackHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import java.util.List;



public class SaberGauntletItemRenderer extends CustomRenderedSaberModelRenderer {
    protected static final PartialModel HILT_BIT = PartialModel.of(epxzzySabers.asResource("item/hilt/gauntlet"));
    protected static final PartialModel GLOWLY_BIT = PartialModel.of(epxzzySabers.asResource("item/additive/katar_blades"));

    @Override
    protected void renderBlade(ItemStack stack, BakedModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        if (stack.getOrCreateTag().getBoolean("ChargedBoiii")) {
            ms.translate(Math.random() * 0.1 + -0.05, Math.random() * 0.1 + -0.05 , Math.random() * 0.1+ -0.05 );
            ms.scale( 1F, 1F, (float) (Math.random() * 0.2) + 1F);
        }
        renderer.renderGlowing(GLOWLY_BIT.get(), LightTexture.FULL_BRIGHT, buffer);
    }

    @Override
    protected void renderHilt(ItemStack stack, BakedModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        renderer.render(HILT_BIT.get(), light);
    }

    @Override
    protected void renderTransforms(ItemStack stack, BakedModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay, LivingEntity entity) {
        return;
    }

    @Override
    protected void renderFirstPersonBlock(ItemStack stack, BakedModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        int offhandmultip = transformType == ItemDisplayContext.FIRST_PERSON_LEFT_HAND? -1:1;
        ms.mulPose(Axis.XP.rotationDegrees(50*offhandmultip));
        ms.mulPose(Axis.ZP.rotationDegrees(10*offhandmultip));
        return;
    }

    @Override
    protected void renderSoftParry(ItemStack stack, BakedModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType,
                                   PoseStack ms, MultiBufferSource buffer, int light, int overlay,LivingEntity entity){
        int offhandmultip = transformType == ItemDisplayContext.FIRST_PERSON_LEFT_HAND? -1:1;
        float newdur = entity.getUseItemRemainingTicks() - Protosaber.SOFT_PARRY;
        float dur = (float) stack.getUseDuration() - newdur;
        float sinn = Mth.sin((6*dur - 0.1F) * 20F) * 30 * offhandmultip;
        ms.mulPose(Axis.ZP.rotationDegrees(sinn + (40*offhandmultip)));
    };
}
