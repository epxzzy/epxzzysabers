package com.epxzzy.createsaburs.networking.packet;

import com.epxzzy.createsaburs.createsaburs;
import com.epxzzy.createsaburs.screen.KyberStationMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ServerboundKyberMenuSlotPosToggle {
    private boolean isDefaultTab;
    public ServerboundKyberMenuSlotPosToggle(boolean tab){
        isDefaultTab= tab;
    }
            //int[] neww


    public ServerboundKyberMenuSlotPosToggle(FriendlyByteBuf buf) {
        isDefaultTab= buf.readBoolean();

    }

    public boolean isDefaultTab() {
       return isDefaultTab;
    }



    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(isDefaultTab);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context contextt = supplier.get();
        contextt.enqueueWork(() -> {

            AbstractContainerMenu menuu = contextt.getSender().containerMenu;
            if (menuu instanceof KyberStationMenu stationMenu) {
                if (!stationMenu.stillValid(contextt.getSender())) {
                    return;

                }
                createsaburs.LOGGER.info("changing slot pos "+isDefaultTab);
                if(this.isDefaultTab()){
                   stationMenu.stanceSlotPose();
                }
                else {
                    stationMenu.resetSlotPose();
                }
            }


            //contextt.getSender().sendSystemMessage(Component.literal("blow me sucka"+COLOURS[0]+""+COLOURS[1]+""+COLOURS[2]+""));


        });
        return true;
    }

}
