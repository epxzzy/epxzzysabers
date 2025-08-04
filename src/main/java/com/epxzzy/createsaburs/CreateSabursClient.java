package com.epxzzy.createsaburs;

import com.epxzzy.createsaburs.item.ModItems;
import com.epxzzy.createsaburs.item.Protosaber;
import com.epxzzy.createsaburs.item.saburtypes.*;
import com.epxzzy.createsaburs.utils.ModTags;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class CreateSabursClient {

    public static void init() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        eventBus.addListener(CreateSabursClient::itemTints);
    }


    public static void onClientSetup(FMLClientSetupEvent event) {
    }
    public static void itemTints(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tint) -> tint > 0 ? -1 : Protosaber.getColor(stack), ModItems.Protosaber.get());
        //event.register((stack, tint) -> tint > 0 ? -1 : protosaber.getColor(stack), ModItems.protosabur2.get());
        event.register((stack, tint) -> tint > 0 ? -1 : SingleBladed.getColor(stack), ModItems.SINGLE_BLADED_SABER.get());
        event.register((stack, tint) -> tint > 0 ? -1 : Protosaber.getColor(stack), ModItems.DUAL_BLADED_SABER.get());
        event.register((stack, tint) -> tint > 0 ? -1 : Protosaber.getColor(stack), ModItems.ROTARY_SABER.get());
        event.register((stack, tint) -> tint > 0 ? -1 : CrossguardSaber.getColor(stack), ModItems.CROSSGUARD_SABER.get());
        event.register((stack, tint) -> tint > 0 ? -1 : BlasterHybrid.getColor(stack), ModItems.BLASTER_SABER.get());
        event.register((stack, tint) -> tint > 0 ? -1 : SaberPike.getColor(stack), ModItems.SABER_PIKE.get());
        event.register((stack, tint) -> tint > 0 ? -1 : SaberGauntlet.getColor(stack), ModItems.SABER_GAUNTLET.get());



    }

    public static void renderParticles(RegisterParticleProvidersEvent event) {
    }

    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
    }
}

