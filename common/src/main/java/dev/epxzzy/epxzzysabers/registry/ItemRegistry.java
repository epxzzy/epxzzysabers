package dev.epxzzy.epxzzysabers.registry;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import dev.epxzzy.epxzzysabers.content.sabers.Proto.Protosaber;
import dev.epxzzy.epxzzysabers.content.sabers.SingleBladed.SingleBladeSaberItem;
import net.minecraft.world.item.Item;

import java.util.HashMap;

public class ItemRegistry {
    public static final HashMap<String, Supplier<Item>> map = new HashMap<>();

    public static final Supplier<Item> singlebladed = Suppliers.memoize(() -> new SingleBladeSaberItem(new Item.Properties()));


    static {
        /*
        map.put("asdf", asdf);
        map.put("protosaber", protosaber);
         */
        map.put("single_bladed_saber", singlebladed);
    }
}
