package dev.epxzzy.epxzzysabers;

import dev.epxzzy.epxzzysabers.registry.ItemRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;

import static dev.epxzzy.epxzzysabers.Constants.MOD_ID;

@Mod(Constants.MOD_ID)
public class ExampleMod {
    static final DeferredRegister<Item> Items = DeferredRegister.create(BuiltInRegistries.ITEM.key(), MOD_ID);

    public ExampleMod(FMLJavaModLoadingContext context) {
        IEventBus eventBus = context.getModEventBus();

        // This method is invoked by the Forge mod loader when it is ready
        // to load your mod. You can access Forge and Common code in this
        // project.

        // Use Forge to bootstrap the Common mod.
        Constants.LOG.info("Hello Forge world!");
        CommonClass.init();

        ItemRegistry.map.forEach(Items::register);
        Items.register(eventBus);
        registerLibEventListeners(eventBus);
    }

    private static void registerLibEventListeners(IEventBus modEventBus) {
        modEventBus.addListener(PartialModelEventHandler::onRegisterAdditional);
        modEventBus.addListener(PartialModelEventHandler::onBakingCompleted);
    }
}