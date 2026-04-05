package com.epxzzy.epxzzysabers.mixin.entity;

import com.epxzzy.epxzzysabers.epxzzySabers;
import com.epxzzy.epxzzysabers.item.SaberItems;
import com.epxzzy.epxzzysabers.item.types.SaberGauntlet;
import com.epxzzy.epxzzysabers.misc.KewlFightsOrchestrator;
import com.epxzzy.epxzzysabers.networking.SaberMessages;
import com.epxzzy.epxzzysabers.networking.packet.player.ClientboundPlayerAttackPacket;
import com.epxzzy.epxzzysabers.networking.packet.player.ClientboundPlayerDefendPacket;
import com.epxzzy.epxzzysabers.networking.packet.player.ClientboundPlayerFlourishPacket;
import com.epxzzy.epxzzysabers.networking.packet.player.ClientboundPlayerStancePacket;
import com.epxzzy.epxzzysabers.util.ConfigHolder;
import com.epxzzy.epxzzysabers.util.SaberTags;
import com.epxzzy.epxzzysabers.util.PlayerHelperLmao;
import com.epxzzy.epxzzysabers.util.TagHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(Player.class)
public abstract class PlayerMixin implements PlayerHelperLmao {
    public boolean stancing = false;

    public boolean attacking = false;
    public InteractionHand attackingHand;
    public int attackProgress = 0;
    //^^^^^^ max should be 6 ticks
    public int attackPose = 0;

    public float SaberAnim = 0;
    public float oSaberAnim = 0;

    public boolean defending = false;
    public InteractionHand defendingHand;
    public int defendProgress = 0;
    //^^^^^^ max should be 6 ticks
    public int defendPose = 0;

    public float SaberdefAnim = 0;
    public float oSaberdefAnim = 0;
    public int flourishId = 0;



    @Inject(
            method = "defineSynchedData",
            at = @At(value = "TAIL"),
            cancellable = true)

    private void defineSynchedData(CallbackInfo ci) {
        Player that = ((Player) (Object) this);
        that.getEntityData().define(STANCE_PREFERENCE, 1);
        that.getEntityData().define(ROTARY_FLIGHT_COOLDOWN, 0);
        //40 == cant fly, 0 == can fly
        that.getEntityData().define(GAUNTLET_CHARGE, 0);
        that.getEntityData().define(ROTARY_USEDUR, 0);

    }

    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;tick()V"
            ),
            cancellable = true)

    private void UpdateAttackDefendTick(CallbackInfo ci) {
        Player that = ((Player) (Object) this);
        this.oSaberAnim = this.SaberAnim;

        if (this.attacking) {
            ++this.attackProgress;
            if (this.attackProgress >= 6) {
                this.attackProgress = 0;
                this.attacking = false;
                //this.attackPose = 0;
                if (!that.level().isClientSide()) {
                    that.displayClientMessage(Component.literal(""), true);
                }
            }
        } else {
            this.attackProgress = 0;
        }

        this.SaberAnim = (float) this.attackProgress / (float) 6;

        this.oSaberdefAnim = this.SaberdefAnim;

        if (this.defending) {
            ++this.defendProgress;
            if (this.defendProgress >= 6) {
                this.defendProgress = 0;
                this.defending = false;
                //this.defendPose = 0;
                if (!that.level().isClientSide()) {
                    that.displayClientMessage(Component.literal(""), true);
                }
            }
        } else {
            this.defendProgress = 0;
        }

        this.SaberdefAnim = (float) this.defendProgress / (float) 6;

        if (!that.level().isClientSide()){
            if(that.getUseItem().getItem() == SaberItems.ROTARY_SABER.get()&&that.getAbilities().flying) that.getEntityData().set(ROTARY_USEDUR, that.getUseItemRemainingTicks());
            else that.getEntityData().set(ROTARY_USEDUR, 0);

        }
    }

    public float getSaberAttackAnim() {
        float f = this.SaberAnim - this.oSaberAnim;
        if (f < 0.0F) {
            ++f;
        }

        return this.oSaberAnim + f;
    }

    public float getSaberDefendAnim() {
        float f = this.SaberdefAnim - this.oSaberdefAnim;
        if (f < 0.0F) {
            ++f;
        }

        return this.oSaberdefAnim + f;
    }
    public boolean isSaberAttacking() {
        return this.attacking;
    }

    public boolean isSaberDefending() {
        return this.defending;
    }

    @Inject(
            method = "hurt",
            at = @At(value = "HEAD"),
            cancellable = true)

    private void BlockSaberAttack(DamageSource pSource, float pAmount, CallbackInfoReturnable<Boolean> cir) {
        //epxzzySabers.LOGGER.debug("player hurt");
        Player that = ((Player) (Object) this);
        LivingEntity attacker = (LivingEntity) (pSource.getEntity() instanceof LivingEntity ? pSource.getEntity() : null);
        boolean gotBlocked = TagHelper.checkUsingActivePoseableWeapon(that);
        //boolean gotBlocked = TagHelper.checkActiveSaber(that, that.getUsedItemHand() == InteractionHand.MAIN_HAND);

        if (!that.level().isClientSide() && gotBlocked) {
            if (!this.defending || this.defendProgress >= 6 / 2 || this.defendProgress < 0) {
                this.defendProgress = -1;
            }
            defending = true;
            int tempatk = 0;

            if (attacker != null) {
                boolean posedAttack = TagHelper.checkActivePoseableWeapon(attacker, true);

                if (gotBlocked && posedAttack) {
                    //epxzzySabers.LOGGER.debug("blocked {}", notThat.getMainHandItem().getOrCreateTag().getCompound("display").getInt("atk"));

                    if (attacker instanceof Player) {
                        tempatk = ((PlayerHelperLmao) attacker).getSaberAttackForm();
                    } else {
                        tempatk = KewlFightsOrchestrator.DetermineNextPossibleAttack(8, (ServerPlayer) that);
                    }
                    if (tempatk > 0) that.displayClientMessage(Component.literal("blocking " + tempatk), true);

                    //that.playSound(SaberSounds.CLASH.get(), 0.2F, 0.8F + that.level().random.nextFloat() * 0.4F);
                }

            }

            SaberMessages.fuckingAnnounce(new ClientboundPlayerDefendPacket(that.getId(), this.defending, this.defendProgress, tempatk), that);
        }
    }

    @Inject(
            method = "attack",
            at = @At(value = "HEAD"),
            cancellable = true)

    private void AttackWithSaber(Entity pTarget, CallbackInfo ci) {
        //epxzzySabers.LOGGER.debug("player hurt");
        Player that = ((Player) (Object) this);
        //Entity notThat = pTarget;

        if (SaberGauntlet.checkForSaberCharge(that, true)) {
            for (LivingEntity livingentity : that.level().getEntitiesOfClass(
                    LivingEntity.class,
                    pTarget.getBoundingBox().inflate(1.0D, 0.25D, 1.0D)
            )) {
                double entityReachSq = Mth.square(that.getEntityReach());
                if (
                        livingentity != that &&
                                livingentity != pTarget &&
                                !that.isAlliedTo(livingentity) &&
                                (
                                        !(livingentity instanceof ArmorStand) || !((ArmorStand) livingentity).isMarker()
                                ) &&
                                that.distanceToSqr(livingentity) < entityReachSq
                ) {
                    livingentity.knockback(
                            0.4F,
                            Mth.sin(that.getYRot() * ((float) Math.PI / 180F)),
                            (-Mth.cos(that.getYRot() * ((float) Math.PI / 180F)))
                    );
                    for (int b = 0; b != 3; b++) {
                        livingentity.invulnerableTime = 0;
                        livingentity.hurt(that.damageSources().playerAttack(that), 1);
                        livingentity.hurtTime = 3;

                    }

                }
            }

            that.sweepAttack();
        }

        if (!that.level().isClientSide() && TagHelper.checkActivePoseableWeapon(that, true)) {
            if (!this.attacking || this.attackProgress >= 6 / 2 || this.attackProgress < 0) {
                this.attackProgress = -1;
                this.attacking = true;
                this.attackingHand = that.swingingArm;
            }

            //int old = tagger.getCompound("display").getInt("atk");

            //int sequential = old > 0 && old < 8 ? old + 1 : 1;
            //problematic method call removed
            //int methodic = KewlFightsOrchestrator.DetermineNextPossibleAttack(7, (ServerPlayer) that);
            int methodic = KewlFightsOrchestrator.DetermineNextPossibleAttack(7, (ServerPlayer) that, pTarget);

            //epxzzysabers.LOGGER.debug("next possible attack value  {}", baller);
            if(methodic > 0) that.displayClientMessage(Component.literal("attacking " + methodic), true);
            ((PlayerHelperLmao) that).setSaberAttackForm(methodic);

            SaberMessages.fuckingAnnounce(new ClientboundPlayerAttackPacket(that.getId(), this.attacking, this.attackProgress, methodic), that);
            //epxzzySabers.LOGGER.debug("attacking {}", that.getMainHandItem().getOrCreateTag().getCompound("display").getInt("atk"));
        }
    }

    public void SyncSTCtoPacket(ClientboundPlayerStancePacket packet) {
        Player that = ((Player) (Object) this);

        this.stancing = packet.stancing;
        that.getEntityData().set(STANCE_PREFERENCE, packet.stanceform);

        epxzzySabers.LOGGER.debug("successfully synced STC for {} w/ pref {}", that, packet.stanceform);
    }

    public void SyncDEFtoPacket(ClientboundPlayerDefendPacket packet) {
        Player that = ((Player) (Object) this);

        this.defendProgress = packet.defendProgress;
        this.defending = packet.defending;
        this.defendPose = packet.defendPose;

        //epxzzySabers.LOGGER.debug("successfully synced DEF for {}", that);
    }

    public void SyncATKtoPacket(ClientboundPlayerAttackPacket packet) {
        Player that = ((Player) (Object) this);

        this.attackProgress = packet.attackProgress;
        this.attacking = packet.attacking;
        this.attackPose = packet.attackPose;

        //epxzzySabers.LOGGER.debug("successfully synced ATK for {}", that);
    }

    public void SyncFRStoPacket(ClientboundPlayerFlourishPacket packet) {
        Player that = ((Player) (Object) this);

        this.flourishId = packet.flourishId;

        //epxzzySabers.LOGGER.debug("successfully synced FRS for {}", that);
    }

    public int getFlyCooldown() {
        Player that = ((Player) (Object) this);
        return that.getEntityData().get(ROTARY_FLIGHT_COOLDOWN);
    }

    @Unique
    public void setFlyCooldown(int val) {
        Player that = ((Player) (Object) this);
        that.getEntityData().set(ROTARY_FLIGHT_COOLDOWN, val);
    }

    @Unique
    public int getChargeDuration() {
        Player that = ((Player) (Object) this);
        return that.getEntityData().get(GAUNTLET_CHARGE);
    }

    @Unique
    public void setChargeDuration(int val) {
        Player that = ((Player) (Object) this);
        that.getEntityData().set(GAUNTLET_CHARGE, val);
    }
    @Unique
    public int getFlyDur() {
        Player that = ((Player) (Object) this);
        return that.getEntityData().get(ROTARY_USEDUR);
    }

    @Unique
    public void setSaberStanceDown(boolean val) {
        this.stancing = val;
    }

    @Unique
    public boolean getSaberStanceDown() {
        return this.stancing;
    }

    @Unique
    public int getStancePreference() {
        Player that = ((Player) (Object) this);
        return that.getEntityData().get(STANCE_PREFERENCE);
    }

    @Unique
    public void setSaberStanceForm(int value) {
        Player that = ((Player) (Object) this);
        that.getEntityData().set(STANCE_PREFERENCE, value);

    }

    @Unique
    public int getSaberAttackForm() {
        return this.attackPose;
    }

    @Unique
    public int getSaberBlockForm() {
        return this.defendPose;
    }

    @Unique
    public void setSaberAttackForm(int val) {
        this.attackPose = val;
    }

    @Unique
    public void setSaberBlockForm(int val) {
        this.defendPose = val;
    }

    @Unique
    public int getSaberFlourishId() {
        return this.flourishId;
    }


    public void LogFlightDetails() {
        //epxzzysabers.LOGGER.debug("PROG: {} ATTK: {}, BLKPROG: {}, DEF: {}", this.attackProgress, this.attacking, this.defendProgress, this.defending);
    }
}
