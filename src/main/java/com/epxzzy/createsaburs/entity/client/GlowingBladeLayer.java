package com.epxzzy.createsaburs.entity.client;

import com.epxzzy.createsaburs.createsaburs;
import com.epxzzy.createsaburs.entity.ModModelLayers;
import com.epxzzy.createsaburs.entity.custom.ThrownRotarySaber;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueHandler;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.SheepFurModel;
import net.minecraft.client.model.SheepModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;

public class GlowingBladeLayer extends RenderLayer<ThrownRotarySaber, ThrownRotarySaberModel<ThrownRotarySaber>> {
    public static final ResourceLocation TEXTURELOC = createsaburs.asResource("textures/entity/thrownrotarysaber.png");

    private final GlowingBladeRotarySaberModel<ThrownRotarySaber> model;

    public GlowingBladeLayer(RenderLayerParent<ThrownRotarySaber, ThrownRotarySaberModel<ThrownRotarySaber>> pRenderer, EntityModelSet pModelSet) {
        super(pRenderer);
        this.model = new GlowingBladeRotarySaberModel<>(pModelSet.bakeLayer(ModModelLayers.thrownrotarysabermodellayer));
    }

    public void render(PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight, ThrownRotarySaber pEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        Minecraft minecraft = Minecraft.getInstance();
            this.getParentModel().copyPropertiesTo(this.model);
            VertexConsumer vertexconsumerrr = pBuffer.getBuffer(RenderType.outline(TEXTURELOC));
            this.model.renderToBuffer(pMatrixStack, vertexconsumerrr, pPackedLight, 0, 0.0F, 0.0F, 0.0F, 1.0F);

        float f;
        float f1;
        float f2;
        if (pEntity.getTags().contains("gay")) {
            int i1 = 25;
            int i = pEntity.tickCount / 25 + pEntity.getId();
            int j = DyeColor.values().length;
            int k = i % j;
            int l = (i + 1) % j;
            float f3 = ((float) (pEntity.tickCount % 25) + pPartialTicks) / 25.0F;
            float[] afloat1 = Sheep.getColorArray(DyeColor.byId(k));
            float[] afloat2 = Sheep.getColorArray(DyeColor.byId(l));
            f = afloat1[0] * (1.0F - f3) + afloat2[0] * f3;
            f1 = afloat1[1] * (1.0F - f3) + afloat2[1] * f3;
            f2 = afloat1[2] * (1.0F - f3) + afloat2[2] * f3;
        } else {
            int[] afloat = pEntity.getColour();
            f = afloat[0];
            f1 = afloat[1];
            f2 = afloat[2];
        }
        pMatrixStack.mulPose(Axis.YP.rotation(-ScrollValueHandler.getScroll((float) (AnimationTickHolder.getPartialTicks()) * 5)));
        pMatrixStack.translate(0, -2, 0);
        VertexConsumer vertexconsumer = pBuffer.getBuffer(RenderType.entityCutoutNoCull(TEXTURELOC));
        this.model.renderToBuffer(pMatrixStack, vertexconsumer, pPackedLight, 0, f, f1, f2, 1.0F);
        //coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model, SHEEP_FUR_LOCATION, pMatrixStack, pBuffer, pPackedLight, pLivingEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch, pPartialTicks, f, f1, f2);
    }

}
