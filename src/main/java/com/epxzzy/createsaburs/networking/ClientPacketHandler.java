package com.epxzzy.createsaburs.networking;

import com.epxzzy.createsaburs.networking.packet.ClientboundPlayerAttackPacket;
import com.epxzzy.createsaburs.networking.packet.ServerboundPlayerDefendPacket;
import com.epxzzy.createsaburs.utils.PlayerHelperLmao;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientPacketHandler {
    public static void handlePacket(ServerboundPlayerDefendPacket packet, Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context contextt = supplier.get();
        contextt.enqueueWork(() -> {
            Level level = Minecraft.getInstance().level;
            if (level != null && level.isClientSide()) {
                Entity pPlayer= level.getEntity(packet.entityId);

                if (pPlayer != null && pPlayer instanceof LocalPlayer lPlayer) {
                    ((PlayerHelperLmao) lPlayer).SyncDEFtoPacket(packet);
                }
            }
        });
    }
    public static void handlePacket(ClientboundPlayerAttackPacket packet, Supplier<NetworkEvent.Context> supplier) {
        /*
        NetworkEvent.Context contextt = supplier.get();
        contextt.enqueueWork(() -> {
            Level level = Minecraft.getInstance().level;
            if (level != null && level.isClientSide()) {
                Entity pPlayer= level.getEntity(packet.entityId);

                if (pPlayer != null && pPlayer instanceof LocalPlayer lPlayer) {
                    ((PlayerHelperLmao) lPlayer).SyncDEFtoPacket(packet);
                }
            }
        });

         */
    }

}
