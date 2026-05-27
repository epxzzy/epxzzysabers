package com.epxzzy.epxzzysabers;

import com.epxzzy.epxzzysabers.block.SaberBlocks;
import com.epxzzy.epxzzysabers.entity.SaberEntities;
import com.epxzzy.epxzzysabers.item.SaberCreativeModTabs;
import com.epxzzy.epxzzysabers.item.SaberItems;
import com.epxzzy.epxzzysabers.networking.SaberMessages;
import com.epxzzy.epxzzysabers.rendering.foundation.PartialModelEventHandler;
import com.epxzzy.epxzzysabers.screen.SaberMenuTypes;
import com.epxzzy.epxzzysabers.sound.SaberSounds;
import com.mojang.logging.LogUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.resource.PathPackResources;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(epxzzySabers.MOD_ID)
public class epxzzySabers{
    public static final String MOD_ID = "epxzzysabers";
    public static final Logger LOGGER = LogUtils.getLogger();

    public epxzzySabers() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);


        SaberItems.register(modEventBus);
        SaberBlocks.register(modEventBus);
        SaberMenuTypes.register(modEventBus);
        SaberSounds.register(modEventBus);
        SaberEntities.register(modEventBus);


        SaberCreativeModTabs.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);

        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> epxzzySabersClient::init);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> epxzzySabers.clientInit( MinecraftForge.EVENT_BUS, modEventBus));

        modEventBus.addListener(this::addPackFinders);

    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(()->{
            SaberMessages.register();
            //
        });

    }


    private static void clientInit(IEventBus forgeEventBus, IEventBus modEventBus) {
        modEventBus.addListener(PartialModelEventHandler::onRegisterAdditional);
        modEventBus.addListener(PartialModelEventHandler::onBakingCompleted);
        //epxzzySabers.LOGGER.debug("FKCRT PartialModelEventHandler events registered");
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }
    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    @SubscribeEvent
    public void addPackFinders(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.SERVER_DATA) {
            registerBuiltinDatapack(event, "bettercombat_compat", "epxzzysabers: BetterCombat compatibility");
        }
    }

    public static void registerBuiltinDatapack(AddPackFindersEvent event, String nam, String displayName) {
        try {
            java.nio.file.Path packPath = ModList.get()
                    .getModFileById(MOD_ID).getFile()
                    .findResource("builtin_datapacks", nam);
            Pack pack = Pack.readMetaAndCreate(
                    "builtin/" + nam,
                    Component.literal(displayName),
                    false,
                    id -> new PathPackResources(id, true, packPath),
                    PackType.SERVER_DATA,
                    Pack.Position.TOP,
                    PackSource.BUILT_IN
            );
            if (pack != null) {
                event.addRepositorySource(consumer -> consumer.accept(pack));
            }
        } catch (Exception e) {
            LOGGER.error("cant register builtin datapack bs '{}': {}", nam, e.getMessage());
        }
    }
}

