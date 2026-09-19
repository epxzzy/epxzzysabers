package dev.epxzzy.epxzzysabers.mixin;

import dev.epxzzy.epxzzysabers.core.foundation.ItemRendererRegistry;
import dev.epxzzy.epxzzysabers.core.item.ISaberItem;
import dev.epxzzy.epxzzysabers.core.util.colourUtils;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemColors.class)
public class ItemColorsMixin {

    @Inject(
        method = "createDefault", 
        at = @At("RETURN")
    )
    private static void onCreateDefault(BlockColors blockColors, CallbackInfoReturnable<ItemColors> cir) {
        ItemColors ic = cir.getReturnValue();

        ItemRendererRegistry.getRendererMap().forEach(((item, customRenderedSaberModelRenderer) -> {
            ic.register((itemStack, tintIndex) -> {
                return colourUtils.portedRGBtoDecimal(colourUtils.rainbowColor((int) System.currentTimeMillis()));
            }, item);
        }));
    }
}
