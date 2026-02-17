package com.epxzzy.epxzzysabers.util;

import net.minecraft.client.Minecraft;
import net.minecraft.data.Main;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TagHelper {
    public static boolean checkHoldingActiveTag(Entity Entityy, boolean Mainhand, TagKey<Item> tagg) {
        if (Entityy instanceof LivingEntity) {
            if (Mainhand)
                return ((LivingEntity) Entityy).getMainHandItem().is(tagg) && StackHelper.isActive(((LivingEntity) Entityy).getMainHandItem());
            return ((LivingEntity) Entityy).getOffhandItem().is(tagg) && StackHelper.isActive(((LivingEntity) Entityy).getOffhandItem());
        }
        return false;
    }

    public static boolean checkMainhandActiveSaber(Entity Entityy) {
        return checkHoldingActiveTag(Entityy, true, SaberTags.Items.LIGHTSABER);
    }
    public static boolean checkMainhandActiveLightWeapon(Entity Entityy) {
        return checkHoldingActiveTag(Entityy, true, SaberTags.Items.LIGHT_WEAPON);
    }

    public static boolean checkMainhandActiveHeavyWeapon(Entity Entityy) {
        return checkHoldingActiveTag(Entityy, true, SaberTags.Items.HEAVY_WEAPON);
    }

    public static boolean checkMainhandActiveUnusualWeapon(Entity Entityy) {
        return checkHoldingActiveTag(Entityy, true, SaberTags.Items.UNUSUAL_WEAPON);
    }

    public static boolean checkMainhandPoseableWeapon(Entity Entityy) {
        return checkHoldingActiveTag(Entityy, true, SaberTags.Items.POSEABLE_LIGHTSABER);
    }

    public static boolean checkActiveSaber(Entity Entityy, boolean Mainhand) {
        return checkHoldingActiveTag(Entityy, Mainhand, SaberTags.Items.LIGHTSABER);
    }

    public static boolean checkActiveLightWeapon(Entity Entityy, boolean Mainhand) {
        return checkHoldingActiveTag(Entityy, Mainhand, SaberTags.Items.LIGHT_WEAPON);
    }

    public static boolean checkActiveHeavyWeapon(Entity Entityy, boolean Mainhand) {
        return checkHoldingActiveTag(Entityy, Mainhand, SaberTags.Items.HEAVY_WEAPON);
    }

    public static boolean checkActiveUnusualWeapon(Entity Entityy, boolean Mainhand) {
        return checkHoldingActiveTag(Entityy, Mainhand, SaberTags.Items.UNUSUAL_WEAPON);
    }
    public static boolean checkActivePoseableWeapon(Entity Entityy, boolean Mainhand) {
        return checkHoldingActiveTag(Entityy, Mainhand, SaberTags.Items.POSEABLE_LIGHTSABER);
    }

    public static boolean isHeavyWeapon(ItemStack stacc){
        return stacc.is(SaberTags.Items.HEAVY_WEAPON);
    }
    public static boolean isLightWeapon(ItemStack stacc){
        return stacc.is(SaberTags.Items.LIGHT_WEAPON);
    }
    public static boolean isUnusualWeapon(ItemStack stacc){
        return stacc.is(SaberTags.Items.UNUSUAL_WEAPON);
    }

}
