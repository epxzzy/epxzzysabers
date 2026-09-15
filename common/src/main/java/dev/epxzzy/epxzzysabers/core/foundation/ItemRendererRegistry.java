package dev.epxzzy.epxzzysabers.core.foundation;

import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ItemRendererRegistry {

    public static final Map<Item, CustomRenderedSaberModelRenderer> RENDERER_MAP = new HashMap<>();

    public static void register(Item item, CustomRenderedSaberModelRenderer renderer){
        RENDERER_MAP.put(item, renderer);
    }

    public static Map<Item, CustomRenderedSaberModelRenderer> getRendererMap(){
        return RENDERER_MAP;
    }
}
