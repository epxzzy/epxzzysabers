package dev.epxzzy.epxzzysabers.mixin;

import dev.epxzzy.epxzzysabers.Constants;
import dev.epxzzy.epxzzysabers.core.foundation.ItemRendererRegistry;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemMixin {

    @Inject(
        method = "getRenderPropertiesInternal",
        at = @At("HEAD"),
        cancellable = true,
        remap = false
    )
    private void epxzzysabers$getRenderProperties(
        CallbackInfoReturnable<Object> cir
    ) {
        Constants.LOG.info("FKCRT Item::getRenderProperties mixin");

        Item item = (Item) (Object) this;

        BlockEntityWithoutLevelRenderer renderer =
            ItemRendererRegistry.getRendererMap().get(item);

        if (renderer == null) {
            return;
        }

        cir.setReturnValue(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return renderer;
            }
        });
    }
}