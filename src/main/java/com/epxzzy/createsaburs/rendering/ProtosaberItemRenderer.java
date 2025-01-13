package com.epxzzy.createsaburs.rendering;

import com.epxzzy.createsaburs.createsaburs;
import com.jozufozu.flywheel.core.PartialModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueHandler;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModel;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModelRenderer;
import com.simibubi.create.foundation.item.render.PartialItemModelRenderer;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class ProtosaberItemRenderer extends CustomRenderedItemModelRenderer {
    protected boolean saber_activation_state = false;
    protected static final PartialModel GEAR = new PartialModel(createsaburs.asResource("item/geur"));
    protected static final PartialModel GLOWLY_BIT = new PartialModel(createsaburs.asResource("item/blade"));

    public void SetSaberCoreState(boolean state) {
        this.saber_activation_state = state;
    }

    @Override
    protected void render(ItemStack stack, CustomRenderedItemModel model, PartialItemModelRenderer renderer, ItemDisplayContext transformType,
                          PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        renderer.render(model.getOriginalModel(), light);

        //float xOffset = -1 / 16f;
        //ms.translate(-xOffset, 0, 0);
        ms.mulPose(Axis.YP.rotationDegrees(ScrollValueHandler.getScroll(AnimationTickHolder.getPartialTicks())));
        //ms.translate(xOffset, 0, 0);

        renderer.render(GEAR.get(), light);
        if (saber_activation_state) {
            renderer.renderGlowing(GLOWLY_BIT.get(),  LightTexture.FULL_BRIGHT);
        }
    }

}