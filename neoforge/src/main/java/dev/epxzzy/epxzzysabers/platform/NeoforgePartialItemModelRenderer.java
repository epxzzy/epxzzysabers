package dev.epxzzy.epxzzysabers.platform;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.epxzzy.epxzzysabers.core.foundation.Iterate;
import dev.epxzzy.epxzzysabers.platform.services.IPartialItemModelRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.model.data.ModelData;

public class NeoforgePartialItemModelRenderer implements IPartialItemModelRenderer {

    public void renderBakedItemModel(ItemStack stack, BakedModel model, int light, PoseStack ms, VertexConsumer buffer, RandomSource random, int overlay) {
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
        //forge above
    }
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
