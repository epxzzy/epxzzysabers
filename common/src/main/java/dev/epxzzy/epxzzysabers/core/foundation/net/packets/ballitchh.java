package dev.epxzzy.epxzzysabers.core.foundation.net.packets;

import dev.epxzzy.epxzzysabers.CommonClass;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record ballitchh(String message, boolean value) implements ISaberPacket {
    public static final Type<ballitchh> TYPE = new Type<>(CommonClass.asResource("ballitchh"));
    public static final StreamCodec<FriendlyByteBuf, ballitchh> codec = StreamCodec.composite(
        ByteBufCodecs.STRING_UTF8, ballitchh::message,
        ByteBufCodecs.BOOL, ballitchh::value,
        ballitchh::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}