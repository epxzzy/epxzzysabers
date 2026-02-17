package com.epxzzy.epxzzysabers.item.types;

import com.epxzzy.epxzzysabers.item.Protosaber;
import com.epxzzy.epxzzysabers.item.SaberItems;
import com.epxzzy.epxzzysabers.rendering.item.CrossguardSaberItemRenderer;
import com.epxzzy.epxzzysabers.rendering.foundation.SimpleCustomRenderer;
import com.epxzzy.epxzzysabers.util.LevelHelper;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class CrossguardSaber extends Protosaber {

    public CrossguardSaber(Properties pProperties, int pRANGE, int pDamage, int pSpeed) {
        super(pProperties, pRANGE , pDamage, pSpeed);
    }
    @Override @OnlyIn(Dist.CLIENT)
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        THE_BETTER_RENDERER = new CrossguardSaberItemRenderer();
        consumer.accept(SimpleCustomRenderer.create(this, THE_BETTER_RENDERER));
    }
    public static boolean checkForSaberBlock(Entity Entityy){
        return LevelHelper.EntityBlockingWithActiveItem(Entityy, SaberItems.CROSSGUARD_SABER.get());
    }

    public static boolean checkForSaberEquipment(Entity Entityy, boolean Mainhand){
        return LevelHelper.EntityEquippedActiveItem(Entityy, Mainhand, SaberItems.CROSSGUARD_SABER.get());
    }

}
