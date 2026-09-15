package dev.epxzzy.epxzzysabers.core.foundation;

import com.google.common.collect.MapMaker;
import dev.epxzzy.epxzzysabers.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.UnknownNullability;

import java.util.concurrent.ConcurrentMap;

public class PartialModel {
    public static final ConcurrentMap<ResourceLocation, PartialModel> ALL = new MapMaker().weakValues().makeMap();
    public static boolean populateOnInit = false;

    private final ResourceLocation modelLocation;
    @UnknownNullability
    public BakedModel bakedModel;

    private PartialModel(ResourceLocation modelLocation) {
        this.modelLocation = modelLocation;

        if (populateOnInit) {
            bakedModel = getBakedModel(Minecraft.getInstance().getModelManager(), modelLocation);
        }
        Constants.LOG.info("FKCRT PartialModel made for {}",modelLocation);
    }

    public BakedModel getBakedModel(ModelManager modelManager, ResourceLocation location) {
        return modelManager.getModel(new ModelResourceLocation(location, "standalone"));
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