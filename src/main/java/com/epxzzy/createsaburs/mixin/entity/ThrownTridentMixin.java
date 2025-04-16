package com.epxzzy.createsaburs.mixin.entity;


import com.epxzzy.createsaburs.createsaburs;
import com.epxzzy.createsaburs.item.protosaber;
import com.epxzzy.createsaburs.item.saburtypes.SingleBladed;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.phys.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ThrownTrident.class)
public class ThrownTridentMixin {

    @Inject(
            method = "Lnet/minecraft/world/entity/projectile/ThrownTrident;onHitEntity(Lnet/minecraft/world/phys/EntityHitResult;)V ",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void CustomOnHitEntity(EntityHitResult pResult, CallbackInfo ci){
        ThrownTrident that = ((ThrownTrident) (Object) this);
        if(protosaber.checkForSaberBlock(pResult.getEntity())|| SingleBladed.checkForSaberBlock(pResult.getEntity())){
            that.setDeltaMovement(that.getDeltaMovement().multiply(-0.01D, -0.1D, -0.01D));
            createsaburs.LOGGER.info("trident understands player is blocking");
            float f1 = 1.0F;
            SoundEvent soundevent = SoundEvents.TRIDENT_HIT;
            that.playSound(soundevent, 1.0f, 1.0F);
            ci.cancel();
        }
    }
}
