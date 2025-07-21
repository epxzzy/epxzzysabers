package com.epxzzy.createsaburs.rendering;

import com.epxzzy.createsaburs.createsaburs;
import com.epxzzy.createsaburs.rendering.foundation.CustomRenderedItemModel;
import com.epxzzy.createsaburs.rendering.foundation.PartialItemModelRenderer;
import com.epxzzy.createsaburs.rendering.foundation.PartialModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueHandler;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import java.util.List;

import static com.epxzzy.createsaburs.rendering.ProtosaberItemRenderer.getEntitiesHoldingItem;

public class ExperimentalBEWLR extends BlockEntityWithoutLevelRenderer {
    protected static final PartialModel GEAR_BIT = PartialModel.of(createsaburs.asResource("item/additive/gear"));
    protected static final PartialModel GLOWLY_BIT = PartialModel.of(createsaburs.asResource("item/additive/blade_single"));
    ModelManager modelManager = Minecraft.getInstance().getModelManager();
    BakedModel model1 = modelManager.getModel(new ModelResourceLocation(createsaburs.asResource("item/additive/gear"), "inventory"));
    private static ExperimentalBEWLR instance;

    public ExperimentalBEWLR(BlockEntityRenderDispatcher pBlockEntityRenderDispatcher, EntityModelSet pEntityModelSet) {
        super(pBlockEntityRenderDispatcher, pEntityModelSet);
    }



    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext context, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int overlay) {
        float partialTick = Minecraft.getInstance().getPartialTick();

        if (GEAR_BIT.get() != null) {
            poseStack.pushPose();
            Minecraft.getInstance().getItemRenderer().render(
                    stack,
                    context,
                    false,
                    poseStack,
                    bufferSource,
                    packedLight,
                    overlay,
                    GEAR_BIT.get()
            );
            createsaburs.LOGGER.warn("FKCRT now rendering a cog biatch");
            poseStack.popPose();
        }

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        poseStack.pushPose();
        poseStack.translate(0.5, 0, 0); // Example transformation
        itemRenderer.render(stack, context, false, poseStack, bufferSource, packedLight, overlay, GEAR_BIT.get());
        poseStack.popPose();
/*
        if (stack.is(JNEItems.PUMP_CHARGE_SHOTGUN.get())) {
            poseStack.pushPose();
            poseStack.translate(0.5f, 1.5f, 0.5f);
            poseStack.mulPose(Axis.XP.rotationDegrees(-180));
            poseStack.mulPose(Axis.YP.rotationDegrees(180));
            poseStack.scale(1.0F, 1.0F, 1.0F);
            VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(texture));
            GEAR_BIT.getBakedModel()
            PUMP_CHARGE_SHOTGUN_MODEL.renderToBuffer(poseStack, vertexConsumer, packedLight, overlay, 1.0f, 1.0f, 1.0f, 1.0f);
            poseStack.popPose();
        }
        if (stack.is(JNEItems.SHOTGUN_FIST.get())) {
            poseStack.pushPose();
            poseStack.translate(0.5f, 1.5f, 0.5f);
            poseStack.mulPose(Axis.XP.rotationDegrees(-180));
            poseStack.mulPose(Axis.YP.rotationDegrees(180));
            poseStack.scale(1.0F, 1.0F, 1.0F);
            VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(texture));
            SHOTGUN_FIST_MODEL.renderToBuffer(poseStack, vertexConsumer, packedLight, overlay, 1.0f, 1.0f, 1.0f, 1.0f);
            poseStack.popPose();
        }
        if (stack.is(JNEItems.JACKHAMMER_FIST.get())) {
            int tickCount = player == null ? 0 : player.tickCount;
            float ageInTicks = player == null ? 0f : tickCount + partialTick;
            poseStack.pushPose();
            poseStack.translate(0.5f, 1.5f, 0.5f);
            poseStack.mulPose(Axis.XP.rotationDegrees(-180));
            poseStack.mulPose(Axis.YP.rotationDegrees(180));
            poseStack.scale(1.0F, 1.0F, 1.0F);
            VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(texture));
            JACKHAMMER_FIST_MODEL.renderToBuffer(poseStack, vertexConsumer, packedLight, overlay, 1.0f, 1.0f, 1.0f, 1.0f);
            poseStack.popPose();
        }

 */
    }

    public static ExperimentalBEWLR getInstance() {
        if (instance == null) {
            instance = new ExperimentalBEWLR(
                    Minecraft.getInstance().getBlockEntityRenderDispatcher(),
                    Minecraft.getInstance().getEntityModels()
            );
        }
        return instance;
    }

    //public void renderByItem(ItemStack stack, ItemDisplayContext context, PoseStack poseStack, MultiBufferSource buffer, int light, int overlay) {
        // Render base item
        //vanillaRenderer.renderByItem(stack, context, poseStack, buffer, light, overlay);

        // Render your dynamic model on top
    //}
}
