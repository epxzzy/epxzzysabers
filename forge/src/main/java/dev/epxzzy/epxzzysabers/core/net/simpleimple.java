package dev.epxzzy.epxzzysabers.core.net;

import dev.epxzzy.epxzzysabers.core.foundation.net.ClientImpl;
import dev.epxzzy.epxzzysabers.core.foundation.net.ServerImpl;
import dev.epxzzy.epxzzysabers.registry.SaberPacketsRegistry;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.SimpleChannel;

import dev.epxzzy.epxzzysabers.registry.SaberPacketsRegistry;

import java.util.function.BiConsumer;

public final class simpleimple{
    private static final String PROTOCOL = "1";
    public static final SimpleChannel CHANNEL = ChannelBuilder
            .named(ResourceLocation.fromNamespaceAndPath("epxzzysabers", "main"))
            .networkProtocolVersion(1)
            .clientAcceptedVersions((status, version) -> true)
            .serverAcceptedVersions((status, version) -> true)
            .simpleChannel();


    public static void registerPackets() {
        for (SaberPacketsRegistry.packet<?> entry : SaberPacketsRegistry.list) {
            register(entry);
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static <T extends CustomPacketPayload> void register(SaberPacketsRegistry.packet<T> entry) {
        BiConsumer<T, CustomPayloadEvent.Context> handler = (payload, ctx) -> {
            ctx.setPacketHandled(true);
            /*if (entry.C2S() && entry.S2C()) {
                registrar.playBidirectional(entry.type, entry.codec,
                    (payload, ctx) -> CommonImpl.handleCommon(payload, ctx.player()));
           }else */ if (entry.S2C() && ctx.isClientSide()) {
                    ctx.enqueueWork(() -> {
                        ClientImpl.handleClient(payload);
                    });
            }
            if (entry.C2S() && ctx.isServerSide()) {
                ctx.enqueueWork(() -> {
                    ServerImpl.handleServer(payload, ctx.getSender());
                });

            }
        };

        CHANNEL
            .messageBuilder(entry.raw())
            .codec((StreamCodec) entry.codec())   // your common STREAM_CODEC
            .consumerMainThread(handler)
            .add();
    }
}