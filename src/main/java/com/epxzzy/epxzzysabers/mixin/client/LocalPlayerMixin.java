package com.epxzzy.epxzzysabers.mixin.client;

import com.epxzzy.epxzzysabers.epxzzySabers;
import com.epxzzy.epxzzysabers.util.PlayerHelperLmao;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.epxzzy.epxzzysabers.rendering.PlayerPoseRouter;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.LivingEntity;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {
    @Inject(
            method = "aiStep",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraftforge/client/ForgeHooksClient;onMovementInputUpdate(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/client/player/Input;)V "
            ),
            cancellable = true)
    public void epxzzysabers$customAiStep(CallbackInfo ci) {
        LocalPlayer that = ((LocalPlayer) (Object) this);
            if(((PlayerHelperLmao) that).getSaberStanceDown()){
                //epxzzySabers.LOGGER.info("player stancing, artifically slowing down on client lmao");
                that.input.tick(true, 0.0F);
                that.stopUsingItem();
                //that.input.shiftKeyDown = true;
                //ci.cancel();
            }
    }

}