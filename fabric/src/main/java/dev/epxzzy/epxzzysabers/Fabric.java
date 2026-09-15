package dev.epxzzy.epxzzysabers;

import dev.epxzzy.epxzzysabers.core.foundation.PartialModelEventHandler;
import dev.epxzzy.epxzzysabers.registry.ItemRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;

import static dev.epxzzy.epxzzysabers.Constants.MOD_ID;

public class Fabric implements ModInitializer {

    @Override
    public void onInitialize() {

        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // project.

        // Use Fabric to bootstrap the Common mod.
        Constants.LOG.info("Hello Fabric world!");
        CommonClass.init();

        ItemRegistry.map.forEach((
            (s, supplier) -> Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(MOD_ID, s),
                supplier.get()
            ))
        );

        setupLib();
    }

    private static void setupLib() {
        ModelLoadingPlugin.register(ctx -> {
            ctx.addModels(PartialModelEventHandler.onRegisterAdditional());
        });
        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(PartialModelEventHandler.ReloadListener.INSTANCE);
    }

}
