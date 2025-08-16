package com.epxzzy.epxzzysabers.networking.packet;

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



    public void toBytes(FriendlyByteBuf buf) {
        buf.writeVarIntArray(COLOURS);
        buf.writeBoolean(GAY);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context contextt = supplier.get();
        contextt.enqueueWork(() -> {

            AbstractContainerMenu menuu = contextt.getSender().containerMenu;
            if (menuu instanceof KyberStationTintMenu stationMenu) {
                if (!stationMenu.stillValid(contextt.getSender())) {
                    return;

                }
                stationMenu.setItemColour(this.getColourIndexes(), GAY);
            }


            //contextt.getSender().sendSystemMessage(Component.literal("blow me sucka"+COLOURS[0]+""+COLOURS[1]+""+COLOURS[2]+""));


        });
        return true;
    }

}
