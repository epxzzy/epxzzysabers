package com.epxzzy.createsaburs.rendering.foundation;

import com.epxzzy.createsaburs.createsaburs;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import com.simibubi.create.foundation.item.render.CustomRenderedItemModel;
//import com.epxzzy.createsaburs.rendering.foundation.CustomRenderedItemModel;
import com.simibubi.create.foundation.item.render.PartialItemModelRenderer;
//import com.epxzzy.createsaburs.rendering.foundation.PartialItemModelRenderer;

public abstract class CustomRenderedItemModelRenderer extends BlockEntityWithoutLevelRenderer {

    public CustomRenderedItemModelRenderer() {
        super(null, null);
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext transformType, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        CustomRenderedItemModel mainModel = (CustomRenderedItemModel) Minecraft.getInstance()
                .getItemRenderer()
                .getModel(stack, null, null, 0);
        PartialItemModelRenderer renderer = PartialItemModelRenderer.of(stack, transformType, ms, buffer, overlay);
        if(transformType.equals(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND)){
            //
        }

        ms.pushPose();
        ms.translate(0.5F, 0.5F, 0.5F);
        render(stack, mainModel, renderer, transformType, ms, buffer, light, overlay);
        ms.popPose();
    }

    protected abstract void render(ItemStack stack, CustomRenderedItemModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType,
                                   PoseStack ms, MultiBufferSource buffer, int light, int overlay);

}