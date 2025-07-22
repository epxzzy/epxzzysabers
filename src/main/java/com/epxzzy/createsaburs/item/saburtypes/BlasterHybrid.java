package com.epxzzy.createsaburs.item.saburtypes;

import com.epxzzy.createsaburs.createsaburs;
import com.epxzzy.createsaburs.item.Protosaber;
import com.epxzzy.createsaburs.rendering.SingleBladedItemRenderer;
import com.epxzzy.createsaburs.rendering.WorseSingleBladedItemRenderer;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModelRenderer;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModel;
import com.simibubi.create.foundation.item.render.SimpleCustomRenderer;
import com.epxzzy.createsaburs.utils.ModTags;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class BlasterHybrid extends Protosaber {

    public BlasterHybrid(Properties pProperties, int pRANGE, int pDamage, int pSpeed) {
        super(pProperties, pRANGE , pDamage, pSpeed);
    }
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        THE_RENDURR = new WorseSingleBladedItemRenderer();
        consumer.accept(SimpleCustomRenderer.create(this, THE_RENDURR));


    }
    public static boolean checkForSaberBlock(Entity Entityy){
        if(Entityy instanceof LivingEntity)
            return ((LivingEntity)Entityy).getMainHandItem().is(ModTags.Items.CREATE_BLASTER_HYBRID) && ((LivingEntity) Entityy).getMainHandItem().getOrCreateTag().getBoolean("ActiveBoiii") && ((LivingEntity)Entityy).isUsingItem();
        return false;
    }

    public static boolean checkForSaberEquipment(Entity Entityy, boolean Mainhand){
        if(Entityy instanceof LivingEntity) {
            if(Mainhand) return ((LivingEntity) Entityy).getMainHandItem().is(ModTags.Items.CREATE_BLASTER_HYBRID) && ((LivingEntity) Entityy).getMainHandItem().getOrCreateTag().getBoolean("ActiveBoiii");
            return ((LivingEntity) Entityy).getOffhandItem().is(ModTags.Items.CREATE_BLASTER_HYBRID) && ((LivingEntity) Entityy).getOffhandItem().getOrCreateTag().getBoolean("ActiveBoiii");
        }
        return false;
    }

}
