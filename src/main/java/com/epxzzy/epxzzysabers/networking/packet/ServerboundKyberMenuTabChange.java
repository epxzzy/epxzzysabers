package com.epxzzy.epxzzysabers.networking.packet;

import com.epxzzy.epxzzysabers.epxzzySabers;
import com.epxzzy.epxzzysabers.screen.components.KyberMenuBase;
import com.epxzzy.epxzzysabers.screen.stance.KyberStationStanceMenu;
import com.epxzzy.epxzzysabers.screen.tint.KyberStationTintMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ServerboundKyberMenuTabChange {
    private int TAB_ID;

    public ServerboundKyberMenuTabChange(int tab) {
        TAB_ID = tab;
    }
    //int[] neww


    public ServerboundKyberMenuTabChange(FriendlyByteBuf buf) {
        TAB_ID = buf.readInt();

    }

    public int TABID() {
        return TAB_ID;
    }


    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(TAB_ID);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context contextt = supplier.get();
        contextt.enqueueWork(() -> {
            AbstractContainerMenu menuu = contextt.getSender().containerMenu;
            epxzzySabers.LOGGER.debug("{} for {}", menuu, TABID());
            Player player = contextt.getSender();
            if (menuu instanceof KyberMenuBase stationMenu) {
                if (!stationMenu.stillValid(contextt.getSender())) {
                    return;

                }
                player.closeContainer();
                BlockPos poslel = stationMenu.blockPos;
                player.openMenu(getMenuProvider(player.level(), poslel));
                epxzzySabers.LOGGER.debug("{} level {} pos {}", player, player.level(), poslel);

            }
            /*

             */
            //contextt.getSender().sendSystemMessage(Component.literal("blow me sucka"+COLOURS[0]+""+COLOURS[1]+""+COLOURS[2]+""));


        });
        return true;
    }


    public MenuProvider getMenuProvider(Level pLevel, BlockPos pPos) {
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
