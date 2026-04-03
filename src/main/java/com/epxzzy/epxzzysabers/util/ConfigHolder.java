package com.epxzzy.epxzzysabers.util;

import com.epxzzy.epxzzysabers.epxzzySabers;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(modid = epxzzySabers.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ConfigHolder implements ResourceManagerReloadListener {
    public static int GAUNTLET_SURGE_DURATION = 160;
    public static int GAUNTLET_SURGE_CHARGEUP = 40;
    public static int ROTARY_FLIGHT_DURATION = 200;
    public static int ROTARY_FLIGHT_COOLDOWN = 40;

    public static boolean KEWL_FIGHTS = true;

    public static void loadConfig(JsonObject json) {
        int gsd = json.get("gauntlet_surge_duration").getAsInt();
        int gsc = json.get("gauntlet_surge_chargeup").getAsInt();
        int rfd = json.get("rotary_flight_duration").getAsInt();
        int rfc = json.get("rotary_flight_cooldown").getAsInt();
        boolean kf = json.get("kewl_fights").getAsBoolean();

        GAUNTLET_SURGE_DURATION = gsd;
        GAUNTLET_SURGE_CHARGEUP = gsc;
        ROTARY_FLIGHT_DURATION = rfd;
        ROTARY_FLIGHT_COOLDOWN = rfc;
        KEWL_FIGHTS = kf;
    }

    @Override
    public void onResourceManagerReload(@NotNull ResourceManager manager) {
        epxzzySabers.LOGGER.info("RELOAD FIRED BOIII");

        try {
            var resource = manager.getResource(
                    epxzzySabers.asResource("config.json")
            );

            if (resource.isPresent()) {
                String json = new String(resource.get().open().readAllBytes());
                JsonObject obj = JsonParser.parseString(json).getAsJsonObject();
                loadConfig(obj);
                epxzzySabers.LOGGER.info("Successfully Reloaded Saberooni Config");
            }
        } catch (Exception e) {
            epxzzySabers.LOGGER.info("Failed To Reload Saberooni Config");
            e.printStackTrace();
        }

    }

    @SubscribeEvent
    public static void onAddReloadListener(AddReloadListenerEvent event) {
        event.addListener(new ConfigHolder());
    }
}
