package com.epxzzy.epxzzysabers.networking.handler;

import com.epxzzy.epxzzysabers.epxzzySabers;
import com.epxzzy.epxzzysabers.networking.packet.ServerboundKyberMenuTabChange;
import com.epxzzy.epxzzysabers.networking.packet.ServerboundRecolourItemPacket;
import com.epxzzy.epxzzysabers.screen.components.KyberMenuBase;
import com.epxzzy.epxzzysabers.screen.stance.KyberStationStanceMenu;
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
    public static void handleTabChange(Supplier<NetworkEvent.Context> supplier, int TAB_ID) {
        NetworkEvent.Context contextt = supplier.get();

        AbstractContainerMenu menuu = contextt.getSender().containerMenu;
        Player player = contextt.getSender();
        if (menuu instanceof KyberMenuBase stationMenu) {
            if (!stationMenu.stillValid(contextt.getSender())) {
                return;

            }
            player.closeContainer();
            BlockPos poslel = stationMenu.blockPos;
            player.openMenu(getMenuProvider(player.level(), poslel, TAB_ID));

        }
    }

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

    public static MenuProvider getMenuProvider(Level pLevel, BlockPos pPos, int TAB_ID) {
        epxzzySabers.LOGGER.info("switching to " + TAB_ID);

        switch (TAB_ID) {
            case 0 -> {
                return new SimpleMenuProvider((MenuConstructor, b, c) ->
                        new KyberStationTintMenu(
                                MenuConstructor,
                                b,
                                pPos,
                                ContainerLevelAccess.create(pLevel, pPos)
                        ),
                        Component.literal("Tint Menu")
                );
            }
            case 1 -> {
                return new SimpleMenuProvider((MenuConstructor, b, c) ->
                        new KyberStationStanceMenu(
                                MenuConstructor,
                                b,
                                pPos,
                                ContainerLevelAccess.create(pLevel, pPos)
                        ),
                        Component.literal("Stance Menu")
                );
            }
        }

        return null;
    }

}
