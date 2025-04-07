package com.epxzzy.createsaburs.item.saburtypes;

import com.epxzzy.createsaburs.item.protosaber;
import com.epxzzy.createsaburs.rendering.SingleBladedItemRenderer;
import com.epxzzy.createsaburs.utils.ModTags;
import com.simibubi.create.foundation.item.render.SimpleCustomRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class SingleBladed extends protosaber {

    public SingleBladed(Properties pProperties, int pRANGE, int pDamage, int pSpeed) {
        super(pProperties, 3 , 14, 2);
    }
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        THE_RENDURR = new SingleBladedItemRenderer();
        consumer.accept(SimpleCustomRenderer.create(this, THE_RENDURR));
    }
    public static boolean checkForSaberBlock(Entity Entityy){
        if(Entityy instanceof LivingEntity)
            return ((LivingEntity)Entityy).getMainHandItem().is(ModTags.Items.CREATE_SINGLE_BLADED) && ((LivingEntity) Entityy).getMainHandItem().getOrCreateTag().getBoolean("ActiveBoiii") && ((LivingEntity)Entityy).isUsingItem();
        return false;
    }

    public static boolean checkForSaberEquipment(Entity Entityy, boolean Mainhand){
        if(Entityy instanceof LivingEntity) {
            if(Mainhand) return ((LivingEntity) Entityy).getMainHandItem().is(ModTags.Items.CREATE_SINGLE_BLADED) && ((LivingEntity) Entityy).getMainHandItem().getOrCreateTag().getBoolean("ActiveBoiii");
            return ((LivingEntity) Entityy).getOffhandItem().is(ModTags.Items.CREATE_SINGLE_BLADED) && ((LivingEntity) Entityy).getOffhandItem().getOrCreateTag().getBoolean("ActiveBoiii");
        }
        return false;
    }

}
