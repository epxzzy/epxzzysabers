package com.epxzzy.epxzzysabers.networking.packet;

import com.epxzzy.epxzzysabers.networking.ServerPacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ServerboundSaberDeflectPacket {
    public ServerboundSaberDeflectPacket() {
    }


    public ServerboundSaberDeflectPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        //epxzzySabers.LOGGER.info("message reciveved");
        NetworkEvent.Context contextt = supplier.get();
        contextt.enqueueWork(() -> {
            //epxzzySabers.LOGGER.info("message processed");
            ServerPacketHandler.handleDeflectPacket(supplier);
            //epxzzySabers.LOGGER.info(Objects.requireNonNull(contextt.getSender())+" named bond having a stonk");
        });
        return true;
    }

}
