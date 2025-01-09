package com.epxzzy.createsaburs.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.*;

import java.util.Iterator;
import java.util.List;

/*
public class protwosabur extends ShieldItem {
    String TAG_COLOR = "color";
    String TAG_DISPLAY = "display";
    int DEFAULT_LEATHER_COLOR = 10511680;

    boolean hasCustomColor(ItemStack pStack) {
        CompoundTag $$1 = pStack.getTagElement("display");
        return $$1 != null && $$1.contains("color", 99);
    }

    public int getColor(ItemStack pStack) {
        CompoundTag $$1 = pStack.getTagElement("display");
        return $$1 != null && $$1.contains("color", 99) ? $$1.getInt("color") : 10511680;
    }

    void clearColor(ItemStack pStack) {
        CompoundTag $$1 = pStack.getTagElement("display");
        if ($$1 != null && $$1.contains("color")) {
            $$1.remove("color");
        }

    }

    void setColor(ItemStack pStack, int pColor) {
        pStack.getOrCreateTagElement("display").putInt("color", pColor);
    }

    static ItemStack dyeArmor(ItemStack pStack, List<DyeItem> pDyes) {
        ItemStack $$2 = ItemStack.EMPTY;
        int[] $$3 = new int[3];
        int $$4 = 0;
        int $$5 = 0;
        Item $$7 = pStack.getItem();
        int $$17;
        float $$20;
        int $$16;
        if ($$7 instanceof DyeableLeatherItem $$6) {
            $$2 = pStack.copyWithCount(1);
            if ($$6.hasCustomColor(pStack)) {
                $$17 = $$6.getColor($$2);
                float $$9 = (float)($$17 >> 16 & 255) / 255.0F;
                float $$10 = (float)($$17 >> 8 & 255) / 255.0F;
                $$20 = (float)($$17 & 255) / 255.0F;
                $$4 += (int)(Math.max($$9, Math.max($$10, $$20)) * 255.0F);
                $$3[0] += (int)($$9 * 255.0F);
                $$3[1] += (int)($$10 * 255.0F);
                $$3[2] += (int)($$20 * 255.0F);
                ++$$5;
            }

            for(Iterator var14 = pDyes.iterator(); var14.hasNext(); ++$$5) {
                DyeItem $$12 = (DyeItem)var14.next();
                float[] $$13 = $$12.getDyeColor().getTextureDiffuseColors();
                int $$14 = (int)($$13[0] * 255.0F);
                int $$15 = (int)($$13[1] * 255.0F);
                $$16 = (int)($$13[2] * 255.0F);
                $$4 += Math.max($$14, Math.max($$15, $$16));
                $$3[0] += $$14;
                $$3[1] += $$15;
                $$3[2] += $$16;
            }
        }

        if ($$6 == null) {
            return ItemStack.EMPTY;
        } else {
            $$17 = $$3[0] / $$5;
            int $$18 = $$3[1] / $$5;
            int $$19 = $$3[2] / $$5;
            $$20 = (float)$$4 / (float)$$5;
            float $$21 = (float)Math.max($$17, Math.max($$18, $$19));
            $$17 = (int)((float)$$17 * $$20 / $$21);
            $$18 = (int)((float)$$18 * $$20 / $$21);
            $$19 = (int)((float)$$19 * $$20 / $$21);
            $$16 = ($$17 << 8) + $$18;
            $$16 = ($$16 << 8) + $$19;
            $$6.setColor($$2, $$16);
            return $$2;
        }
    }
    public protwosabur(Properties pProperties) {
        super(pProperties);
    }
}
*/