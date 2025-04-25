package com.epxzzy.createsaburs.screen;


import com.epxzzy.createsaburs.block.ModBlocks;
import com.epxzzy.createsaburs.createsaburs;
import com.epxzzy.createsaburs.misc.ColourConverter;
import com.epxzzy.createsaburs.sound.ModSounds;
import com.epxzzy.createsaburs.utils.ModTags;
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
import org.jetbrains.annotations.NotNull;

public class KyberStationMenu extends AbstractContainerMenu {
    private final ContainerLevelAccess access;
    private final Slot saber_slot;
    private final Slot krystal_slot;
    private final Slot resultSlot;
    public ItemStack lastCachedItemStacc = null;
    public int CachedColour = 0;
    public boolean isColourCached = false;
    private final DataSlot[] ColourValueIndexes = {DataSlot.standalone(), DataSlot.standalone(), DataSlot.standalone()};
    Runnable slotUpdateListener = () -> {
    };
    Runnable inputUpdateListener = () -> {
    };

    private final Container inputContainer = new SimpleContainer(2) {
        /**
         * For block entities, ensures the chunk containing the block entity is saved to disk later - the game won't think
         * it hasn't changed and skip it.
         */
        public void setChanged() {
            super.setChanged();
            KyberStationMenu.this.slotsChanged(this);
            KyberStationMenu.this.slotUpdateListener.run();
            KyberStationMenu.this.inputUpdateListener.run();
        }
    };
    private final Container outputContainer = new SimpleContainer(1) {
        /**
         * For block entities, ensures the chunk containing the block entity is saved to disk later - the game won't think
         * it hasn't changed and skip it.
         */
        public void setChanged() {
            super.setChanged();
            KyberStationMenu.this.slotUpdateListener.run();
        }
    };

    public KyberStationMenu(int pContainerId, Inventory inv, FriendlyByteBuf ffff) {
        this(pContainerId, inv, ContainerLevelAccess.NULL);
    }

    public KyberStationMenu(int pContainerId, Inventory playerinv, final ContainerLevelAccess pAccess) {
        super(ModMenuTypes.SKREEN.get(), pContainerId);
        checkContainerSize(playerinv, 2);
        playerinv.player.level();
        this.access = pAccess;

        this.saber_slot = this.addSlot(new Slot(this.inputContainer, 0, 8, 59){
            @Override
            public boolean mayPlace(ItemStack pStack) {
                return pStack.is(ModTags.Items.CREATE_DYEABLE_LIGHTSABER);
            }
        });
        this.krystal_slot = this.addSlot(new Slot(this.inputContainer, 1, 44, 59){
            @Override
            public boolean mayPlace(ItemStack pStack) {
                return pStack.is(ModTags.Items.CREATE_KYBER_CRYSTAL);
            }

        });

        this.resultSlot = this.addSlot(new Slot(this.outputContainer, 0, 152, 59) {
            public boolean mayPlace(@NotNull ItemStack stacc) {
                return false;
            }

            public void onTake(@NotNull Player pPlayer, @NotNull ItemStack stacc) {
                KyberStationMenu.this.saber_slot.remove(1);
                KyberStationMenu.this.krystal_slot.remove(1);
                pAccess.execute((a, b) -> {
                    a.playSound((Player) null, b, ModSounds.CLASH.get(), SoundSource.PLAYERS, 1, 1);
                });
                createsaburs.LOGGER.warn("taken??");
                super.onTake(pPlayer, stacc);
            }
        });

        addPlayerInventory(playerinv);
        addPlayerHotbar(playerinv);

        this.addDataSlot(ColourValueIndexes[0]);
        this.addDataSlot(ColourValueIndexes[1]);
        this.addDataSlot(ColourValueIndexes[2]);
    }


    public boolean canCraft() {
        return !this.inputContainer.getItem(0).isEmpty() ^ !this.inputContainer.getItem(1).isEmpty();
    }

    public boolean setItemColour(int[] colour) {
        if (this.ColourValueIndexes[2].get() == colour[0] && this.ColourValueIndexes[1].get() == colour[1] && this.ColourValueIndexes[0].get() == colour[2]) {
            return false;
        } else {
            this.ColourValueIndexes[0].set(colour[0]);
            this.ColourValueIndexes[1].set(colour[1]);
            this.ColourValueIndexes[2].set(colour[2]);

            //this.ColourValueIndexes[0].set(a);
            //this.ColourValueIndexes[1].set(b);
            //this.ColourValueIndexes[2].set(c);
            //this.broadcastChanges();
            createsaburs.LOGGER.warn("colours have been set as: " + colour[0] + " " + colour[1] + " " + colour[2]);
            this.setupResultSlot(colour[0], colour[1], colour[2]);
            return true;

        }
        /*
        if(a==0 && b==0 && c==0){
            //return;
        }
         */
    }

    public boolean setItemColourButBetter(int colour) {
        //this.ColourValueIndexes[0].set(a);
        //this.ColourValueIndexes[1].set(b);
        //this.ColourValueIndexes[2].set(c);
        //this.broadcastChanges();
        //createsaburs.LOGGER.warn("colours have been set as: "+colour[0]+" "+colour[1]+" "+colour[2]);
        this.setupResultSlotButBetter(colour);
        return true;
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
            } else if ((pIndex == this.saber_slot.index || (pIndex == this.krystal_slot.index))) {
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

    @Override
    public void removed(@NotNull Player pPlayer) {
        super.removed(pPlayer);
        this.access.execute((guh1, guh2) -> {
            this.clearContainer(pPlayer, this.inputContainer);
        });
    }

    @Override
    public void slotsChanged(@NotNull Container pContainer) {
        ItemStack saber = this.saber_slot.getItem();
        ItemStack krystal = this.krystal_slot.getItem();
        if (!saber.isEmpty()) {
            if (!krystal.isEmpty()) {
                createsaburs.LOGGER.warn("THE MERGE");
                return;
            } else {
                //createsaburs.LOGGER.warn("THE FORGE");
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
            createsaburs.LOGGER.warn("THE ZA ZA KRYSTALLL");
            return;
        }
        this.resultSlot.set(ItemStack.EMPTY);
        super.slotsChanged(pContainer);
    }

    public void setupResultSlot(int r, int g, int b) {
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

        ItemStack base = this.saber_slot.getItem().copy();
        CompoundTag taggussy = base.getOrCreateTag();

        createsaburs.LOGGER.warn("crafting colours " + r + " " + g + " " + b);

        taggussy.getCompound("display").putInt("color", colour);
        base.setTag(taggussy);

        this.broadcastChanges();
        //if(this.lastCachedItemStacc == null){
        //    lastCachedItemStacc = base;
        //}
        //this.outputContainer.setItem(base);
        this.resultSlot.set(base);
    }

    public void setupResultSlotButBetter(int colooorrr) {
        int[] baseInput = this.getInputColour();
        if (!(ColourConverter.portedRGBtoDecimal(baseInput) == colooorrr)) {
            ItemStack base = this.saber_slot.getItem().copy();
            CompoundTag taggussy = base.getOrCreateTag();
            taggussy.getCompound("display").putInt("color", colooorrr);
            base.setTag(taggussy);

            this.broadcastChanges();
            this.resultSlot.set(base);
        }
    }

    public int[] getInputColour() {
        return ColourConverter.PortedDecimaltoRGB(this.saber_slot.getItem().getOrCreateTag().getCompound("display").getInt("color"));
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
        return this.inputContainer.isEmpty() ? 0 : this.saber_slot.index;
    }

    public int getResultSlot() {
        return this.resultSlot.index;
    }

    private int getInventorySlotStart() {
        return this.getResultSlot() + 1;
    }

    private int getInventorySlotEnd() {
        return this.getInventorySlotStart() + 27;
    }

    private int getUseRowStart() {
        return this.getInventorySlotEnd();
    }

    private int getUseRowEnd() {
        return this.getUseRowStart() + 9;
    }
}