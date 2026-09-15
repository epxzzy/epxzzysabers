package dev.epxzzy.epxzzysabers;

import dev.epxzzy.epxzzysabers.core.foundation.ItemRendererRegistry;
import dev.epxzzy.epxzzysabers.core.foundation.Iterate;
import dev.epxzzy.epxzzysabers.core.foundation.PartialModelEventHandler;
import dev.epxzzy.epxzzysabers.registry.ItemRegistry;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import org.spongepowered.asm.util.IConsumer;

import java.util.function.Consumer;

import static dev.epxzzy.epxzzysabers.Constants.MOD_ID;

@Mod(Constants.MOD_ID)
public class Forge {
    static final DeferredRegister<Item> Items = DeferredRegister.create(BuiltInRegistries.ITEM.key(), MOD_ID);

    public Forge(FMLJavaModLoadingContext context) {
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
        eventBus.addListener(this::onClientSetup);
    }

    private static void registerLibEventListeners(IEventBus modEventBus) {
        modEventBus.addListener(PartialModelEventHandler::onRegisterAdditional);
        modEventBus.addListener(PartialModelEventHandler::onBakingCompleted);
    }

    private void onClientSetup(final FMLClientSetupEvent event) {
        /*
        Constants.LOG.info("FKCRT PartialModelEventHandler registerItemExtensions event");

        ItemRendererRegistry.getRendererMap().forEach((item, renderer) -> {
            item.initializeClient(extensions ->
                new IClientItemExtensions() {
                    @Override
                    public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                        Constants.LOG.info("FKCRT getCustomRender set");
                        return renderer;
                    }
                }
            );
            item.initClient();
        });

         */
    }
}