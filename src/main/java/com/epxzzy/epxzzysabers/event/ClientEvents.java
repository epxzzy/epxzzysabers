package com.epxzzy.epxzzysabers.event;

import com.epxzzy.epxzzysabers.epxzzySabers;
import com.epxzzy.epxzzysabers.entity.SaberModelLayers;
import com.epxzzy.epxzzysabers.entity.client.bolt.PlasmaBoltModel;
import com.epxzzy.epxzzysabers.entity.client.rotary.ThrownRotarySaberBladeModel;
import com.epxzzy.epxzzysabers.entity.client.rotary.ThrownRotarySaberGuardModel;
import com.epxzzy.epxzzysabers.misc.KeyBinding;
import com.epxzzy.epxzzysabers.networking.SaberMessages;
import com.epxzzy.epxzzysabers.networking.packet.saber.ServerboundSaberAbilityPacket;
import com.epxzzy.epxzzysabers.networking.packet.saber.ServerboundSaberStancePacket;
import com.epxzzy.epxzzysabers.screen.stance.StancePreferenceScreen;
import com.epxzzy.epxzzysabers.util.AnimationTickHolder;
import com.epxzzy.epxzzysabers.util.PlayerHelperLmao;
import com.epxzzy.epxzzysabers.util.ScrollValueHandler;
import com.epxzzy.epxzzysabers.util.TagHelper;


//import net.createmod.catnip.levelWrappers.WrappedClientLevel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientEvents {
    public static ShaderInstance glowingShader;

    @Mod.EventBusSubscriber(modid = epxzzySabers.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        //private static long bengignhold = 0;
        private static boolean wasUp = false;

        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            if (KeyBinding.SABER_ABILITY_KEY.consumeClick()) {
                //throw the saber the actually
                //Minecraft.getInstance().player.connection.send(new ServerboundRotarySaberAbilityPacket());
                SaberMessages.sendToServer(new ServerboundSaberAbilityPacket());
            }
            Player player = Minecraft.getInstance().player;
            PlayerHelperLmao MixinPlayer = (PlayerHelperLmao)((Player) player);
                if (!wasUp && KeyBinding.SABER_STANCE_KEY.isDown()) {
                    wasUp = true;
                    SaberMessages.sendToServer(new ServerboundSaberStancePacket(KeyBinding.SABER_STANCE_KEY.isDown(), MixinPlayer.getStancePreference()));
                }
                if (wasUp && !KeyBinding.SABER_STANCE_KEY.isDown()) {
                    wasUp = false;
                    SaberMessages.sendToServer(new ServerboundSaberStancePacket(KeyBinding.SABER_STANCE_KEY.isDown(), MixinPlayer.getStancePreference()));
                }

            if (KeyBinding.SABER_STANCE_KEY.isDown()&&player.isShiftKeyDown()){
                Minecraft.getInstance().setScreen(new StancePreferenceScreen(player));
            }

        }
    }

    @Mod.EventBusSubscriber(modid = epxzzySabers.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.SABER_ABILITY_KEY);
            event.register(KeyBinding.SABER_STANCE_KEY);
        }

        @SubscribeEvent
        public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(SaberModelLayers.THROWN_ROTARY_SABER_GUARD, ThrownRotarySaberGuardModel::createBodyLayer);
            event.registerLayerDefinition(SaberModelLayers.THROWN_ROTART_SABER_BLADE, ThrownRotarySaberBladeModel::createBodyLayer);
            event.registerLayerDefinition(SaberModelLayers.PLASMA_BOLT_LAYER, PlasmaBoltModel::createBodyLayer);


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
        if (world.isClientSide() && world instanceof ClientLevel){ //&& !(world instanceof WrappedClientLevel)) {
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

}


