package com.epxzzy.createsaburs.item.saburtypes;

import com.epxzzy.createsaburs.item.ModItems;
import com.epxzzy.createsaburs.item.Protosaber;
import com.epxzzy.createsaburs.rendering.CrossguardSaberItemRenderer;
import com.epxzzy.createsaburs.rendering.SaberGauntletItemRenderer;
import com.epxzzy.createsaburs.rendering.foundation.SimpleCustomRenderer;
import com.epxzzy.createsaburs.utils.ModTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class SaberGauntlet extends Protosaber {
    int ability = 0;
    int abilityActiveDuration = 0;
    // 0 = none, 1 = supercharge, 2 = possibly disruption, 3 = possibly kyber surge
    public SaberGauntlet(Properties pProperties, int pRANGE, int pDamage, int pSpeed) {
        super(pProperties, pRANGE , pDamage, pSpeed);
    }
    @Override @OnlyIn(Dist.CLIENT)
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        THE_BETTER_RENDERER = new SaberGauntletItemRenderer();
        consumer.accept(SimpleCustomRenderer.create(this, THE_BETTER_RENDERER));
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        //supercharge
        if(GetCurrentAbility(pStack, 1)){
            //not much to do here
            if(abilityActiveDuration > 200){
                SetAbility(1, false, pStack, pLevel, pEntity, pSlotId, pIsSelected);
            }
        }
        if(GetCurrentAbility(pStack, 2)){
            //fuck with people and their lightsabers

            if(abilityActiveDuration > 100){
                SetAbility(2, false, pStack, pLevel, pEntity, pSlotId, pIsSelected);
            }
        }
        if(GetCurrentAbility(pStack, 3)){
            //fuck with people and their lightsabers

            //if hit limit is reached
            //TODO: make this incremented if someone is is hit with this ability
            if(abilityActiveDuration > 5){
                SetAbility(3, false, pStack, pLevel, pEntity, pSlotId, pIsSelected);
            }
        }


        //if there is an active ability that is not kyber surge
        if(!GetCurrentAbility(pStack, 0)&&!GetCurrentAbility(pStack, 3)) {
            abilityActiveDuration++;
        }
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

    public void SetAbility(int abilityID, boolean switchvalue, ItemStack stacc, Level pLevel, Entity pEntity, int pSlotID, boolean pIsSelcted){
        stacc.getOrCreateTag().remove("ActiveAbility");
        //remove the ability already written so we dont fuck with the next assigned ability

        if(ability==1){
           if(switchvalue){
              stacc.getOrCreateTag().putInt("ActiveAbility", 1);

              if(pEntity instanceof Player pPlayer){
                  //give reach
                  //give sweep attacks
                  //give coool sounds
                  //add parry range
              }

           }
           else{

                //remove reach
               //remove sweep attacks
               //remove parry range
           }
        }
        if(ability==2){
            if(switchvalue){
                stacc.getOrCreateTag().putInt("ActiveAbility", 2);


            }
            else{

            }
        }
    }

    public static int GetCurrentAbility(ItemStack stacc){
        return stacc.getOrCreateTag().getInt("ActiveAbility");
    }
    public static boolean GetCurrentAbility(ItemStack stacc, int ability){
        return stacc.getOrCreateTag().getInt("ActiveAbility") == ability;
    }

    public static boolean checkForSaberBlock(Entity Entityy){
        if(Entityy instanceof LivingEntity)
            return ((LivingEntity)Entityy).getMainHandItem().is(ModItems.SABER_GAUNTLET.get()) && ((LivingEntity) Entityy).getMainHandItem().getOrCreateTag().getBoolean("ActiveBoiii") && ((LivingEntity)Entityy).isUsingItem();
        return false;
    }

    public static boolean checkForSaberEquipment(Entity Entityy, boolean Mainhand){
        if(Entityy instanceof LivingEntity) {
            if(Mainhand) return ((LivingEntity) Entityy).getMainHandItem().is(ModItems.SABER_GAUNTLET.get()) && ((LivingEntity) Entityy).getMainHandItem().getOrCreateTag().getBoolean("ActiveBoiii");
            return ((LivingEntity) Entityy).getOffhandItem().is(ModItems.SABER_GAUNTLET.get()) && ((LivingEntity) Entityy).getOffhandItem().getOrCreateTag().getBoolean("ActiveBoiii");
        }
        return false;
    }

}
