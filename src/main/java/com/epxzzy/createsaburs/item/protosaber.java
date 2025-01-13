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
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
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

    public void ToggleSaberCore(Level pLevel, Player pPlayer,ItemStack pStack){
        Active = !Active;
        CompoundTag nbeetea = pStack.getOrCreateTag();
        if (Active) {
            createsaburs.LOGGER.info("ADDING DAMAGE");
            pStack.addAttributeModifier(Attributes.ATTACK_DAMAGE,
                    new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double) 10, AttributeModifier.Operation.ADDITION),EquipmentSlot.MAINHAND);

            nbeetea.putInt("CustomModelData", 1);
        } else {
            createsaburs.LOGGER.info("REMOVING THEY DAMAGE");
            //createsaburs.LOGGER.info(nbeetea.);
            pStack.getAttributeModifiers(EquipmentSlot.MAINHAND);
        //Attributes.ATTACK_DAMAGE,
         //           new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double) 0.05, AttributeModifier.Operation.),EquipmentSlot.MAINHAND);

            nbeetea.remove("CustomModelData");
        }

        THE_RENDURR.SetSaberCoreState(Active);
        pLevel.playSound(pPlayer, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(),
                (Active ? ModSounds.ACTIVATION.get() : ModSounds.DEACTIVATION.get()), SoundSource.BLOCKS, 1f, 1f
        );


    }


    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {

    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, @NotNull InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (Active&& !pLevel.isClientSide) {
            pPlayer.startUsingItem(pHand);
            return InteractionResultHolder.fail(itemstack);
        }
        if (pLevel.isClientSide) {
            long winhandl = Minecraft.getInstance().getWindow().getWindow();
            boolean ctrl = InputConstants.isKeyDown(winhandl, InputConstants.KEY_LCONTROL) || InputConstants.isKeyDown(winhandl, InputConstants.KEY_RCONTROL);
            if (ctrl && pHand == InteractionHand.MAIN_HAND) {
                ToggleSaberCore(pLevel, pPlayer, itemstack);
                return InteractionResultHolder.fail(pPlayer.getItemInHand(pHand));
            }
        }
        return InteractionResultHolder.fail(itemstack);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        MutableComponent ActiveDetail = Component.literal(Active ? "On" : "Off")
                .withStyle(Active ? ChatFormatting.WHITE: ChatFormatting.GRAY);
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
        if(isActive()) return UseAnim.BLOCK;
        else return UseAnim.NONE;
    }

    public int getUseDuration(@NotNull ItemStack pStack) {
        return 72000;
    }
    public boolean isActive(){
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
