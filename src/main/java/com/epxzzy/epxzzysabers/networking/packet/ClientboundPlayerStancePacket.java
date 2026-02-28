package com.epxzzy.epxzzysabers.networking.packet;

import com.epxzzy.epxzzysabers.networking.ClientPacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientboundPlayerStancePacket {
        public int entityId;
        public boolean stancing;
        public int stanceform;
        //public boolean mainattackingHand;


        public ClientboundPlayerStancePacket(int entityId, boolean stancing, int stanceform) {
            this.entityId = entityId;
            this.stancing = stancing;
            this.stanceform= stanceform;
            //this.mainattackingHand = mainhand;
        }
        public ClientboundPlayerStancePacket(FriendlyByteBuf buf) {
            this.entityId = buf.readInt();
            this.stancing= buf.readBoolean();
            this.stanceform= buf.readInt();
            //this.mainattackingHand = buf.readBoolean();

        }
        public void toBytes(FriendlyByteBuf buf) {
            buf.writeInt(entityId);
            buf.writeBoolean(stancing);
            buf.writeInt(stanceform);
            //buf.writeBoolean(mainattackingHand);
        }

        public static void handle(ClientboundPlayerStancePacket packet, Supplier<NetworkEvent.Context> supplier) {
            supplier.get().enqueueWork(() -> {
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->
                        ClientPacketHandler.handlePacket(packet, supplier));
            });

            supplier.get().setPacketHandled(true);
        }
}

