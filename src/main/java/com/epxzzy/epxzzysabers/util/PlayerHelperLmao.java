package com.epxzzy.epxzzysabers.util;

import com.epxzzy.epxzzysabers.networking.packet.player.ClientboundPlayerAttackPacket;
import com.epxzzy.epxzzysabers.networking.packet.player.ClientboundPlayerDefendPacket;
import com.epxzzy.epxzzysabers.networking.packet.player.ClientboundPlayerFlourishPacket;
import com.epxzzy.epxzzysabers.networking.packet.player.ClientboundPlayerStancePacket;

public interface PlayerHelperLmao {
    int getFlyCooldown();
    int getFlightDuration();
    void setFlyCooldown(int i);
    void setFlightDuration(int val);
    int getChargeDuration();
    void setChargeDuration(int val);



    public void setSaberStanceDown(boolean value);
    public boolean getSaberStanceDown();
    public int getSaberFlourishId();
    public int getSaberStanceForm();
    public int getSaberAttackForm();
    public int getSaberBlockForm();
    public void setSaberAttackForm(int val);
    public void setSaberBlockForm(int val);

    public void setSaberStanceForm(int value);


    public void LogFlightDetails();
    public float getSaberAttackAnim();
    public float getSaberDefendAnim();
    public void SyncDEFtoPacket(ClientboundPlayerDefendPacket packet);
    public void SyncATKtoPacket(ClientboundPlayerAttackPacket packet);
    public void SyncSTCtoPacket(ClientboundPlayerStancePacket packet);
    public void SyncFRStoPacket(ClientboundPlayerFlourishPacket packet);

    public int getAttackTime();

}
