package com.epxzzy.createsaburs.item;

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
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class protosaber extends Item {
    public static final int EFFECTIVE_BLOCK_DELAY = 5;
    private final float attackDamage;
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;
    public static final float MINIMUM_DURABILITY_DAMAGE = 3.0F;
    public static final String TAG_BASE_COLOR = "Base";

    private boolean isActive = false;

    public protosaber(Properties pProperties) {
        super(pProperties);
        this.attackDamage = (float) 5f;
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double) this.attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", (double) -1f, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (pLevel.isClientSide) {
            long winhandl = Minecraft.getInstance().getWindow().getWindow();
            boolean ctrl = InputConstants.isKeyDown(winhandl, InputConstants.KEY_LCONTROL) || InputConstants.isKeyDown(winhandl, InputConstants.KEY_RCONTROL);
            if (ctrl && pHand == InteractionHand.MAIN_HAND) {
                isActive = !isActive;
                pLevel.playSeededSound(pPlayer, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(),
                        (isActive ? ModSounds.ACTIVATION.get() : ModSounds.DEACTIVATION.get()), SoundSource.BLOCKS, 1f, 1f, 0
                );
                return InteractionResultHolder.success(pPlayer.getItemInHand(pHand));
            }
        }
        if (isActive) {
            pPlayer.startUsingItem(pHand);
            return InteractionResultHolder.consume(itemstack);
        }
        return InteractionResultHolder.fail(itemstack);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        MutableComponent rarityText = Component.literal(isActive ? "Active State" : "Inactive State")
                .withStyle(isActive ? ChatFormatting.AQUA : ChatFormatting.GRAY);
        tooltip.add(rarityText);
    }

    public boolean isValidRepairItem(ItemStack pToRepair, ItemStack pRepair) {
        return false;
    }

    @Override
    public boolean canPerformAction(ItemStack stack, net.minecraftforge.common.ToolAction toolAction) {
        return net.minecraftforge.common.ToolActions.DEFAULT_SHIELD_ACTIONS.contains(toolAction);
        // return false;
    }

    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BLOCK;
    }

    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot pEquipmentSlot) {
        return pEquipmentSlot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(pEquipmentSlot);
    }


    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(SimpleCustomRenderer.create(this, new ProtosaberItemRenderer()));
    }
}
