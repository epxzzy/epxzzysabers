package dev.epxzzy.epxzzysabers;

import dev.epxzzy.epxzzysabers.core.foundation.misc.SaberBindings;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

public class FabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        KeyBindingHelper.registerKeyBinding(SaberBindings.SABER_ABILITY_KEY);
        KeyBindingHelper.registerKeyBinding(SaberBindings.SABER_STANCE_KEY);


    }
}