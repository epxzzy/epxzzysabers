package com.epxzzy.createsaburs.entity.client;

import com.epxzzy.createsaburs.entity.custom.ThrownRotarySaber;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public class GlowingBladeRotarySaberModel<T extends ThrownRotarySaber> extends HierarchicalModel<T> {
    private static final Vector3f ANIMATION_VECTOR_CACHE = new Vector3f();
    private final ModelPart grouproot;

    public GlowingBladeRotarySaberModel(ModelPart pRoot) {
        super(RenderType::entityTranslucentEmissive);
        this.grouproot = pRoot.getChild("group");
    }

    @Override
    public ModelPart root() {
        return grouproot;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bb_main = partdefinition.addOrReplaceChild("cube", CubeListBuilder.create().texOffs(-48, 0).addBox(-24.0F, -3.0F, -24.0F, 48.0F, 0.0F, 48.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }


    public void renderToBuffer(PoseStack pMatrixStack, MultiBufferSource pBuffer, ThrownRotarySaber pEntity, float pPartialTicks, ResourceLocation GLOWLOC,  int pPackedLight, int pPackedOverlay) {
        pMatrixStack.pushPose();

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
            f = (int) (afloat1[0] * (1.0F - f3) + afloat2[0] * f3);
            f1 = (int) (afloat1[1] * (1.0F - f3) + afloat2[1] * f3);
            f2 = (int) (afloat1[2] * (1.0F - f3) + afloat2[2] * f3);
        } else {
            int[] afloat = pEntity.getColour();
            f = afloat[0];
            f1 = afloat[1];
            f2 = afloat[2];
        }
        //pMatrixStack.mulPose(Axis.YP.rotation(-ScrollValueHandler.getScroll((float) (AnimationTickHolder.getPartialTicks()) * 5)));
        pMatrixStack.translate(0, -2, 0);
        pMatrixStack.scale(2,2,2);
        VertexConsumer vertexconsumur = pBuffer.getBuffer(RenderType.entityTranslucentEmissive(GLOWLOC));
        //VertexConsumer vertexconsumur = pBuffer.color(f, f1, f2, 1.0F);

        this.grouproot.render(pMatrixStack, vertexconsumur, pPackedLight, pPackedOverlay, f, f1, f2, 1.0F);
        pMatrixStack.popPose();

        //this.grouproot.render(pPoseStack, pBuffer, pPackedLight, pPackedOverlay, pRed, pGreen, pBlue, pAlpha);
    }


    @Override
    public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

    }
}