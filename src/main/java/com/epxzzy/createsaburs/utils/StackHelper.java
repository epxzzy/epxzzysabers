package com.epxzzy.createsaburs.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class StackHelper {
    public static List<LivingEntity> getEntitiesHoldingItem(ItemStack stack) {
        List<LivingEntity> result = new ArrayList<>();
        Minecraft mc = Minecraft.getInstance();

        Player player = mc.player;
        if (player != null && isHoldingItemAnyHand(player, stack)) {
            result.add(player);
        }

        // Check other entities in the loaded world
        if (mc.level != null) {
            for (Entity entity : mc.level.entitiesForRendering()) {
                if (entity instanceof LivingEntity livingEntity) {
                    // skippity
                    if (livingEntity == player) continue;

                    if (isHoldingItemAnyHand(livingEntity, stack)) {
                        result.add(livingEntity);
                    }
                }
            }
        }

        return result;
    }

    public static boolean isHoldingItemAnyHand(LivingEntity entity, ItemStack stack) {
        return (ItemStack.isSameItemSameTags(entity.getMainHandItem(), stack) && ItemStack.isSameItemSameTags(entity.getOffhandItem(), stack)) || ItemStack.isSameItemSameTags(entity.getMainHandItem(), stack);
        //return entity.getMainHandItem().is(ModTags.Items.CREATE_LIGHTSABER);
    }
    public static boolean isHoldingItemOffHand(LivingEntity entity, ItemStack stack) {
        return ItemStack.isSameItemSameTags(entity.getOffhandItem(), stack);
        //return entity.getMainHandItem().is(ModTags.Items.CREATE_LIGHTSABER);
    }
    public static boolean isHoldingItemMainHand(LivingEntity entity, ItemStack stack) {
        return ItemStack.isSameItemSameTags(entity.getMainHandItem(), stack);
        //return entity.getMainHandItem().is(ModTags.Items.CREATE_LIGHTSABER);
    }


}
