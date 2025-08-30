package com.epxzzy.epxzzysabers.utils;

import com.epxzzy.epxzzysabers.entity.custom.ThrownRotarySaber;
import com.epxzzy.epxzzysabers.item.ModItems;
import com.epxzzy.epxzzysabers.item.Protosaber;
import com.epxzzy.epxzzysabers.sound.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

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


    public static boolean checkHoldingActiveTag(Entity Entityy, boolean Mainhand, TagKey<Item> tagg) {
        if (Entityy instanceof LivingEntity) {
            if (Mainhand)
                return ((LivingEntity) Entityy).getMainHandItem().is(tagg) && ((LivingEntity) Entityy).getMainHandItem().getOrCreateTag().getBoolean("ActiveBoiii");
            return ((LivingEntity) Entityy).getOffhandItem().is(tagg) && ((LivingEntity) Entityy).getOffhandItem().getOrCreateTag().getBoolean("ActiveBoiii");
        }
        return false;
    }


    public static int random1to8(int old){
        int random = (int) new Random().nextInt(8) + 1;
        return old != random? random: random1to8(old);
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
        //return entity.getMainHandItem().is(ModTags.Items.LIGHTSABER);
    }

    public static boolean isHoldingItemAnyHand(LivingEntity entity, ItemStack stack) {
        return (isHoldingItemMainHand(entity, stack)) ||
                (isHoldingItemOffHand(entity, stack));
        //return entity.getMainHandItem().is(ModTags.Items.LIGHTSABER);
    }

    public static boolean isHoldingItemOffHand(LivingEntity entity, ItemStack stack) {
        return ItemStack.isSameItemSameTags(entity.getOffhandItem(), stack);
        //return entity.getMainHandItem().is(ModTags.Items.LIGHTSABER);
    }

    public static boolean isHoldingItemMainHand(LivingEntity entity, ItemStack stack) {
        return ItemStack.isSameItemSameTags(entity.getMainHandItem(), stack);
        //return entity.getMainHandItem().is(ModTags.Items.LIGHTSABER);
    }

    public static boolean isLeftArmMain(LivingEntity entity) {
        return ((Player) entity).getMainArm() == HumanoidArm.LEFT;
    }

    public static boolean isCorrectArmLeading(LivingEntity entity, ItemDisplayContext transformType, boolean[] values) {
        boolean OFFHAND = values[1]; // true if offhand has the item
        boolean BOTH_HANDS_USED = values[2]; // true if both hands have the item
        boolean isLeftMain = isLeftArmMain(entity); // true if main hand is left
        boolean isOffhandContext = transformType == ItemDisplayContext.THIRD_PERSON_LEFT_HAND || transformType == ItemDisplayContext.FIRST_PERSON_LEFT_HAND;
        boolean isMainhandContext = transformType == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND || transformType == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND;

        // Determine which hand is offhand and mainhand based on handedness
        boolean offhandIsLeft = !isLeftMain; // Offhand is left if main hand is right
        boolean mainhandIsLeft = isLeftMain;

        // Case 1: Both hands have the item (offhand priority)
        if (BOTH_HANDS_USED) {
            return (offhandIsLeft) || (!offhandIsLeft);
        }

        // Case 2: Only offhand has the item
        if (OFFHAND && !BOTH_HANDS_USED) {
            return (offhandIsLeft) || (!offhandIsLeft);
        }

        // Case 3: Offhand is empty, mainhand has the item (implied when OFFHAND is false)
        if (!OFFHAND && !BOTH_HANDS_USED) {
            // Assume item is in mainhand if OFFHAND is false and item is present
            return (mainhandIsLeft) || (!mainhandIsLeft);
        }

        // Case 4: No item in either hand (or invalid state)
        return false;
    }

    public static boolean isMainHandDisplayContext(LivingEntity entity, ItemDisplayContext transformType) {
        return !isLeftArmMain(entity) && transformType == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
    }


}
