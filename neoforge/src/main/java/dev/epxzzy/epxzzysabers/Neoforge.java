package dev.epxzzy.epxzzysabers;


import com.mojang.logging.LogUtils;
import dev.epxzzy.epxzzysabers.core.foundation.ItemRendererRegistry;
import dev.epxzzy.epxzzysabers.core.foundation.PartialModelEventHandler;
import dev.epxzzy.epxzzysabers.core.item.SingleBladedItemRenderer;
import dev.epxzzy.epxzzysabers.registry.ItemRegistry;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.logging.Logger;

import static dev.epxzzy.epxzzysabers.Constants.MOD_ID;

@Mod(Constants.MOD_ID)
public class Neoforge {
    static final DeferredRegister<Item> Items = DeferredRegister.createItems(MOD_ID);

    public Neoforge(IEventBus eventBus) {
        eventBus.addListener(this::registerItemExtensions);
        // This method is invoked by the NeoForge mod loader when it is ready
        // to load your mod. You can access NeoForge and Common code in this
        // project.

        // Use NeoForge to bootstrap the Common mod.
        Constants.LOG.info("Hello NeoForge world!");
        CommonClass.init();

        ItemRegistry.map.forEach(Items::register);
        Items.register(eventBus);
        registerLibEventListeners(NeoForge.EVENT_BUS, eventBus);
    }

    private static void registerLibEventListeners(IEventBus gameEventBus, IEventBus modEventBus) {
        modEventBus.addListener(PartialModelEventHandler::onRegisterAdditional);
        modEventBus.addListener(PartialModelEventHandler::onBakingCompleted);
        Constants.LOG.info("FKCRT PartialModelEventHandler events registered");

    }

    @SubscribeEvent
    public void registerItemExtensions(RegisterClientExtensionsEvent event) {
        Constants.LOG.info("FKCRT PartialModelEventHandler registerItemExtensions event");
        ItemRendererRegistry.getRendererMap().forEach((item, renderer) -> {
            event.registerItem(
                new IClientItemExtensions(){
                    @Override
                    public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                        return renderer;
                    }
                },
                item);
        });
    }
}