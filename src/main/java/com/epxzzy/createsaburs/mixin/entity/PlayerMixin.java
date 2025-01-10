package com.epxzzy.createsaburs.mixin.entity;

import com.epxzzy.createsaburs.utils.ModTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin {
    @Inject(
            method = "blockUsingShield",
            at = @At(value = "HEAD")
    )
    private void createsaburs$customblockUsingShield(LivingEntity pttEntity, CallbackInfo ci) {
        pttEntity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 15));
        boolean has_saber = pttEntity.getMainHandItem().is(ModTags.Items.CREATE_LIGHTSABER) || pttEntity.getOffhandItem().is(ModTags.Items.CREATE_LIGHTSABER);
        if (has_saber) {
            //pttEntity.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 100, 5));
            //((Player) (Object) this).disableShield(true);
            Player that = ((Player) (Object) this);
            that.getCooldowns().addCooldown(that.getUseItem().getItem(), 5);
            that.stopUsingItem();
            that.level().broadcastEntityEvent(that, (byte) 30);
            //that.level().
        }

    }


}
