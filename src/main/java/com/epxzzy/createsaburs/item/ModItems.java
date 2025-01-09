package com.epxzzy.createsaburs.item;

import com.epxzzy.createsaburs.createsaburs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ShieldItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> items = DeferredRegister.create(ForgeRegistries.ITEMS, createsaburs.MOD_ID);

    public static final RegistryObject<Item> protosabur = items.register("protosabur", () -> new ShieldItem(new ShieldItem.Properties()));

    public static void register(IEventBus eventBus){
        items.register(eventBus);
    }
}
