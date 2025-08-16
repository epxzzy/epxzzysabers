package com.epxzzy.epxzzysabers.networking.packet;

import com.epxzzy.epxzzysabers.networking.ClientPacketHandler;
import com.epxzzy.epxzzysabers.utils.PlayerHelperLmao;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientboundPlayerAttackPacket {
    public int entityId;
    public boolean attacking;
    public int attackProgress;
    //public boolean mainattackingHand;


    public ClientboundPlayerAttackPacket(int entityId, boolean attacking, int attackProgress) {
        this.entityId = entityId;
        this.attacking = attacking;
        this.attackProgress = attackProgress;
        //this.mainattackingHand = mainhand;
    }

    public ClientboundPlayerAttackPacket(FriendlyByteBuf buf) {
        this.entityId = buf.readInt();
        this.attacking = buf.readBoolean();
        this.attackProgress = buf.readInt();
        //this.mainattackingHand = buf.readBoolean();

    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeBoolean(attacking);
        buf.writeInt(attackProgress);
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

