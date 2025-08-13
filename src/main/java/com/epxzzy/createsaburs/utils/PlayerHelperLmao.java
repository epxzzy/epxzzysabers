package com.epxzzy.createsaburs.utils;

import com.epxzzy.createsaburs.networking.packet.ClientboundPlayerAttackPacket;
import com.epxzzy.createsaburs.networking.packet.ClientboundPlayerDefendPacket;

public interface PlayerHelperLmao {
    public void LogFlightDetails();
    public float getSaberAttackAnim();
    public float getSaberDefendAnim();
    public void SyncDEFtoPacket(ClientboundPlayerDefendPacket packet);
    public void SyncATKtoPacket(ClientboundPlayerAttackPacket packet);

    public int getAttackTime();
}
