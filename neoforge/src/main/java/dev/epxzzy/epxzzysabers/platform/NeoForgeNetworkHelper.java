package dev.epxzzy.epxzzysabers.platform;

import dev.epxzzy.epxzzysabers.core.foundation.net.packets.ISaberPacket;
import dev.epxzzy.epxzzysabers.platform.services.INetworkHelper;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;

public class NeoForgeNetworkHelper implements INetworkHelper {

    @Override
    public void sendToServer(ISaberPacket packet) {
        PacketDistributor.sendToServer(packet);
    }

    @Override
    public void sendToClient(ServerPlayer player, ISaberPacket packet) {
        PacketDistributor.sendToPlayer(player, packet);
    }

    @Override
    public void sendToClients(Level level, ISaberPacket packet) {
        PacketDistributor.sendToAllPlayers(packet);
    }
}