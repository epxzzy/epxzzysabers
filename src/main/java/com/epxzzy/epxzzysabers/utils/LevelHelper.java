package com.epxzzy.epxzzysabers.utils;

import com.epxzzy.epxzzysabers.entity.custom.ThrownRotarySaber;
import com.epxzzy.epxzzysabers.item.ModItems;
import com.epxzzy.epxzzysabers.item.Protosaber;
import com.epxzzy.epxzzysabers.sound.ModSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class LevelHelper {

    public static boolean EntityEquippedActiveItem(Entity Entityy, boolean Mainhand, Item item){
        if(Entityy instanceof LivingEntity) {
            if(Mainhand) return ((LivingEntity) Entityy).getMainHandItem().is(item) && ((LivingEntity) Entityy).getMainHandItem().getOrCreateTag().getBoolean("ActiveBoiii");
            return ((LivingEntity) Entityy).getOffhandItem().is(item) && ((LivingEntity) Entityy).getOffhandItem().getOrCreateTag().getBoolean("ActiveBoiii");
        }
        return false;
    }

    public static boolean EntityBlockingWithActiveItem(Entity Entityy, Item item){
        if(Entityy instanceof LivingEntity)
            return ((LivingEntity)Entityy).getMainHandItem().is(item) && ((LivingEntity) Entityy).getMainHandItem().getOrCreateTag().getBoolean("ActiveBoiii") && ((LivingEntity)Entityy).isUsingItem();
        return false;
    }

    public static void AnimateDefelctionClient(ItemStack pStack, Player entity) {
        Level pLevel = entity.level();
        int PARRY_RANGE = Protosaber.getSaberParryRange(pStack);
        //epxzzySabers.LOGGER.info("parry range for this deflection packet {}", PARRY_RANGE);

        if (pLevel.isClientSide) {
            Vec3 asdf = entity.position();
            List<Projectile> notThat = LevelHelper.getProjectilesInRadius(asdf, pLevel, PARRY_RANGE);

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
                                //epxzzySabers.LOGGER.debug("deflected an ordinary arrow");
                                ((AbstractArrow) entity1).shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 1.5F, 1.0F);
                                entity.level().playSound((Player) null, entity.blockPosition(), ModSounds.CLASH.get(), SoundSource.PLAYERS, 0.5f, 1f);

                                continue;
                            }
                            if (ThrownRotarySaber.class.isAssignableFrom(entity1.getClass())) {
                                //epxzzySabers.LOGGER.debug("deflected a thrown lightsaber");
                                ((AbstractArrow) entity1).shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 2.0F, 1.0F);
                                entity.level().playSound((Player) null, entity.blockPosition(), ModSounds.CLASH.get(), SoundSource.PLAYERS, 0.5f, 1f);

                                continue;
                            }


                            if (AbstractHurtingProjectile.class.isAssignableFrom(entity1.getClass())) {
                                //epxzzySabers.LOGGER.debug("deflected a projectile");

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


    public static List<LivingEntity> getEntitiesInRadius(Vec3 poss, Level level, double radius) {
        List<LivingEntity> entities = level.getEntitiesOfClass(
                LivingEntity.class,
                new AABB(
                        poss.x - radius, poss.y - radius, poss.z - radius,
                        poss.x + radius, poss.y + radius, poss.z + radius
                )
        );

        // remove if not in radious
        entities.removeIf(moron -> poss.distanceTo(moron.position()) >= radius);

        return entities;
    }

    public static List<Projectile> getProjectilesInRadius(Vec3 poss, Level level, double radius) {
        List<Projectile> entities = level.getEntitiesOfClass(
                Projectile.class,
                new AABB(
                        poss.x - radius, poss.y - radius, poss.z - radius,
                        poss.x + radius, poss.y + radius, poss.z + radius
                )
        );

        // remove if not in radious
        entities.removeIf(moron -> poss.distanceTo(moron.position()) >= radius);

        return entities;
    }

}
