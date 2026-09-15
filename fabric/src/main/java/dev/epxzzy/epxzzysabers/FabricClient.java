package dev.epxzzy.epxzzysabers;

import dev.epxzzy.epxzzysabers.core.foundation.ItemRendererRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;

public class FabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Constants.LOG.info("FKCRT clientInit fabric");
        /*
        ItemRendererRegistry.getRendererMap().forEach((item, renderer) -> {
            BuiltinItemRendererRegistry.INSTANCE.register(
                item,
                (BuiltinItemRenderer) renderer
            );

        });

         */

    }
}