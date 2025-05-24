package com.epxzzy.createsaburs.item.saburtypes;

import com.epxzzy.createsaburs.createsaburs;
import com.epxzzy.createsaburs.item.Protosaber;
import com.epxzzy.createsaburs.rendering.InquisitoriusItemRenderer;
import com.epxzzy.createsaburs.utils.ModTags;
import com.simibubi.create.foundation.item.render.SimpleCustomRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Consumer;

public class RotarySaber extends Protosaber {
    public int flyCooldown = 40;
    //300 == cant fly, 0 == can fly
    //public int flightDuration = 40;
    //0 == no more fly, 300 == flyyy

    public RotarySaber(Properties pProperties, int pRANGE, int pDamage, int pSpeed) {
        super(pProperties, pRANGE , pDamage, pSpeed);
    }
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        THE_RENDURR = new InquisitoriusItemRenderer();
        consumer.accept(SimpleCustomRenderer.create(this, THE_RENDURR));
    }

    public static int getColor(ItemStack pStack) {
        CompoundTag compoundtag = pStack.getOrCreateTagElement("display");
        if (compoundtag.getInt("color") == 0) {
            //setColor(pStack, 65280);
            return 16724787;
        }
        return Objects.requireNonNull(pStack.getTagElement("display")).getInt("color");
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
        if(pEntity instanceof LivingEntity pLiving && !(pLevel.isClientSide())){
/*
            if(pLiving instanceof Player pPlayer && pPlayer.getAbilities().flying){
                --this.flightDuration;
                if(this.flightDuration >= 0){
                    pPlayer.stopUsingItem();
                    createsaburs.LOGGER.info("you can no longer fly");
                    this.flightDuration = 40;
                }
            }

 */
            if(pLiving instanceof Player pPlayer && !(pPlayer.getAbilities().flying)){
                if(this.flyCooldown >= 1){
                    --this.flyCooldown;

                    if(this.flyCooldown == 0) createsaburs.LOGGER.info("you can now fly");
                }
            }

        }
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, @NotNull InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);

        if (readActivetag(pPlayer.getItemInHand(pHand)) && !pLevel.isClientSide) {
            if((pPlayer.xRotO < -35 || pLevel.getBlockState(pPlayer.blockPosition().below(3)).isAir())&&this.flyCooldown == 0){
               pPlayer.getAbilities().flying = true;
               pPlayer.onUpdateAbilities();
               //this.flyCooldown = 40;
               createsaburs.LOGGER.info("flying activated");


            }
            if(this.flyCooldown >= 1){
               createsaburs.LOGGER.info("you cant seem to fly, flightcooldown: "+this.flyCooldown+ " and flightduration: " +"plugged out");
            }
            pPlayer.startUsingItem(pHand);
        }
        if (pPlayer.isShiftKeyDown() && pHand == InteractionHand.MAIN_HAND) {
            super.ToggleSaberCore(pLevel, pPlayer, itemstack);
            pPlayer.stopUsingItem();
            return InteractionResultHolder.fail(pPlayer.getItemInHand(pHand));
        }
        return InteractionResultHolder.fail(itemstack);
    }

    @Override
    public void onStopUsing(ItemStack pStack, LivingEntity entity, int count) {
        CompoundTag nbeetea = pStack.getOrCreateTag();
        nbeetea.putBoolean("BlockBoiii", false);

        nbeetea.putBoolean("FlyBoiii", false);
        if (entity instanceof Player){
            ((Player) entity).getAbilities().flying = false;
            ((Player) entity).onUpdateAbilities();
            createsaburs.LOGGER.info("flying deactivated");
            this.flyCooldown = 40;
        }

        pStack.setTag(nbeetea);
        super.onStopUsing(pStack, entity, count);
    }

    public static boolean checkForSaberEquipment(Entity Entityy, boolean Mainhand){
        if(Entityy instanceof LivingEntity) {
            if(Mainhand) return ((LivingEntity) Entityy).getMainHandItem().is(ModTags.Items.CREATE_ROTARY_SABER) && ((LivingEntity) Entityy).getMainHandItem().getOrCreateTag().getBoolean("ActiveBoiii");
            return ((LivingEntity) Entityy).getOffhandItem().is(ModTags.Items.CREATE_ROTARY_SABER) && ((LivingEntity) Entityy).getOffhandItem().getOrCreateTag().getBoolean("ActiveBoiii");
        }
        return false;
    }

    public static boolean checkForSaberBlock(Player Entityy){
        /*
        createsaburs.LOGGER.info("first:" +Entityy.getMainHandItem().is(ModTags.Items.CREATE_ROTARY_SABER));
        createsaburs.LOGGER.info("second:" +Entityy.getMainHandItem().getOrCreateTag().getBoolean("ActiveBoiii") );
        createsaburs.LOGGER.info("third:" +Entityy.isUsingItem());

         */
        //Entityy.getMainHandItem().is(RotarySaber)
        return Entityy.getMainHandItem().is(ModTags.Items.CREATE_ROTARY_SABER) &&  Entityy.getMainHandItem().getOrCreateTag().getBoolean("ActiveBoiii") && Entityy.isUsingItem();
    }
    public static boolean checkForSaberFly(Entity Entityy){
        if(Entityy instanceof Player)
            return ((Player)Entityy).getMainHandItem().is(ModTags.Items.CREATE_ROTARY_SABER) && ((Player) Entityy).getAbilities().flying ;
        return false;
    }


}
