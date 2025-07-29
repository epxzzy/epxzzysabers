package com.epxzzy.createsaburs.item.saburtypes;

import com.epxzzy.createsaburs.item.Protosaber;
import com.epxzzy.createsaburs.rendering.CrossguardSaberItemRenderer;
import com.epxzzy.createsaburs.rendering.SingleBladedItemRenderer;
import com.epxzzy.createsaburs.rendering.foundation.SimpleCustomRenderer;
import com.epxzzy.createsaburs.utils.ModTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class CrossguardSaber extends Protosaber {

    public CrossguardSaber(Properties pProperties, int pRANGE, int pDamage, int pSpeed) {
        super(pProperties, pRANGE , pDamage, pSpeed);
    }
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        THE_BETTER_RENDERER = new CrossguardSaberItemRenderer();
        consumer.accept(SimpleCustomRenderer.create(this, THE_BETTER_RENDERER));
    }
    public static boolean checkForSaberBlock(Entity Entityy){
        if(Entityy instanceof LivingEntity)
            return ((LivingEntity)Entityy).getMainHandItem().is(ModTags.Items.CREATE_CROSSGUARD_SABER) && ((LivingEntity) Entityy).getMainHandItem().getOrCreateTag().getBoolean("ActiveBoiii") && ((LivingEntity)Entityy).isUsingItem();
        return false;
    }

    public static boolean checkForSaberEquipment(Entity Entityy, boolean Mainhand){
        if(Entityy instanceof LivingEntity) {
            if(Mainhand) return ((LivingEntity) Entityy).getMainHandItem().is(ModTags.Items.CREATE_CROSSGUARD_SABER) && ((LivingEntity) Entityy).getMainHandItem().getOrCreateTag().getBoolean("ActiveBoiii");
            return ((LivingEntity) Entityy).getOffhandItem().is(ModTags.Items.CREATE_CROSSGUARD_SABER) && ((LivingEntity) Entityy).getOffhandItem().getOrCreateTag().getBoolean("ActiveBoiii");
        }
        return false;
    }

}
