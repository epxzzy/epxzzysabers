package com.epxzzy.createsaburs.screen.stance;


import com.epxzzy.createsaburs.block.ModBlocks;
import com.epxzzy.createsaburs.CreateSaburs;
import com.epxzzy.createsaburs.screen.ModMenuTypes;
import com.epxzzy.createsaburs.screen.components.KyberMenuBase;
import com.epxzzy.createsaburs.screen.flourish.KyberStationFlourishMenu;
import com.epxzzy.createsaburs.utils.ColourConverter;
import com.epxzzy.createsaburs.sound.ModSounds;
import com.epxzzy.createsaburs.utils.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.checkerframework.checker.units.qual.K;
import org.jetbrains.annotations.NotNull;

public class KyberStationStanceMenu extends KyberMenuBase {
    public final ContainerLevelAccess access;
    public Slot input_slot;
    public Slot krystal_slot;
    public Slot resultSlot;

    Runnable slotUpdateListener = () -> {
    };
    Runnable inputUpdateListener = () -> {
    };

    private final Container inputContainer = new SimpleContainer(2) {
        public void setChanged() {
            super.setChanged();
            KyberStationStanceMenu.this.slotsChanged(this);
            KyberStationStanceMenu.this.slotUpdateListener.run();
            KyberStationStanceMenu.this.inputUpdateListener.run();
        }
    };
    private final Container outputContainer = new SimpleContainer(1) {
        public void setChanged() {
            super.setChanged();
            //KyberStationTintMenu.this.slotUpdateListener.run();
        }
    };

    public KyberStationStanceMenu(int pContainerId, Inventory inv, FriendlyByteBuf ffff) {
        this(pContainerId, inv, BlockPos.ZERO, ContainerLevelAccess.NULL);
    }

    public KyberStationStanceMenu(int pContainerId, Inventory inv, BlockPos pos, FriendlyByteBuf ffff) {
        this(pContainerId, inv, pos, ContainerLevelAccess.NULL);
    }

    public KyberStationStanceMenu(int pContainerId, Inventory playerinv, BlockPos pos, final ContainerLevelAccess pAccess) {
        super(ModMenuTypes.SKREEN_STANCE.get(), pos, pContainerId);
        checkContainerSize(playerinv, 2);
        playerinv.player.level();
        this.access = pAccess;
        this.input_slot = this.addSlot(new Slot(this.inputContainer, 0, 7, 8) {
            @Override
            public boolean mayPlace(ItemStack pStack) {
                return pStack.is(ModTags.Items.LIGHTSABER);
            }
        });
        this.krystal_slot = this.addSlot(new Slot(this.inputContainer, 1, 7, 28) {
            @Override
            public boolean mayPlace(ItemStack pStack) {
                return pStack.is(ModTags.Items.KYBER_CRYSTAL);
            }

        });
        this.resultSlot = this.addSlot( new Slot(this.outputContainer, 0, 153, 8) {
            public boolean mayPlace(@NotNull ItemStack stacc) {
                return false;
            }

            public void onTake(@NotNull Player pPlayer, @NotNull ItemStack stacc) {
                KyberStationStanceMenu.this.input_slot.remove(1);
                KyberStationStanceMenu.this.krystal_slot.remove(1);
                access.execute((a, b) -> {
                    a.playSound((Player) null, b, ModSounds.CLASH.get(), SoundSource.PLAYERS, 1, 1);
                });
                CreateSaburs.LOGGER.warn("taken??");
                super.onTake(pPlayer, stacc);
            }
        });

        addPlayerInventory(playerinv);
        addPlayerHotbar(playerinv);

    }


    public boolean canCraft() {
        return !this.inputContainer.getItem(0).isEmpty() ^ !this.inputContainer.getItem(1).isEmpty();
    }


    public boolean setItemColour(int[] colour, boolean gay) {
        return false;
    }


    @Override
    public void removed(@NotNull Player pPlayer) {
        super.removed(pPlayer);
        this.access.execute((guh1, guh2) -> {
            this.clearContainer(pPlayer, this.inputContainer);
        });
    }

    @Override
    public void slotsChanged(@NotNull Container pContainer) {
        ItemStack saber = this.input_slot.getItem();
        ItemStack krystal = this.krystal_slot.getItem();
        if (!saber.isEmpty()) {
            if (!krystal.isEmpty()) {
                CreateSaburs.LOGGER.warn("THE MERGE");
                return;
            } else {
                //CreateSaburs.LOGGER.warn("THE FORGE");
                /*
                setupResultSlot(
                        this.ColourValueIndexes[0].get(),
                        this.ColourValueIndexes[1].get(),
                        this.ColourValueIndexes[2].get()
                );
                 */
                this.broadcastChanges();

                return;
            }
        } else if (!krystal.isEmpty() && saber.isEmpty()) {
            CreateSaburs.LOGGER.warn("THE ZA ZA KRYSTALLL");
            return;
        }
        this.resultSlot.set(ItemStack.EMPTY);
        super.slotsChanged(pContainer);
    }

    public void setupResultSlot(int r, int g, int b, boolean gay) {
        //int colour;
        //if(isColourCached){
        //    colour = this.CachedColour;
        //this.resultSlot.set(this.lastCachedItemStacc);
        //return;
        //}
        //else {
        int colour = ColourConverter.portedRGBtoDecimal(new int[]{r, g, b});
        //    this.CachedColour = colour;
        //    this.isColourCached = true;
        //}

        ItemStack base = this.input_slot.getItem().copy();
        CompoundTag taggussy = base.getOrCreateTag();

        CreateSaburs.LOGGER.warn("crafting colours " + r + " " + g + " " + b);

        taggussy.getCompound("display").putInt("colour", colour);
        taggussy.getCompound("display").putBoolean("gay", gay);
        base.setTag(taggussy);

        this.broadcastChanges();
        //if(this.lastCachedItemStacc == null){
        //    lastCachedItemStacc = base;
        //}
        //this.outputContainer.setItem(base);
        this.resultSlot.set(base);
    }

    public int[] getInputColour() {
        return ColourConverter.PortedDecimaltoRGB(this.input_slot.getItem().getOrCreateTag().getCompound("display").getInt("colour"));
    }

    public boolean isInputGay() {
        return this.input_slot.getItem().getOrCreateTag().getCompound("display").getBoolean("gay");
    }

    public boolean stillValid(@NotNull Player pPlayer) {
        return stillValid(this.access, pPlayer, ModBlocks.KYBERSTATION.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    public void registerUpdateListener(Runnable pListener) {
        this.slotUpdateListener = pListener;
    }

    public void registerInputUpdateListener(Runnable pListener) {
        this.inputUpdateListener = pListener;
    }

    protected boolean canMoveIntoInputSlots(ItemStack pStack) {
        return true;
    }

    public int getSlotToQuickMoveTo(ItemStack pStack) {
        return this.inputContainer.isEmpty() ? 0 : this.input_slot.index;
    }

    public int getResultSlot() {
        return this.resultSlot.index;
    }

    public int getInventorySlotStart() {
        return this.getResultSlot() + 1;
    }

    public int getInventorySlotEnd() {
        return this.getInventorySlotStart() + 27;
    }

    public int getUseRowStart() {
        return this.getInventorySlotEnd();
    }

    public int getUseRowEnd() {
        return this.getUseRowStart() + 9;
    }
    @Override
    public @NotNull ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            int i = this.getInventorySlotStart();
            int j = this.getUseRowEnd();
            if (pIndex == this.getResultSlot()) {
                if (!this.moveItemStackTo(itemstack1, i, j, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            } else if ((pIndex == this.input_slot.index || (pIndex == this.krystal_slot.index))) {
                if (!this.moveItemStackTo(itemstack1, i, j, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.canMoveIntoInputSlots(itemstack1) && pIndex >= this.getInventorySlotStart() && pIndex < this.getUseRowEnd()) {
                int k = this.getSlotToQuickMoveTo(itemstack);
                if (!this.moveItemStackTo(itemstack1, k, this.getResultSlot(), false)) {
                    return ItemStack.EMPTY;
                }
            } else if (pIndex >= this.getInventorySlotStart() && pIndex < this.getInventorySlotEnd()) {
                if (!this.moveItemStackTo(itemstack1, this.getUseRowStart(), this.getUseRowEnd(), false)) {
                    return ItemStack.EMPTY;
                }
            } else if (pIndex >= this.getUseRowStart() && pIndex < this.getUseRowEnd() && !this.moveItemStackTo(itemstack1, this.getInventorySlotStart(), this.getInventorySlotEnd(), false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(pPlayer, itemstack1);
        }

        return itemstack;
    }

}