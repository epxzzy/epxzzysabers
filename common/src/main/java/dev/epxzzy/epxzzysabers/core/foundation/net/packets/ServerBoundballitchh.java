package dev.epxzzy.epxzzysabers.core.foundation.net.packets;

import dev.epxzzy.epxzzysabers.CommonClass;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record ServerBoundballitchh(String message, boolean value) implements ISaberPacket {
    public static final Type<ServerBoundballitchh> TYPE = new Type<>(CommonClass.asResource("sballitchh"));
    public static final StreamCodec<FriendlyByteBuf, ServerBoundballitchh> codec = StreamCodec.composite(
        ByteBufCodecs.STRING_UTF8, ServerBoundballitchh::message,
        ByteBufCodecs.BOOL, ServerBoundballitchh::value,
        ServerBoundballitchh::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}