package com.epxzzy.createsaburs.rendering.foundation;

import java.util.Map;

import com.epxzzy.createsaburs.createsaburs;
import org.jetbrains.annotations.ApiStatus;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.ModelEvent;

public final class PartialModelEventHandler {
    private PartialModelEventHandler() {
    }

    public static void onRegisterAdditional(ModelEvent.RegisterAdditional event) {
        for (ResourceLocation modelLocation : PartialModel.ALL.keySet()) {
            event.register(modelLocation);
        }
    }

    public static void onBakingCompleted(ModelEvent.BakingCompleted event) {
        PartialModel.populateOnInit = true;
        createsaburs.LOGGER.warn("partial models baked lmao");
        Map<ResourceLocation, BakedModel> models = event.getModels();

        for ( PartialModel partial : PartialModel.ALL.values()) {
            partial.bakedModel = models.get(partial.modelLocation());
        }
    }
}
