package com.epxzzy.createsaburs.misc;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import com.mojang.blaze3d.platform.InputConstants;

import javax.annotation.Nullable;
import java.util.List;

public class MagnetItem extends Item {
    private boolean isActive = false; // Simulate the toggle state.

    public MagnetItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pLevel.isClientSide) {
            long winhandl = Minecraft.getInstance().getWindow().getWindow();
            boolean ctrl = InputConstants.isKeyDown(winhandl, InputConstants.KEY_LCONTROL) || InputConstants.isKeyDown(winhandl, InputConstants.KEY_RCONTROL);
            if(ctrl && pUsedHand == InteractionHand.MAIN_HAND) {
                isActive = !isActive;
                pPlayer.sendSystemMessage(Component.literal("Magnet is now " + (isActive ? "Active" : "Inactive")));
                return InteractionResultHolder.success(pPlayer.getItemInHand(pUsedHand));
            }
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        MutableComponent rarityText = Component.literal(isActive ? "Active State" : "Inactive State")
                .withStyle(isActive ? ChatFormatting.AQUA : ChatFormatting.GRAY);
        tooltip.add(rarityText);
    }
}

