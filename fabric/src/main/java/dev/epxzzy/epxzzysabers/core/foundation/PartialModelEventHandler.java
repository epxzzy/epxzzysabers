package dev.epxzzy.epxzzysabers.core.foundation;

import dev.epxzzy.epxzzysabers.Constants;
import dev.epxzzy.epxzzysabers.core.foundation.visual.PartialModel;
import net.fabricmc.fabric.api.resource.ResourceReloadListenerKeys;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;

import java.util.List;

public final class PartialModelEventHandler {
    /*
    private PartialModelEventHandler() {
    }
    huh???
     */

    public static ResourceLocation[] onRegisterAdditional() {
        return dev.epxzzy.epxzzysabers.core.foundation.visual.PartialModel.ALL.keySet().toArray(ResourceLocation[]::new);
    }

    public static void onBakingCompleted(ModelManager manager) {
        dev.epxzzy.epxzzysabers.core.foundation.visual.PartialModel.populateOnInit = true;
        //epxzzySabers.LOGGER.debug("FKCRT PRTLMDLEVHNDLR partial models baked lmao");

        for (dev.epxzzy.epxzzysabers.core.foundation.visual.PartialModel partial : PartialModel.ALL.values()) {
            //epxzzySabers.LOGGER.debug("FKCRT PRTLMDLEVHNDLR partial model: {}", partial.modelLocation());
            partial.bakedModel = manager.getModel(partial.modelLocation());
        }
    }

    public static final class ReloadListener implements SimpleSynchronousResourceReloadListener {
        public static final ReloadListener INSTANCE = new ReloadListener();

        public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID,"partial_models");
        public static final List<ResourceLocation> DEPENDENCIES = List.of(ResourceReloadListenerKeys.MODELS);

        private ReloadListener() {
        }

        @Override
        public void onResourceManagerReload(ResourceManager resourceManager) {
            onBakingCompleted(Minecraft.getInstance().getModelManager());
        }

        @Override
        public ResourceLocation getFabricId() {
            return ID;
        }

        @Override
        public List<ResourceLocation> getFabricDependencies() {
            return DEPENDENCIES;
        }
    }
}