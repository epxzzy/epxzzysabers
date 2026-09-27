package dev.epxzzy.epxzzysabers.core.foundation.net.packets;

import dev.epxzzy.epxzzysabers.CommonClass;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record ClientBoundballitchh(String message, boolean value) implements ISaberPacket {
    public static final Type<ClientBoundballitchh> TYPE = new Type<>(CommonClass.asResource("cballitchh"));
    public static final StreamCodec<FriendlyByteBuf, ClientBoundballitchh> codec = StreamCodec.composite(
        ByteBufCodecs.STRING_UTF8, ClientBoundballitchh::message,
        ByteBufCodecs.BOOL, ClientBoundballitchh::value,
        ClientBoundballitchh::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}