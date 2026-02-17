package com.epxzzy.epxzzysabers.misc;

import com.epxzzy.epxzzysabers.epxzzySabers;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;


public class SaberDamageTypes {

        public static final ResourceKey<DamageType> SABER_BURN = ResourceKey.create(Registries.DAMAGE_TYPE, epxzzySabers.asResource("saber_burn"));

        public static DamageSource causeSaberBurnDamage(RegistryAccess registryAccess) {
            return new DamageSourceRandomMessages(registryAccess.registry(Registries.DAMAGE_TYPE).get().getHolderOrThrow(SABER_BURN), 1);
        }

        private static class DamageSourceRandomMessages extends DamageSource {

            private int messageCount;

            public DamageSourceRandomMessages(Holder.Reference<DamageType> message, int messageCount) {
                super(message);
                this.messageCount = messageCount;
            }

            public DamageSourceRandomMessages(Holder.Reference<DamageType> message, Entity source, int messageCount) {
                super(message, source);
                this.messageCount = messageCount;
            }

            @Override
            public Component getLocalizedDeathMessage(LivingEntity attacked) {
                int type = attacked.getRandom().nextInt(this.messageCount);
                String s = "death.attack." + this.getMsgId() + "_" + type;
                Entity entity = this.getDirectEntity() == null ? this.getEntity() : this.getDirectEntity();
                if (entity != null) {
                    return Component.translatable(s + ".entity", attacked.getDisplayName(), entity.getDisplayName());
                }else{
                    return Component.translatable(s, attacked.getDisplayName());
                }
            }
        }
    }
