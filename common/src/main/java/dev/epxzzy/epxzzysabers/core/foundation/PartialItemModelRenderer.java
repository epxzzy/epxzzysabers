package dev.epxzzy.epxzzysabers.core.foundation;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class PartialItemModelRenderer {

    private static final PartialItemModelRenderer INSTANCE = new PartialItemModelRenderer();

    private final RandomSource random = RandomSource.create();

    private ItemStack stack;
    private ItemDisplayContext transformType;
    private PoseStack ms;
    private MultiBufferSource buffer;
    private int overlay;

    public static PartialItemModelRenderer of(ItemStack stack, ItemDisplayContext transformType,
                                              PoseStack ms, MultiBufferSource buffer, int overlay) {
        //epxzzySabers.LOGGER.debug("FKCRT PartialItemModelRenderer made for itemstacc {}", stack);
        PartialItemModelRenderer instance = INSTANCE;
        instance.stack = stack;
        instance.transformType = transformType;
        instance.ms = ms;
        instance.buffer = buffer;
        instance.overlay = overlay;
        return instance;
    }

    public void render(BakedModel model, int light) {
        render(model, Sheets.translucentCullBlockSheet(), light);
    }

    public void renderGlowing(BakedModel model, int light, MultiBufferSource bugger) {
        renderGlowy(model, RenderTypes.ITEM_GLOWING_EXPERIMENTAL, light, bugger);
    }

    public void render(BakedModel model, RenderType type, int light) {
        if (stack.isEmpty())
            return;

        ms.pushPose();
        ms.translate(-0.5D, -0.5D, -0.5D);

        VertexConsumer vc = ItemRenderer.getFoilBufferDirect(buffer, type, true, stack.hasFoil());
        renderBakedItemModel(model, light, ms, vc);
        ms.popPose();
    }

    public void renderGlowy(BakedModel model, RenderType type, int light, MultiBufferSource bugger) {
        if (stack.isEmpty())
            return;

        ms.pushPose();
        ms.translate(-0.5D, -0.5D, -0.5D);

        VertexConsumer vc = bugger.getBuffer(type);
        renderBakedItemModel(model, light, ms, vc);

        ms.popPose();
    }

    private void renderBakedItemModel(BakedModel model, int light, PoseStack ms, VertexConsumer buffer) {
        ItemRenderer ir = Minecraft.getInstance()
            .getItemRenderer();

            for (Direction direction : Direction.values()) {
                random.setSeed(42L);
                ir.renderQuadList(ms, buffer, model.getQuads(null, direction, random), stack, light,
                    overlay);
            }

            random.setSeed(42L);
            ir.renderQuadList(ms, buffer, model.getQuads(null, null, random), stack, light, overlay);
    }

}