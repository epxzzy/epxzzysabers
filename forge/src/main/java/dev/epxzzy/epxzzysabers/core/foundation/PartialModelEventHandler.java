package dev.epxzzy.epxzzysabers.core.foundation;

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
        for (ResourceLocation modelLocation : PartialModel.ALL.keySet()) {
            event.register(ModelResourceLocation.inventory(modelLocation));
        }
    }

    public static void onBakingCompleted(ModelEvent.BakingCompleted event) {
        PartialModel.populateOnInit = true;
        //epxzzySabers.LOGGER.debug("FKCRT PRTLMDLEVHNDLR partial models baked lmao");
        Map<ModelResourceLocation, BakedModel> models = event.getModels();

        for (PartialModel partial : PartialModel.ALL.values()) {
            //epxzzySabers.LOGGER.debug("FKCRT PRTLMDLEVHNDLR partial model: {}", partial.modelLocation());
            partial.bakedModel = models.get(ModelResourceLocation.inventory(partial.modelLocation()));
        }
    }
}