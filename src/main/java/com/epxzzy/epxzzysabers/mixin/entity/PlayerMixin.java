package com.epxzzy.epxzzysabers.mixin.entity;

import com.epxzzy.epxzzysabers.epxzzySabers;
import com.epxzzy.epxzzysabers.item.Protosaber;
import com.epxzzy.epxzzysabers.item.types.SaberGauntlet;
import com.epxzzy.epxzzysabers.misc.KewlFightsOrchestrator;
import com.epxzzy.epxzzysabers.networking.SaberMessages;
import com.epxzzy.epxzzysabers.networking.packet.ClientboundPlayerAttackPacket;
import com.epxzzy.epxzzysabers.networking.packet.ClientboundPlayerDefendPacket;
import com.epxzzy.epxzzysabers.networking.packet.ClientboundPlayerStancePacket;
import com.epxzzy.epxzzysabers.util.SaberTags;
import com.epxzzy.epxzzysabers.util.PlayerHelperLmao;
import com.epxzzy.epxzzysabers.util.StackHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(Player.class)
public abstract class PlayerMixin implements PlayerHelperLmao {
    public boolean stancing = false;
    public int stanceform = 0;

    public boolean attacking = false;
    public InteractionHand attackingHand;
    public int attackProgress = 0;
    //^^^^^^ max should be 6 ticks

    public float SaberAnim = 0;
    public float oSaberAnim = 0;

    public boolean defending = false;
    public InteractionHand defendingHand;
    public int defendProgress = 0;
    //^^^^^^ max should be 6 ticks

    public float SaberdefAnim = 0;
    public float oSaberdefAnim = 0;

    public int flyCooldownVar = 40;
    //160 == cant fly, 0 == can fly
    public int flightDurationVar = 200;
    //0 == no more fly, 300 == flyyy
    int supercharredTime = 0;




    public void LogFlightDetails() {
        //epxzzysabers.LOGGER.debug("PROG: {} ATTK: {}, BLKPROG: {}, DEF: {}", this.attackProgress, this.attacking, this.defendProgress, this.defending);
    }


    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;tick()V"
            ),
            cancellable = true)

    private void epxzzysabers$customTick(CallbackInfo ci) {
        Player that = ((Player) (Object) this);
        this.oSaberAnim = this.SaberAnim;

        if (this.attacking) {
            ++this.attackProgress;
            if (this.attackProgress >= 6) {
                this.attackProgress = 0;
                this.attacking = false;
                if (!that.level().isClientSide()) {
                    CompoundTag temptag = that.getMainHandItem().getOrCreateTag().getCompound("display");
                    temptag.remove("atk");
                    that.displayClientMessage(Component.literal(""), true);
                }
            }
        } else {
            this.attackProgress = 0;
            //that.displayClientMessage(Component.literal(""), true);

        }

        this.SaberAnim = (float) this.attackProgress / (float) 6;

        this.oSaberdefAnim = this.SaberdefAnim;

        if (this.defending) {
            ++this.defendProgress;
            if (this.defendProgress >= 6) {
                this.defendProgress = 0;
                this.defending = false;
                if (!that.level().isClientSide()) {
                    CompoundTag temptag = that.getMainHandItem().getOrCreateTag().getCompound("display");
                    temptag.remove("blk");
                    that.displayClientMessage(Component.literal(""), true);
                }
            }
        } else {
            this.defendProgress = 0;

        }

        this.SaberdefAnim = (float) this.defendProgress / (float) 6;
    }


    public boolean getSaberStanceDown() {
        return this.stancing;
    }
    public int getSaberStanceForm() {
        return this.stanceform;
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

    public void SyncSTCtoPacket(ClientboundPlayerStancePacket packet) {
        Player that = ((Player) (Object) this);

        this.stancing = packet.stancing;
        this.stanceform = packet.stancing?packet.stanceform:0;

        epxzzySabers.LOGGER.debug("successfully synced STC for {}", that);
    }

    public void SyncDEFtoPacket(ClientboundPlayerDefendPacket packet) {
        Player that = ((Player) (Object) this);

        this.defendProgress = packet.defendProgress;
        this.defending = packet.defending;

        //epxzzySabers.LOGGER.debug("successfully synced DEF for {}", that);
    }

    public void SyncATKtoPacket(ClientboundPlayerAttackPacket packet) {
        Player that = ((Player) (Object) this);

        this.attackProgress = packet.attackProgress;
        this.attacking = packet.attacking;

        //epxzzySabers.LOGGER.debug("successfully synced ATK for {}", that);
    }


    @Inject(
            method = "hurt",
            at = @At(value = "HEAD"),
            cancellable = true)

    private void epxzzySabers$customPlayerhurt(DamageSource pSource, float pAmount, CallbackInfoReturnable<Boolean> cir) {
        //epxzzySabers.LOGGER.debug("player hurt");
        Player that = ((Player) (Object) this);
        LivingEntity notThat = (LivingEntity) (pSource.getEntity() instanceof LivingEntity ? pSource.getEntity() : null);
        boolean blocking_with_sabur = that.getUseItem().is(SaberTags.Items.LIGHTSABER);

        if (!that.level().isClientSide() && blocking_with_sabur) {
            if (!this.defending || this.defendProgress >= 6 / 2 || this.defendProgress < 0) {
                this.defendProgress = -1;
                this.defending = true;
            }
            defending = true;


            if (notThat != null) {
                boolean attacking_with_sabur = notThat.getMainHandItem().is(SaberTags.Items.LIGHTSABER);


                if (blocking_with_sabur && attacking_with_sabur) {
                    epxzzySabers.LOGGER.debug("blocked {}", notThat.getMainHandItem().getOrCreateTag().getCompound("display").getInt("atk"));
                    int block_value = notThat.getMainHandItem().getOrCreateTag().getCompound("display").getInt("atk");

                    CompoundTag tagger = that.getUseItem().getOrCreateTag();
                    tagger.getCompound("display").putInt("blk", block_value);
                    that.displayClientMessage(Component.literal("blocking " + block_value), true);
                    that.getMainHandItem().setTag(tagger);

                    //remove the custom block animation
                    //cir.cancel();
                    //that.playSound(SaberSounds.CLASH.get(), 0.2F, 0.8F + that.level().random.nextFloat() * 0.4F);
                }

            }

            SaberMessages.fuckingAnnounce(new ClientboundPlayerDefendPacket(that.getId(), this.defending, this.defendProgress), that);

        }
    }

    @Inject(
            method = "attack",
            at = @At(value = "HEAD"),
            cancellable = true)

    private void epxzzySabers$customAttack(Entity pTarget, CallbackInfo ci) {
        //epxzzySabers.LOGGER.debug("player hurt");
        Player that = ((Player) (Object) this);
        Entity notThat = pTarget;

        epxzzySabers.LOGGER.info("server level: " +  !that.level().isClientSide);

        if(SaberGauntlet.checkForSaberCharge(that, true)){

            for(LivingEntity livingentity : that.level().getEntitiesOfClass(
                    LivingEntity.class,
                    pTarget.getBoundingBox().inflate(1.0D, 0.25D, 1.0D)
            )) {
                double entityReachSq = Mth.square(that.getEntityReach());
                if (
                        livingentity != that &&
                        livingentity != pTarget &&
                        !that.isAlliedTo(livingentity) &&
                        (
                                !(livingentity instanceof ArmorStand) || !( (ArmorStand) livingentity).isMarker()
                        ) &&
                        that.distanceToSqr(livingentity) < entityReachSq
                ) {
                    livingentity.knockback(
                            0.4F,
                            Mth.sin(that.getYRot() * ((float)Math.PI / 180F)),
                            (-Mth.cos(that.getYRot() * ((float)Math.PI / 180F)))
                    );
                    for (int b = 0; b != 3; b++ ){
                        livingentity.invulnerableTime = 0;
                        livingentity.hurt(that.damageSources().playerAttack(that), 1);
                        livingentity.hurtTime = 3;

                    }

                }
            }

            that.sweepAttack();
            epxzzySabers.LOGGER.debug("");
        }

        if (!that.level().isClientSide() && that.getMainHandItem().is(SaberTags.Items.LIGHTSABER)) {
            if (!this.attacking || this.attackProgress >= 6 / 2 || this.attackProgress < 0) {
                this.attackProgress = -1;
                this.attacking = true;
                this.attackingHand = that.swingingArm;
            }

            CompoundTag tagger = that.getMainHandItem().getOrCreateTag();
            int old = tagger.getCompound("display").getInt("atk");
            //int sequential = old > 0 && old < 8 ? old + 1 : 1;
            //problematic method call removed
            int methodic = KewlFightsOrchestrator.DetermineNextPossibleAttack(old, (ServerPlayer) that);
            tagger.getCompound("display").putInt("atk", methodic);

            //epxzzysabers.LOGGER.debug("next possible attack value  {}", baller);
            that.displayClientMessage(Component.literal("attacking " + methodic), true);
            that.getMainHandItem().setTag(tagger);


            //SaberMessages.sendToServer(new ClientboundPlayerAttackPacket(that.getId(), this.attacking, this.attackProgress));
            epxzzySabers.LOGGER.debug("attacking {}", that.getMainHandItem().getOrCreateTag().getCompound("display").getInt("atk"));

            SaberMessages.fuckingAnnounce(new ClientboundPlayerAttackPacket(that.getId(), this.attacking, this.attackProgress), that);

        }
    }


    /*
        @Inject(
                method = "isInvulnerableTo",
                at = @At(value = "HEAD"),
                cancellable = true
        )

        private void epxzzySabers$customIsInvulnerableTo(DamageSource pSource, CallbackInfoReturnable<Boolean> cir){
            Player that = ((Player) (Object) this);
            if(pSource.is(DamageTypeTags.IS_PROJECTILE)&&!(that.getAbilities().flying)){
               cir.setReturnValue(Protosaber.checkForSaberBlock(that)||SingleBladed.checkForSaberBlock(that));
               cir.cancel();
            }
        }

     */
    @Inject(
            method = "attack",
            at = @At(value = "HEAD")
    )
    private void epxzzySabers$customattacknoise(Entity pTarget, CallbackInfo ci) {
        Player that = ((Player) (Object) this);
        if (that.getMainHandItem().is(SaberTags.Items.LIGHTSABER) && that.getMainHandItem().getOrCreateTag().getBoolean("ActiveBoiii")) {
            //that.level().playSound((Player) null, that.getX(), that.getY(), that.getZ(), SaberSounds.SWING.get(), that.getSoundSource(), 1.0F, 1.0F);
        }

    }

    @Inject(
            method = "serverAiStep",
            at = @At(value = "HEAD")
    )
    private void epxzzySabers$customattacknoise(CallbackInfo ci) {
        LivingEntity that = ((LivingEntity) (Object) this);
        ItemStack pStack = that.getMainHandItem();
        if(that.swingTime == 0 && pStack.is(SaberTags.Items.LIGHTSABER)){
            Protosaber.removeFlourishTag(that, pStack);
        }
    }


    public int getFlyCooldown(){
        return flyCooldownVar;
    }
    @Unique
    public int getFlightDuration(){
        return flightDurationVar;
    }
    @Unique
    public void setFlyCooldown(int val){
        flyCooldownVar = val;
    }
    @Unique
    public void setFlightDuration(int val){
        flightDurationVar = val;
    }
    @Unique
    public int getChargeDuration() {
        return supercharredTime;
    }
    @Unique
    public void setChargeDuration(int val){
        supercharredTime= val;
    }
}
