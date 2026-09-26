package dev.epxzzy.epxzzysabers.core.foundation.net;

import dev.epxzzy.epxzzysabers.CommonClass;
import dev.epxzzy.epxzzysabers.Constants;
import dev.epxzzy.epxzzysabers.core.foundation.net.packets.ballitchh;
import net.minecraft.client.multiplayer.ClientCommonPacketListenerImpl;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public class ClientImpl {
    public static boolean handleClient(
        CustomPacketPayload pack
    ){
        if(pack.type().equals(ballitchh.TYPE)){
            Constants.LOG.info("handle packet on client {}", ((ballitchh)pack).message());
            return true;
        }
        return false;
    }
}
