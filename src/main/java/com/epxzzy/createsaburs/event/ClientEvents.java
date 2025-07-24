package com.epxzzy.createsaburs.event;

import com.epxzzy.createsaburs.createsaburs;
import com.epxzzy.createsaburs.entity.ModModelLayers;
import com.epxzzy.createsaburs.entity.client.PlasmaBoltModel;
import com.epxzzy.createsaburs.entity.client.ThrownRotarySaberModel;
import com.epxzzy.createsaburs.entity.client.thebladepart;
import com.epxzzy.createsaburs.misc.KeyBinding;
import com.epxzzy.createsaburs.networking.ModMessages;
import com.epxzzy.createsaburs.networking.packet.ServerboundSaberAbilityPacket;
import com.epxzzy.createsaburs.utils.AnimationTickHolder;
import com.epxzzy.createsaburs.utils.ScrollValueHandler;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;


import net.createmod.catnip.levelWrappers.WrappedClientLevel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;


@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientEvents {
    public static ShaderInstance glowingShader;

    @Mod.EventBusSubscriber(modid = createsaburs.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            if (KeyBinding.SABER_ABILITY_KEY.consumeClick()) {
                //throw the saber the actually
                //Minecraft.getInstance().player.connection.send(new ServerboundRotarySaberAbilityPacket());
                ModMessages.sendToServer(new ServerboundSaberAbilityPacket());
            }
        }
    }

    @Mod.EventBusSubscriber(modid = createsaburs.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.SABER_ABILITY_KEY);
        }

        @SubscribeEvent
        public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(ModModelLayers.thrownrotarysabermodellayer, ThrownRotarySaberModel::createBodyLayer);
            event.registerLayerDefinition(ModModelLayers.thrownrotarysaberblademodellayer, thebladepart::createBodyLayer);
            event.registerLayerDefinition(ModModelLayers.PLASMA_BOLT_LAYER, PlasmaBoltModel::createBodyLayer);


        }
    }
    @SubscribeEvent
    public static void onTick(TickEvent.ClientTickEvent event) {
        AnimationTickHolder.tick();

        if (!ScrollValueHandler.isGameActive())
            return;

        if (event.phase == TickEvent.Phase.START) {
            return;
        }
        ScrollValueHandler.tick();
    }

    @SubscribeEvent
    public static void onLoadWorld(LevelEvent.Load event) {
        LevelAccessor world = event.getLevel();
        if (world.isClientSide() && world instanceof ClientLevel && !(world instanceof WrappedClientLevel)) {
            AnimationTickHolder.reset();
        }
    }

    @SubscribeEvent
    public static void onUnloadWorld(LevelEvent.Unload event) {
        if (!event.getLevel()
                .isClientSide())
            return;
        AnimationTickHolder.reset();
    }

        @SubscribeEvent
    public static void onRegisterShaders(RegisterShadersEvent event) throws IOException {
        ResourceProvider resourceProvider = event.getResourceProvider();
        event.registerShader(new ShaderInstance(resourceProvider, createsaburs.asResource("glowing_shader"),
                DefaultVertexFormat.NEW_ENTITY), shader -> glowingShader = shader);
    }
}


