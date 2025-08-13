package com.epxzzy.createsaburs.networking;

import com.epxzzy.createsaburs.networking.packet.ClientboundPlayerAttackPacket;
import com.epxzzy.createsaburs.networking.packet.ClientboundPlayerDefendPacket;
import com.epxzzy.createsaburs.utils.PlayerHelperLmao;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientPacketHandler {
    public static void handlePacket(ClientboundPlayerDefendPacket packet, Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context contextt = supplier.get();
        contextt.enqueueWork(() -> {
            Level level = contextt.getSender() != null ? contextt.getSender().level() : null;
            if (level == null) {
                level = net.minecraft.client.Minecraft.getInstance().level;
            }
            if (level != null) {
                Entity entity = level.getEntity(packet.entityId);
                if (entity instanceof Player player && player instanceof PlayerHelperLmao mixin) {
                    mixin.SyncDEFtoPacket(packet);
                }
            }
        });
    }
    public static void handlePacket(ClientboundPlayerAttackPacket packet, Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context contextt = supplier.get();
        contextt.enqueueWork(() -> {
            Level level = contextt.getSender() != null ? contextt.getSender().level() : null;
            if (level == null) {
                level = net.minecraft.client.Minecraft.getInstance().level;
            }
            if (level != null) {
                Entity entity = level.getEntity(packet.entityId);
                if (entity instanceof Player player && player instanceof PlayerHelperLmao mixin) {
                    mixin.SyncATKtoPacket(packet);
                }
            }
        });
    }

}
