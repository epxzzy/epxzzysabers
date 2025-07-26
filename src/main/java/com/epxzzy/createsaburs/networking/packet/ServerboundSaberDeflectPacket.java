package com.epxzzy.createsaburs.networking.packet;

import com.epxzzy.createsaburs.createsaburs;
import com.epxzzy.createsaburs.entity.custom.PlasmaBolt;
import com.epxzzy.createsaburs.entity.custom.ThrownRotarySaber;
import com.epxzzy.createsaburs.item.ModItems;
import com.epxzzy.createsaburs.item.Protosaber;
import com.epxzzy.createsaburs.item.saburtypes.RotarySaber;
import com.epxzzy.createsaburs.sound.ModSounds;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ServerboundSaberDeflectPacket {
    public ServerboundSaberDeflectPacket() {
    }


    public ServerboundSaberDeflectPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        //createsaburs.LOGGER.info("message reciveved");
        NetworkEvent.Context contextt = supplier.get();
        contextt.enqueueWork(() -> {
            //createsaburs.LOGGER.info("message processed");

            //createsaburs.LOGGER.info(Objects.requireNonNull(contextt.getSender())+" named bond having a stonk");

            if (contextt.getSender() != null) {
                ServerPlayer entity = contextt.getSender();
                Level pLevel = entity.level();
                ItemStack pStack = entity.getItemInHand(InteractionHand.MAIN_HAND);
                int PARRY_RANGE = Protosaber.getSaberParryRange(pStack);
                //createsaburs.LOGGER.info("parry range for this deflection packet {}", PARRY_RANGE);


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
                            //createsaburs.LOGGER.warn("PLAYUR???? NAHHHHH!!");
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
                            //createsaburs.LOGGER.warn("oh look what do we have here?");
                            //createsaburs.LOGGER.warn("is on ground: " + entity1.onGround() + " and is decending? " + entity1.isDescending());
                            //createsaburs.LOGGER.warn("avrg speed is " + (speee));

                            //createsaburs.LOGGER.warn("GET DEFELECTED IDIOT");


                            if (Projectile.class.isAssignableFrom(entity1.getClass())) {
                                //createsaburs.LOGGER.warn("its a projectile???");

                                if (AbstractArrow.class.isAssignableFrom(entity1.getClass()) && !((AbstractArrow) entity1).inGround ) {
                                    //createsaburs.LOGGER.warn("deflected an ordinary arrow");
                                    ((AbstractArrow) entity1).shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 2.0F, 1.0F);
                                    entity.level().playSound((Player) null, entity.blockPosition(), ModSounds.CLASH.get(), SoundSource.PLAYERS, 0.5f, 1f);

                                    continue;
                                }
                                if (ThrownRotarySaber.class.isAssignableFrom(entity1.getClass())) {
                                    //createsaburs.LOGGER.warn("deflected a thrown lightsaber");
                                    ((AbstractArrow) entity1).shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 2.0F, 1.0F);
                                    entity.level().playSound((Player) null, entity.blockPosition(), ModSounds.CLASH.get(), SoundSource.PLAYERS, 0.5f, 1f);

                                    continue;
                                }


                                //Vec3 pos = entity.getLookAngle();
                                //entity1.setDeltaMovement(pos);
                                //entity1.setDeltaMovement(vec3);
                                if (AbstractHurtingProjectile.class.isAssignableFrom(entity1.getClass())) {
                                    //createsaburs.LOGGER.warn("deflected a projectile");

                                    ((AbstractHurtingProjectile) entity1).xPower = vec3.x * 0.1D;
                                    ((AbstractHurtingProjectile) entity1).yPower = vec3.y * 0.1D;
                                    ((AbstractHurtingProjectile) entity1).zPower = vec3.z * 0.1D;

                                    Vec3 pos = entity.getLookAngle();
                                    entity1.setDeltaMovement(pos);
                                    entity1.setDeltaMovement(vec3);
                                    ((AbstractHurtingProjectile) entity1).setOwner(entity);

                                    entity.level().playSound((Player) null, entity.blockPosition(), ModSounds.CLASH.get(), SoundSource.PLAYERS, 0.5f, 1f);
                                }


                                //entity1.setDeltaMovement(entity1.getDeltaMovement().scale(-10));
                                //entity1.setDeltaMovement(entity1.getDeltaMovement().reverse());
                                //entity1.moveTo(entity1.getDeltaMovement());
                                //entity1.setXRot(entity.getXRot());
                                //entity1.setYRot(entity.getYRot());
                            }
                        }
                    }
                    //createsaburs.LOGGER.warn("wait was that all of them? dam thats sad");
                } else if (pStack.getOrCreateTag().getBoolean("ActiveBoiii")) {
                    entity.level().playSound((Player) null, entity.blockPosition(), ModSounds.SWING.get(), SoundSource.PLAYERS, 0.1F, 0.8F + entity.level().random.nextFloat() * 0.4F);

                }

            }
        });
        return true;
    }

}
