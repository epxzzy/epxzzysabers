package com.epxzzy.createsaburs.block;

import com.epxzzy.createsaburs.block.entity.KyberStationBlockEntity;
import com.epxzzy.createsaburs.createsaburs;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, createsaburs.MOD_ID);
    public static final RegistryObject<BlockEntityType<KyberStationBlockEntity>> KYBER_STATION_BE =
            BLOCK_ENTITIES.register("gem_polishing_be", () ->
                    BlockEntityType.Builder.of(KyberStationBlockEntity::new,
                            ModBlocks.KYBERSTATION.get()).build(null));
    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}