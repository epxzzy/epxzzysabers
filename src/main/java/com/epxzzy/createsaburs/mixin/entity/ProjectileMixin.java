package com.epxzzy.createsaburs.mixin.entity;


import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Projectile.class)
public class ProjectileMixin {
    @Inject(
            method = "onHitBlock",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void CustomOnHitBlock(BlockHitResult pResult, CallbackInfo ci){
       Projectile that = ((Projectile) (Object) this);
       that.setDeltaMovement(0,0,0);
    }
}
