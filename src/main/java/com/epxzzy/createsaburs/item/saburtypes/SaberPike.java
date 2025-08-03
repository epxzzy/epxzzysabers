package com.epxzzy.createsaburs.item.saburtypes;

import com.epxzzy.createsaburs.item.Protosaber;
import com.epxzzy.createsaburs.rendering.CrossguardSaberItemRenderer;
import com.epxzzy.createsaburs.rendering.PikeSaberItemRenderer;
import com.epxzzy.createsaburs.rendering.foundation.SimpleCustomRenderer;
import com.epxzzy.createsaburs.utils.ModTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class SaberPike extends Protosaber {

    public SaberPike(Properties pProperties, int pRANGE, int pDamage, int pSpeed) {
        super(pProperties, pRANGE , pDamage, pSpeed);
    }
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        THE_BETTER_RENDERER = new PikeSaberItemRenderer();
        consumer.accept(SimpleCustomRenderer.create(this, THE_BETTER_RENDERER));
    }
    public static boolean checkForSaberBlock(Entity Entityy){
        if(Entityy instanceof LivingEntity)
            return ((LivingEntity)Entityy).getMainHandItem().is(ModTags.Items.CREATE_PIKE) && ((LivingEntity) Entityy).getMainHandItem().getOrCreateTag().getBoolean("ActiveBoiii") && ((LivingEntity)Entityy).isUsingItem();
        return false;
    }

    public static boolean checkForSaberEquipment(Entity Entityy, boolean Mainhand){
        if(Entityy instanceof LivingEntity) {
            if(Mainhand) return ((LivingEntity) Entityy).getMainHandItem().is(ModTags.Items.CREATE_PIKE) && ((LivingEntity) Entityy).getMainHandItem().getOrCreateTag().getBoolean("ActiveBoiii");
            return ((LivingEntity) Entityy).getOffhandItem().is(ModTags.Items.CREATE_PIKE) && ((LivingEntity) Entityy).getOffhandItem().getOrCreateTag().getBoolean("ActiveBoiii");
        }
        return false;
    }

}
