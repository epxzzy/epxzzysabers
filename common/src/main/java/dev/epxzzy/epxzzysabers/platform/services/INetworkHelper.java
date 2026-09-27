package dev.epxzzy.epxzzysabers.platform.services;

import dev.epxzzy.epxzzysabers.core.foundation.net.packets.ISaberPacket;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

public interface INetworkHelper {
    void sendToServer(ISaberPacket packet);
    void sendToClient(ServerPlayer player, ISaberPacket packet);
    void sendToClients(Level level, ISaberPacket packet);

}
