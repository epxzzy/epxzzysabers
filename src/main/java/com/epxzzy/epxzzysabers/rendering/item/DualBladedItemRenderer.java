package com.epxzzy.epxzzysabers.rendering.item;

import com.epxzzy.epxzzysabers.epxzzySabers;

import com.epxzzy.epxzzysabers.rendering.ItemTransformRouter;
import com.epxzzy.epxzzysabers.rendering.foundation.CustomRenderedItemModelRenderer;
import com.epxzzy.epxzzysabers.rendering.foundation.CustomRenderedSaberModelRenderer;
import com.epxzzy.epxzzysabers.rendering.foundation.PartialItemModelRenderer;
import com.epxzzy.epxzzysabers.rendering.foundation.PartialModel;

//import com.simibubi.create.foundation.item.renderMid.CustomRenderedItemModel;
//import com.simibubi.create.foundation.item.renderMid.CustomRenderedItemModelRenderer;
//import com.simibubi.create.foundation.item.renderMid.PartialItemModelRenderer;
//import dev.engine_room.flywheel.lib.model.baked.PartialModel;

import com.epxzzy.epxzzysabers.rendering.parry.light.LightItemRenderer;
import com.epxzzy.epxzzysabers.util.AnimationTickHolder;
import com.epxzzy.epxzzysabers.util.PlayerHelperLmao;
import com.epxzzy.epxzzysabers.util.StackHelper;
import com.epxzzy.epxzzysabers.util.TagHelper;
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


public class DualBladedItemRenderer extends CustomRenderedSaberModelRenderer {
    protected static final PartialModel HILT_BIT = PartialModel.of(epxzzySabers.asResource("item/hilt/dual_hilt"));
    protected static final PartialModel GLOWLY_BIT = PartialModel.of(epxzzySabers.asResource("item/additive/blade"));

    @Override
    protected void renderBlade(ItemStack stack, BakedModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        renderer.renderGlowing(GLOWLY_BIT.get(), LightTexture.FULL_BRIGHT);
    }

    @Override
    protected void renderHilt(ItemStack stack, BakedModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        renderer.render(HILT_BIT.get(), light);
    }

    @Override
    protected void renderFirstPersonBlock(ItemStack stack, BakedModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        boolean leftHand = transformType == ItemDisplayContext.FIRST_PERSON_LEFT_HAND;
        int modifier = leftHand ? -1 : 1;
        ms.mulPose(Axis.ZP.rotationDegrees(modifier * 60));

    }


}
