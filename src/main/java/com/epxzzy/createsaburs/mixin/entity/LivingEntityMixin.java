package com.epxzzy.createsaburs.mixin.entity;

import com.epxzzy.createsaburs.sound.ModSounds;
import com.epxzzy.createsaburs.utils.ModTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Inject(
            method = "hurt",
            at = @At(value = "HEAD")
    )
    private void createsaburs$customhurt(DamageSource pSource, float pAmount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity that = ((LivingEntity) (Object) this);
        boolean has_saber = that.getMainHandItem().is(ModTags.Items.CREATE_LIGHTSABER) && that.isBlocking();
        if (has_saber) {
            that.playSound(ModSounds.CLASH.get(), 1.0F, 0.8F + that.level().random.nextFloat() * 0.4F);
            return;
        }


    }
    @Inject(
            method = "handleEntityEvent",
            at = @At(value = "HEAD")
    )
    private void createsaburs$customhurt(byte pId, CallbackInfo ci) {
        if (pId == 29) {
            LivingEntity that = ((LivingEntity) (Object) this);
            if (that.getMainHandItem().is(ModTags.Items.CREATE_LIGHTSABER)) {
                that.playSound(ModSounds.CLASH.get(), 1.0F, 0.8F + that.level().random.nextFloat() * 0.4F);
                return;
            }
        }


    }




}
