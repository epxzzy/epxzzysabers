package com.epxzzy.epxzzysabers.networking.handler;

import com.epxzzy.epxzzysabers.entity.custom.PlasmaBolt;
import com.epxzzy.epxzzysabers.entity.custom.ThrownRotarySaber;
import com.epxzzy.epxzzysabers.epxzzySabers;
import com.epxzzy.epxzzysabers.item.Protosaber;
import com.epxzzy.epxzzysabers.item.SaberItems;
import com.epxzzy.epxzzysabers.sound.SaberSounds;
import com.epxzzy.epxzzysabers.util.LevelHelper;
import com.epxzzy.epxzzysabers.util.SaberTags;
import com.epxzzy.epxzzysabers.util.StackHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

public class SaberPacketHandler {

    public static void handleDeflectPacket(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context contextt = supplier.get();

        if (contextt.getSender() != null) {
            ServerPlayer player = contextt.getSender();
            Level pLevel = player.level();
            ItemStack pStack = player.getItemInHand(InteractionHand.MAIN_HAND);
            float PARRY_RANGE = Protosaber.getSaberParryRange(pStack);
            //epxzzySabers.LOGGER.info("parry range for this deflection packet {}", PARRY_RANGE);

            Vec3 asdf = player.position();
            List<Projectile> notThat = LevelHelper.getProjectilesInRadius(asdf, pLevel, PARRY_RANGE);


            //if (!player.level().isClientSide()) {
            if (!notThat.isEmpty() && StackHelper.isActive(pStack)) {
                for (Entity entity : notThat) {
                    Vec3 vec32 = entity.position();
                    double speee = entity.getDeltaMovement().length();
                    Vec3 vec3 = player.getViewVector(1.0F);
                    Vec3 vec31 = vec32.vectorTo(player.position()).normalize();
                    vec31 = new Vec3(vec31.x, vec31.y, vec31.z);
                    if (vec31.dot(vec3) < 0.4D && speee > -2.0D) {
                        //epxzzySabers.LOGGER.debug("oh look what do we have here?");
                        //epxzzySabers.LOGGER.debug("is on ground: " + SupposedProjectile.onGround() + " and is decending? " + SupposedProjectile.isDescending());
                        //epxzzySabers.LOGGER.debug("avrg speed is " + (speee));

                        //epxzzySabers.LOGGER.debug("GET DEFELECTED IDIOT");


                        if (entity instanceof Projectile SupposedProjectile) {
                            //epxzzySabers.LOGGER.debug("its a projectile???");

                            if (SupposedProjectile instanceof ThrownRotarySaber SupposedSaber) {
                                epxzzySabers.LOGGER.debug("deflected a thrown lightsaber");
                                //thrown saber does sometimes gets caught as an abstract arrow for some unknown reason
                                SupposedSaber.shootFromRotationSaber(player, player.getXRot(), player.getYRot(), 0.0F, 0.5F + (1f * SupposedSaber.getReturnMultiplier()), 1.0F);
                                SupposedSaber.level().playSound((Player) null, player.blockPosition(), SaberSounds.CLASH.get(), SoundSource.PLAYERS, 0.5f, 1f);

                                continue;
                            }

                            if (SupposedProjectile instanceof AbstractArrow SupposedArrow && !SupposedArrow.inGround ) {
                                epxzzySabers.LOGGER.debug("deflected an ordinary arrow");
                                SupposedArrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
                                player.level().playSound((Player) null, player.blockPosition(), SaberSounds.CLASH.get(), SoundSource.PLAYERS, 0.5f, 1f);

                                continue;
                            }

                            //Vec3 pos = player.getLookAngle();
                            //entity1.setDeltaMovement(pos);
                            //entity1.setDeltaMovement(vec3);
                            if (SupposedProjectile instanceof AbstractHurtingProjectile) {
                                epxzzySabers.LOGGER.debug("deflected a projectile");

                                ((AbstractHurtingProjectile) SupposedProjectile).xPower = vec3.x * 0.1D;
                                ((AbstractHurtingProjectile) SupposedProjectile).yPower = vec3.y * 0.1D;
                                ((AbstractHurtingProjectile) SupposedProjectile).zPower = vec3.z * 0.1D;

                                Vec3 pos = player.getLookAngle();
                                SupposedProjectile.setDeltaMovement(pos);
                                SupposedProjectile.setDeltaMovement(vec3);
                                ((AbstractHurtingProjectile) SupposedProjectile).setOwner(player);

                                player.level().playSound((Player) null, player.blockPosition(), SaberSounds.CLASH.get(), SoundSource.PLAYERS, 0.5f, 1f);
                            }


                            //entity1.setDeltaMovement(entity1.getDeltaMovement().scale(-10));
                            //entity1.setDeltaMovement(entity1.getDeltaMovement().reverse());
                            //entity1.moveTo(entity1.getDeltaMovement());
                            //entity1.setXRot(entity.getXRot());
                            //entity1.setYRot(entity.getYRot());
                        }
                    }
                }
                //epxzzySabers.LOGGER.debug("wait was that all of them? dam thats sad");
            }
        }
    }

    public static void handleAbilityPacket(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context contextt = supplier.get();

        if (contextt.getSender() != null) {
            ServerPlayer player = contextt.getSender();
            Level pLevel = player.level();
            ItemStack pStack = player.getItemInHand(InteractionHand.MAIN_HAND);

            if (pStack.is(SaberItems.ROTARY_SABER.get()) && StackHelper.isActive(pStack)) {
                if (!(player.getCooldowns().isOnCooldown(pStack.getItem()))) {
                    player.getCooldowns().addCooldown(pStack.getItem(), 80);
                    player.stopUsingItem();


                    ThrownRotarySaber thrownsaber = new ThrownRotarySaber(pLevel, player, pStack);
                    //epxzzySabers.LOGGER.debug("colour given is:" + RotarySaber.getColor(pStack));
                    thrownsaber.shootFromRotationSaber(player, player.getXRot(), player.getYRot(), 0.0F, 2.5F + (float) 4 * 0.5F, 1.0F);
                    if (player.getAbilities().instabuild) {
                        //thrownsaber.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                    }
                    player.swing(InteractionHand.MAIN_HAND, true);
                    pLevel.addFreshEntity(thrownsaber);
                    pLevel.playSound((Player) null, thrownsaber, SaberSounds.ACTIVATION.get(), SoundSource.PLAYERS, 0.05F, 1.0F);
                    if (!player.getAbilities().instabuild) {
                        player.getInventory().removeItem(pStack);
                    }
                }
            }

            if (pStack.is(SaberItems.BLASTER_SABER.get())) {
                if (!(player.getCooldowns().isOnCooldown(pStack.getItem()))) {
                    player.getCooldowns().addCooldown(pStack.getItem(), 6);
                    player.stopUsingItem();
                    //player.swing(InteractionHand.MAIN_HAND, true);

                    PlasmaBolt blasterbolt = new PlasmaBolt(player, pLevel);

                    blasterbolt.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F + 0.5F, 1.0F);
                    pLevel.addFreshEntity(blasterbolt);
                    pLevel.playSound((Player) null, blasterbolt, SoundEvents.BUBBLE_COLUMN_UPWARDS_INSIDE, SoundSource.PLAYERS, 0.05F, 1.0F);
                }
            }

            if (pStack.is(SaberItems.SABER_GAUNTLET.get()) && StackHelper.isActive(pStack)) {
                if (!(player.getCooldowns().isOnCooldown(pStack.getItem()))) {
                    player.getCooldowns().addCooldown(pStack.getItem(), 60);

                    pLevel.playSound((Player) null, player.blockPosition(), SoundEvents.BELL_RESONATE, SoundSource.PLAYERS, 0.5F, 2);

                    //set appropriate abilty

                    List<LivingEntity> tities = LevelHelper.getEntitiesInRadius(player.position(), pLevel, 16);

                    tities.removeIf(tit -> tit == player);
                    tities.removeIf(tit -> tit.getMainHandItem().is(SaberTags.Items.LIGHTSABER) && !tit.getMainHandItem().is(SaberItems.SABER_GAUNTLET.get()));

                    if (!tities.isEmpty() && pStack.getOrCreateTag().getBoolean("ActiveBoiii")) {
                        for (LivingEntity thisSpecficTity : tities) {
                            if (thisSpecficTity instanceof Player playertity) {
                                if (thisSpecficTity.getMainHandItem().is(SaberItems.ROTARY_SABER.get())) {

                                    thisSpecficTity.stopUsingItem();
                                    playertity.getCooldowns().addCooldown(playertity.getMainHandItem().getItem(), 80);


                                    playertity.level().playSound((Player) null, playertity.blockPosition(), SaberSounds.DEACTIVATION.get(), SoundSource.PLAYERS, 0.5F, 2);
                                } else {
                                    playertity.getCooldowns().addCooldown(playertity.getMainHandItem().getItem(), 20);

                                    playertity.level().playSound((Player) null, playertity.blockPosition(), SaberSounds.DEACTIVATION.get(), SoundSource.PLAYERS, 0.5F, 1);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}