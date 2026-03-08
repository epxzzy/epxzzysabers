package com.epxzzy.epxzzysabers.networking.packet.saber;

//import com.epxzzy.epxzzysabers.entity.custom.PlasmaBolt;
import com.epxzzy.epxzzysabers.networking.ServerPacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ServerboundSaberStancePacket {
    public boolean down;
    public ServerboundSaberStancePacket(boolean keydown) {
        this.down = keydown;
    }

    public ServerboundSaberStancePacket(FriendlyByteBuf buf) {
        this.down = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(this.down);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context contextt = supplier.get();
        contextt.enqueueWork(() -> {
            ServerPacketHandler.handleStancePacket(supplier, this.down);
            //contextt.getSender().sendSystemMessage(Component.literal("serverplayer name:" + contextt.getSender().getTabListDisplayName().getString() + " and thats about it"));
        });
        return true;
    }

}
