package dev.epxzzy.epxzzysabers.registry;

import dev.epxzzy.epxzzysabers.core.foundation.net.packets.ClientBoundballitchh;
import dev.epxzzy.epxzzysabers.core.foundation.net.packets.ServerBoundballitchh;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import java.util.ArrayList;
import java.util.List;

public class SaberPacketsRegistry {
    public static final List<packet> list = new ArrayList<>();

    public record packet<T extends CustomPacketPayload>(
        CustomPacketPayload.Type<T> type,
        StreamCodec<? extends FriendlyByteBuf, T> codec,
        boolean C2S, boolean S2C,
        Class<T> raw
    ){}

    static {
        list.add(new packet(ClientBoundballitchh.TYPE, ClientBoundballitchh.codec, false, true, ClientBoundballitchh.class));
        list.add(new packet(ServerBoundballitchh.TYPE, ServerBoundballitchh.codec, true, false, ServerBoundballitchh.class));
    }
}