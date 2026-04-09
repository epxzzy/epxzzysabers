package com.epxzzy.epxzzysabers.mixin.client;

import com.epxzzy.epxzzysabers.util.PlayerHelperLmao;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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