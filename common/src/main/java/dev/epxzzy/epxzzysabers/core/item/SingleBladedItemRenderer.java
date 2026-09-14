package dev.epxzzy.epxzzysabers.core.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.epxzzy.epxzzysabers.CommonClass;
import dev.epxzzy.epxzzysabers.core.foundation.CustomRenderedSaberModelRenderer;
import dev.epxzzy.epxzzysabers.core.foundation.PartialItemModelRenderer;
import dev.epxzzy.epxzzysabers.core.foundation.PartialModel;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class SingleBladedItemRenderer extends CustomRenderedSaberModelRenderer {
    protected static final PartialModel HILTBIT = PartialModel.of(CommonClass.asResource("item/hilt/mono_hilt"));
    protected static final PartialModel GLOWLY_BIT = PartialModel.of(CommonClass.asResource("item/additive/blade_single"));

    @Override
    protected void renderBlade(ItemStack stack, BakedModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        renderer.renderGlowing(GLOWLY_BIT.get(), LightTexture.FULL_BRIGHT, buffer);
    }

    @Override
    protected void renderHilt(ItemStack stack, BakedModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        renderer.render(HILTBIT.get(), light);
    }

    @Override
    protected void renderFirstPersonBlock(ItemStack stack, BakedModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        boolean leftHand = transformType == ItemDisplayContext.FIRST_PERSON_LEFT_HAND;
        int modifier = leftHand ? -1 : 1;
        ms.mulPose(Axis.ZP.rotationDegrees(modifier * 60));

    }
}