package dev.epxzzy.epxzzysabers.core.net;

import dev.epxzzy.epxzzysabers.core.foundation.net.ClientImpl;
import dev.epxzzy.epxzzysabers.core.foundation.net.ServerImpl;
import dev.epxzzy.epxzzysabers.registry.SaberPacketsRegistry;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

public class simpleimple {
    public static void registorpucket(RegisterPayloadHandlersEvent event) {
        var registrar = event.registrar("1"); // version string

        for (SaberPacketsRegistry.packet entry : SaberPacketsRegistry.list) {
            if (entry.S2C()) {
                registrar.playToClient(entry.type(), entry.codec(),
                    (payload, ctx) -> ctx.enqueueWork(() -> ClientImpl.handleClient(payload)));
            } else if (entry.C2S()) {
                registrar.playToServer(entry.type(), entry.codec(),
                    (payload, ctx) -> ctx.enqueueWork(() -> ServerImpl.handleServer(payload, (ServerPlayer) ctx.player())));
            }
        }
    }
}
