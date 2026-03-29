package com.epxzzy.epxzzysabers.networking;

import com.epxzzy.epxzzysabers.networking.handler.KyberTablePacketHandler;
import com.epxzzy.epxzzysabers.networking.handler.SaberPacketHandler;
import com.epxzzy.epxzzysabers.networking.packet.ServerboundRecolourItemPacket;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ServerPacketHandler {
    public static void handleDeflectPacket(Supplier<NetworkEvent.Context> supplier) {
        SaberPacketHandler.handleDeflectPacket(supplier);
    }

    public static void handleAbilityPacket(Supplier<NetworkEvent.Context> supplier) {
        SaberPacketHandler.handleAbilityPacket(supplier);
    }

    public static void handleStancePacket(Supplier<NetworkEvent.Context> supplier, boolean keydown, int preference) {
        SaberPacketHandler.handleStancePacket(supplier, keydown, preference);
    }

    public static void handleRecolour(ServerboundRecolourItemPacket packet, Supplier<NetworkEvent.Context> supplier) {
        KyberTablePacketHandler.handleRecolour(packet, supplier);
    }
}
