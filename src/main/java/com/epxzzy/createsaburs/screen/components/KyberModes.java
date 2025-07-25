package com.epxzzy.createsaburs.screen.components;

import com.google.common.collect.ImmutableList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.List;

public enum KyberModes implements net.minecraftforge.common.IExtensibleEnum {
    RECOLOUR(new ItemStack(Items.RED_DYE), new ItemStack(Items.GREEN_DYE), new ItemStack(Items.BLUE_DYE)),
    STANCE(new ItemStack(Items.ARMOR_STAND));

    private final List<ItemStack> itemIcons;

    private KyberModes(ItemStack... pItemIcons) {
        this.itemIcons = ImmutableList.copyOf(pItemIcons);
    }


    public List<ItemStack> getIconItems() {
        return this.itemIcons;
    }

    public static List<KyberModes> getCategories() {
        List list;
        list = ImmutableList.of(RECOLOUR, STANCE);

        return list;
    }

    public static KyberModes create(String name, ItemStack... icons) {
        throw new IllegalStateException("Enum not extended");
    }

}
