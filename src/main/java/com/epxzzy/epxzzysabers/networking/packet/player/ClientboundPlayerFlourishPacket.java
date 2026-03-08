package com.epxzzy.epxzzysabers.networking.packet.player;

import com.epxzzy.epxzzysabers.networking.ClientPacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientboundPlayerFlourishPacket {
    public int entityId;
    public int flourishId;
    //public boolean mainattackingHand;


    public ClientboundPlayerFlourishPacket(int entityId, int floruishdi) {
        this.entityId = entityId;
        this.flourishId = floruishdi;
        //this.mainattackingHand = mainhand;
    }

    public ClientboundPlayerFlourishPacket(FriendlyByteBuf buf) {
        this.entityId = buf.readInt();
        this.flourishId = buf.readInt();
        //this.mainattackingHand = buf.readBoolean();

    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeInt(flourishId);
        //buf.writeBoolean(mainattackingHand);
    }

    public static void handle(ClientboundPlayerFlourishPacket packet, Supplier<NetworkEvent.Context> supplier) {
        supplier.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->
                    ClientPacketHandler.handlePacket(packet, supplier));
        });

        supplier.get().setPacketHandled(true);
    }
}

