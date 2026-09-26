package dev.epxzzy.epxzzysabers.platform.services;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.epxzzy.epxzzysabers.core.foundation.net.packets.ISaberPacket;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;

public interface INetworkHelper {
    void sendToServer(ISaberPacket packet);
    void sendToClient(ServerPlayer player, ISaberPacket packet);
    void sendToClients(ISaberPacket packet);

}
