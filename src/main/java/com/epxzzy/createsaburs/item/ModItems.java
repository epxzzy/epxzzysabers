package com.epxzzy.createsaburs.item;

import com.epxzzy.createsaburs.createsaburs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ShieldItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.awt.*;

public class ModItems {
    public static final DeferredRegister<Item> items = DeferredRegister.create(ForgeRegistries.ITEMS, createsaburs.MOD_ID);
    public static final Rarity RARITY_FIRE = Rarity.create("createsaburs:fire",
            style -> {
                float hue = 0.05f + (float)(0.05 * Math.sin(System.currentTimeMillis() / 300.0));
                return style.withColor(Color.HSBtoRGB(hue, 1f, 1f));
            });
   public static final Rarity RARITY_SPARK = Rarity.create("createsaburs:spark",
            style -> style.withColor(Color.HSBtoRGB( (float)(0.25f + 0.15f *  Math.sin(System.currentTimeMillis() / 100)), 1f, 1f)));
    public static final RegistryObject<Item> protosabur = items.register("protosabur", () -> new ShieldItem(new ShieldItem.Properties().rarity(RARITY_FIRE)));
    public static final RegistryObject<Item> protosabur2 = items.register("protwosabur", () -> new ShieldItem(new ShieldItem.Properties().rarity(RARITY_SPARK)));



    public static void register(IEventBus eventBus){
        items.register(eventBus);
    }
}
