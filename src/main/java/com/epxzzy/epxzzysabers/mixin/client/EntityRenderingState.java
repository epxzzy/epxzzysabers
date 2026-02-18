package com.epxzzy.epxzzysabers.mixin.client;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;

public class EntityRenderingState {

    /* Entity and block entity rendering should be single-threaded, but use thread locals to
       avoid difficult bugs in case something changes. */
    public static final ThreadLocal<RenderType> currentRenderType = new ThreadLocal<>();
    public static final ThreadLocal<MultiBufferSource> currentBufferSource = new ThreadLocal<>();
    public static final ThreadLocal<Integer> partRenderDepth = ThreadLocal.withInitial(() -> -1);
    public static final ThreadLocal<Boolean> isBlockEntity = ThreadLocal.withInitial(() -> false);

}
