package com.epxzzy.createsaburs.entity.client.bolt;

import com.epxzzy.createsaburs.createsaburs;
import com.epxzzy.createsaburs.entity.ModModelLayers;
import com.epxzzy.createsaburs.entity.custom.PlasmaBolt;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class PlasmaBoltRenderer extends EntityRenderer<PlasmaBolt> {
    private static final ResourceLocation TEXTURE_LOCATION = createsaburs.asResource("textures/entity/wind_charge.png");
    private final PlasmaBoltModel model;

    public PlasmaBoltRenderer(Context context) {
        super(context);
        this.model = new PlasmaBoltModel(context.bakeLayer(ModModelLayers.PLASMA_BOLT_LAYER));
    }

    public void render(PlasmaBolt entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        VertexConsumer var8 = buffer.getBuffer(RenderType.eyes(TEXTURE_LOCATION));
        this.model.setupAnim(entity, 0.0F, 0.0F, partialTick, 0.0F, 0.0F);
        this.model.renderToBuffer(poseStack, var8, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 0.5F);
        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }


    protected float xOffset(float tickCount) {
        return tickCount * 0.03F;
    }

    public ResourceLocation getTextureLocation(PlasmaBolt entity) {
        return TEXTURE_LOCATION;
    }


    // $FF: synthetic method
    // $FF: bridge method
}
