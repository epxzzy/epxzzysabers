package com.epxzzy.createsaburs.entity.client.rotary;


import com.epxzzy.createsaburs.createsaburs;
import com.epxzzy.createsaburs.entity.ModModelLayers;
import com.epxzzy.createsaburs.entity.custom.ThrownRotarySaber;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class ThrownRotarySaberRenderer extends EntityRenderer<ThrownRotarySaber> implements RenderLayerParent<ThrownRotarySaber, ThrownRotarySaberGuardModel<ThrownRotarySaber>> {
    public static final ResourceLocation TRIDENT_LOCATION = createsaburs.asResource("textures/entity/thrownrotarysaber.png");
    public static final ResourceLocation GLOWLOC = createsaburs.asResource("textures/entity/glowingpart.png");

    private final ThrownRotarySaberGuardModel<ThrownRotarySaber> model;
    private final ThrownRotarySaberBladeModel<ThrownRotarySaber> blade;

    protected final List<RenderLayer<ThrownRotarySaber, ThrownRotarySaberGuardModel<ThrownRotarySaber>>> layers = Lists.newArrayList();

    public ThrownRotarySaberRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);

        this.model = new ThrownRotarySaberGuardModel<ThrownRotarySaber>(pContext.bakeLayer(ModModelLayers.THROWN_ROTARY_SABER_GUARD));
        this.blade = new ThrownRotarySaberBladeModel<ThrownRotarySaber>(pContext.bakeLayer(ModModelLayers.THROWN_ROTART_SABER_BLADE));

        //this.addLayer(new GlowingBladeLayer( this,pContext.getModelSet()));
    }

    public final boolean addLayer(RenderLayer<ThrownRotarySaber, ThrownRotarySaberGuardModel<ThrownRotarySaber>> pLayer) {
        return this.layers.add(pLayer);
    }

    @Override
    public ThrownRotarySaberGuardModel<ThrownRotarySaber> getModel() {
        return this.model;
    }

    @Override
    public ResourceLocation getTextureLocation(ThrownRotarySaber pEntity) {
        return TRIDENT_LOCATION;
    }
    protected int getBlockLightLevel(ThrownRotarySaber p_234560_, BlockPos p_234561_) {
        return 15;
    }

    public void render(@NotNull ThrownRotarySaber pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {


        pMatrixStack.pushPose();
        //pMatrixStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.yRotO, pEntity.getYRot()) - 90.0F));
        //pMatrixStack.mulPose(Axis.YP.rotation(-ScrollValueHandler.getScroll((float) (AnimationTickHolder.getPartialTicks()) * 5)));
        pMatrixStack.translate(0, -1, 0);
        // pMatrixStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.xRotO, pEntity.getXRot()) + 90.0F));
        VertexConsumer vertexconsumer = ItemRenderer.getFoilBufferDirect(pBuffer, this.model.renderType(this.getTextureLocation(pEntity)), false, false);
        //VertexConsumer vertexconsumer = pBuffer.getBuffer(RenderType.eyes(GLOWLOC));

        this.model.renderToBuffer(pMatrixStack, vertexconsumer, 15728880, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        pMatrixStack.popPose();


        this.blade.renderToBuffer(pMatrixStack, pBuffer, pEntity, pPartialTicks, GLOWLOC,15728880, OverlayTexture.NO_OVERLAY);
        //super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}