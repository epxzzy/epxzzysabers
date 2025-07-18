package com.epxzzy.createsaburs.rendering.foundation;

import com.epxzzy.createsaburs.ModServices;
import com.epxzzy.createsaburs.createsaburs;
//import com.simibubi.create.foundation.item.render.CustomItemModels;
//import com.simibubi.create.foundation.item.render.CustomRenderedItemModel;
import com.simibubi.create.foundation.item.render.CustomItemModels;
import com.simibubi.create.foundation.item.render.CustomRenderedItems;

import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.mutable.MutableObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ModelSwapper {

    protected CustomItemModels customItemModels = new CustomItemModels();

    public CustomItemModels getCustomItemModels() {
        return customItemModels;
    }

    public void onModelBake(ModelEvent.ModifyBakingResult event) {
        Map<ResourceLocation, BakedModel> modelRegistry = event.getModels();
        customItemModels.forEach((item, modelFunc) -> swapModels(modelRegistry, getItemModelLocation(item), modelFunc));
        CustomRenderedItems.forEach(item -> swapModels(modelRegistry, getItemModelLocation(item), CustomRenderedItemModel::new));
    }

    public void registerListeners(IEventBus modEventBus) {
        modEventBus.addListener(this::onModelBake);
    }


    public static <T extends BakedModel> void swapModels(Map<ResourceLocation, BakedModel> modelRegistry,
                                                         ModelResourceLocation location, Function<BakedModel, T> factory) {
        createsaburs.LOGGER.warn("now logging {}", location.toString());
        modelRegistry.put(location, factory.apply(modelRegistry.get(location)));
    }

    public static ModelResourceLocation getItemModelLocation(Item item) {
        return new ModelResourceLocation(ModServices.REGISTRIES.getKeyOrThrow(item), "inventory");
    }

}
