package com.epxzzy.epxzzysabers.utils;

import com.epxzzy.epxzzysabers.epxzzySabers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {

        private static TagKey<Block> tag(String name){
            return BlockTags.create(new ResourceLocation(epxzzySabers.MOD_ID, name));
        }
    }
    public static class Items {
        public static final TagKey<Item> LIGHTSABER = tag("lightsaber");
        public static final TagKey<Item> DYEABLE_LIGHTSABER = tag("lightsaber_dyeable");
        public static final TagKey<Item> POSEABLE_LIGHTSABER = tag("lightsaber_poseable");
        public static final TagKey<Item> SINGLE_BLADED = tag("single_bladed");
        public static final TagKey<Item> DUAL_BLADED = tag("dual_bladed");

        public static final TagKey<Item> KYBER_CRYSTAL= tag("kyber_crystal");


        private static TagKey<Item> tag(String name){
            return ItemTags.create(new ResourceLocation(epxzzySabers.MOD_ID, name));
        }

    }
}
