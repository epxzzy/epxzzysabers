package dev.epxzzy.epxzzysabers.core.foundation.net;

import dev.epxzzy.epxzzysabers.CommonClass;
import dev.epxzzy.epxzzysabers.Constants;
import dev.epxzzy.epxzzysabers.core.foundation.net.packets.ClientBoundballitchh;
import dev.epxzzy.epxzzysabers.core.foundation.net.packets.ServerBoundballitchh;
import dev.epxzzy.epxzzysabers.platform.Services;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

public class ServerImpl {
    public static boolean handleServer(
        CustomPacketPayload pack,
        ServerPlayer player
    ){
        if(pack.type().equals(ServerBoundballitchh.TYPE)){
            Constants.LOG.info("handle packet on server: {}", ((ServerBoundballitchh) pack).message());
            Services.NetowrkHelper.sendToClient(
                player,
                new ClientBoundballitchh("oh shi really? ", false)
            );
            return true;
        }
        return false;
    }

}
