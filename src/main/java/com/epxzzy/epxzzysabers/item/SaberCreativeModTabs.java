package com.epxzzy.epxzzysabers.item;

import com.epxzzy.epxzzysabers.block.SaberBlocks;
import com.epxzzy.epxzzysabers.epxzzySabers;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class SaberCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, epxzzySabers.MOD_ID);

    public static final RegistryObject<CreativeModeTab> SABERS_TAB = CREATIVE_MODE_TABS.register("moditums",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(SaberItems.Protosaber.get()))
                    .title(Component.translatable("epxzzysabers.moditemsiglol"))
                    .displayItems((pParameters, pOutput) -> {
                        //pOutput.accept(SaberItems.Protosaber.get());
                        pOutput.accept(SaberItems.SINGLE_BLADED_SABER.get());
                        pOutput.accept(SaberItems.DUAL_BLADED_SABER.get());
                        pOutput.accept(SaberItems.ROTARY_SABER.get());
                        pOutput.accept(SaberItems.CROSSGUARD_SABER.get());
                        pOutput.accept(SaberItems.BLASTER_SABER.get());
                        pOutput.accept(SaberItems.SABER_PIKE.get());
                        pOutput.accept(SaberItems.SABER_GAUNTLET.get());

                        pOutput.accept(SaberItems.KRYSTAL.get());


                        pOutput.accept(SaberBlocks.KYBERSTATION.get());
                        //pOutput.accept(SaberItems.protosabur2.get());

                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
