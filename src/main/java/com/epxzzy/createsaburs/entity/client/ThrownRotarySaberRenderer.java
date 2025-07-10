package com.epxzzy.createsaburs.entity.client;


import com.epxzzy.createsaburs.createsaburs;
import com.epxzzy.createsaburs.entity.ModModelLayers;
import com.epxzzy.createsaburs.entity.custom.ThrownRotarySaber;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueHandler;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.layers.EnderEyesLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.layers.SheepFurLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.GlowSquid;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.DyeColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class ThrownRotarySaberRenderer extends EntityRenderer<ThrownRotarySaber> implements RenderLayerParent<ThrownRotarySaber, ThrownRotarySaberModel<ThrownRotarySaber>> {
    public static final ResourceLocation TRIDENT_LOCATION = createsaburs.asResource("textures/entity/thrownrotarysaber.png");
    public static final ResourceLocation GLOWLOC = createsaburs.asResource("textures/entity/glowingpart.png");

    private final ThrownRotarySaberModel<ThrownRotarySaber> model;
    private final thebladepart<ThrownRotarySaber> blade;

    protected final List<RenderLayer<ThrownRotarySaber, ThrownRotarySaberModel<ThrownRotarySaber>>> layers = Lists.newArrayList();

    public ThrownRotarySaberRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);

        this.model = new ThrownRotarySaberModel<ThrownRotarySaber>(pContext.bakeLayer(ModModelLayers.thrownrotarysabermodellayer));
        this.blade = new thebladepart<ThrownRotarySaber>(pContext.bakeLayer(ModModelLayers.thrownrotarysaberblademodellayer));

        //this.addLayer(new GlowingBladeLayer( this,pContext.getModelSet()));
    }

    public final boolean addLayer(RenderLayer<ThrownRotarySaber, ThrownRotarySaberModel<ThrownRotarySaber>> pLayer) {
        return this.layers.add(pLayer);
    }

    @Override
    public ThrownRotarySaberModel<ThrownRotarySaber> getModel() {
        return this.model;
    }

    @Override
    public ResourceLocation getTextureLocation(com.epxzzy.createsaburs.entity.custom.ThrownRotarySaber pEntity) {
        return TRIDENT_LOCATION;
    }

    public void render(@NotNull ThrownRotarySaber pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {


        pMatrixStack.pushPose();
        //pMatrixStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.yRotO, pEntity.getYRot()) - 90.0F));
        //pMatrixStack.mulPose(Axis.YP.rotation(-ScrollValueHandler.getScroll((float) (AnimationTickHolder.getPartialTicks()) * 5)));
        pMatrixStack.translate(0, -1, 0);
        // pMatrixStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.xRotO, pEntity.getXRot()) + 90.0F));
        VertexConsumer vertexconsumer = ItemRenderer.getFoilBufferDirect(pBuffer, this.model.renderType(this.getTextureLocation(pEntity)), false, pEntity.isFoil());
        this.model.renderToBuffer(pMatrixStack, vertexconsumer, 15728640, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        pMatrixStack.popPose();


        this.blade.renderToBuffer(pMatrixStack, pBuffer, pEntity, pPartialTicks, GLOWLOC, 15, OverlayTexture.NO_OVERLAY);
        //super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}