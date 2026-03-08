package com.epxzzy.epxzzysabers.networking.packet.player;

import com.epxzzy.epxzzysabers.networking.ClientPacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientboundPlayerDefendPacket {
        public int entityId;
        public boolean defending;
        public int defendProgress;
        public int defendPose;

        //public boolean mainattackingHand;


        public ClientboundPlayerDefendPacket(int entityId, boolean defending, int defendProgress, int defendPose) {
            this.entityId = entityId;
            this.defending = defending;
            this.defendProgress = defendProgress;
            this.defendPose = defendPose;
            //this.mainattackingHand = mainhand;
        }
        public ClientboundPlayerDefendPacket(FriendlyByteBuf buf) {
            this.entityId = buf.readInt();
            this.defending = buf.readBoolean();
            this.defendProgress = buf.readInt();
            this.defendPose = buf.readInt();
            //this.mainattackingHand = buf.readBoolean();

        }
        public void toBytes(FriendlyByteBuf buf) {
            buf.writeInt(entityId);
            buf.writeBoolean(defending);
            buf.writeInt(defendProgress);
            buf.writeInt(defendPose);

            //buf.writeBoolean(mainattackingHand);
        }

        public static void handle(ClientboundPlayerDefendPacket packet, Supplier<NetworkEvent.Context> supplier) {
            supplier.get().enqueueWork(() -> {
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->
                        ClientPacketHandler.handlePacket(packet, supplier));
            });

            supplier.get().setPacketHandled(true);
        }
}

