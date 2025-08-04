package com.epxzzy.createsaburs.rendering.foundation;

import java.io.IOException;

import com.epxzzy.createsaburs.CreateSaburs;
import com.epxzzy.createsaburs.entity.client.rotary.ThrownRotarySaberRenderer;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;

import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

public class RenderTypes extends RenderStateShard {
    public static final RenderStateShard.ShaderStateShard GLOWING_SHADER = new RenderStateShard.ShaderStateShard(() -> com.epxzzy.createsaburs.rendering.foundation.RenderTypes.Shaders.glowingShader);

    private static final RenderType ITEM_GLOWING_SOLID = RenderType.create(createLayerName("item_glowing_solid"),
            DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, true, false, RenderType.CompositeState.builder()
                    .setShaderState(GLOWING_SHADER)
                    .setTextureState(BLOCK_SHEET)
                    .setLightmapState(LIGHTMAP)
                    .setOverlayState(OVERLAY)
                    .createCompositeState(true)
    );

    private static final RenderType ITEM_GLOWING_TRANSLUCENT = RenderType.create(createLayerName("item_glowing_translucent"),
            DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, true, true, RenderType.CompositeState.builder()
                    .setShaderState(GLOWING_SHADER)
                    .setTextureState(BLOCK_SHEET)
                    .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                    .setLightmapState(LIGHTMAP)
                    .setOverlayState(OVERLAY)
                    .createCompositeState(true)
    );

    private static final RenderType ROTARY_BLADE = RenderType.create(
            createLayerName("ROTARY_BLADE"),
            DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, true, true, RenderType.CompositeState.builder()
                    .setShaderState(GLOWING_SHADER)
                    .setTextureState(new RenderStateShard.TextureStateShard(ThrownRotarySaberRenderer.GLOWLOC, false, false))
                    .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                    .setLightmapState(LIGHTMAP)
                    .setOverlayState(OVERLAY)
                    .createCompositeState(true)
    );

    public static RenderType itemGlowingSolid() {
        return ITEM_GLOWING_SOLID;
    }

    public static RenderType itemGlowingTranslucent() {
        return ITEM_GLOWING_TRANSLUCENT;
    }
    public static RenderType glowingRotaryBlade() {
        return ROTARY_BLADE;
    }


    private static String createLayerName(String name) {
        return CreateSaburs.MOD_ID + ":" + name;
    }

    // Mmm gimme those protected fields
    private RenderTypes() {
        super(null, null, null);
    }

    @EventBusSubscriber(value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
    private static class Shaders {
        private static ShaderInstance glowingShader;

        @SubscribeEvent
        public static void onRegisterShaders(RegisterShadersEvent event) throws IOException {
            ResourceProvider resourceProvider = event.getResourceProvider();
            event.registerShader(new ShaderInstance(resourceProvider, CreateSaburs.asResource("glowing_shader"),
                    DefaultVertexFormat.NEW_ENTITY), shader -> glowingShader = shader);
        }
    }
}

