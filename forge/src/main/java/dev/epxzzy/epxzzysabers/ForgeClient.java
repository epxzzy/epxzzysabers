package dev.epxzzy.epxzzysabers;

import dev.epxzzy.epxzzysabers.core.foundation.misc.SaberBindings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ForgeClient {
    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(SaberBindings.SABER_STANCE_KEY);
        event.register(SaberBindings.SABER_ABILITY_KEY);
    }
}
