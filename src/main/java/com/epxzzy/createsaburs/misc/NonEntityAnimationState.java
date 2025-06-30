package com.epxzzy.createsaburs.misc;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

import java.util.function.Consumer;

public class NonEntityAnimationState {
    private static final long STOPPED = Long.MAX_VALUE;
    private long lastTime = Long.MAX_VALUE;
    private long accumulatedTime;
    private Entity entity;

    public NonEntityAnimationState() {
    }

    public void start(int pTickCount, Entity entity) {
        this.lastTime = (long)pTickCount * 1000L / 20L;
        this.accumulatedTime = 0L;
        this.entity = entity;
    }

    public void startIfStopped(int pTickCount, Entity entity) {
        if (!this.isStarted()) {
            this.start(pTickCount, entity);
        }

    }

    public void animateWhen(boolean bl, int tickCount, Entity entity) {
        if (bl) {
            this.startIfStopped(tickCount, entity);
        } else {
            this.stop(entity);
        }
    }

    public void stop(Entity entity) {
        this.lastTime = Long.MAX_VALUE;
    }

    public void ifStarted(Consumer<NonEntityAnimationState> action) {
        if (this.isStarted()) {
            action.accept(this);
        }
    }

    public void updateTime(float ageInTicks, float speed) {
        if (this.isStarted()) {
            long $$2 = Mth.lfloor(ageInTicks * 1000.0F / 20.0F);
            this.accumulatedTime += (long)((float)($$2 - this.lastTime) * speed);
            this.lastTime = $$2;
        }
    }

    public long getAccumulatedTime() {
        return this.accumulatedTime;
    }

    public boolean isStarted() {
        return this.lastTime != Long.MAX_VALUE;
    }

    public boolean matchesViewingEntity(Entity entity) {
        return this.entity == entity;
    }
}