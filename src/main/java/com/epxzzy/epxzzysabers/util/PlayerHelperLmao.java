package com.epxzzy.epxzzysabers.util;

import com.epxzzy.epxzzysabers.networking.packet.player.ClientboundPlayerAttackPacket;
import com.epxzzy.epxzzysabers.networking.packet.player.ClientboundPlayerDefendPacket;
import com.epxzzy.epxzzysabers.networking.packet.player.ClientboundPlayerFlourishPacket;
import com.epxzzy.epxzzysabers.networking.packet.player.ClientboundPlayerStancePacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.player.Player;

public interface PlayerHelperLmao {
    int getFlyCooldown();
    void setFlyCooldown(int i);
    int getChargeDuration();
    void setChargeDuration(int val);

    public static final EntityDataAccessor<Integer> STANCE_PREFERENCE = SynchedEntityData.defineId(Player.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> GAUNTLET_CHARGE = SynchedEntityData.defineId(Player.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> ROTARY_FLIGHT_COOLDOWN = SynchedEntityData.defineId(Player.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> ROTARY_USEDUR = SynchedEntityData.defineId(Player.class, EntityDataSerializers.INT);
    public int getFlyDur();


    public void setSaberStanceDown(boolean value);
    public boolean getSaberStanceDown();
    public int getSaberFlourishId();
    public int getSaberStanceForm();
    public int getSaberAttackForm();
    public int getSaberBlockForm();
    public void setSaberAttackForm(int val);
    public void setSaberBlockForm(int val);

    public void setSaberStanceForm(int value);

    public void setStancePreference(int value);
    public int getStancePreference();

    public void LogFlightDetails();
    public float getSaberAttackAnim();
    public float getSaberDefendAnim();
    public boolean isSaberAttacking();
    public boolean isSaberDefending();
    public void SyncDEFtoPacket(ClientboundPlayerDefendPacket packet);
    public void SyncATKtoPacket(ClientboundPlayerAttackPacket packet);
    public void SyncSTCtoPacket(ClientboundPlayerStancePacket packet);
    public void SyncFRStoPacket(ClientboundPlayerFlourishPacket packet);


}
