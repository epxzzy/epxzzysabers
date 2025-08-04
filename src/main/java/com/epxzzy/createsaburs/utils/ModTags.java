package com.epxzzy.createsaburs.utils;

import com.epxzzy.createsaburs.createsaburs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {

        private static TagKey<Block> tag(String name){
            return BlockTags.create(new ResourceLocation(createsaburs.MOD_ID, name));
        }
    }
    public static class Items {
        public static final TagKey<Item> CREATE_LIGHTSABER = tag("create_lightsaber");
        public static final TagKey<Item> CREATE_DYEABLE_LIGHTSABER = tag("create_lightsaber_dyeable");
        public static final TagKey<Item> CREATE_SINGLE_BLADED = tag("create_single_bladed");
        public static final TagKey<Item> CREATE_DUAL_BLADED = tag("create_dual_bladed");
        public static final TagKey<Item> CREATE_ROTARY_SABER = tag("create_rotary_saber");
        public static final TagKey<Item> CREATE_CROSSGUARD_SABER = tag("create_crossguard_saber");
        public static final TagKey<Item> CREATE_BLASTER_HYBRID = tag("create_blaster_hybrid");
        public static final TagKey<Item> CREATE_PIKE = tag("create_pike_saber");
        public static final TagKey<Item> CREATE_GAUNTLET = tag("create_gauntlet_saber");


        public static final TagKey<Item> CREATE_KYBER_CRYSTAL= tag("create_kyber_crystal");


        private static TagKey<Item> tag(String name){
            return ItemTags.create(new ResourceLocation(createsaburs.MOD_ID, name));
        }

    }
}
