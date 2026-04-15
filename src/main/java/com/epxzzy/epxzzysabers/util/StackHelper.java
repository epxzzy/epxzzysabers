package com.epxzzy.epxzzysabers.util;

import net.minecraft.client.Minecraft;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StackHelper {
    public static List<LivingEntity> getEntitiesHoldingItemRightOrBoth(ItemStack stack) {
        List<LivingEntity> result = new ArrayList<>();
        Minecraft mc = Minecraft.getInstance();

        Player player = mc.player;
        if (player != null && isHoldingItemMainOrBoth(player, stack)) {
            result.add(player);
        }

        // Check other entities in the loaded world
        if (mc.level != null) {
            for (Entity entity : mc.level.entitiesForRendering()) {
                if (entity instanceof LivingEntity livingEntity) {
                    // skippity
                    if (livingEntity == player) continue;

                    if (isHoldingItemMainOrBoth(livingEntity, stack)) {
                        result.add(livingEntity);
                    }
                }
            }
        }

        return result;
    }
    public static List<LivingEntity> getPlayersHoldingItemRightOrBoth(ItemStack stack) {
        List<LivingEntity> result = new ArrayList<>();
        Minecraft mc = Minecraft.getInstance();

        Player player = mc.player;
        if (player != null && isHoldingItemMainOrBoth(player, stack)) {
            result.add(player);
        }

        // Check other entities in the loaded world
        if (mc.level != null) {
            for (Entity entity : mc.level.entitiesForRendering()) {
                if (entity instanceof Player livingEntity) {
                    // skippity
                    if (livingEntity == player) continue;

                    if (isHoldingItemMainOrBoth(livingEntity, stack)) {
                        result.add(livingEntity);
                    }
                }
            }
        }

        return result;
    }


    public static int random1to8(int old, Player pPlayer){
        int random = pPlayer.getRandom().nextInt(8) + 1;
        return old != random? random: random+1;
    };

    public static List<LivingEntity> getEntitiesHoldingItemAnyHand(ItemStack stack) {
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

    public static boolean isHoldingItemMainOrBoth(LivingEntity entity, ItemStack stack) {
        return (ItemStack.isSameItemSameTags(entity.getMainHandItem(), stack)
                && ItemStack.isSameItemSameTags(entity.getOffhandItem(), stack)) || ItemStack.isSameItemSameTags(entity.getMainHandItem(), stack);
        //return entity.getMainHandItem().is(SaberTags.Items.LIGHTSABER);
    }

    public static boolean isHoldingItemAnyHand(LivingEntity entity, ItemStack stack) {
        return (isHoldingItemMainHand(entity, stack)) ||
                (isHoldingItemOffHand(entity, stack));
        //return entity.getMainHandItem().is(SaberTags.Items.LIGHTSABER);
    }

    public static boolean isHoldingItemOffHand(LivingEntity entity, ItemStack stack) {
        return ItemStack.isSameItemSameTags(entity.getOffhandItem(), stack);
        //return entity.getMainHandItem().is(SaberTags.Items.LIGHTSABER);
    }

    public static boolean isHoldingItemMainHand(LivingEntity entity, ItemStack stack) {
        return ItemStack.isSameItemSameTags(entity.getMainHandItem(), stack);
        //return entity.getMainHandItem().is(SaberTags.Items.LIGHTSABER);
    }

    public static boolean isFlying(ItemStack stacc) {
        return stacc.getOrCreateTag().getBoolean("FlyBoiii");
    }


}
