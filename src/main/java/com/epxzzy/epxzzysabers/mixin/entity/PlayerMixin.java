package com.epxzzy.epxzzysabers.mixin.entity;

import com.epxzzy.epxzzysabers.epxzzySabers;
import com.epxzzy.epxzzysabers.item.saburtypes.SaberGauntlet;
import com.epxzzy.epxzzysabers.misc.KewlFightsOrchestrator;
import com.epxzzy.epxzzysabers.networking.ModMessages;
import com.epxzzy.epxzzysabers.networking.packet.ClientboundPlayerAttackPacket;
import com.epxzzy.epxzzysabers.networking.packet.ClientboundPlayerDefendPacket;
import com.epxzzy.epxzzysabers.utils.ModTags;
import com.epxzzy.epxzzysabers.utils.PlayerHelperLmao;
import com.epxzzy.epxzzysabers.utils.StackHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraftforge.fml.common.Mod;
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

        epxzzySabers.LOGGER.debug("successfully synced DEF for {}", that);
    }

    public void SyncATKtoPacket(ClientboundPlayerAttackPacket packet) {
        Player that = ((Player) (Object) this);

        this.attackProgress = packet.attackProgress;
        this.attacking = packet.attacking;

        epxzzySabers.LOGGER.debug("successfully synced ATK for {}", that);
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

        if (!that.level().isClientSide() && blocking_with_sabur) {
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

        if(SaberGauntlet.checkForSaberCharge(that, true)){

            for(LivingEntity livingentity : that.level().getEntitiesOfClass(LivingEntity.class, Items.STONE_SWORD.getSweepHitBox(that.getMainHandItem(), that, pTarget))) {
                double entityReachSq = Mth.square(that.getEntityReach()); // Use entity reach instead of constant 9.0. Vanilla uses bottom center-to-center checks here, so don't update this to use canReach, since it uses closest-corner checks.
                if (livingentity != that && livingentity != pTarget && !that.isAlliedTo(livingentity) && (!(livingentity instanceof ArmorStand) || !((ArmorStand)livingentity).isMarker()) && that.distanceToSqr(livingentity) < entityReachSq) {
                    livingentity.knockback((double)0.4F, (double)Mth.sin(that.getYRot() * ((float)Math.PI / 180F)), (double)(-Mth.cos(that.getYRot() * ((float)Math.PI / 180F))));
                    for (int b = 0; b != 3; b++ ){
                        livingentity.invulnerableTime = 0;
                        livingentity.hurt(that.damageSources().playerAttack(that), 1);
                        livingentity.hurtTime = 3;

                    }

                }
            }

            that.sweepAttack();
        }

        if (!that.level().isClientSide() && that.getMainHandItem().is(ModTags.Items.LIGHTSABER)) {
            if (!this.attacking || this.attackProgress >= 6 / 2 || this.attackProgress < 0) {
                this.attackProgress = -1;
                this.attacking = true;
                this.attackingHand = that.swingingArm;
            }
            this.attacking = true;

            CompoundTag tagger = that.getMainHandItem().getOrCreateTag();
            int old = tagger.getCompound("display").getInt("atk");
            int sequential = old > 0 && old < 8 ? old + 1 : 1;
            int random = StackHelper.random1to8(old);
            int methodic = KewlFightsOrchestrator.DetermineNextPossibleAttack(old, that);
            tagger.getCompound("display").putInt("atk", methodic);

            //epxzzysabers.LOGGER.debug("next possible attack value  {}", baller);
            that.displayClientMessage(Component.literal("attacking " + methodic), true);
            that.getMainHandItem().setTag(tagger);


            //ModMessages.sendToServer(new ClientboundPlayerAttackPacket(that.getId(), this.attacking, this.attackProgress));
            epxzzySabers.LOGGER.debug("attacking {}", that.getMainHandItem().getOrCreateTag().getCompound("display").getInt("atk"));

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
