package dev.epxzzy.epxzzysabers.registry;

import dev.epxzzy.epxzzysabers.core.foundation.net.ServerImpl;
import dev.epxzzy.epxzzysabers.core.foundation.net.packets.ISaberPacket;
import dev.epxzzy.epxzzysabers.core.foundation.net.packets.ballitchh;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class SaberPacketsRegistry {
    public static final List<packet> list = new ArrayList<>();

    public record packet<T extends CustomPacketPayload>(
        CustomPacketPayload.Type<T> type,
        StreamCodec<? super FriendlyByteBuf, T> codec,
        boolean C2S, boolean S2C,
        Class<ISaberPacket> raw
    ){}

    static {
        list.add(new packet(ballitchh.TYPE, ballitchh.codec, true, true, ballitchh.class));
    }
}