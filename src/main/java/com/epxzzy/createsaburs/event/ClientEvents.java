package com.epxzzy.createsaburs.event;

import com.epxzzy.createsaburs.createsaburs;
import com.epxzzy.createsaburs.entity.ModModelLayers;
import com.epxzzy.createsaburs.entity.client.PlasmaBoltModel;
import com.epxzzy.createsaburs.entity.client.ThrownRotarySaberModel;
import com.epxzzy.createsaburs.entity.client.thebladepart;
import com.epxzzy.createsaburs.misc.KeyBinding;
import com.epxzzy.createsaburs.networking.ModMessages;
import com.epxzzy.createsaburs.networking.packet.ServerboundSaberAbilityPacket;
import com.epxzzy.createsaburs.rendering.foundation.CustomRenderedItemModel;
import com.epxzzy.createsaburs.rendering.foundation.CustomRenderedItems;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.simibubi.create.Create;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;
import java.util.Map;

import static com.epxzzy.createsaburs.rendering.foundation.ModelSwapper.*;

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
        @SubscribeEvent
        public void modifyBakingResult(ModelEvent.ModifyBakingResult event) {
            createsaburs.LOGGER.warn("FKCRT swaping it rn");

            Map<ResourceLocation, BakedModel> modelRegistry = event.getModels();
            //customItemModels.forEach((item, modelFunc) -> swapModels(modelRegistry, getItemModelLocation(item), modelFunc));
            CustomRenderedItems.forEach(item -> swapModels(modelRegistry, getItemModelLocation(item), CustomRenderedItemModel::new));
            com.simibubi.create.foundation.item.render.CustomRenderedItems.forEach(item -> screamoutloud(modelRegistry, getItemModelLocation(item)));
            CustomRenderedItems.forEach(item -> screamoutloud(modelRegistry, getItemModelLocation(item)));

        }
    }

    @SubscribeEvent
    public static void onRegisterShaders(RegisterShadersEvent event) throws IOException {
        ResourceProvider resourceProvider = event.getResourceProvider();
        event.registerShader(new ShaderInstance(resourceProvider, createsaburs.asResource("glowing_shader"),
                DefaultVertexFormat.NEW_ENTITY), shader -> glowingShader = shader);
    }
}


