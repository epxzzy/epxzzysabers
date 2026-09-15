package dev.epxzzy.epxzzysabers.registry;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import dev.epxzzy.epxzzysabers.content.sabers.protosaber.Protosaber;
import net.minecraft.world.item.Item;

import java.util.HashMap;

public class ItemRegistry {
    public static final HashMap<String, Supplier<Item>> map = new HashMap<>();

    public static final Supplier<Item> asdf = Suppliers.memoize(() -> new Item(new Item.Properties()));
    public static final Supplier<Item> protosaber = Suppliers.memoize(() -> new Protosaber(new Item.Properties()));
    public static final Supplier<Item> singlebladed = Suppliers.memoize(() -> new Protosaber(new Item.Properties()));


    static {
        /*
        map.put("asdf", asdf);
        map.put("protosaber", protosaber);
         */
        map.put("single_bladed_saber", singlebladed);
    }
}
