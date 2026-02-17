package com.epxzzy.epxzzysabers.util;

import com.epxzzy.epxzzysabers.epxzzySabers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class SaberTags {
    public static class Blocks {

        private static TagKey<Block> tag(String name){
            return BlockTags.create(new ResourceLocation(epxzzySabers.MOD_ID, name));
        }
    }
    public static class Items {
        public static final TagKey<Item> LIGHTSABER = tag("lightsaber");
        public static final TagKey<Item> DYEABLE_LIGHTSABER = tag("config/lightsaber_dyeable");
        public static final TagKey<Item> POSEABLE_LIGHTSABER = tag("config/lightsaber_poseable");

        public static final TagKey<Item> LIGHT_WEAPON = tag("weapons/light_weapon");
        public static final TagKey<Item> HEAVY_WEAPON = tag("weapons/heavy_weapon");
        public static final TagKey<Item> UNUSUAL_WEAPON = tag("weapons/unusual_weapon");


        public static final TagKey<Item> KYBER_CRYSTAL= tag("kyber_crystal");


        private static TagKey<Item> tag(String name){
            return ItemTags.create(new ResourceLocation(epxzzySabers.MOD_ID, name));
        }

    }
}
