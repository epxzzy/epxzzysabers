package com.epxzzy.createsaburs.entity;


import com.epxzzy.createsaburs.createsaburs;
//import com.epxzzy.createsaburs.entity.custom.PlasmaBolt;
import com.epxzzy.createsaburs.entity.custom.PlasmaBolt;
import com.epxzzy.createsaburs.entity.custom.ThrownRotarySaber;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, createsaburs.MOD_ID);

    public static final RegistryObject<EntityType<ThrownRotarySaber>> ROTARY_SABER_ENTITY =
            ENTITY_TYPES.register("rotary_saber_entity", () -> EntityType.Builder.<ThrownRotarySaber>of(ThrownRotarySaber::new, MobCategory.MISC)
                    .sized(2f, 0.5f).build("rotary_saber_entity"));

    public static final RegistryObject<EntityType<PlasmaBolt>> PLASMA_BOLT =
            ENTITY_TYPES.register("plasma_bolt", () -> EntityType.Builder.<PlasmaBolt>of(PlasmaBolt::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("plasma_bolt"));






    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
