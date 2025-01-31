package com.epxzzy.createsaburs.mixin.entity;

import com.epxzzy.createsaburs.sound.ModSounds;
import com.epxzzy.createsaburs.utils.ModTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Shadow
    protected abstract void blockedByShield(LivingEntity pDefender);

    /*
        @Inject(
                method = "hurt",
                at = @At(value = "HEAD")
        )
        private void createsaburs$customhurt(DamageSource pSource, float pAmount, CallbackInfoReturnable<Boolean> cir) {
            LivingEntity that = ((LivingEntity) (Object) this);
            LivingEntity notThat = (LivingEntity) (pSource.getEntity() instanceof LivingEntity ? pSource.getEntity() : null);
            boolean blocking_with_sabur = that.getMainHandItem().is(ModTags.Items.CREATE_LIGHTSABER) && that.isBlocking();
            boolean attacking_with_sabur = notThat.getMainHandItem().is(ModTags.Items.CREATE_LIGHTSABER);

            if (notThat != null) {
                if (blocking_with_sabur && attacking_with_sabur) {
                    that.playSound(ModSounds.CLASH.get(), 1.0F, 0.8F + that.level().random.nextFloat() * 0.4F);
                    return;
                }
            }


        }


     */
    @Inject(
            method = "swing(Lnet/minecraft/world/InteractionHand;)V",
            at = @At(value = "HEAD")
    )
    private void createsaburs$customSwing(InteractionHand pHand, CallbackInfo ci) {
            LivingEntity that = ((LivingEntity) (Object) this);
            ItemStack stacc = that.getMainHandItem();
            if (stacc.is(ModTags.Items.CREATE_LIGHTSABER)&&stacc.getOrCreateTag().getBoolean("ActiveBoiii")) {
                //ci.cancel();
                that.playSound(ModSounds.SWING.get(), 0.1F, 0.8F + that.level().random.nextFloat() * 0.4F);

                return;

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
}
