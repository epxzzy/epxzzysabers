package com.epxzzy.createsaburs.networking;

import com.epxzzy.createsaburs.CreateSaburs;
import com.epxzzy.createsaburs.networking.packet.ServerboundKyberMenuSlotPosToggle;
import com.epxzzy.createsaburs.networking.packet.ServerboundRecolourItemPacket;
import com.epxzzy.createsaburs.networking.packet.ServerboundSaberAbilityPacket;
import com.epxzzy.createsaburs.networking.packet.ServerboundSaberDeflectPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

//import net.minecraft.util.
public class ModMessages {
    private static final String PROTOCOL_VER = "1";
    private static int packetId = 0;
    private static int id(){
        return packetId++;
    }
    public static SimpleChannel INSTANCE;
    //public static final Identifier


    public static void register(){
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(CreateSaburs.MOD_ID, "main"))
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
        net.messageBuilder(ServerboundKyberMenuSlotPosToggle.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ServerboundKyberMenuSlotPosToggle::new)
                .encoder(ServerboundKyberMenuSlotPosToggle::toBytes)
                .consumerMainThread(ServerboundKyberMenuSlotPosToggle::handle)
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


    }

    public static <MSG> void sendToServer(MSG message){
        INSTANCE.sendToServer(message);
    }


    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player){
        INSTANCE.send(PacketDistributor.PLAYER.with(()->player),message );
    }


}
