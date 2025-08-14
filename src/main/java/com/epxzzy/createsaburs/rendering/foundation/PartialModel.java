package com.epxzzy.createsaburs.rendering.foundation;

import com.epxzzy.createsaburs.CreateSaburs;
import com.google.common.collect.MapMaker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.UnknownNullability;

import java.util.concurrent.ConcurrentMap;

public class PartialModel {
    static final ConcurrentMap<ResourceLocation, PartialModel> ALL = new MapMaker().weakValues().makeMap();
    static boolean populateOnInit = false;


    private final ResourceLocation modelLocation;
    @UnknownNullability
    BakedModel bakedModel;


    private PartialModel(ResourceLocation modelLocation) {
        this.modelLocation = modelLocation;

        if (populateOnInit) {
            bakedModel = getBakedModel(Minecraft.getInstance().getModelManager(), modelLocation);
        }
        //CreateSaburs.LOGGER.debug("FKCRT PartialModel made for {}",modelLocation);
    }


    public BakedModel getBakedModel(ModelManager modelManager, ResourceLocation location) {
        return modelManager.getModel(location);
    }

    public static PartialModel of(ResourceLocation modelLocation) {
        return ALL.computeIfAbsent(modelLocation, PartialModel::new);
    }


    @UnknownNullability
    public BakedModel get() {
        return bakedModel;
    }

    public ResourceLocation modelLocation() {
        return modelLocation;
    }

}
