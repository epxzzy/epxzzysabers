package com.epxzzy.createsaburs.mixin.entity;

import com.epxzzy.createsaburs.createsaburs;
import com.epxzzy.createsaburs.utils.ModTags;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class PlayerMixin{



    @Inject(
            method = "blockUsingShield",
            at = @At(value = "HEAD")
    )
    private void createsaburs$customblockUsingShield(LivingEntity pttEntity, CallbackInfo ci) {
        pttEntity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 15));
        boolean sablur = pttEntity.getMainHandItem().is(ModTags.Items.CREATE_LIGHTSABER);
        boolean sablur2 = pttEntity.getOffhandItem().is(ModTags.Items.CREATE_LIGHTSABER);
        if(pttEntity.getMainHandItem().is(ModTags.Items.CREATE_LIGHTSABER)||pttEntity.getOffhandItem().is(ModTags.Items.CREATE_LIGHTSABER)){
            createsaburs.LOGGER.info("wut the fluccc?");
            pttEntity.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 15));
            //this.addItem(Items.BRAIN_CORAL_BLOCK.getDefaultInstance());
        }
    }


}
