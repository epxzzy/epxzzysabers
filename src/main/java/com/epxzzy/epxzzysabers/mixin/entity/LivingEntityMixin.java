package com.epxzzy.epxzzysabers.mixin.entity;

import com.epxzzy.epxzzysabers.epxzzySabers;
import com.epxzzy.epxzzysabers.item.Protosaber;
import com.epxzzy.epxzzysabers.item.SaberItems;
import com.epxzzy.epxzzysabers.sound.SaberSounds;
import com.epxzzy.epxzzysabers.util.SaberTags;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {


    @Inject(
            method = "hurt",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void epxzzySabers$customhurt(DamageSource pSource, float pAmount, CallbackInfoReturnable<Boolean> cir) {

        LivingEntity that = ((LivingEntity) (Object) this);

        Entity notThat = pSource.getEntity();

        if (notThat != null) {

            boolean blocking_with_sabur = that.getUseItem().canPerformAction(epxzzySabers.SABER_BLOCK);

            boolean attacking_with_sabur = LivingEntity.class.isAssignableFrom(notThat.getClass()) ? ((LivingEntity) notThat).getMainHandItem().is(SaberTags.Items.LIGHTSABER) : false;

            //epxzzySabers.LOGGER.info("living entity custom hurt can be cancelled: " + cir.isCancellable());
            //epxzzySabers.LOGGER.debug("living entity hurt in mixin");

            if (blocking_with_sabur && attacking_with_sabur) {
                Vec3 vec32 = notThat.position();
                Vec3 vec3 = that.getViewVector(1.0F);
                Vec3 vec31 = vec32.vectorTo(that.position()).normalize();
                vec31 = new Vec3(vec31.x, 0.0D, vec31.z);
                if (vec31.dot(vec3) < 0.0D) {
                    //epxzzySabers.LOGGER.debug("attack was blocked via a lightsaber");
                    if (that instanceof Player) {
                        //epxzzySabers.LOGGER.debug("adding kewldown to the blocking player");
                        cir.cancel();
                        if(!that.getUseItem().is(SaberItems.ROTARY_SABER.get())){
                            ((Player) that).getCooldowns().addCooldown(that.getUseItem().getItem(), 5);
                            that.stopUsingItem();
                            that.level().playSound((Player) null, that.blockPosition(), SaberSounds.CLASH.get(), SoundSource.PLAYERS, 0.5F, 1);
                            return;
                        }
                        //that.level().broadcastEntityEvent(that, (byte) 30);
                        //that.playSound(SaberSounds.CLASH.get(), 0.2F, 1);
                        that.level().playSound((Player) null, that.blockPosition(), SaberSounds.CLASH.get(), SoundSource.PLAYERS);
                    }
                    // return true;
                }
                //cir.cancel();
                //cir.setReturnValue(false);
            }

            if (blocking_with_sabur && Projectile.class.isAssignableFrom(Objects.requireNonNull(pSource.getDirectEntity()).getClass())) {
                if(Player.class.isAssignableFrom(Objects.requireNonNull(that.getClass()))&&!(((Player) that).getAbilities().flying)){
                    if(((Player) that).getTicksUsingItem() > Protosaber.SOFT_PARRY) return; // GH1-#1

                    //epxzzySabers.LOGGER.debug("blocked a projectile");
                    cir.cancel();
                    that.level().playSound((Player) null, that.blockPosition(), SaberSounds.CLASH.get(), SoundSource.PLAYERS);
                }
                else {
                    //epxzzySabers.LOGGER.debug("flying bozo spotted, die");
                    //((Player) that).getCooldowns().addCooldown(that.getUseItem().getItem(), 20*8);
                    that.stopUsingItem();
                    that.swing(InteractionHand.MAIN_HAND);
                }
                return;
            }

            if (blocking_with_sabur && !attacking_with_sabur) {
                //epxzzySabers.LOGGER.debug("blocked a regular ass weapon");
                //cir.cancel();
                //that.level().playSound((Player) null, that.blockPosition(), SaberSounds.CLASH.get(), SoundSource.PLAYERS);
                return;
            }


            //cir.setReturnValue(true);

        }


    }
    @Inject(
            method = "swing(Lnet/minecraft/world/InteractionHand;)V",
            at = @At(value = "HEAD")
    )
    private void epxzzySabers$customSwing(InteractionHand pHand, CallbackInfo ci) {
        LivingEntity that = ((LivingEntity) (Object) this);
        ItemStack stacc = that.getMainHandItem();
        if (stacc.is(SaberTags.Items.LIGHTSABER) && stacc.getOrCreateTag().getBoolean("ActiveBoiii")) {
            //ci.cancel();

            //that.playSound(SaberSounds.SWING.get(), 0.1F, 0.8F + that.level().random.nextFloat() * 0.4F);
            return;
        }
    }

    @Inject(
            method = "handleEntityEvent",
            at = @At(value = "HEAD"), cancellable = true)
    private void epxzzySabers$HandleEntityEventCustom(byte pId, CallbackInfo ci) {
        if (pId == 29) {
            LivingEntity that = ((LivingEntity) (Object) this);
            if (that.getMainHandItem().is(SaberTags.Items.LIGHTSABER)) {
                that.playSound(SaberSounds.CLASH.get(), 0.09F, 0.8F + that.level().random.nextFloat() * 0.4F);
                ci.cancel();
                return;
            }
            return;
        }
        return;
    }
}