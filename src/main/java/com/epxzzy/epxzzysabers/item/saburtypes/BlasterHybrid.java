package com.epxzzy.epxzzysabers.item.saburtypes;

import com.epxzzy.epxzzysabers.epxzzySabers;
import com.epxzzy.epxzzysabers.item.ModItems;
import com.epxzzy.epxzzysabers.item.Protosaber;
import com.epxzzy.epxzzysabers.rendering.BlasterSaberItemRenderer;
import com.epxzzy.epxzzysabers.rendering.foundation.SimpleCustomRenderer;
import com.epxzzy.epxzzysabers.utils.LevelHelper;
import com.epxzzy.epxzzysabers.utils.ModTags;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class BlasterHybrid extends Protosaber {

    public BlasterHybrid(Properties pProperties, int pRANGE, int pDamage, int pSpeed) {
        super(pProperties, pRANGE , pDamage, pSpeed);
    }
    @Override @OnlyIn(Dist.CLIENT)
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        THE_BETTER_RENDERER = new BlasterSaberItemRenderer();
        consumer.accept(SimpleCustomRenderer.create(this, THE_BETTER_RENDERER));


    }
    public static boolean checkForSaberBlock(Entity Entityy){
        return LevelHelper.EntityBlockingWithActiveItem(Entityy, ModItems.BLASTER_SABER.get());
    }

    public static boolean checkForSaberEquipment(Entity Entityy, boolean Mainhand){
        return LevelHelper.EntityEquippedActiveItem(Entityy, Mainhand, ModItems.BLASTER_SABER.get());
    }

}
