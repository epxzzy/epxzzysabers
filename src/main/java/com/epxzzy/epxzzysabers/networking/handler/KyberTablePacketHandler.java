package com.epxzzy.epxzzysabers.networking.handler;

import com.epxzzy.epxzzysabers.epxzzySabers;
import com.epxzzy.epxzzysabers.networking.packet.ServerboundRecolourItemPacket;
import com.epxzzy.epxzzysabers.screen.components.KyberMenuBase;
import com.epxzzy.epxzzysabers.screen.tint.KyberStationTintMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class KyberTablePacketHandler {
    public static void handleRecolour(ServerboundRecolourItemPacket packet, Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context contextt = supplier.get();

        AbstractContainerMenu menuu = contextt.getSender().containerMenu;
        if (menuu instanceof KyberStationTintMenu stationMenu) {
            if (!stationMenu.stillValid(contextt.getSender())) {
                return;

            }
            stationMenu.setItemColour(packet.getColourIndexes(), packet.GAY());
        }
    }
}
