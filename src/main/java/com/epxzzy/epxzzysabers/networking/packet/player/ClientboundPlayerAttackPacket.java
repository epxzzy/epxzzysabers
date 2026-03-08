package com.epxzzy.epxzzysabers.networking.packet.player;

import com.epxzzy.epxzzysabers.networking.ClientPacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientboundPlayerAttackPacket {
    public int entityId;
    public boolean attacking;
    public int attackProgress;
    public int attackPose;
    //public boolean mainattackingHand;


    public ClientboundPlayerAttackPacket(int entityId, boolean attacking, int attackProgress, int attackPose) {
        this.entityId = entityId;
        this.attacking = attacking;
        this.attackProgress = attackProgress;
        this.attackPose = attackPose;
        //this.mainattackingHand = mainhand;
    }

    public ClientboundPlayerAttackPacket(FriendlyByteBuf buf) {
        this.entityId = buf.readInt();
        this.attacking = buf.readBoolean();
        this.attackProgress = buf.readInt();
        this.attackPose = buf.readInt();
        //this.mainattackingHand = buf.readBoolean();

    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeBoolean(attacking);
        buf.writeInt(attackProgress);
        buf.writeInt(attackPose);
        //buf.writeBoolean(mainattackingHand);
    }

    public static void handle(ClientboundPlayerAttackPacket packet, Supplier<NetworkEvent.Context> supplier) {
        supplier.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->
                    ClientPacketHandler.handlePacket(packet, supplier));
        });

        supplier.get().setPacketHandled(true);
    }
}

