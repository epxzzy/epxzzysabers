package com.epxzzy.createsaburs.item;

import com.epxzzy.createsaburs.createsaburs;
import com.epxzzy.createsaburs.rendering.ProtosaberItemRenderer;
import com.epxzzy.createsaburs.sound.ModSounds;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.mojang.blaze3d.platform.InputConstants;
import com.simibubi.create.foundation.item.render.SimpleCustomRenderer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class protosaber extends Item {
    public static final int maxStackSize = 1;
    public static final int EFFECTIVE_BLOCK_DELAY = 5;
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;
    public static final float MINIMUM_DURABILITY_DAMAGE = 3.0F;

    public static ProtosaberItemRenderer THE_RENDURR;

    private boolean Active = false;

    public protosaber(Properties pProperties) {
        super(pProperties);
        float attackDamage = (float) 5f;
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double) 0, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", (double) -1f, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }

    public boolean readtag(ItemStack pStack) {
        CompoundTag temp = pStack.getOrCreateTag();
        boolean temp2 = temp.getBoolean("ActiveBoiii");
        //createsaburs.LOGGER.info("read nbt as " + temp2);
        return temp2;
    }

    public void writetag(ItemStack pStack, boolean bool) {
        CompoundTag ea = new CompoundTag();
        ea.putBoolean("ActiveBoiii", bool);
        pStack.setTag(ea);
        createsaburs.LOGGER.info("wrote nbt as" + bool);
    }


    public void ToggleSaberCore(Level pLevel, Player pPlayer, ItemStack pStack) {
        CompoundTag nbeetea = pStack.getOrCreateTag();
        if(!pLevel.isClientSide) {
            if (!readtag(pStack)) {
                createsaburs.LOGGER.info("HARD");
                //pStack.addAttributeModifier(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double) 10, AttributeModifier.Operation.ADDITION),EquipmentSlot.MAINHAND);
                //nbeetea.putInt("CustomModelData", 1);
                nbeetea.putBoolean("ActiveBoiii", true);
                //writetag(pStack, true);
                Active = true;
            } else {
                createsaburs.LOGGER.info("NO LONGER HARD");
                //createsaburs.LOGGER.info(nbeetea.);
                //pStack.getAttributeModifiers(EquipmentSlot.MAINHAND);
                //Attributes.ATTACK_DAMAGE,new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double) 0.05, AttributeModifier.Operation.),EquipmentSlot.MAINHAND);
                //nbeetea.putBoolean("BlockBoiii", false);
                //nbeetea.remove("ActiveBoiii");
                writetag(pStack, false);
                Active = false;

            }

            pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(),
                    (Active ? ModSounds.ACTIVATION.get() : ModSounds.DEACTIVATION.get()), SoundSource.NEUTRAL, 0.5f, 1f
            );
        }
    }


    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {

    }

    public boolean isPlayerPressing(int pInputConstraint, Level pLevel) {
        if (pLevel.isClientSide) {
            long winhandl = Minecraft.getInstance().getWindow().getWindow();
            return InputConstants.isKeyDown(winhandl, pInputConstraint) || InputConstants.isKeyDown(winhandl, pInputConstraint);
        } else {
            return false;
        }
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, @NotNull InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (readtag(pPlayer.getItemInHand(pHand)) && !pLevel.isClientSide) {
            CompoundTag nbeetea = itemstack.getOrCreateTag();
            //nbeetea.putBoolean("BlockBoiii", true);
            pPlayer.startUsingItem(pHand);
            //return ItemUtils.startUsingInstantly(pLevel, pPlayer, pHand);
        }
        if (pPlayer.isShiftKeyDown() && pHand == InteractionHand.MAIN_HAND) {
                ToggleSaberCore(pLevel, pPlayer, itemstack);
                return InteractionResultHolder.fail(pPlayer.getItemInHand(pHand));
        }
        return InteractionResultHolder.fail(itemstack);
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        //createsaburs.LOGGER.info("stopped blocking, changing custom model data");
        //CompoundTag nbeetea = pStack.getOrCreateTag();
        //nbeetea.putBoolean("BlockBoiii", false);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        MutableComponent ActiveDetail = Component.literal(readtag(stack) ? "On" : "Off")
                .withStyle(readtag(stack) ? ChatFormatting.WHITE : ChatFormatting.GRAY);
        tooltip.add(ActiveDetail);
    }

    @Override
    public boolean canPerformAction(ItemStack stack, net.minecraftforge.common.ToolAction toolAction) {
        if (toolAction == createsaburs.SABER_SWING) return true;
        return net.minecraftforge.common.ToolActions.DEFAULT_SHIELD_ACTIONS.contains(toolAction);
        // return false;
    }

    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot pEquipmentSlot) {
        return pEquipmentSlot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(pEquipmentSlot);
    }


    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack pStack) {
        if (readtag(pStack)) return UseAnim.BLOCK;
        else return UseAnim.NONE;
    }

    public int getUseDuration(@NotNull ItemStack pStack) {
        return 72000;
    }

    public boolean isActive(CompoundTag tagg) {
        if (tagg != null) return tagg.getBoolean("ActiveBoiii");
        return Active;
    }

    public boolean isValidRepairItem(@NotNull ItemStack pToRepair, @NotNull ItemStack pRepair) {
        return false;
    }


    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        THE_RENDURR = new ProtosaberItemRenderer();
        consumer.accept(SimpleCustomRenderer.create(this, THE_RENDURR));
    }
}
