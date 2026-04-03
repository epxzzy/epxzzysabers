package com.epxzzy.epxzzysabers.util;

import com.epxzzy.epxzzysabers.epxzzySabers;
import com.google.gson.JsonObject;

public class ConfigHolder {
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
}
