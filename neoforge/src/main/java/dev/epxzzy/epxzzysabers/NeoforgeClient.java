package dev.epxzzy.epxzzysabers;

import com.mojang.datafixers.kinds.Const;
import dev.epxzzy.epxzzysabers.core.foundation.misc.SaberBindings;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@EventBusSubscriber(modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class NeoforgeClient {
    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        Constants.LOG.info("reg kez nufrog");
        event.register(SaberBindings.SABER_ABILITY_KEY);
        event.register(SaberBindings.SABER_STANCE_KEY);
    }
}
