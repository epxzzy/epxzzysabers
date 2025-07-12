package com.epxzzy.createsaburs.event;

import com.epxzzy.createsaburs.createsaburs;
import com.epxzzy.createsaburs.entity.ModModelLayers;
import com.epxzzy.createsaburs.entity.client.ThrownRotarySaberModel;
import com.epxzzy.createsaburs.entity.client.thebladepart;
import com.epxzzy.createsaburs.misc.KeyBinding;
import com.epxzzy.createsaburs.networking.ModMessages;
import com.epxzzy.createsaburs.networking.packet.ServerboundSaberAbilityPacket;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.simibubi.create.Create;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;

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

        }
    }

    @SubscribeEvent
    public static void onRegisterShaders(RegisterShadersEvent event) throws IOException {
        ResourceProvider resourceProvider = event.getResourceProvider();
        event.registerShader(new ShaderInstance(resourceProvider, createsaburs.asResource("glowing_shader"),
                DefaultVertexFormat.NEW_ENTITY), shader -> glowingShader = shader);
    }
}


