package dev.epxzzy.epxzzysabers.content.sabers.SingleBladed;

import dev.epxzzy.epxzzysabers.Constants;
import dev.epxzzy.epxzzysabers.content.sabers.Proto.Protosaber;
import dev.epxzzy.epxzzysabers.core.foundation.net.packets.ballitchh;
import dev.epxzzy.epxzzysabers.core.foundation.visual.CustomRenderedSaberModelRenderer;
import dev.epxzzy.epxzzysabers.core.util.colourUtils;
import dev.epxzzy.epxzzysabers.platform.Services;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SingleBladeSaberItem extends Protosaber {
    public SingleBladeSaberItem(Properties properties) {
        super(properties);
    }

    @Override
    public CustomRenderedSaberModelRenderer getRenderer() {
        return new SingleBladedItemRenderer();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if(level.isClientSide){
            Services.NetowrkHelper.sendToServer(new ballitchh("my dih itch", true));
        }
        return super.use(level, player, usedHand);
    }

    @Override
    public int getColour() {
        Constants.LOG.info("called");
        return colourUtils.portedRGBtoDecimal(colourUtils.rainbowColor((int) System.currentTimeMillis()));
    }
}
