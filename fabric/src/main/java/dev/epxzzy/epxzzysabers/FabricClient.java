package dev.epxzzy.epxzzysabers;

import net.fabricmc.api.ClientModInitializer;

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