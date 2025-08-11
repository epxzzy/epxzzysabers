package com.epxzzy.createsaburs.mixin.entity;

import com.epxzzy.createsaburs.CreateSaburs;
import com.epxzzy.createsaburs.item.Protosaber;
import com.epxzzy.createsaburs.item.saburtypes.SingleBladed;
import com.epxzzy.createsaburs.utils.ModTags;
import com.epxzzy.createsaburs.utils.PlayerHelperLmao;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin implements PlayerHelperLmao {

    public boolean attacking = false;

    public InteractionHand attackingHand;
    public boolean attackingWithSaber = false;

    public int attackProgress = 0;
    //^^^^^^ max should be 6 ticks

    public float SaberAnim = 0;
    public float oSaberAnim = 0;
    /*
    @Inject(
            method = "blockUsingShield",
            at = @At(value = "HEAD"),
            cancellable = true)
    private void CreateSaburs$customblockUsingShield(LivingEntity pttEntity, CallbackInfo ci) {
        CreateSaburs.LOGGER.info(" event can be cancelled: "+ ci.isCancellable() );
        pttEntity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 15));
        boolean has_saber = pttEntity.getMainHandItem().is(ModTags.Items.LIGHTSABER)
                || pttEntity.getOffhandItem().is(ModTags.Items.LIGHTSABER);
        boolean is_active = pttEntity.getMainHandItem().getOrCreateTag().getBoolean("ActiveBoiii")
                || pttEntity.getOffhandItem().getOrCreateTag().getBoolean("ActiveBoiii");

        if (has_saber && is_active) {
            //ci.cancel();
            Player that = ((Player) (Object) this);
            that.getCooldowns().addCooldown(that.getUseItem().getItem(), 5);
            that.stopUsingItem();
            //that.level().broadcastEntityEvent(that, (byte) 30);
            that.playSound(ModSounds.CLASH.get(), 0.2F, 1);
            //that.level().

        }

    }

     */
    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;tick()V"
                 ),
            cancellable = true)

    private void CreateSaburs$customTick(CallbackInfo ci) {
        //CreateSaburs.LOGGER.warn("player hurt");
        Player that = ((Player) (Object) this);

        if (this.attacking) {
            ++this.attackProgress;
            if (this.attackProgress>= 6) {
                this.attackProgress = 0;
                this.attacking = false;
            }
        } else {
            this.attackProgress = 0;
            that.displayClientMessage(Component.literal(""), true);

        }

        //this.SaberAnim = (float)this.swingTime / (float)i;

        //ATTACKING = true;
    }
    public float getSaberAttackAnim(float pPartialTick) {
        float f = this.SaberAnim - this.oSaberAnim;
        if (f < 0.0F) {
            ++f;
        }

        return this.oSaberAnim + f * pPartialTick;
    }
    public int getAttackTime(){
        return this.attackProgress;
    }

    public void LogFlightDetails(){
       //CreateSaburs.LOGGER.debug("PROG: {} ATTK: {}, ARM: {}",this.attackProgress,this.attacking,this.attackingHand);
    };
    @Inject(
            method = "hurt",
            at = @At(value = "HEAD"),
            cancellable = true)

    private void CreateSaburs$customPlayerhurt(DamageSource pSource, float pAmount, CallbackInfoReturnable<Boolean> cir) {
        //CreateSaburs.LOGGER.warn("player hurt");
        Player that = ((Player) (Object) this);
        LivingEntity notThat = (LivingEntity) (pSource.getEntity() instanceof LivingEntity ? pSource.getEntity() : null);

        if (notThat != null) {
            boolean blocking_with_sabur = that.getUseItem().is(ModTags.Items.LIGHTSABER);
            boolean attacking_with_sabur = notThat.getMainHandItem().is(ModTags.Items.LIGHTSABER);




            if (blocking_with_sabur && attacking_with_sabur) {
                CreateSaburs.LOGGER.debug("before i get killed, i would like to preface that i was infact hit by a attack of {}",notThat.getMainHandItem().getOrCreateTag().getCompound("display").getInt("atk"));
                int block_value = notThat.getMainHandItem().getOrCreateTag().getCompound("display").getInt("atk");

                CompoundTag tagger = that.getUseItem().getOrCreateTag();
                tagger.getCompound("display").putInt("blk", block_value);

                that.getMainHandItem().setTag(tagger);

                //remove the custom block animation
                //cir.cancel();
                //that.playSound(ModSounds.CLASH.get(), 0.2F, 0.8F + that.level().random.nextFloat() * 0.4F);
                return;
            }
        }


    }

    @Inject(
            method = "attack",
            at = @At(value = "HEAD"),
            cancellable = true)

    private void CreateSaburs$customAttack(Entity pTarget, CallbackInfo ci) {
        //CreateSaburs.LOGGER.warn("player hurt");
        Player that = ((Player) (Object) this);
        Entity notThat = pTarget;
        CreateSaburs.LOGGER.debug("before i kill this individual, i would like to preface that i was infact attacking an attack of {}", that.getMainHandItem().getOrCreateTag().getCompound("display").getInt("atk"));

        if(!that.level().isClientSide()) {
            CompoundTag tagger = that.getMainHandItem().getOrCreateTag();
            int old = tagger.getCompound("display").getInt("atk");
            int baller = old > 0 && old < 8? old+1:1;
            tagger.getCompound("display").putInt("atk", baller);

            CreateSaburs.LOGGER.debug("setting an atk of  {}", baller);
            that.displayClientMessage(Component.literal("attacking " +baller), true);
            that.getMainHandItem().setTag(tagger);
        }




        if (!this.attacking || this.attackProgress >= 6 / 2 || this.attackProgress < 0) {
            this.attackProgress = -1;
            this.attacking = true;
            this.attackingHand = that.swingingArm;
        }
        attacking = true;
    }


    /*
        @Inject(
                method = "isInvulnerableTo",
                at = @At(value = "HEAD"),
                cancellable = true
        )

        private void CreateSaburs$customIsInvulnerableTo(DamageSource pSource, CallbackInfoReturnable<Boolean> cir){
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
    private void CreateSaburs$customattacknoise(Entity pTarget, CallbackInfo ci) {
        Player that = ((Player) (Object) this);
        if (that.getMainHandItem().is(ModTags.Items.LIGHTSABER) && that.getMainHandItem().getOrCreateTag().getBoolean("ActiveBoiii")) {
            //that.level().playSound((Player) null, that.getX(), that.getY(), that.getZ(), ModSounds.SWING.get(), that.getSoundSource(), 1.0F, 1.0F);
        }

    }

}
