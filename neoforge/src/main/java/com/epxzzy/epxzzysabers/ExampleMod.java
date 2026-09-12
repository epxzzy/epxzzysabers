package dev.epxzzy.epxzzysabers;


import dev.epxzzy.epxzzysabers.registry.ItemRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredRegister;

import static dev.epxzzy.epxzzysabers.Constants.MOD_ID;

@Mod(Constants.MOD_ID)
public class ExampleMod {
    static final DeferredRegister<Item> Items =DeferredRegister.createItems(MOD_ID);

    public ExampleMod(IEventBus eventBus) {

        // This method is invoked by the NeoForge mod loader when it is ready
        // to load your mod. You can access NeoForge and Common code in this
        // project.

        // Use NeoForge to bootstrap the Common mod.
        Constants.LOG.info("Hello NeoForge world!");
        CommonClass.init();

        ItemRegistry.map.forEach(Items::register);
        Items.register(eventBus);

    }
}