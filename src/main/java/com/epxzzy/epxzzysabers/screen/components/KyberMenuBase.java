package com.epxzzy.epxzzysabers.screen.components;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class KyberMenuBase extends AbstractContainerMenu {
    public BlockPos blockPos;
    public ContainerLevelAccess access;
    protected KyberMenuBase(@Nullable MenuType<?> pMenuType,BlockPos pos, int pContainerId) {
        super(pMenuType, pContainerId);
        this.blockPos = pos;
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return null;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return false;
    }
}
