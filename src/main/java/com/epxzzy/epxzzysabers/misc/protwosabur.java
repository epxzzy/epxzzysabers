package com.epxzzy.epxzzysabers.misc;

import com.epxzzy.epxzzysabers.item.SaberItems;
import com.epxzzy.epxzzysabers.item.Protosaber;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class protwosabur extends Protosaber {
    public protwosabur(Properties pProperties) {
        super(pProperties, 1,1,1);
    }
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        /*
        Minecraft.getInstance().getItemColors().register((stack, tintIndex) -> {
            if (tintIndex == 0) { // Tintindex 0 corresponds to the dyeable part
                CompoundTag tag = stack.getTag();
                if (tag != null && tag.contains("display", Tag.TAG_COMPOUND)) {
                    CompoundTag display = tag.getCompound("display");
                    if (display.contains("color", Tag.TAG_INT)) {
                        return display.getInt("color");
                    }
                }
                return 0xFFFFFF; // Default color (white)
            }
            return 0xFFFFFF; // Non-tinted
        }, SaberItems.protosabur2.get());

         */
    }

}
