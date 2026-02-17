package com.epxzzy.epxzzysabers.entity.custom;

import com.epxzzy.epxzzysabers.epxzzySabers;
import com.epxzzy.epxzzysabers.entity.SaberEntities;
import com.epxzzy.epxzzysabers.item.Protosaber;
import com.epxzzy.epxzzysabers.item.types.RotarySaber;
import com.epxzzy.epxzzysabers.util.ColourConverter;
import com.epxzzy.epxzzysabers.util.SaberTags;
import com.epxzzy.epxzzysabers.util.StackHelper;
import com.epxzzy.epxzzysabers.util.TagHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class DummyThrownRotarySaber extends AbstractArrow {
    private static final EntityDataAccessor<Integer> Decimal_Colour = SynchedEntityData.defineId(DummyThrownRotarySaber.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> Gay = SynchedEntityData.defineId(DummyThrownRotarySaber.class, EntityDataSerializers.BOOLEAN);

    private boolean dealtDamage;
    public int clientSideReturnSaberTickCount;

    public DummyThrownRotarySaber(EntityType<ThrownRotarySaber> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public DummyThrownRotarySaber(Level pLevel, LivingEntity pShooter, ItemStack pStack) {
        super(SaberEntities.ROTARY_SABER_ENTITY.get(), pShooter, pLevel);
        this.entityData.set(Decimal_Colour, RotarySaber.getColor(pStack));
        this.entityData.set(Gay, Protosaber.isGay(pStack));

        epxzzySabers.LOGGER.debug("colour given to thrown saber is:" + RotarySaber.getColor(pStack));

    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(Decimal_Colour, 0);
        this.entityData.define(Gay, false);
    }

    public int[] getColour() {
        //epxzzySabers.LOGGER.debug("colour asked from thrown saber is:" + this.entityData.get(Decimal_Colour));
        //int[] colour = ColourConverter.PortedDecimaltoRGB(this.entityData.get(Decimal_Colour));
        //epxzzySabers.LOGGER.debug("colours have been set as: " +  ColourConverter.PortedDecimaltoRGB(this.entityData.get(Decimal_Colour))[0] + " " +  ColourConverter.PortedDecimaltoRGB(this.entityData.get(Decimal_Colour))[1] + " " +  ColourConverter.PortedDecimaltoRGB(this.entityData.get(Decimal_Colour))[2]);
        if (this.entityData.get(Gay)) {
            return ColourConverter.rainbowColor((int) System.currentTimeMillis() * 2);
        }

        return ColourConverter.PortedDecimaltoRGB(this.entityData.get(Decimal_Colour));
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        Entity entity = pResult.getEntity();
        if(TagHelper.checkActiveSaber(entity, true)){
            ItemStack stacc = ((ServerPlayer) entity).getMainHandItem();
            this.entityData.set(Decimal_Colour, Protosaber.getColor(stacc));
            this.entityData.set(Gay, Protosaber.isGay(stacc));
        }
        //super.onHitEntity(pResult);
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {
        if (this.inGroundTime > 1) {
            this.dealtDamage = true;
        }

        super.tick();
    }

    public void shootFromRotation(Entity pShooter, float pX, float pY, float pZ, float pVelocity, float pInaccuracy) {
        float f = -Mth.sin(pY * ((float) Math.PI / 180F)) * Mth.cos(pX * ((float) Math.PI / 180F));
        float f1 = -Mth.sin((pX + pZ) * ((float) Math.PI / 180F));
        float f2 = Mth.cos(pY * ((float) Math.PI / 180F)) * Mth.cos(pX * ((float) Math.PI / 180F));
        this.shoot((double) f, (double) f1, (double) f2, pVelocity, pInaccuracy);
        Vec3 vec3 = pShooter.getDeltaMovement();
        this.setDeltaMovement(this.getDeltaMovement().add(vec3.x, pShooter.onGround() ? 0.0D : vec3.y, vec3.z));
    }

    private boolean isAcceptibleReturnOwner() {
        Entity entity = this.getOwner();
        if (entity != null && entity.isAlive()) {
            return !(entity instanceof ServerPlayer) || !entity.isSpectator();
        } else {
            return false;
        }
    }

    /**
     * Gets the EntityHitResult representing the entity hit
     */
    @Nullable
    protected EntityHitResult findHitEntity(@NotNull Vec3 pStartVec, @NotNull Vec3 pEndVec) {
        return this.dealtDamage ? null : super.findHitEntity(pStartVec, pEndVec);
    }

    /**
     * Called when the arrow hits an entity
     */
    protected boolean tryPickup(@NotNull Player pPlayer) {
        return super.tryPickup(pPlayer) || this.isNoPhysics() && this.ownedBy(pPlayer) && pPlayer.getInventory().add(this.getPickupItem());
    }

    @Override
    protected ItemStack getPickupItem() {
        return null;
    }

    /**
     * Called by a player entity when they collide with an entity
     */
    public void playerTouch(@NotNull Player pEntity) {
        if (this.ownedBy(pEntity) || this.getOwner() == null) {
            super.playerTouch(pEntity);
        }

    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        this.getOwner().spawnAtLocation(this.getPickupItem());
        return super.hurt(pSource, pAmount);
    }

    @Override
    public void remove(RemovalReason pReason) {
        //this.getOwner().spawnAtLocation(this.getPickupItem());
        super.remove(pReason);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);

        this.dealtDamage = pCompound.getBoolean("DealtDamage");
    }

    public void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
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