package com.epxzzy.createsaburs.rendering.foundation;

import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;

import com.epxzzy.createsaburs.createsaburs;
//import com.simibubi.create.foundation.item.render.CustomRenderedItemModel;
//import com.simibubi.create.foundation.item.render.CustomRenderedItemModelRenderer;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModel;
import com.simibubi.create.foundation.item.render.CustomRenderedItemModelRenderer;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.registries.ForgeRegistries;


public class CustomRenderedItems {

    private static final Set<Item> ITEMS = new ReferenceOpenHashSet<>();
    private static boolean itemsFiltered = false;

    /**
     * Track an item that uses a subclass of {@link com.simibubi.create.foundation.item.render.CustomRenderedItemModelRenderer} as its custom renderer
     * to automatically wrap its model with {@link CustomRenderedItemModel}.
     *
     * @param item The item that should have its model swapped.
     */
    public static void register(Item item) {
        createsaburs.LOGGER.warn("FKCRT item is being added to CustomRenderedItems {}", item);
        ITEMS.add(item);
    }

    /**
     * This method must not be called before item registration is finished!
     */
    public static void forEach(Consumer<Item> consumer) {
        createsaburs.LOGGER.warn("FKCRT now looping over CustomRenderedItems");
        if (!itemsFiltered) {
            Iterator<Item> iterator = ITEMS.iterator();
            while (iterator.hasNext()) {
                Item item = iterator.next();
                if (!ForgeRegistries.ITEMS.containsValue(item) || !(IClientItemExtensions.of(item)
                        .getCustomRenderer() instanceof CustomRenderedItemModelRenderer)) {
                    iterator.remove();
                }
            }
            itemsFiltered = true;
        }
        ITEMS.forEach(consumer);
    }
}