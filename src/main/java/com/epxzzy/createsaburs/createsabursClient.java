package com.epxzzy.createsaburs;

import com.epxzzy.createsaburs.item.ModItems;
import com.epxzzy.createsaburs.item.protosaber;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static com.epxzzy.createsaburs.item.ModItems.protosabur;

public class createsabursClient {
        public static boolean INSIDE_SOUL_GLASS = false;
        public static boolean INSIDE_MAGMA_CREAM_BLOCK = false;
        public static boolean INSIDE_ECTOPLASM = false;

        public static void init() {
            IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

            eventBus.addListener(createsabursClient::itemTints);
        }

        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
           // event.registerAboveAll("agitated", AgitatedOverlay.HUD);
        }

        public static void onClientSetup(FMLClientSetupEvent event) {
        }

        public static void itemTints(RegisterColorHandlersEvent.Item event) {
            event.register((stack, tint) -> tint > 0 ? -1 : protosaber.getColor(stack), protosabur.get());
            event.register((stack, tint) -> tint > 0 ? -1 : protosaber.getColor(stack), ModItems.protosabur2.get());

        }

        public static void renderParticles(RegisterParticleProvidersEvent event) {
        }

        public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        }
    }

