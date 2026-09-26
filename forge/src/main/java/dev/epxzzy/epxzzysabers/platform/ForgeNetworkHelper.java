package dev.epxzzy.epxzzysabers.platform;

import dev.epxzzy.epxzzysabers.core.foundation.net.packets.ISaberPacket;
import dev.epxzzy.epxzzysabers.core.net.simpleimple;
import dev.epxzzy.epxzzysabers.platform.services.INetworkHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.PacketDistributor;

public class ForgeNetworkHelper implements INetworkHelper {
    @Override
    public void sendToServer(ISaberPacket packet) {
        simpleimple.CHANNEL.send(packet, PacketDistributor.SERVER.noArg());
    }

    @Override
    public void sendToClient(ServerPlayer player, ISaberPacket packet) {
        simpleimple.CHANNEL.send(packet, PacketDistributor.PLAYER.with(player));
    }

    @Override
    public void sendToClients(ISaberPacket packet) {
        simpleimple.CHANNEL.send(packet, PacketDistributor.ALL.noArg());
    }
}