package dev.epxzzy.epxzzysabers.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.epxzzy.epxzzysabers.Constants;
import dev.epxzzy.epxzzysabers.core.foundation.ItemRendererRegistry;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {

    @Inject(
        method = "render",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/BlockEntityWithoutLevelRenderer;renderByItem(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V"
        ),
        cancellable = true
    )
    private void epxzzysabers$renderCustomBEWLR(
        ItemStack stack,
        ItemDisplayContext displayContext,
        boolean leftHand,
        PoseStack poseStack,
        MultiBufferSource bufferSource,
        int combinedLight,
        int combinedOverlay,
        BakedModel model,
        CallbackInfo ci
    ) {
        Constants.LOG.info("FKCRT ItemRendererMixin fabric");
        BlockEntityWithoutLevelRenderer renderer =
            ItemRendererRegistry.getRendererMap().get(stack.getItem());

        if (renderer == null) {
            return;
        }

        renderer.renderByItem(
            stack,
            displayContext,
            poseStack,
            bufferSource,
            combinedLight,
            combinedOverlay
        );

        poseStack.popPose();
        ci.cancel();
    }
}