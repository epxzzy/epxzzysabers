package com.epxzzy.createsaburs.entity.client;// Made with Blockbench 4.12.5
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.epxzzy.createsaburs.entity.custom.ThrownRotarySaber;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueHandler;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;

public class thebladepart<T extends ThrownRotarySaber> extends Model {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	private final ModelPart bone;

	public thebladepart(ModelPart root) {
        super(RenderType::entityTranslucentEmissive);
        this.bone = root.getChild("bone");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(-48, 0).addBox(-24.0F, -2.0F, -24.0F, 48.0F, 0.0F, 48.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	public void renderToBuffer(PoseStack pMatrixStack, MultiBufferSource pBuffer, ThrownRotarySaber pEntity, float pPartialTicks,ResourceLocation GLOWLOC, int packedLight, int packedOverlay) {
		pMatrixStack.pushPose();

		float f;
		float f1;
		float f2;

		//if (pEntity.getTags().contains("gay")) {
		/*
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

		 */
		//} else {


			int[] afloat = pEntity.getColour();
			f = afloat[0];
			f1 = afloat[1];
			f2 = afloat[2];

		//}
		//pMatrixStack.mulPose(Axis.YP.rotation(-ScrollValueHandler.getScroll((float) (AnimationTickHolder.getPartialTicks()) * 5)));
		pMatrixStack.mulPose(Axis.YP.rotation(-ScrollValueHandler.getScroll((float) (AnimationTickHolder.getPartialTicks()) * 3)));

		pMatrixStack.translate(0, -1, 0);
		VertexConsumer vertexconsumur = pBuffer.getBuffer(RenderType.entityTranslucentEmissive(GLOWLOC)).color(f/255, f1/255, f2/255, 1.0F);
		//VertexConsumer vertexconsumur = pBuffer.color(f, f1, f2, 1.0F);

		this.bone.render(pMatrixStack, vertexconsumur, packedLight, packedOverlay, f/255, f1/255, f2/255, 1.0F);
		pMatrixStack.popPose();


		//bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public ModelPart root() {
		return this.bone;
	}


	@Override
	public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {

	}
}