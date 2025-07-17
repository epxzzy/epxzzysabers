package com.epxzzy.createsaburs.rendering.foundation;

import com.epxzzy.createsaburs.createsaburs;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.mutable.MutableObject;

import java.util.Map;

public class ModelSwapper {

    public static void onModifyBakingResult(ModelEvent.ModifyBakingResult event) {
        Map<ResourceLocation, BakedModel> modelRegistry = event.getModels();
        CustomRenderedItems.forEach(item -> {
            ResourceLocation registryName = ForgeRegistries.ITEMS.getKey(item);
            if (registryName == null) return;
            ModelResourceLocation mrl = new ModelResourceLocation(registryName, "inventory");
            MutableObject<BakedModel> result = new MutableObject<>();
            BakedModel original = modelRegistry.get(mrl);
            if (original == null) {
                createsaburs.LOGGER.warn("No inventory model found for item: " + registryName);
                return;
            }
            result.setValue(new CustomRenderedItemModel(original));
            modelRegistry.put(mrl, result.getValue());
        });
    }

}
