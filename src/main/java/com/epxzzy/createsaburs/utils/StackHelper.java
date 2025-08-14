package com.epxzzy.createsaburs.utils;

import com.epxzzy.createsaburs.entity.custom.ThrownRotarySaber;
import com.epxzzy.createsaburs.item.Protosaber;
import com.epxzzy.createsaburs.sound.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
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

    public static void AnimateDefelctionClient(ItemStack pStack, Player entity) {
        Level pLevel = entity.level();
        int PARRY_RANGE = Protosaber.getSaberParryRange(pStack);
        //CreateSaburs.LOGGER.info("parry range for this deflection packet {}", PARRY_RANGE);

        if (pLevel.isClientSide) {
            Vec3 asdf = entity.blockPosition().getCenter();
            List<Entity> notThat = entity.level().getEntities(null, new AABB(
                    asdf.x - PARRY_RANGE,
                    asdf.y - PARRY_RANGE,
                    asdf.z - PARRY_RANGE,
                    asdf.x + PARRY_RANGE,
                    asdf.y + PARRY_RANGE,
                    asdf.z + PARRY_RANGE)
            );
            notThat.removeIf(new Predicate<Entity>() {
                @Override
                public boolean test(Entity entity) {
                    if (entity instanceof Player) {
                        return true;
                    }
                    return false;
                }
            });
            //if (!entity.level().isClientSide()) {
            if (!notThat.isEmpty() && pStack.getOrCreateTag().getBoolean("ActiveBoiii")) {
                for (Entity entity1 : notThat) {
                    Vec3 vec32 = entity1.position();
                    double speee = entity1.getDeltaMovement().length();
                    Vec3 vec3 = entity.getViewVector(1.0F);
                    Vec3 vec31 = vec32.vectorTo(entity.position()).normalize();
                    vec31 = new Vec3(vec31.x, vec31.y, vec31.z);
                    if (vec31.dot(vec3) < 0.4D && speee > -2.0D) {

                        if (Projectile.class.isAssignableFrom(entity1.getClass())) {

                            if (AbstractArrow.class.isAssignableFrom(entity1.getClass()) && !((AbstractArrow) entity1).inGround) {
                                //CreateSaburs.LOGGER.debug("deflected an ordinary arrow");
                                ((AbstractArrow) entity1).shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 1.5F, 1.0F);
                                entity.level().playSound((Player) null, entity.blockPosition(), ModSounds.CLASH.get(), SoundSource.PLAYERS, 0.5f, 1f);

                                continue;
                            }
                            if (ThrownRotarySaber.class.isAssignableFrom(entity1.getClass())) {
                                //CreateSaburs.LOGGER.debug("deflected a thrown lightsaber");
                                ((AbstractArrow) entity1).shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 2.0F, 1.0F);
                                entity.level().playSound((Player) null, entity.blockPosition(), ModSounds.CLASH.get(), SoundSource.PLAYERS, 0.5f, 1f);

                                continue;
                            }


                            if (AbstractHurtingProjectile.class.isAssignableFrom(entity1.getClass())) {
                                //CreateSaburs.LOGGER.debug("deflected a projectile");

                                ((AbstractHurtingProjectile) entity1).xPower = vec3.x * 0.1D;
                                ((AbstractHurtingProjectile) entity1).yPower = vec3.y * 0.1D;
                                ((AbstractHurtingProjectile) entity1).zPower = vec3.z * 0.1D;

                                Vec3 pos = entity.getLookAngle();
                                entity1.setDeltaMovement(pos);
                                entity1.setDeltaMovement(vec3);
                                ((AbstractHurtingProjectile) entity1).setOwner(entity);

                                entity.level().playSound((Player) null, entity.blockPosition(), ModSounds.CLASH.get(), SoundSource.PLAYERS, 0.5f, 1f);
                            }
                        }
                    }
                }
            }
        }
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
