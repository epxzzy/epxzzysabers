package dev.epxzzy.epxzzysabers.core.net;

import dev.epxzzy.epxzzysabers.core.foundation.net.ClientImpl;
import dev.epxzzy.epxzzysabers.core.foundation.net.ServerImpl;
import dev.epxzzy.epxzzysabers.registry.SaberPacketsRegistry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.network.Channel;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.SimpleChannel;

import dev.epxzzy.epxzzysabers.registry.SaberPacketsRegistry;

import java.util.function.BiConsumer;

public final class simpleimple {
    private static final String PROTOCOL = "1";
    public static SimpleChannel CHANNEL = ChannelBuilder
        .named(ResourceLocation.fromNamespaceAndPath("epxzzysabers", "main"))
        .networkProtocolVersion(1)
        .clientAcceptedVersions(Channel.VersionTest.exact(1))
        .serverAcceptedVersions(Channel.VersionTest.exact(1))
        .simpleChannel();


    public static void registerPackets() {
        for (SaberPacketsRegistry.packet<?> entry : SaberPacketsRegistry.list) {
            register(entry);
        }
        CHANNEL.build();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static <T extends CustomPacketPayload> void register(SaberPacketsRegistry.packet<T> entry) {
        BiConsumer<T, CustomPayloadEvent.Context> handler = (payload, ctx) -> {
            ctx.setPacketHandled(true);
            if (ctx.isServerSide()) {
                ctx.enqueueWork(() -> ServerImpl.handleServer(payload, ctx.getSender()));
            } else {
                ctx.enqueueWork(() -> ClientImpl.handleClient(payload));
            }
        };
        if (entry.C2S()) {
            CHANNEL.play()
                .serverbound()
                .addMain(
                    entry.raw(),
                    (StreamCodec<RegistryFriendlyByteBuf, T>) (StreamCodec) entry.codec(),
                    handler
                )
            ;

        } else {
            CHANNEL.play()
                .clientbound()
                .addMain(
                    entry.raw(),
                    (StreamCodec<RegistryFriendlyByteBuf, T>) (StreamCodec) entry.codec(),
                    handler
                );
        }


    }
}