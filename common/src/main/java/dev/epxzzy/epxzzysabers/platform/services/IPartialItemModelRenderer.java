package dev.epxzzy.epxzzysabers.platform.services;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;

public interface IPartialItemModelRenderer {

    void renderBakedItemModel(ItemStack stack, BakedModel model, int light, PoseStack ms, VertexConsumer buffer, RandomSource random, int overlay);
    /*
    {
        ItemRenderer ir = Minecraft.getInstance()
            .getItemRenderer();
        ModelData data = ModelData.EMPTY;

        for (RenderType renderType : model.getRenderTypes(stack, false)) {
            for (Direction direction : Iterate.directions) {
                random.setSeed(42L);
                ir.renderQuadList(ms, buffer, model.getQuads(null, direction, random, data, renderType), stack, light,
                    overlay);
            }

            random.setSeed(42L);
            ir.renderQuadList(ms, buffer, model.getQuads(null, null, random, data, renderType), stack, light, overlay);
        }
    }
     */
    //forge above
    /*
    {
        ItemRenderer ir = Minecraft.getInstance()
            .getItemRenderer();
//		IModelData data = EmptyModelData.INSTANCE;

        for (Direction direction : Iterate.directions) {
            random.setSeed(42L);
            ItemRendererHelper.renderQuadList(ir, ms, buffer, model.getQuads(null, direction, random), stack, light, overlay);
        }

        random.setSeed(42L);
        ItemRendererHelper.renderQuadList(ir, ms, buffer, model.getQuads(null, null, random), stack, light, overlay);
    }
     */
    //fabric
}
