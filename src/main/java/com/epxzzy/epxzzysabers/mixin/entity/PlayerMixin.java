package com.epxzzy.epxzzysabers.mixin.entity;

import com.epxzzy.epxzzysabers.epxzzySabers;
import com.epxzzy.epxzzysabers.networking.ModMessages;
import com.epxzzy.epxzzysabers.networking.packet.ClientboundPlayerAttackPacket;
import com.epxzzy.epxzzysabers.networking.packet.ClientboundPlayerDefendPacket;
import com.epxzzy.epxzzysabers.utils.ModTags;
import com.epxzzy.epxzzysabers.utils.PlayerHelperLmao;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(Player.class)
public abstract class PlayerMixin implements PlayerHelperLmao {
    @Shadow
    @Final
    private Inventory inventory;
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

    public void LogFlightDetails() {
        //epxzzysabers.LOGGER.debug("PROG: {} ATTK: {}, BLKPROG: {}, DEF: {}", this.attackProgress, this.attacking, this.defendProgress, this.defending);
    }

    ;


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
                    CompoundTag tagger = that.getMainHandItem().getOrCreateTag();
                    int old = tagger.getCompound("display").getInt("atk");
                    int sequential = old > 0 && old < 8 ? old + 1 : 1;
                    int random = (int) new Random().nextInt(8)+1;
                    tagger.getCompound("display").putInt("atk", random);

                    //epxzzysabers.LOGGER.debug("next possible attack value  {}", baller);
                    that.displayClientMessage(Component.literal("attacking " + random), true);
                    that.getMainHandItem().setTag(tagger);
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
                CompoundTag temptag = that.getMainHandItem().getOrCreateTag().getCompound("display");
                temptag.remove("blk");
            }
        } else {
            this.defendProgress = 0;

        }

        this.SaberdefAnim = (float) this.defendProgress / (float) 6;
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


    public void SyncDEFtoPacket(ClientboundPlayerDefendPacket packet) {
        Player that = ((Player) (Object) this);

        this.defendProgress = packet.defendProgress;
        this.defending = packet.defending;

        //epxzzysabers.LOGGER.debug("successfully synced DEF for {}", that);
    }

    public void SyncATKtoPacket(ClientboundPlayerAttackPacket packet) {
        Player that = ((Player) (Object) this);

        this.attackProgress = packet.attackProgress;
        this.attacking = packet.attacking;

        //epxzzysabers.LOGGER.debug("successfully synced ATK for {}", that);
    }


    @Inject(
            method = "hurt",
            at = @At(value = "HEAD"),
            cancellable = true)

    private void epxzzySabers$customPlayerhurt(DamageSource pSource, float pAmount, CallbackInfoReturnable<Boolean> cir) {
        //epxzzySabers.LOGGER.debug("player hurt");
        Player that = ((Player) (Object) this);
        LivingEntity notThat = (LivingEntity) (pSource.getEntity() instanceof LivingEntity ? pSource.getEntity() : null);
        boolean blocking_with_sabur = that.getUseItem().is(ModTags.Items.LIGHTSABER);

        if(!that.level().isClientSide() && blocking_with_sabur){
            if (!this.defending || this.defendProgress >= 6 / 2 || this.defendProgress < 0) {
                this.defendProgress = -1;
                this.defending = true;
            }
            defending = true;




            if (notThat != null) {
                boolean attacking_with_sabur = notThat.getMainHandItem().is(ModTags.Items.LIGHTSABER);


                if (blocking_with_sabur && attacking_with_sabur) {
                    epxzzySabers.LOGGER.debug("blocked {}", notThat.getMainHandItem().getOrCreateTag().getCompound("display").getInt("atk"));
                    int block_value = notThat.getMainHandItem().getOrCreateTag().getCompound("display").getInt("atk");

                    CompoundTag tagger = that.getUseItem().getOrCreateTag();
                    tagger.getCompound("display").putInt("blk", block_value);
                    that.displayClientMessage(Component.literal("blocking " + block_value), true);
                    that.getMainHandItem().setTag(tagger);

                    //remove the custom block animation
                    //cir.cancel();
                    //that.playSound(ModSounds.CLASH.get(), 0.2F, 0.8F + that.level().random.nextFloat() * 0.4F);
                    return;
                }

            }

            ModMessages.fuckingAnnounce(new ClientboundPlayerDefendPacket(that.getId(), this.defending, this.defendProgress), that);

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
        epxzzySabers.LOGGER.debug("attacking {}", that.getMainHandItem().getOrCreateTag().getCompound("display").getInt("atk"));



        if(!that.level().isClientSide()) {
            if (!this.attacking || this.attackProgress >= 6 / 2 || this.attackProgress < 0) {
                this.attackProgress = -1;
                this.attacking = true;
                this.attackingHand = that.swingingArm;
            }
            this.attacking = true;
            //ModMessages.sendToServer(new ClientboundPlayerAttackPacket(that.getId(), this.attacking, this.attackProgress));

            ModMessages.fuckingAnnounce(new ClientboundPlayerAttackPacket(that.getId(), this.attacking, this.attackProgress), that);

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
        if (that.getMainHandItem().is(ModTags.Items.LIGHTSABER) && that.getMainHandItem().getOrCreateTag().getBoolean("ActiveBoiii")) {
            //that.level().playSound((Player) null, that.getX(), that.getY(), that.getZ(), ModSounds.SWING.get(), that.getSoundSource(), 1.0F, 1.0F);
        }

    }

}
