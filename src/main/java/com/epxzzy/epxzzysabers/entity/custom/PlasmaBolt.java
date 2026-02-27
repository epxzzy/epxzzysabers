package com.epxzzy.epxzzysabers.entity.custom;

import com.epxzzy.epxzzysabers.entity.SaberEntities;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;


public class PlasmaBolt extends AbstractHurtingProjectile implements ItemSupplier {
    private int DecayTickCount;
    private static int DECAYDURATION = 10;

    public PlasmaBolt(EntityType<PlasmaBolt> entityType, Level level) {
        super(entityType, level);
    }
    public PlasmaBolt(Entity owner, Level level) {
        super(SaberEntities.PLASMA_BOLT.get(), level);
        this.setPos(owner.getX(),owner.getEyeY()-0.1,owner.getZ());
        this.setOwner(owner);
    }

    @Override
    public void tick() {

        if(DecayTickCount >= DECAYDURATION){
            goPoof();
        }
        DecayTickCount++;

        super.tick();
    }

    public void goPoof(){
        this.level().addParticle(ParticleTypes.SWEEP_ATTACK, this.position().x, this.position().y ,this.position().z, 0.0D, 0.0D, 0.0D);
        this.discard();
    }

    @Override
    protected AABB makeBoundingBox() {
        float var1 = this.getType().getDimensions().width / 2.0F;
        float var2 = this.getType().getDimensions().height;
        float var3 = 0.15F;
        return new AABB(
                this.position().x - (double)var1,
                this.position().y - 0.15F,
                this.position().z - (double)var1,
                this.position().x + (double)var1,
                this.position().y - 0.15F + (double)var2,
                this.position().z + (double)var1
        );
    }
    @Override
    public boolean canCollideWith(Entity entity) {
        return !(entity instanceof PlasmaBolt) && super.canCollideWith(entity);
    }
    @Override
    protected boolean canHitEntity(Entity target) {
        return !(target instanceof PlasmaBolt) && super.canHitEntity(target);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!this.level().isClientSide) {
            Entity var10000 = result.getEntity();
            Entity var3 = this.getOwner();
            var10000.hurt(this.damageSources().mobProjectile(this, var3 instanceof LivingEntity var2 ? var2 : null), 2.0F);
            if(var10000 instanceof Player pplayer){
                //todo shti
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        goPoof();
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide) {
            goPoof();
        }
    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }

    @Override
    public ItemStack getItem() {
        return ItemStack.EMPTY;
    }

    @Override
    protected float getInertia() {
        return 1.0F;
    }
    @Override
    protected ParticleOptions getTrailParticle() {
        return ParticleTypes.END_ROD;
    }
}


