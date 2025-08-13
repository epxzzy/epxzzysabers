package com.epxzzy.createsaburs.networking.packet;

import com.epxzzy.createsaburs.networking.ClientPacketHandler;
import com.epxzzy.createsaburs.utils.PlayerHelperLmao;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ServerboundPlayerDefendPacket {
    public int entityId;
    public boolean defending;
    public int defendProgress;
    //public boolean mainattackingHand;


    public ServerboundPlayerDefendPacket(int entityId, boolean defending, int defendProgress) {
        this.entityId = entityId;
        this.defending = defending;
        this.defendProgress = defendProgress;
    }

    public ServerboundPlayerDefendPacket(FriendlyByteBuf buf) {
        this.entityId = buf.readInt();
        this.defending = buf.readBoolean();
        this.defendProgress = buf.readInt();

    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeBoolean(defending);
        buf.writeInt(defendProgress);
    }

    public static void handle(ServerboundPlayerDefendPacket packet, Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context contextt = supplier.get();
        contextt.enqueueWork(() -> {
            Level level = contextt.getSender().level();
            if (level != null) {
                Entity entity = level.getEntity(packet.entityId);
                if (entity instanceof Player player) {
                    ((PlayerHelperLmao) player).SyncDEFtoPacket(packet);

                }
            }
        });

        supplier.get().setPacketHandled(true);
    }
}

