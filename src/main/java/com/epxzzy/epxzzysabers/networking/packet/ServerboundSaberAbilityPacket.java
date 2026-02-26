package com.epxzzy.epxzzysabers.networking.packet;

//import com.epxzzy.epxzzysabers.entity.custom.PlasmaBolt;
import com.epxzzy.epxzzysabers.networking.ServerPacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ServerboundSaberAbilityPacket {
    public ServerboundSaberAbilityPacket() {
    }


    public ServerboundSaberAbilityPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        //epxzzySabers.LOGGER.info("message reciveved");
        NetworkEvent.Context contextt = supplier.get();
        contextt.enqueueWork(() -> {
            ServerPacketHandler.handleAbilityPacket(supplier);
            //contextt.getSender().sendSystemMessage(Component.literal("serverplayer name:" + contextt.getSender().getTabListDisplayName().getString() + " and thats about it"));
        });
        return true;
    }

}
