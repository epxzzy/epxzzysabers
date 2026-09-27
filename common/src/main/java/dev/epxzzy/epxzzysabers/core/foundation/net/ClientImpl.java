package dev.epxzzy.epxzzysabers.core.foundation.net;

import dev.epxzzy.epxzzysabers.Constants;
import dev.epxzzy.epxzzysabers.core.foundation.net.packets.ClientBoundballitchh;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public class ClientImpl {
    public static boolean handleClient(
        CustomPacketPayload pack
    ){
        if(pack.type().equals(ClientBoundballitchh.TYPE)){
            Constants.LOG.info("handle packet on client {}", ((ClientBoundballitchh)pack).message());
            return true;
        }
        return false;
    }
}
