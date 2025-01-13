package com.epxzzy.createsaburs.mixin.entity;

import com.epxzzy.createsaburs.sound.ModSounds;
import com.epxzzy.createsaburs.utils.ModTags;
import net.minecraft.world.entity.LivingEntity;
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
            method = "handleEntityEvent",
            at = @At(value = "HEAD")
    )
    private void createsaburs$HandleEntityEventCustom(byte pId, CallbackInfo ci) {
        if (pId == 29) {
            LivingEntity that = ((LivingEntity) (Object) this);
            if (that.getMainHandItem().is(ModTags.Items.CREATE_LIGHTSABER)) {
                that.playSound(ModSounds.CLASH.get(), 1.0F, 0.8F + that.level().random.nextFloat() * 0.4F);
                return;
            }
        }
    }
}
