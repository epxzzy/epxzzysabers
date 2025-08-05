package com.epxzzy.createsaburs.entity.custom;

import com.epxzzy.createsaburs.CreateSaburs;
import com.epxzzy.createsaburs.entity.ModEntities;
import com.epxzzy.createsaburs.item.ModItems;
import com.epxzzy.createsaburs.item.Protosaber;
import com.epxzzy.createsaburs.item.saburtypes.RotarySaber;
import com.epxzzy.createsaburs.utils.ColourConverter;
import com.epxzzy.createsaburs.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
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
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class ThrownRotarySaber extends AbstractArrow {
    private ItemStack saberitem = new ItemStack(ModItems.ROTARY_SABER.get());
    private static final EntityDataAccessor<Integer> Decimal_Colour= SynchedEntityData.defineId(ThrownRotarySaber.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> Gay = SynchedEntityData.defineId(ThrownRotarySaber.class, EntityDataSerializers.BOOLEAN);

    private boolean dealtDamage;
    public int clientSideReturnSaberTickCount;

    public ThrownRotarySaber(EntityType<ThrownRotarySaber> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ThrownRotarySaber(Level pLevel, LivingEntity pShooter, ItemStack pStack) {
        super(ModEntities.ROTARY_SABER_ENTITY.get(), pShooter, pLevel);
        this.saberitem= pStack;
        this.entityData.set(Decimal_Colour,RotarySaber.getColor(pStack));
        this.entityData.set(Gay, Protosaber.isGay(pStack));

        CreateSaburs.LOGGER.warn("colour given to thrown saber is:" + RotarySaber.getColor(pStack));

    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(Decimal_Colour, 0);
        this.entityData.define(Gay, false);
    }

    public int[] getColour(){
        //CreateSaburs.LOGGER.warn("colour asked from thrown saber is:" + this.entityData.get(Decimal_Colour));
        //int[] colour = ColourConverter.PortedDecimaltoRGB(this.entityData.get(Decimal_Colour));
        //CreateSaburs.LOGGER.warn("colours have been set as: " +  ColourConverter.PortedDecimaltoRGB(this.entityData.get(Decimal_Colour))[0] + " " +  ColourConverter.PortedDecimaltoRGB(this.entityData.get(Decimal_Colour))[1] + " " +  ColourConverter.PortedDecimaltoRGB(this.entityData.get(Decimal_Colour))[2]);
        if(this.entityData.get(Gay)){
            return ColourConverter.rainbowColor((int) System.currentTimeMillis() * 2 );
        }

        return ColourConverter.PortedDecimaltoRGB(this.entityData.get(Decimal_Colour)) ;
    };


    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {
        if (this.inGroundTime > 1) {
            this.dealtDamage = true;
        }
        this.playSound(ModSounds.SWING.get(), 0.05f, 1.0f);
        Entity entity = this.getOwner();
        if ((this.dealtDamage || this.isNoPhysics()) && entity != null) {
            if (!this.isAcceptibleReturnOwner()) {

                if (!this.level().isClientSide && this.pickup == Pickup.ALLOWED) {
                    //this.spawnAtLocation(this.getPickupItem(), 0.1F);
                    //this.saberitem.get
                }

                //this.discard();
            } else {
                this.setNoPhysics(true);
                Vec3 vec3 = entity.getEyePosition().subtract(this.position());
                this.setPosRaw(this.getX(), this.getY() + vec3.y * 0.015D * 3, this.getZ());
                if (this.level().isClientSide) {
                    this.yOld = this.getY();
                }

                double d0 = 0.05D * 10;
                this.setDeltaMovement(this.getDeltaMovement().scale(0.95D).add(vec3.normalize().scale(d0)));
                if (this.clientSideReturnSaberTickCount == 0) {
                    this.playSound(ModSounds.CLASH.get(), 0.05f, 1.0f);
                }

                ++this.clientSideReturnSaberTickCount;
            }
        }
        if (entity == null){
            this.spawnAtLocation(this.getPickupItem(), 0.1F);
            this.discard();
        }

        super.tick();
    }

    public void shootFromRotation(Entity pShooter, float pX, float pY, float pZ, float pVelocity, float pInaccuracy) {
        float f = -Mth.sin(pY * ((float)Math.PI / 180F)) * Mth.cos(pX * ((float)Math.PI / 180F));
        float f1 = -Mth.sin((pX + pZ) * ((float)Math.PI / 180F));
        float f2 = Mth.cos(pY * ((float)Math.PI / 180F)) * Mth.cos(pX * ((float)Math.PI / 180F));
        this.shoot((double)f, (double)f1, (double)f2, pVelocity, pInaccuracy);
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

    protected @NotNull ItemStack getPickupItem() {
        return this.saberitem.copy();
    }

    public boolean isFoil() {
        return false;
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
    protected void onHitEntity(EntityHitResult pResult) {
        Entity entity = pResult.getEntity();
        float f = 12.0F;

        Entity entity1 = this.getOwner();
        DamageSource dmgsrc = this.damageSources().generic();

        this.dealtDamage = true;
        SoundEvent soundevent = ModSounds.CLASH.get();
        if (entity.hurt(dmgsrc, f)) {
            if (entity.getType() == EntityType.ENDERMAN) {
                return;
            }

            if (entity instanceof LivingEntity livingentity1) {

                this.doPostHurtEffects(livingentity1);
            }
        }

        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01D, -0.1D, -0.01D));
        float f1 = 1.0F;

        this.playSound(soundevent, f1, 1.0F);
    }

    protected boolean tryPickup(@NotNull Player pPlayer) {
        return super.tryPickup(pPlayer) || this.isNoPhysics() && this.ownedBy(pPlayer) && pPlayer.getInventory().add(this.getPickupItem());
    }

    /**
     * The sound made when an entity is hit by this projectile
     */
    protected @NotNull SoundEvent getDefaultHitGroundSoundEvent() {
        return ModSounds.CLASH.get();
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
        this.getOwner().spawnAtLocation(this.getPickupItem());
        super.remove(pReason);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("saber", 10)) {
            this.saberitem = ItemStack.of(pCompound.getCompound("RotarySaber"));
        }

        this.dealtDamage = pCompound.getBoolean("DealtDamage");
    }

    public void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
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