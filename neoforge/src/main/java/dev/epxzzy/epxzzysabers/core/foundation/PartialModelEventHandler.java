package dev.epxzzy.epxzzysabers.core.foundation;

import dev.epxzzy.epxzzysabers.Constants;
import dev.epxzzy.epxzzysabers.Neoforge;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.ModelEvent;

import java.util.Map;

public final class PartialModelEventHandler {
    /*
    private PartialModelEventHandler() {
    }
    huh???
     */

    public static void onRegisterAdditional(ModelEvent.RegisterAdditional event) {
        for (ResourceLocation modelLocation : PartialModel.ALL.keySet()) {
            event.register(ModelResourceLocation.standalone(modelLocation));
        }
    }

    public static void onBakingCompleted(ModelEvent.BakingCompleted event) {
        PartialModel.populateOnInit = true;
        Constants.LOG.info("FKCRT PRTLMDLEVHNDLR partial models baked lmao");
        Map<ModelResourceLocation, BakedModel> models = event.getModels();

        for (PartialModel partial : PartialModel.ALL.values()) {
            Constants.LOG.info("FKCRT PRTLMDLEVHNDLR partial model: {}", partial.modelLocation());
            partial.bakedModel = models.get(ModelResourceLocation.standalone(partial.modelLocation()));
        }
    }
}