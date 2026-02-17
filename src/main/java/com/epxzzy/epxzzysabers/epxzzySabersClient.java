package com.epxzzy.epxzzysabers;

import com.epxzzy.epxzzysabers.item.SaberItems;
import com.epxzzy.epxzzysabers.item.Protosaber;
import com.epxzzy.epxzzysabers.item.types.*;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class epxzzySabersClient {

    public static void init() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        eventBus.addListener(epxzzySabersClient::itemTints);
    }


    public static void onClientSetup(FMLClientSetupEvent event) {
    }
    public static void itemTints(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tint) -> tint > 0 ? -1 : Protosaber.getColor(stack), SaberItems.Protosaber.get());
        //event.register((stack, tint) -> tint > 0 ? -1 : protosaber.getColor(stack), SaberItems.protosabur2.get());
        event.register((stack, tint) -> tint > 0 ? -1 : SingleBladed.getColor(stack), SaberItems.SINGLE_BLADED_SABER.get());
        event.register((stack, tint) -> tint > 0 ? -1 : Protosaber.getColor(stack), SaberItems.DUAL_BLADED_SABER.get());
        event.register((stack, tint) -> tint > 0 ? -1 : Protosaber.getColor(stack), SaberItems.ROTARY_SABER.get());
        event.register((stack, tint) -> tint > 0 ? -1 : CrossguardSaber.getColor(stack), SaberItems.CROSSGUARD_SABER.get());
        event.register((stack, tint) -> tint > 0 ? -1 : BlasterHybrid.getColor(stack), SaberItems.BLASTER_SABER.get());
        event.register((stack, tint) -> tint > 0 ? -1 : SaberPike.getColor(stack), SaberItems.SABER_PIKE.get());
        event.register((stack, tint) -> tint > 0 ? -1 : SaberGauntlet.getColor(stack), SaberItems.SABER_GAUNTLET.get());



    }

    public static void renderParticles(RegisterParticleProvidersEvent event) {
    }

    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
    }
}

