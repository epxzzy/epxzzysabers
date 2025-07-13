package com.epxzzy.createsaburs.item;

import com.epxzzy.createsaburs.block.ModBlocks;
import com.epxzzy.createsaburs.createsaburs;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, createsaburs.MOD_ID);

    public static final RegistryObject<CreativeModeTab> TUTORIAL_TAB = CREATIVE_MODE_TABS.register("moditums",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.Protosaber.get()))
                    .title(Component.translatable("createsaburs.moditemsiglol"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.Protosaber.get());
                        pOutput.accept(ModItems.SINGLE_BLADED_SABER.get());
                        pOutput.accept(ModItems.DUAL_BLADED_SABER.get());
                        pOutput.accept(ModItems.ROTARY_SABER.get());
                        pOutput.accept(ModItems.CROSSGUARD_SABER.get());
                        pOutput.accept(ModItems.BLASTER_HYBRID.get());

                        //pOutput.accept(ModItems.mognet.get());

                        //pOutput.accept(Items.DIAMOND);

                        pOutput.accept(ModBlocks.KYBERSTATION.get());
                        //pOutput.accept(ModItems.protosabur2.get());

                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
