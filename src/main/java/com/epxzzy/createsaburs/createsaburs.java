package com.epxzzy.createsaburs;

import com.epxzzy.createsaburs.block.ModBlockEntities;
import com.epxzzy.createsaburs.block.ModBlocks;
import com.epxzzy.createsaburs.entity.ModEntities;
import com.epxzzy.createsaburs.entity.client.PlasmaBoltRenderer;
import com.epxzzy.createsaburs.entity.client.ThrownRotarySaberRenderer;
import com.epxzzy.createsaburs.item.ModCreativeModTabs;
import com.epxzzy.createsaburs.item.ModItems;
import com.epxzzy.createsaburs.networking.ModMessages;
import com.epxzzy.createsaburs.rendering.foundation.ModelSwapper;
import com.epxzzy.createsaburs.rendering.foundation.PartialModelEventHandler;
import com.epxzzy.createsaburs.screen.KyberStationScreen;
import com.epxzzy.createsaburs.screen.ModMenuTypes;
import com.epxzzy.createsaburs.sound.ModSounds;
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
import net.minecraftforge.fml.CrashReportCallables;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(createsaburs.MOD_ID)
public class createsaburs {
    public static final String MOD_ID = "createsaburs";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static ToolAction SABER_SWING = null;
    public static ToolAction SABER_BLOCK = null;


    public createsaburs() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModSounds.register(modEventBus);
        ModEntities.register(modEventBus);


        ModCreativeModTabs.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

        SABER_SWING = ToolAction.get("saber_swing");
        SABER_BLOCK = ToolAction.get("saber_block");


        modEventBus.addListener(this::addCreative);
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> createsabursClient::init);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> createsaburs.clientInit( MinecraftForge.EVENT_BUS, modEventBus));
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> createsabursClient.onCtorClient(MinecraftForge.EVENT_BUS, modEventBus));


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
        createsaburs.LOGGER.warn("FKCRT PartialModelEventHandler events registered");
        //modEventBus.addListener(ModelSwapper::registerListeners);
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
            MenuScreens.register(ModMenuTypes.SKREEN.get(), KyberStationScreen::new);


            EntityRenderers.register(ModEntities.ROTARY_SABER_ENTITY.get(), ThrownRotarySaberRenderer::new);
            EntityRenderers.register(ModEntities.PLASMA_BOLT.get(), PlasmaBoltRenderer::new);


        }
    }
}

