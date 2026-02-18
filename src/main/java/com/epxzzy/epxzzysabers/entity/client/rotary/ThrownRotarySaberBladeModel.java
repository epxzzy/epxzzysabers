package com.epxzzy.epxzzysabers.entity.client.rotary;// Made with Blockbench 4.12.5
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.epxzzy.epxzzysabers.entity.custom.ThrownRotarySaber;
import com.epxzzy.epxzzysabers.rendering.foundation.RenderTypes;
import com.epxzzy.epxzzysabers.util.AnimationTickHolder;
import com.epxzzy.epxzzysabers.util.ScrollValueHandler;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class ThrownRotarySaberBladeModel<T extends ThrownRotarySaber> extends Model {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	private final ModelPart bone;

	public ThrownRotarySaberBladeModel(ModelPart root) {
        super(RenderType::entitySolid);
        this.bone = root.getChild("bone");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(-48, 0).addBox(-24.0F, -2.0F, -24.0F, 48.0F, 0.0F, 48.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
		//PartDefinition bone2 = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(-48, 0).addBox(-24.0F, -1.0F, -24.0F, 48.0F, 0.0F, 48.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	public void renderToBuffer(PoseStack pMatrixStack, MultiBufferSource pBuffer, ThrownRotarySaber pEntity, float pPartialTicks,ResourceLocation GLOWLOC, int packedLight, int packedOverlay) {
		pMatrixStack.pushPose();

		float f;
		float f1;
		float f2;

			int[] afloat = pEntity.getColour();
			f = afloat[0];
			f1 = afloat[1];
			f2 = afloat[2];

		//pMatrixStack.mulPose(Axis.YP.rotation(-ScrollValueHandler.getScroll((float) (AnimationTickHolder.getPartialTicks()) * 5)));
		pMatrixStack.mulPose(Axis.YP.rotation(-ScrollValueHandler.getScroll((float) (AnimationTickHolder.getPartialTicks()) * 5)));

		pMatrixStack.translate(0, -1, 0);
		VertexConsumer vertexconsumur = pBuffer.getBuffer(RenderTypes.ROTARY_BLADE_EXPERIMENTAL);
		//VertexConsumer vertexconsumur = pBuffer.getBuffer(RenderType.eyes(GLOWLOC));

		//.color(f/255, f1/255, f2/255, 1.0F);
		//VertexConsumer vertexconsumur = pBuffer.color(f, f1, f2, 1.0F);

		this.bone.render(pMatrixStack, vertexconsumur, packedLight, packedOverlay, f/255, f1/255, f2/255, 1.0F);
		pMatrixStack.popPose();


		//bone.renderMid(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public ModelPart root() {
		return this.bone;
	}


	@Override
	public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {

	}
}