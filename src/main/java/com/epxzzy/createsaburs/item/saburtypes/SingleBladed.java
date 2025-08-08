package com.epxzzy.createsaburs.item.saburtypes;

import com.epxzzy.createsaburs.CreateSaburs;
import com.epxzzy.createsaburs.item.Protosaber;
import com.epxzzy.createsaburs.rendering.SingleBladedItemRenderer;
import com.epxzzy.createsaburs.utils.ModTags;
//import com.simibubi.create.foundation.item.render.SimpleCustomRenderer;
import com.epxzzy.createsaburs.rendering.foundation.SimpleCustomRenderer;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class SingleBladed extends Protosaber {
    public boolean HURTED;
    public boolean SWANG;


    public SingleBladed(Properties pProperties, int pRANGE, int pDamage, int pSpeed) {
        super(pProperties, pRANGE , pDamage, pSpeed);
    }

    public static boolean checkForSaberBlock(Entity Entityy){
        if(Entityy instanceof LivingEntity)
            return ((LivingEntity)Entityy).getMainHandItem().is(ModTags.Items.SINGLE_BLADED) && ((LivingEntity) Entityy).getMainHandItem().getOrCreateTag().getBoolean("ActiveBoiii") && ((LivingEntity)Entityy).isUsingItem();
        return false;
    }

    public static boolean checkForSaberEquipment(Entity Entityy, boolean Mainhand){
        if(Entityy instanceof LivingEntity) {
            if(Mainhand) return ((LivingEntity) Entityy).getMainHandItem().is(ModTags.Items.SINGLE_BLADED) && ((LivingEntity) Entityy).getMainHandItem().getOrCreateTag().getBoolean("ActiveBoiii");
            return ((LivingEntity) Entityy).getOffhandItem().is(ModTags.Items.SINGLE_BLADED) && ((LivingEntity) Entityy).getOffhandItem().getOrCreateTag().getBoolean("ActiveBoiii");
        }
        return false;
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        /*
        if(!entity.level().isClientSide()) {
            CompoundTag tagger = stack.getOrCreateTag();
            int baller = (int) ((Math.random() * 8) + 1);
            tagger.getCompound("display").putInt("atk", baller);
            CreateSaburs.LOGGER.debug("randomiser decided on number {}", baller);
            stack.setTag(tagger);
        }

         */

        //SWANG = true;
        //CreateSaburs.LOGGER.debug("swang: {}, hurted: {}", this.SWANG, this.HURTED);
        //ofc swing triggers first cause why the fuck would things make sense
        return false;
        //return super.onEntitySwing(stack, entity);
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        //HURTED = true;
        //CreateSaburs.LOGGER.debug("swang: {}, hurted: {}", this.SWANG, this.HURTED);
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        THE_BETTER_RENDERER = new SingleBladedItemRenderer();
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
