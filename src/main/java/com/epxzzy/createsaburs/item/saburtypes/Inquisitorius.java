package com.epxzzy.createsaburs.item.saburtypes;

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

public class Inquisitorius extends Protosaber {

    public Inquisitorius(Properties pProperties, int pRANGE, int pDamage, int pSpeed) {
        super(pProperties, 3 , 14, 2);
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

    public static boolean checkForSaberBlock(Entity Entityy){
        if(Entityy instanceof LivingEntity)
            return ((LivingEntity)Entityy).getMainHandItem().is(ModTags.Items.CREATE_SINGLE_BLADED) && ((LivingEntity) Entityy).getMainHandItem().getOrCreateTag().getBoolean("ActiveBoiii") && ((LivingEntity)Entityy).isUsingItem();
        return false;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, @NotNull InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (readActivetag(pPlayer.getItemInHand(pHand)) && !pLevel.isClientSide) {
            pPlayer.startUsingItem(pHand);
            //return ItemUtils.startUsingInstantly(pLevel, pPlayer, pHand);
        }
        if (pPlayer.isShiftKeyDown() && pHand == InteractionHand.MAIN_HAND) {
            ToggleSaberCore(pLevel, pPlayer, itemstack);
            pPlayer.stopUsingItem();
            return InteractionResultHolder.fail(pPlayer.getItemInHand(pHand));
        }
        return InteractionResultHolder.fail(itemstack);
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        //createsaburs.LOGGER.info("stopped blocking, changing custom model data");
        CompoundTag nbeetea = pStack.getOrCreateTag();
        if(pLevel.getBlockState(pLivingEntity.blockPosition().below()).isAir()){
            nbeetea.putBoolean("blockboiii", false);
        }
        if(!(pLevel.getBlockState(pLivingEntity.blockPosition().below()).isAir())){
            nbeetea.putBoolean("flyboiii", false);
        }

        pStack.setTag(nbeetea);
    }

    @Override
    public void onStopUsing(ItemStack pStack, LivingEntity entity, int count) {
        CompoundTag nbeetea = pStack.getOrCreateTag();
        nbeetea.putBoolean("BlockBoiii", false);
        pStack.setTag(nbeetea);
        super.onStopUsing(pStack, entity, count);
    }

    public static boolean checkForSaberEquipment(Entity Entityy, boolean Mainhand){
        if(Entityy instanceof LivingEntity) {
            if(Mainhand) return ((LivingEntity) Entityy).getMainHandItem().is(ModTags.Items.CREATE_SINGLE_BLADED) && ((LivingEntity) Entityy).getMainHandItem().getOrCreateTag().getBoolean("ActiveBoiii");
            return ((LivingEntity) Entityy).getOffhandItem().is(ModTags.Items.CREATE_SINGLE_BLADED) && ((LivingEntity) Entityy).getOffhandItem().getOrCreateTag().getBoolean("ActiveBoiii");
        }
        return false;
    }

}
