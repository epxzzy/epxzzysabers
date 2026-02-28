package com.epxzzy.epxzzysabers.util;

import com.epxzzy.epxzzysabers.networking.packet.ClientboundPlayerAttackPacket;
import com.epxzzy.epxzzysabers.networking.packet.ClientboundPlayerDefendPacket;
import com.epxzzy.epxzzysabers.networking.packet.ClientboundPlayerStancePacket;

public interface PlayerHelperLmao {
    int getFlyCooldown();
    int getFlightDuration();
    void setFlyCooldown(int i);
    void setFlightDuration(int val);
    int getChargeDuration();
    void setChargeDuration(int val);



    public void SyncSTCtoPacket(ClientboundPlayerStancePacket packet);
    public boolean getSaberStanceDown();
    public int getSaberStanceForm();


    public void LogFlightDetails();
    public float getSaberAttackAnim();
    public float getSaberDefendAnim();
    public void SyncDEFtoPacket(ClientboundPlayerDefendPacket packet);
    public void SyncATKtoPacket(ClientboundPlayerAttackPacket packet);

    public int getAttackTime();

}
