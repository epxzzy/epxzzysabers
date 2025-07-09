package com.epxzzy.createsaburs.entity.custom;

import com.epxzzy.createsaburs.entity.ModEntities;
import com.epxzzy.createsaburs.item.ModItems;
import com.epxzzy.createsaburs.misc.ColourConverter;
import com.epxzzy.createsaburs.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class ThrownRotarySaber extends AbstractArrow {
    private ItemStack saberitem = new ItemStack(ModItems.ROTARY_SABER.get());
    private boolean dealtDamage;
    public int clientSideReturnTridentTickCount;

    public ThrownRotarySaber(EntityType<ThrownRotarySaber> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ThrownRotarySaber(Level pLevel, LivingEntity pShooter, ItemStack pStack) {
        super(ModEntities.ROTARY_SABER_ENTITY.get(), pShooter, pLevel);
        this.saberitem= pStack.copy();
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
    }

    public int[] getColour(){
        return ColourConverter.PortedDecimaltoRGB(this.saberitem.getOrCreateTag().getCompound("display").getInt("color"));
    };


    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {
        if (this.inGroundTime > 2) {
            this.dealtDamage = true;
        }
        //this.playSound(ModSounds.SWING.get(), 0.2f, 1.0f);
        Entity entity = this.getOwner();
        if ((this.dealtDamage || this.isNoPhysics()) && entity != null) {
            if (!this.isAcceptibleReturnOwner()) {
                if (!this.level().isClientSide && this.pickup == Pickup.ALLOWED) {
                    //this.spawnAtLocation(this.getPickupItem(), 0.1F);
                }

                //this.discard();
            } else {
                this.setNoPhysics(true);
                Vec3 vec3 = entity.getEyePosition().subtract(this.position());
                this.setPosRaw(this.getX(), this.getY() + vec3.y * 0.015D * 3, this.getZ());
                if (this.level().isClientSide) {
                    this.yOld = this.getY();
                }

                double d0 = 0.05D * 4;
                this.setDeltaMovement(this.getDeltaMovement().scale(0.95D).add(vec3.normalize().scale(d0)));
                if (this.clientSideReturnTridentTickCount == 0) {
                    this.playSound(ModSounds.CLASH.get(), 5.0F, 1.0F);
                }

                ++this.clientSideReturnTridentTickCount;
            }
        }
        if (entity == null){
            //this.spawnAtLocation(this.getPickupItem(), 0.1F);
            //this.discard();
        }

        super.tick();
    }

    private boolean isAcceptibleReturnOwner() {
        Entity entity = this.getOwner();
        if (entity != null && entity.isAlive()) {
            return !(entity instanceof ServerPlayer) || !entity.isSpectator();
        } else {
            return false;
        }
    }

    protected ItemStack getPickupItem() {
        return this.saberitem.copy();
    }

    public boolean isFoil() {
        return false;
    }

    /**
     * Gets the EntityHitResult representing the entity hit
     */
    @Nullable
    protected EntityHitResult findHitEntity(Vec3 pStartVec, Vec3 pEndVec) {
        return this.dealtDamage ? null : super.findHitEntity(pStartVec, pEndVec);
    }

    /**
     * Called when the arrow hits an entity
     */
    protected void onHitEntity(EntityHitResult pResult) {
        Entity entity = pResult.getEntity();
        float f = 12.0F;

        Entity entity1 = this.getOwner();
        DamageSource dmgsrc = this.damageSources().generic();

        this.dealtDamage = true;
        SoundEvent soundevent = SoundEvents.TRIDENT_HIT;
        if (entity.hurt(dmgsrc, f)) {
            if (entity.getType() == EntityType.ENDERMAN) {
                return;
            }

            if (entity instanceof LivingEntity) {
                LivingEntity livingentity1 = (LivingEntity)entity;

                this.doPostHurtEffects(livingentity1);
            }
        }

        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01D, -0.1D, -0.01D));
        float f1 = 1.0F;
        if (this.level() instanceof ServerLevel && this.level().isThundering()) {
            BlockPos blockpos = entity.blockPosition();
            if (this.level().canSeeSky(blockpos)) {
                LightningBolt lightningbolt = EntityType.LIGHTNING_BOLT.create(this.level());
                if (lightningbolt != null) {
                    lightningbolt.moveTo(Vec3.atBottomCenterOf(blockpos));
                    lightningbolt.setCause(entity1 instanceof ServerPlayer ? (ServerPlayer)entity1 : null);
                    this.level().addFreshEntity(lightningbolt);
                    soundevent = SoundEvents.TRIDENT_THUNDER;
                    f1 = 5.0F;
                }
            }
        }

        this.playSound(soundevent, f1, 1.0F);
    }

    protected boolean tryPickup(Player pPlayer) {
        return super.tryPickup(pPlayer) || this.isNoPhysics() && this.ownedBy(pPlayer) && pPlayer.getInventory().add(this.getPickupItem());
    }

    /**
     * The sound made when an entity is hit by this projectile
     */
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return ModSounds.CLASH.get();
    }

    /**
     * Called by a player entity when they collide with an entity
     */
    public void playerTouch(Player pEntity) {
        if (this.ownedBy(pEntity) || this.getOwner() == null) {
            super.playerTouch(pEntity);
        }

    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("Trident", 10)) {
            this.saberitem = ItemStack.of(pCompound.getCompound("RotarySaber"));
        }

        this.dealtDamage = pCompound.getBoolean("DealtDamage");
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.put("RotarySaber", this.saberitem.save(new CompoundTag()));
        pCompound.putBoolean("DealtDamage", this.dealtDamage);
    }

    public void tickDespawn() {
        if (this.pickup != Pickup.ALLOWED) {
            super.tickDespawn();
        }

    }

    protected float getWaterInertia() {
        return 0.99F;
    }

    public boolean shouldRender(double pX, double pY, double pZ) {
        return true;
    }
}