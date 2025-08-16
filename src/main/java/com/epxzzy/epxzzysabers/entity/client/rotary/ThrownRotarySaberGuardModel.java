package com.epxzzy.epxzzysabers.entity.client.rotary;

import com.epxzzy.epxzzysabers.entity.custom.ThrownRotarySaber;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.AnimationState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public class ThrownRotarySaberGuardModel<T extends ThrownRotarySaber> extends HierarchicalModel<T> {
    private static final Vector3f ANIMATION_VECTOR_CACHE = new Vector3f();
    private final ModelPart group;
    private final ModelPart guard;
    private final ModelPart handle;
    public AnimationState state = new AnimationState();

    public ThrownRotarySaberGuardModel(ModelPart pRoot) {
        super(RenderType::entityTranslucent);
        this.group = pRoot.getChild("group");
        this.guard = this.group.getChild("guard");
        this.handle = this.group.getChild("handle");
    }

    @Override
    public ModelPart root() {
        return group;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition group = partdefinition.addOrReplaceChild("group", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition guard = group.addOrReplaceChild("guard", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -5.0F, -7.0F, 0.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 2.0F));

        PartDefinition handle = group.addOrReplaceChild("handle", CubeListBuilder.create().texOffs(4, 20).addBox(-3.0F, -5.0F, -3.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 2.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }


    public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {
        this.group.render(pPoseStack, pBuffer, pPackedLight, pPackedOverlay, pRed, pGreen, pBlue, pAlpha);
    }


    @Override
    public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

    }
}