package com.epxzzy.createsaburs;

import com.epxzzy.createsaburs.item.ModItems;
import com.epxzzy.createsaburs.item.Protosaber;
import com.epxzzy.createsaburs.item.saburtypes.BlasterHybrid;
import com.epxzzy.createsaburs.item.saburtypes.CrossguardSaber;
import com.epxzzy.createsaburs.item.saburtypes.RotarySaber;
import com.epxzzy.createsaburs.item.saburtypes.SingleBladed;
import com.epxzzy.createsaburs.rendering.foundation.PartialModelEventHandler;
import com.simibubi.create.AllParticleTypes;
import com.simibubi.create.CreateClient;
import com.simibubi.create.compat.Mods;
import com.simibubi.create.compat.ftb.FTBIntegration;
import com.simibubi.create.foundation.render.AllInstanceTypes;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class createsabursClient {

    public static void init() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        eventBus.addListener(createsabursClient::itemTints);
    }


    public static void onClientSetup(FMLClientSetupEvent event) {
    }
    public static void itemTints(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tint) -> tint > 0 ? -1 : Protosaber.getColor(stack), ModItems.Protosaber.get());
        //event.register((stack, tint) -> tint > 0 ? -1 : protosaber.getColor(stack), ModItems.protosabur2.get());
        event.register((stack, tint) -> tint > 0 ? -1 : SingleBladed.getColor(stack), ModItems.SINGLE_BLADED_SABER.get());
        event.register((stack, tint) -> tint > 0 ? -1 : Protosaber.getColor(stack), ModItems.DUAL_BLADED_SABER.get());
        event.register((stack, tint) -> tint > 0 ? -1 : RotarySaber.getColor(stack), ModItems.ROTARY_SABER.get());
        event.register((stack, tint) -> tint > 0 ? -1 : CrossguardSaber.getColor(stack), ModItems.CROSSGUARD_SABER.get());
        event.register((stack, tint) -> tint > 0 ? -1 : BlasterHybrid.getColor(stack), ModItems.BLASTER_HYBRID.get());

    }

    public static void renderParticles(RegisterParticleProvidersEvent event) {
    }

    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
    }
}

