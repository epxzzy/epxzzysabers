package com.epxzzy.createsaburs.mixin.client;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.epxzzy.createsaburs.rendering.PlayerSaberRenderer;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.LivingEntity;

@Mixin(HumanoidModel.class)
public class HumanoidModelMixin<T extends LivingEntity> {
    @Shadow
    @Final
    public ModelPart body;

    @Inject(method = "setupAnim*", at = @At("RETURN"))
    private void create$afterSetupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch, CallbackInfo callbackInfo) {
        if (!(pEntity instanceof AbstractClientPlayer player))
            return;

        PlayerSaberRenderer.afterSetupAnim(player, (HumanoidModel<?>) (Object) this);
    }

    @Inject(method = "setupAnim*", at = @At("HEAD"))
    private void create$beforeSetupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch, CallbackInfo callbackInfo) {
        if (!(pEntity instanceof AbstractClientPlayer player))
            return;

        PlayerSaberRenderer.beforeSetupAnim(player, (HumanoidModel<?>) (Object) this);
    }
}