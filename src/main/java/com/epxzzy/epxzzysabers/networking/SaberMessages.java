package com.epxzzy.epxzzysabers.networking;

import com.epxzzy.epxzzysabers.epxzzySabers;
import com.epxzzy.epxzzysabers.networking.packet.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

//import net.minecraft.util.
public class SaberMessages {
    private static final String PROTOCOL_VER = "1";
    private static int packetId = 0;
    private static int id(){
        return packetId++;
    }
    public static SimpleChannel INSTANCE;
    //public static final Identifier


    public static void register(){
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(epxzzySabers.MOD_ID, "main"))
                .networkProtocolVersion(()->"1.0")
                .clientAcceptedVersions(s->true)
                .serverAcceptedVersions(s->true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(ServerboundRecolourItemPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ServerboundRecolourItemPacket::new)
                .encoder(ServerboundRecolourItemPacket::toBytes)
                .consumerMainThread(ServerboundRecolourItemPacket::handle)
                .add();
        net.messageBuilder(ServerboundKyberMenuTabChange.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ServerboundKyberMenuTabChange::new)
                .encoder(ServerboundKyberMenuTabChange::toBytes)
                .consumerMainThread(ServerboundKyberMenuTabChange::handle)
                .add();

        net.messageBuilder(ServerboundSaberAbilityPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ServerboundSaberAbilityPacket::new)
                .encoder(ServerboundSaberAbilityPacket::toBytes)
                .consumerMainThread(ServerboundSaberAbilityPacket::handle)
                .add();
        net.messageBuilder(ServerboundSaberDeflectPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ServerboundSaberDeflectPacket::new)
                .encoder(ServerboundSaberDeflectPacket::toBytes)
                .consumerMainThread(ServerboundSaberDeflectPacket::handle)
                .add();
        net.messageBuilder(ServerboundSaberStancePacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ServerboundSaberStancePacket::new)
                .encoder(ServerboundSaberStancePacket::toBytes)
                .consumerMainThread(ServerboundSaberStancePacket::handle)
                .add();


        net.messageBuilder(ClientboundPlayerStancePacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ClientboundPlayerStancePacket::new)
                .encoder(ClientboundPlayerStancePacket::toBytes)
                .consumerMainThread(ClientboundPlayerStancePacket::handle)
                .add();
        net.messageBuilder(ClientboundPlayerAttackPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ClientboundPlayerAttackPacket::new)
                .encoder(ClientboundPlayerAttackPacket::toBytes)
                .consumerMainThread(ClientboundPlayerAttackPacket::handle)
                .add();
        net.messageBuilder(ClientboundPlayerDefendPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ClientboundPlayerDefendPacket::new)
                .encoder(ClientboundPlayerDefendPacket::toBytes)
                .consumerMainThread(ClientboundPlayerDefendPacket::handle)
                .add();

    }

    public static <MSG> void sendToServer(MSG message){
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player){
        INSTANCE.send(PacketDistributor.PLAYER.with(()->player),message );
    }

    public static <MSG> void fuckingAnnounce(MSG message, Player player){
        INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player),message);
    }

}
