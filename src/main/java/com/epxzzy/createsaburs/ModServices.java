package com.epxzzy.createsaburs;

import com.epxzzy.createsaburs.misc.services.RegisteredObjectsHelper;
import net.createmod.ponder.Ponder;

import java.util.ServiceLoader;

public class ModServices {

    public static final RegisteredObjectsHelper<?> REGISTRIES = load(RegisteredObjectsHelper.class);

    public static <T> T load(Class<T> clazz) {
        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("FKCRT Failed to load service for " + clazz.getName()));
        //Ponder.LOGGER.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }

}
