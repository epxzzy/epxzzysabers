package com.epxzzy.CreateSaburs.mixin.entity;

import com.epxzzy.CreateSaburs.CreateSaburs;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.ToolActions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin{


    @Inject(
            method = "blockUsingShield",
            at = @At(value = "HEAD")
    )
    private void CreateSaburs$blockUsingShield(LivingEntity pttEntity, CallbackInfo ci) {
        if(pttEntity.getMainHandItem().canPerformAction(ToolActions.SHIELD_BLOCK)){
            CreateSaburs.LOGGER.info("wut the fluccc?");
            //pttEntity..disableShield(true);
            //this.addItem(Items.BRAIN_CORAL_BLOCK.getDefaultInstance());
        }
    }


}
