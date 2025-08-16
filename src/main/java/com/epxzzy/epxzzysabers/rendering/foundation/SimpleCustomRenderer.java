package com.epxzzy.epxzzysabers.rendering.foundation;

import com.epxzzy.epxzzysabers.epxzzySabers;
//import com.simibubi.create.foundation.item.render.CustomRenderedItemModelRenderer;
//import com.simibubi.create.foundation.item.render.CustomRenderedItems;

import net.minecraft.world.item.Item;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class SimpleCustomRenderer implements IClientItemExtensions {

    protected CustomRenderedItemModelRenderer renderer;

    protected SimpleCustomRenderer(CustomRenderedItemModelRenderer renderer) {
        this.renderer = renderer;
    }

    public static SimpleCustomRenderer create(Item item, CustomRenderedItemModelRenderer renderer) {
        return new SimpleCustomRenderer(renderer);
    }

    @Override
    public CustomRenderedItemModelRenderer getCustomRenderer() {
        return renderer;
    }

}

