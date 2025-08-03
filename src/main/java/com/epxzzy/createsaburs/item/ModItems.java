package com.epxzzy.createsaburs.item;

import com.epxzzy.createsaburs.createsaburs;
import com.epxzzy.createsaburs.item.saburtypes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.awt.*;

public class ModItems {
    public static final DeferredRegister<Item> items = DeferredRegister.create(ForgeRegistries.ITEMS, createsaburs.MOD_ID);
    public static final Rarity RARITY_KYBER = Rarity.create("createsaburs:fire",
            style -> {
                float hue = 0.5f + (float)(0.1 * Math.sin(System.currentTimeMillis() / 300.0));
                return style.withColor(Color.HSBtoRGB(hue, 1f, 1f));
            });

    public static final RegistryObject<Item> Protosaber = items.register("protosabur", () -> new Protosaber(new Item.Properties().rarity(RARITY_KYBER), 5, 16, 1));
    //public static final RegistryObject<Item> protosabur2 = items.register("protwosabur", () -> new Protosaber(new ShieldItem.Properties().rarity(RARITY_KYBER), 4, 18, 1));
    public static final RegistryObject<Item> SINGLE_BLADED_SABER = items.register("single_bladed_saber", () -> new SingleBladed(new Item.Properties().rarity(RARITY_KYBER), 3, 18, -2));
    public static final RegistryObject<Item> DUAL_BLADED_SABER = items.register("dual_bladed_saber", () -> new Protosaber(new Item.Properties().rarity(RARITY_KYBER), 3, 16, -1));
    public static final RegistryObject<Item> ROTARY_SABER = items.register("rotary_saber", () -> new RotarySaber(new Item.Properties().rarity(RARITY_KYBER), 3, 12, -1));
    public static final RegistryObject<Item> CROSSGUARD_SABER = items.register("crossguard_saber", () -> new CrossguardSaber(new Item.Properties().rarity(RARITY_KYBER), 2, 10, -1));
    public static final RegistryObject<Item> BLASTER_HYBRID = items.register("blaster_saber", () -> new BlasterHybrid(new Item.Properties().rarity(RARITY_KYBER), 2, 14, -2));
    public static final RegistryObject<Item> SABER_PIKE = items.register("saber_pike", () -> new SaberPike(new Item.Properties().rarity(RARITY_KYBER), 4, 16, -1));



    public static void register(IEventBus eventBus){
        items.register(eventBus);
    }

}
