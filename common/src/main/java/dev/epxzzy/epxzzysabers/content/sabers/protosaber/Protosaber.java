package dev.epxzzy.epxzzysabers.content.sabers.protosaber;

import dev.epxzzy.epxzzysabers.core.foundation.ItemRendererRegistry;
import dev.epxzzy.epxzzysabers.core.item.SingleBladedItemRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class Protosaber extends Item {
    public Protosaber(Properties properties) {
        super(properties);
        ItemRendererRegistry.register(this, new SingleBladedItemRenderer());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        player.displayClientMessage(Component.literal("laughing my balls off"), true);
        return super.use(level, player, usedHand);
    }
}
