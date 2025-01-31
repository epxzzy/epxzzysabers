package com.epxzzy.createsaburs.mixin.entity;

import com.epxzzy.createsaburs.createsaburs;
import com.epxzzy.createsaburs.sound.ModSounds;
import com.epxzzy.createsaburs.utils.ModTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin {
    @Inject(
            method = "blockUsingShield",
            at = @At(value = "HEAD"),
            cancellable = true)
    private void createsaburs$customblockUsingShield(LivingEntity pttEntity, CallbackInfo ci) {
        createsaburs.LOGGER.info(" event can be cancelled: "+ ci.isCancellable() );
        pttEntity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 15));
        boolean has_saber = pttEntity.getMainHandItem().is(ModTags.Items.CREATE_LIGHTSABER)
                || pttEntity.getOffhandItem().is(ModTags.Items.CREATE_LIGHTSABER);
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

    @Inject(
            method = "hurt",
            at = @At(value = "HEAD"),
            cancellable = true)

    private void createsaburs$customPlayerhurt(DamageSource pSource, float pAmount, CallbackInfoReturnable<Boolean> cir) {
        Player that = ((Player) (Object) this);
        LivingEntity notThat = (LivingEntity) (pSource.getEntity() instanceof LivingEntity ? pSource.getEntity() : null);

        if (notThat != null) {
            boolean blocking_with_sabur = that.getMainHandItem().is(ModTags.Items.CREATE_LIGHTSABER) && that.isBlocking();
            boolean attacking_with_sabur = notThat.getMainHandItem().is(ModTags.Items.CREATE_LIGHTSABER);

            if (blocking_with_sabur && attacking_with_sabur) {
                //cir.cancel();
                that.playSound(ModSounds.CLASH.get(), 0.2F, 0.8F + that.level().random.nextFloat() * 0.4F);
                return;
            }
        }


    }

    @Inject(
            method = "attack",
            at = @At(value = "HEAD")
    )
    private void createsaburs$customattacknoise(Entity pTarget, CallbackInfo ci) {
        Player that = ((Player) (Object) this);
        if (that.getMainHandItem().is(ModTags.Items.CREATE_LIGHTSABER) && that.getMainHandItem().getOrCreateTag().getBoolean("ActiveBoiii")) {
            //that.level().playSound((Player) null, that.getX(), that.getY(), that.getZ(), ModSounds.SWING.get(), that.getSoundSource(), 1.0F, 1.0F);
        }

    }

}
