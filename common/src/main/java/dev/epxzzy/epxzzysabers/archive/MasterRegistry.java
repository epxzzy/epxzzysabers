package dev.epxzzy.epxzzysabers.registration;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class MasterRegistry {
    //
    public static final List<ISlaveRegistry> REGISTRIES = new ArrayList<>();

    public static ISlaveRegistry create(ResourceKey<? extends Registry<?>> loc){
        //ISlaveRegistry reg = PlatformRegistryFactory
        //REGISTRIES.add()
        return null;
    }

}
