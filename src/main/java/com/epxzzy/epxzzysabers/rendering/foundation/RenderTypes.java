package com.epxzzy.epxzzysabers.rendering.foundation;

import java.io.IOException;

import com.epxzzy.epxzzysabers.epxzzySabers;
import com.epxzzy.epxzzysabers.entity.client.rotary.ThrownRotarySaberRenderer;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;

import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

public class RenderTypes extends RenderStateShard {

    /* experimental rendertypes for external shader support
    */

    public static RenderType ITEM_GLOWING_EXPERIMENTAL = RenderType.create(
        createLayerName("item_glowing_experimental"),
        DefaultVertexFormat.NEW_ENTITY,
        VertexFormat.Mode.QUADS,
       256,
        true,
            true,
                RenderType.CompositeState.builder()
                        .setShaderState(RenderStateShard.RENDERTYPE_EYES_SHADER)
                        .setTextureState(BLOCK_SHEET)
                        //.setTextureState(new RenderStateShard.TextureStateShard(BLADE_TEXTURE, false, false))
                        .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                        .setLightmapState(LIGHTMAP)
                        .setOverlayState(OVERLAY)
                        //.setCullState(NO_CULL)
                        .createCompositeState(true)
    );

    public static final RenderType ROTARY_BLADE_EXPERIMENTAL = RenderType.create(
            createLayerName("rotary_blade_experimental"),
            DefaultVertexFormat.NEW_ENTITY,
            VertexFormat.Mode.QUADS,
            256,
            true,
            true,
            RenderType.CompositeState.builder()
                    .setShaderState(RENDERTYPE_EYES_SHADER)
                    //.setTextureState(BLOCK_SHEET)
                    //.setTextureState(new RenderStateShard.)
                    .setTextureState(new RenderStateShard.TextureStateShard(ThrownRotarySaberRenderer.GLOWLOC, false, false))
                    .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
                    .setLightmapState(LIGHTMAP)
                    .setOverlayState(OVERLAY)
                    .createCompositeState(true)
    );

    /*
     */

    private static String createLayerName(String name) {
        return epxzzySabers.MOD_ID + ":" + name;
    }

    // Mmm gimme those protected fields
    private RenderTypes() {
        super(null, null, null);
    }

}

