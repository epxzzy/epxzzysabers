package com.epxzzy.createsaburs.item;

import com.epxzzy.createsaburs.rendering.ProtosaberItemRenderer;
import com.mojang.blaze3d.platform.InputConstants;
import com.simibubi.create.foundation.item.render.SimpleCustomRenderer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class protosaber extends ShieldItem {
    private boolean isActive = false;
    public protosaber(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);

        if (pLevel.isClientSide) {
            long winhandl = Minecraft.getInstance().getWindow().getWindow();
            boolean ctrl = InputConstants.isKeyDown(winhandl, InputConstants.KEY_LCONTROL) || InputConstants.isKeyDown(winhandl, InputConstants.KEY_RCONTROL);
            if(ctrl && pHand == InteractionHand.MAIN_HAND) {
                isActive = !isActive;
                pPlayer.sendSystemMessage(Component.literal("Magnet is now " + (isActive ? "Active" : "Inactive")));
                return InteractionResultHolder.success(pPlayer.getItemInHand(pHand));
            }
        }
        if(isActive) {
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

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(SimpleCustomRenderer.create(this, new ProtosaberItemRenderer()));
    }
}
