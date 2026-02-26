package com.epxzzy.epxzzysabers.networking.packet;

import com.epxzzy.epxzzysabers.networking.ServerPacketHandler;
import com.epxzzy.epxzzysabers.screen.tint.KyberStationTintMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ServerboundRecolourItemPacket {
    private int[] COLOURS;
    private boolean GAY;

    public ServerboundRecolourItemPacket( int[] colours, boolean gay){
        COLOURS = colours;
        GAY = gay;
    }
            //int[] neww


    public ServerboundRecolourItemPacket(FriendlyByteBuf buf) {
        COLOURS = buf.readVarIntArray();
        GAY = buf.readBoolean();

    }

    public int[] getColourIndexes() {
       return COLOURS;
    }

    public boolean GAY(){
        return this.GAY;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeVarIntArray(COLOURS);
        buf.writeBoolean(GAY);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context contextt = supplier.get();
        contextt.enqueueWork(() -> {
            ServerPacketHandler.handleRecolour(this, supplier);
        });
        return true;
    }

}
