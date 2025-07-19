package com.epxzzy.createsaburs.rendering.foundation;

import com.epxzzy.createsaburs.createsaburs;
import com.epxzzy.createsaburs.item.Protosaber;
import com.epxzzy.createsaburs.rendering.foundation.CustomRenderedItemModelRenderer;
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
        createsaburs.LOGGER.warn("FKCRT SimpleCustomRenderer made for {}",item);
        CustomRenderedItems.register(item);
        com.simibubi.create.foundation.item.render.CustomRenderedItems.register(item);
        return new SimpleCustomRenderer(renderer);
    }

    @Override
    public CustomRenderedItemModelRenderer getCustomRenderer() {
        return renderer;
    }

}

