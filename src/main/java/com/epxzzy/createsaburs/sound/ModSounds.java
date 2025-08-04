package com.epxzzy.createsaburs.sound;

import com.epxzzy.createsaburs.CreateSaburs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, CreateSaburs.MOD_ID);

    public static final RegistryObject<SoundEvent> ACTIVATION = registerSoundEvents("saber_activation");
    public static final RegistryObject<SoundEvent> DEACTIVATION = registerSoundEvents("saber_deactivation");
    public static final RegistryObject<SoundEvent> CLASH = registerSoundEvents("clash");
    public static final RegistryObject<SoundEvent> SWING = registerSoundEvents("swing");

    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        CreateSaburs.LOGGER.info("sounding the sound of "+ name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(CreateSaburs.MOD_ID, name)));

    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}