package com.epxzzy.epxzzysabers.item.types;

import com.epxzzy.epxzzysabers.item.SaberItems;
import com.epxzzy.epxzzysabers.item.Protosaber;
import com.epxzzy.epxzzysabers.rendering.item.DualBladedItemRenderer;
import com.epxzzy.epxzzysabers.util.LevelHelper;
//import com.simibubi.create.foundation.item.renderMid.SimpleCustomRenderer;
import com.epxzzy.epxzzysabers.rendering.foundation.SimpleCustomRenderer;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class DualBladed extends Protosaber {
    public boolean HURTED;
    public boolean SWANG;


    public DualBladed(Properties pProperties, int pRANGE, int pDamage, int pSpeed) {
        super(pProperties, pRANGE , pDamage, pSpeed);
    }

    public static boolean checkForSaberBlock(Entity Entityy){
        return LevelHelper.EntityBlockingWithActiveItem(Entityy, SaberItems.DUAL_BLADED_SABER.get());
    }

    public static boolean checkForSaberEquipment(Entity Entityy, boolean Mainhand){
        return LevelHelper.EntityEquippedActiveItem(Entityy, Mainhand, SaberItems.DUAL_BLADED_SABER.get());
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        //HURTED = true;
        //epxzzySabers.LOGGER.debug("swang: {}, hurted: {}", this.SWANG, this.HURTED);
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    @Override @OnlyIn(Dist.CLIENT)
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        THE_BETTER_RENDERER = new DualBladedItemRenderer();
        //CustomRenderedItems.register(this);
        consumer.accept(SimpleCustomRenderer.create(this, THE_BETTER_RENDERER));
       /* consumer.accept(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return ExperimentalBEWLR.getInstance();
            }
        });

        */

    }
}
