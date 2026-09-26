package dev.epxzzy.epxzzysabers.core.foundation.net;

import dev.epxzzy.epxzzysabers.Constants;
import dev.epxzzy.epxzzysabers.core.foundation.net.packets.ballitchh;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.player.Player;

public class ServerImpl {
    public static boolean handleServer(
        CustomPacketPayload pack,
        ServerPlayer player
    ){
        if(pack.type().equals(ballitchh.TYPE)){
            Constants.LOG.info("handle packet on server: {}", ((ballitchh) pack).message());
            player.connection.send(
                new ClientboundCustomPayloadPacket(
                    new ballitchh("oh shi really? ", false)
                )
            );
            return true;
        }
        return false;
    }

}
