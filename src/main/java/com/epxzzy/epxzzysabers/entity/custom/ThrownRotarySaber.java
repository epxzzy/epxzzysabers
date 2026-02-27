package com.epxzzy.epxzzysabers.entity.custom;

import com.epxzzy.epxzzysabers.epxzzySabers;
import com.epxzzy.epxzzysabers.entity.SaberEntities;
import com.epxzzy.epxzzysabers.item.Protosaber;
import com.epxzzy.epxzzysabers.item.SaberItems;
import com.epxzzy.epxzzysabers.item.types.RotarySaber;
import com.epxzzy.epxzzysabers.sound.SaberSounds;
import com.epxzzy.epxzzysabers.util.ColourConverter;
import com.epxzzy.epxzzysabers.util.LevelHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class ThrownRotarySaber extends Projectile {
    private ItemStack saberitem = new ItemStack(SaberItems.ROTARY_SABER.get());
    private static final EntityDataAccessor<Integer> Decimal_Colour = SynchedEntityData.defineId(ThrownRotarySaber.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> Gay = SynchedEntityData.defineId(ThrownRotarySaber.class, EntityDataSerializers.BOOLEAN);

    public boolean returning;
    public int clientSideReturnSaberTickCount;
    public int PathTickCount;
    public static int PATHDECAYDURATION = 10;
    public int ReturnCount = 0;
    public boolean inGround;
    public int inGroundTime;

    public ThrownRotarySaber(EntityType<ThrownRotarySaber> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ThrownRotarySaber(Level pLevel, LivingEntity pShooter, ItemStack pStack) {
        super(SaberEntities.ROTARY_SABER_ENTITY.get(), pLevel);
        this.setOwner(pShooter);
        BlockPos blockpos = pShooter.blockPosition();
        double d0 = (double)blockpos.getX() + 0.5D;
        double d1 = (double)blockpos.getY() + 0.5D;
        double d2 = (double)blockpos.getZ() + 0.5D;
        this.moveTo(d0, d1, d2, this.getYRot(), this.getXRot());
        this.saberitem = pStack;
        this.entityData.set(Decimal_Colour, RotarySaber.getColor(pStack));
        this.entityData.set(Gay, Protosaber.isGay(pStack));

        epxzzySabers.LOGGER.debug("colour given to thrown saber is:" + RotarySaber.getColor(pStack));
    }

    @Override
    protected void defineSynchedData() {
        //super.defineSynchedData();
        this.entityData.define(Decimal_Colour, 0);
        this.entityData.define(Gay, false);
    }

    public int[] getColour() {
        //epxzzySabers.LOGGER.debug("colour asked from thrown saber is:" + this.entityData.get(Decimal_Colour));
        //int[] colour = ColourConverter.PortedDecimaltoRGB(this.entityData.get(Decimal_Colour));
        //epxzzySabers.LOGGER.debug("colours have been set as: " +  ColourConverter.PortedDecimaltoRGB(this.entityData.get(Decimal_Colour))[0] + " " +  ColourConverter.PortedDecimaltoRGB(this.entityData.get(Decimal_Colour))[1] + " " +  ColourConverter.PortedDecimaltoRGB(this.entityData.get(Decimal_Colour))[2]);
        if (this.entityData.get(Gay)) {
            return ColourConverter.rainbowColor((int) System.currentTimeMillis());
        }

        return ColourConverter.PortedDecimaltoRGB(this.entityData.get(Decimal_Colour));
    }

    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);
        this.inGround = true;
    }

    public int getReturnMultiplier(){
        return this.ReturnCount > 5 ? 0: 5 + -this.ReturnCount;
    }
    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void tick() {
        super.tick();
        Vec3 vec3 = this.getDeltaMovement();

        BlockPos blockpos = this.blockPosition();
        BlockState blockstate = this.level().getBlockState(blockpos);
        if (!blockstate.isAir() && !this.noPhysics) {
            VoxelShape voxelshape = blockstate.getCollisionShape(this.level(), blockpos);
            if (!voxelshape.isEmpty()) {
                Vec3 vec31 = this.position();

                for(AABB aabb : voxelshape.toAabbs()) {
                    if (aabb.move(blockpos).contains(vec31)) {
                        this.inGround = true;
                        break;
                    }
                }
            }
        }

        if (this.inGround && !this.noPhysics) {
            ++this.inGroundTime;
        } else {
            this.inGroundTime = 0;
            Vec3 vec32 = this.position();
            Vec3 vec33 = vec32.add(vec3);
            HitResult hitresult = this.level().clip(new ClipContext(vec32, vec33, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
            if (hitresult.getType() != HitResult.Type.MISS) {
                vec33 = hitresult.getLocation();
            }

            vec3 = this.getDeltaMovement();
            double d5 = vec3.x;
            double d6 = vec3.y;
            double d1 = vec3.z;

            double d7 = this.getX() + d5;
            double d2 = this.getY() + d6;
            double d3 = this.getZ() + d1;
            double d4 = vec3.horizontalDistance();
            this.setYRot((float)(Mth.atan2(-d5, -d1) * (double)(180F / (float)Math.PI)));
            this.setYRot((float)(Mth.atan2(d5, d1) * (double)(180F / (float)Math.PI)));

            this.setXRot((float)(Mth.atan2(d6, d4) * (double)(180F / (float)Math.PI)));
            this.setXRot(lerpRotation(this.xRotO, this.getXRot()));
            this.setYRot(lerpRotation(this.yRotO, this.getYRot()));
            float f = 0.99F;

            this.setDeltaMovement(vec3.scale((double)f));
            if (!this.isNoGravity() && !noPhysics) {
                Vec3 vec34 = this.getDeltaMovement();
                this.setDeltaMovement(vec34.x, vec34.y - (double)0.05F, vec34.z);
            }

            this.setPos(d7, d2, d3);
            this.checkInsideBlocks();
        }
        if (this.inGroundTime > 1 || this.PathTickCount >= PATHDECAYDURATION) {
            this.returning = true;
        }

        List<LivingEntity> titesInRegion = LevelHelper.getEntitiesInRadius(this.position(), this.level(), 2.5);
        titesInRegion.remove(this.getOwner());

        if(this.getOwner() != null) {
            for (LivingEntity thisSpecficTity : titesInRegion) {
                thisSpecficTity.hurt(this.getOwner().damageSources().mobAttack((Player) this.getOwner()), 2);
            }
        }

        this.playSound(SaberSounds.SWING.get(), 0.05f, 1.0f);
        Entity owner = this.getOwner();
        if(!this.returning){
            this.PathTickCount++;
        }
        if (this.returning && owner != null) {
            if (this.isAcceptibleReturnOwner()) {
                this.setNoPhysics(true);
                Vec3 vec33 = owner.getEyePosition().subtract(this.position());
                this.setPosRaw(this.getX(), this.getY() + vec33.y * 0.015D * 3, this.getZ());
                if (this.level().isClientSide) {
                    this.yOld = this.getY();
                }

                double d0 = (24 > owner.distanceTo(this)?4:8) * 0.05D;
                //deceleration so its easier to parry
                this.setDeltaMovement(this.getDeltaMovement().scale(0.95D).add(vec33.normalize().scale(d0)));
                if (this.clientSideReturnSaberTickCount == 0) {
                    this.playSound(SaberSounds.CLASH.get(), 0.05f, 1.0f);
                }

                this.clientSideReturnSaberTickCount++;
                this.PathTickCount = 0;
            }
        }
        if (owner == null) {
            //this.spawnAtLocation(this.getPickupItem(), 0.1F);
            this.discard();
        }


        super.tick();
    }

    public void shootFromRotationSaber(Entity pShooter, float pX, float pY, float pZ, float pVelocity, float pInaccuracy) {
        float f = -Mth.sin(pY * ((float) Math.PI / 180F)) * Mth.cos(pX * ((float) Math.PI / 180F));
        float f1 = -Mth.sin((pX + pZ) * ((float) Math.PI / 180F));
        float f2 = Mth.cos(pY * ((float) Math.PI / 180F)) * Mth.cos(pX * ((float) Math.PI / 180F));
        this.shoot((double) f, (double) f1, (double) f2, pVelocity, pInaccuracy);
        Vec3 vec3 = pShooter.getDeltaMovement();
        this.returning = false;
        this.ReturnCount++;
        this.PathTickCount = 0;
        this.setNoPhysics(false);
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
     * Sets if this arrow can noClip
     */
    public void setNoPhysics(boolean pNoPhysics) {
        this.noPhysics = pNoPhysics;
    }

    protected @NotNull ItemStack getPickupItem() {
        return this.saberitem.copy();
    }

    /**
     * Called when the arrow hits an entity
     */
    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        Entity entity = pResult.getEntity();
        float f = 12.0F;

        Entity entity1 = this.getOwner();
        DamageSource dmgsrc = this.damageSources().generic();

        this.returning = true;
        SoundEvent soundevent = SaberSounds.CLASH.get();
        if (entity.hurt(dmgsrc, f)) {
            if(this.getOwner().getUUID() == entity.getUUID()){
                return;
            }

            if (entity.getType() == EntityType.ENDERMAN) {
                return;
            }

            if (entity instanceof LivingEntity livingentity1) {

                //this.doPostHurtEffects(livingentity1);
            }
            if(this.getOwner().getUUID() == entity.getUUID()){}
        }

        this.setDeltaMovement(this.getDeltaMovement().multiply(-0.01D, -0.1D, -0.01D));
        float f1 = 1.0F;

        this.playSound(soundevent, f1, 1.0F);
    }

    protected boolean tryPickup(@NotNull Player pPlayer) {
        return this.returning && this.ownedBy(pPlayer) && pPlayer.getInventory().add(this.getPickupItem());
    }

    /**
     * The sound made when an entity is hit by this projectile
     */
    protected @NotNull SoundEvent getDefaultHitGroundSoundEvent() {
        return SaberSounds.CLASH.get();
    }

    /**
     * Called by a player entity when they collide with an entity
     */
    @Override
    public void playerTouch(@NotNull Player pEntity) {
        if(pEntity.getBoundingBox().contains(this.getX(), this.getY(),this.getZ())){
            if (!this.level().isClientSide && this.returning) {
                if (this.tryPickup(pEntity)) {
                    pEntity.take(this, 1);
                    this.discard();
                }
            }
        }
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        //this.getOwner().spawnAtLocation(this.getPickupItem());
        return super.hurt(pSource, pAmount);
    }

    @Override
    public void remove(RemovalReason pReason) {
        //this.getOwner().spawnAtLocation(this.getPickupItem());
        super.remove(pReason);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("saber", 10)) {
            this.saberitem = ItemStack.of(pCompound.getCompound("RotarySaber"));
            this.entityData.set(Decimal_Colour, RotarySaber.getColor(this.saberitem));
            this.entityData.set(Gay, RotarySaber.isGay(this.saberitem));

        }

        this.returning = pCompound.getBoolean("Returning");
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.put("RotarySaber", this.saberitem.save(new CompoundTag()));

        pCompound.putBoolean("Returning", this.returning);
    }
}