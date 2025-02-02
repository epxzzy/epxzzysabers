package com.epxzzy.createsaburs.networking.packet;

import com.epxzzy.createsaburs.screen.KyberStationMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ServerboundRecolourItemPacket {
    private int[] COLOURS;
    public ServerboundRecolourItemPacket( int[] colours){
        COLOURS = colours;
    }
            //int[] neww


    public ServerboundRecolourItemPacket(FriendlyByteBuf buf) {
        COLOURS = buf.readVarIntArray();

    }

    public int[] getColourIndexes() {
       return COLOURS;
    }



    public void toBytes(FriendlyByteBuf buf) {
        buf.writeVarIntArray(COLOURS);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context contextt = supplier.get();
        contextt.enqueueWork(() -> {

            AbstractContainerMenu menuu = contextt.getSender().containerMenu;
            if (menuu instanceof KyberStationMenu stationMenu) {
                if (!stationMenu.stillValid(contextt.getSender())) {
                    return;

                }
                stationMenu.setItemColour(this.getColourIndexes());
            }


            //contextt.getSender().sendSystemMessage(Component.literal("blow me sucka"+COLOURS[0]+""+COLOURS[1]+""+COLOURS[2]+""));


        });
        return true;
    }

}
