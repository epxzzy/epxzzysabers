package com.epxzzy.epxzzysabers;

import com.epxzzy.epxzzysabers.block.SaberBlocks;
import com.epxzzy.epxzzysabers.entity.SaberEntities;
import com.epxzzy.epxzzysabers.entity.client.bolt.PlasmaBoltRenderer;
import com.epxzzy.epxzzysabers.entity.client.rotary.ThrownRotarySaberRenderer;
import com.epxzzy.epxzzysabers.item.SaberCreativeModTabs;
import com.epxzzy.epxzzysabers.item.SaberItems;
import com.epxzzy.epxzzysabers.networking.ModMessages;
import com.epxzzy.epxzzysabers.rendering.foundation.PartialModelEventHandler;
import com.epxzzy.epxzzysabers.screen.stance.KyberStationStanceScreen;
import com.epxzzy.epxzzysabers.screen.tint.KyberStationTintScreen;
import com.epxzzy.epxzzysabers.screen.SaberMenuTypes;
import com.epxzzy.epxzzysabers.sound.SaberSounds;
import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(epxzzySabers.MOD_ID)
public class epxzzySabers{
    public static final String MOD_ID = "epxzzysabers";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static ToolAction SABER_SWING = null;
    public static ToolAction SABER_BLOCK = null;


    public epxzzySabers() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);


        SaberItems.register(modEventBus);
        SaberBlocks.register(modEventBus);
        SaberMenuTypes.register(modEventBus);
        SaberSounds.register(modEventBus);
        SaberEntities.register(modEventBus);


        SaberCreativeModTabs.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

        SABER_SWING = ToolAction.get("saber_swing");
        SABER_BLOCK = ToolAction.get("saber_block");

        modEventBus.addListener(this::addCreative);

        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> epxzzySabersClient::init);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> epxzzySabers.clientInit( MinecraftForge.EVENT_BUS, modEventBus));



    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(()->{
            ModMessages.register();
            //
        });

    }


    private static void clientInit(IEventBus forgeEventBus, IEventBus modEventBus) {
        modEventBus.addListener(PartialModelEventHandler::onRegisterAdditional);
        modEventBus.addListener(PartialModelEventHandler::onBakingCompleted);
        //epxzzySabers.LOGGER.debug("FKCRT PartialModelEventHandler events registered");
    }

        // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }
    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
        // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(SaberMenuTypes.SKREEN_TINT.get(), KyberStationTintScreen::new);
            MenuScreens.register(SaberMenuTypes.SKREEN_STANCE.get(), KyberStationStanceScreen::new);


            EntityRenderers.register(SaberEntities.ROTARY_SABER_ENTITY.get(), ThrownRotarySaberRenderer::new);
            EntityRenderers.register(SaberEntities.PLASMA_BOLT.get(), PlasmaBoltRenderer::new);


        }
    }
}

