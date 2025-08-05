package com.epxzzy.createsaburs.screen;


import com.epxzzy.createsaburs.CreateSaburs;
import com.epxzzy.createsaburs.screen.flourish.KyberStationFlourishMenu;
import com.epxzzy.createsaburs.screen.stance.KyberStationStanceMenu;
import com.epxzzy.createsaburs.screen.stance.KyberStationStanceScreen;
import com.epxzzy.createsaburs.screen.tint.KyberStationTintMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, CreateSaburs.MOD_ID);

    public static final RegistryObject<MenuType<KyberStationTintMenu>> SKREEN_TINT=
            registerMenuType("kyber_station_menu_tint", KyberStationTintMenu::new);
    public static final RegistryObject<MenuType<KyberStationStanceMenu>> SKREEN_STANCE=
            registerMenuType("kyber_station_menu_stance", KyberStationStanceMenu::new);
    public static final RegistryObject<MenuType<KyberStationFlourishMenu>> SKREEN_FLOURISH=
            registerMenuType("kyber_station_menu_flourish", KyberStationFlourishMenu::new);




    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}