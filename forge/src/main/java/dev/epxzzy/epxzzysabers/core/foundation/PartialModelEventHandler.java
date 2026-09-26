package dev.epxzzy.epxzzysabers.core.foundation;

import dev.epxzzy.epxzzysabers.core.foundation.visual.PartialModel;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.ModelEvent;

import java.util.Map;

public final class PartialModelEventHandler {
    /*
    private PartialModelEventHandler() {
    }
    huh???
     */

    public static void onRegisterAdditional(ModelEvent.RegisterAdditional event) {
        for (ResourceLocation modelLocation : dev.epxzzy.epxzzysabers.core.foundation.visual.PartialModel.ALL.keySet()) {
            event.register(ModelResourceLocation.inventory(modelLocation));
        }
    }

    public static void onBakingCompleted(ModelEvent.BakingCompleted event) {
        dev.epxzzy.epxzzysabers.core.foundation.visual.PartialModel.populateOnInit = true;
        //epxzzySabers.LOGGER.debug("FKCRT PRTLMDLEVHNDLR partial models baked lmao");
        Map<ModelResourceLocation, BakedModel> models = event.getModels();

        for (dev.epxzzy.epxzzysabers.core.foundation.visual.PartialModel partial : PartialModel.ALL.values()) {
            //epxzzySabers.LOGGER.debug("FKCRT PRTLMDLEVHNDLR partial model: {}", partial.modelLocation());
            partial.bakedModel = models.get(ModelResourceLocation.inventory(partial.modelLocation()));
        }
    }
}