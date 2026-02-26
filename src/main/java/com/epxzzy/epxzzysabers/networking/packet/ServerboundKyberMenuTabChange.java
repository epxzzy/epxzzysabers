package com.epxzzy.epxzzysabers.networking.packet;

import com.epxzzy.epxzzysabers.epxzzySabers;
import com.epxzzy.epxzzysabers.networking.ServerPacketHandler;
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
            ServerPacketHandler.handleTabChange(this, supplier);
        });
        return true;
    }
}
