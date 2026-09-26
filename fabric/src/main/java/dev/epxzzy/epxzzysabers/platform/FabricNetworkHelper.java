package dev.epxzzy.epxzzysabers.platform;

import dev.epxzzy.epxzzysabers.core.foundation.net.packets.ISaberPacket;
import dev.epxzzy.epxzzysabers.platform.services.INetworkHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.MinecraftServer;

public class FabricNetworkHelper implements INetworkHelper {
    @Override
    public void sendToServer(ISaberPacket packet) {
        ClientPlayNetworking.send(packet);
    }

    @Override
    public void sendToClient(ServerPlayer player, ISaberPacket packet) {
        ServerPlayNetworking.send(player, packet);
    }

    @Override
    public void sendToClients(ISaberPacket packet) {
        /*
        if (server == null) return;
        for (ServerPlayer player : PlayerLookup.all(server)) {
            ServerPlayNetworking.send(player, packet);
        }
         */
    }
}