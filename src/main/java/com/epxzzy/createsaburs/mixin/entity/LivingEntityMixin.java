package com.epxzzy.createsaburs.mixin.entity;

import com.epxzzy.createsaburs.createsaburs;
import com.epxzzy.createsaburs.sound.ModSounds;
import com.epxzzy.createsaburs.utils.ModTags;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(
            method = "hurt",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void createsaburs$customhurt(DamageSource pSource, float pAmount, CallbackInfoReturnable<Boolean> cir) {

        LivingEntity that = ((LivingEntity) (Object) this);
        LivingEntity notThat = (LivingEntity) (pSource.getEntity() instanceof LivingEntity ? pSource.getEntity() : null);

        if (notThat != null) {

            boolean blocking_with_sabur = that.getUseItem().canPerformAction(createsaburs.SABER_BLOCK); ;

            boolean attacking_with_sabur = notThat.getMainHandItem().is(ModTags.Items.CREATE_LIGHTSABER);

            createsaburs.LOGGER.info("living entity custom hurt can be cancelled: " + cir.isCancellable());
            createsaburs.LOGGER.warn("living entity hurt in mixin");

            if (blocking_with_sabur && attacking_with_sabur) {



                    Vec3 vec32 = notThat.position();
                        Vec3 vec3 = that.getViewVector(1.0F);
                        Vec3 vec31 = vec32.vectorTo(that.position()).normalize();
                        vec31 = new Vec3(vec31.x, 0.0D, vec31.z);
                        if (vec31.dot(vec3) < 0.0D) {
                            createsaburs.LOGGER.warn("attack was blocked via a lightsaber");
                            if(that instanceof Player){
                                createsaburs.LOGGER.warn("adding kewldown to the blocking player");
                                cir.cancel();

                                ((Player) that).getCooldowns().addCooldown(that.getUseItem().getItem(), 10);
                                that.stopUsingItem();
                                //that.level().broadcastEntityEvent(that, (byte) 30);
                                //that.playSound(ModSounds.CLASH.get(), 0.2F, 1);

                                that.level().playSound((Player) null, that.blockPosition(), ModSounds.CLASH.get(), SoundSource.PLAYERS);
                            }
//                            return true;
                        }

//                 */



                //cir.cancel();

                //cir.setReturnValue(false);
                return;
            }
            //cir.setReturnValue(true);

        }


    }


    @Inject(
            method = "handleEntityEvent",
            at = @At(value = "HEAD"), cancellable = true)
    private void createsaburs$HandleEntityEventCustom(byte pId, CallbackInfo ci) {
        if (pId == 29) {
            LivingEntity that = ((LivingEntity) (Object) this);
            if (that.getMainHandItem().is(ModTags.Items.CREATE_LIGHTSABER)) {
                that.playSound(ModSounds.CLASH.get(), 0.09F, 0.8F + that.level().random.nextFloat() * 0.4F);
                ci.cancel();
                return;
            }
            return;
        }
        return;
    }


    @Inject(
            method = "swing(Lnet/minecraft/world/InteractionHand;)V",
            at = @At(value = "HEAD")
    )
    private void createsaburs$customSwing(InteractionHand pHand, CallbackInfo ci) {
        LivingEntity that = ((LivingEntity) (Object) this);
        ItemStack stacc = that.getMainHandItem();
        if (stacc.is(ModTags.Items.CREATE_LIGHTSABER) && stacc.getOrCreateTag().getBoolean("ActiveBoiii")) {
            //ci.cancel();
            that.playSound(ModSounds.SWING.get(), 0.1F, 0.8F + that.level().random.nextFloat() * 0.4F);

            return;

        }
    }

}
