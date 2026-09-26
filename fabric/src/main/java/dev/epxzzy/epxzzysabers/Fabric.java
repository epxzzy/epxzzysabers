package dev.epxzzy.epxzzysabers;

import dev.epxzzy.epxzzysabers.core.foundation.PartialModelEventHandler;
import dev.epxzzy.epxzzysabers.core.foundation.net.ClientImpl;
import dev.epxzzy.epxzzysabers.core.foundation.net.ServerImpl;
import dev.epxzzy.epxzzysabers.core.foundation.net.packets.ISaberPacket;
import dev.epxzzy.epxzzysabers.registry.ItemRegistry;
import dev.epxzzy.epxzzysabers.registry.SaberPacketsRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.impl.networking.CommonRegisterPayload;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;

import static dev.epxzzy.epxzzysabers.Constants.MOD_ID;

public class Fabric implements ModInitializer {

    @Override
    public void onInitialize() {

        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // project.

        // Use Fabric to bootstrap the Common mod.
        Constants.LOG.info("Hello Fabric world!");
        CommonClass.init();

        registerloads();

        ItemRegistry.map.forEach((
            (s, supplier) -> Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(MOD_ID, s),
                supplier.get()
            ))
        );

        setupLib();
    }

    private static void setupLib() {
        ModelLoadingPlugin.register(ctx -> {
            ctx.addModels(PartialModelEventHandler.onRegisterAdditional());
        });
        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(PartialModelEventHandler.ReloadListener.INSTANCE);
    }


    public static void registerloads() {
        for (SaberPacketsRegistry.packet<?> entry : SaberPacketsRegistry.list) {
            /*if (entry.C2S() && entry.S2C()) {
                registrar.playBidirectional(entry.type, entry.codec,
                    (payload, ctx) -> CommonImpl.handleCommon(payload, ctx.player()));
            } else*/ if (entry.S2C()) {
                PayloadTypeRegistry.playS2C().register(entry.type(), (StreamCodec) entry.codec());
                ClientPlayNetworking.registerGlobalReceiver(entry.type(), (payload, ctx) -> {
                    ctx.client().execute(() -> ClientImpl.handleClient(payload));
                });
            } if (entry.C2S()) {
                PayloadTypeRegistry.playC2S().register(entry.type(), (StreamCodec) entry.codec());
                ServerPlayNetworking.registerGlobalReceiver(entry.type(), (payload, ctx) -> {
                    ctx.server().execute(() -> ServerImpl.handleServer(payload, ctx.player()));
                });
            }
        }
    }

}
