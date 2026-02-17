package com.epxzzy.epxzzysabers.sound;

import com.epxzzy.epxzzysabers.epxzzySabers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SaberSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, epxzzySabers.MOD_ID);

    public static final RegistryObject<SoundEvent> ACTIVATION = registerSoundEvents("saber_activation");
    public static final RegistryObject<SoundEvent> DEACTIVATION = registerSoundEvents("saber_deactivation");
    public static final RegistryObject<SoundEvent> CLASH = registerSoundEvents("clash");
    public static final RegistryObject<SoundEvent> SWING = registerSoundEvents("swing");
    public static final RegistryObject<SoundEvent> SWING_RAPID = registerSoundEvents("swing_rapid");

    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        epxzzySabers.LOGGER.info("sounding the sound of "+ name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(epxzzySabers.MOD_ID, name)));

    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}