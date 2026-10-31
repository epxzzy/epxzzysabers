package dev.epxzzy.epxzzysabers.registration;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

import dev.epxzzy.epxzzysabers.Constants;
import dev.epxzzy.epxzzysabers.content.sabers.Proto.Protosaber;
import dev.epxzzy.epxzzysabers.content.sabers.SingleBladed.SingleBladeSaberItem;
import dev.epxzzy.epxzzysabers.regutils.RegistrationProvider;
import dev.epxzzy.epxzzysabers.regutils.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;

public class ItemRegistry {
  public static void init() {
  }

  public static final RegistrationProvider<Item> ITEMS = RegistrationProvider.get(Registries.ITEM, Constants.MOD_ID);

  public static final RegistryObject<Item, Item> singlebladed = ITEMS.register(
      "single_bladed_saber",
      () -> new SingleBladeSaberItem(new Item.Properties()));


  public static final RegistrationProvider<CreativeModeTab> CREATIVE_MODE_TABS = RegistrationProvider
      .get(Registries.CREATIVE_MODE_TAB, Constants.MOD_ID);

  public static final RegistryObject<CreativeModeTab, CreativeModeTab> TAB = CREATIVE_MODE_TABS
      .register(Constants.MOD_ID + "_tab", () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
          .icon(() -> new ItemStack(singlebladed.get()))
          .displayItems(
              (itemDisplayParameters, output) -> {
                output.accept(singlebladed.get());
              })
          .title(Component.translatable("itemGroup." + Constants.MOD_ID + ".tab"))
          .build());

  /*
   * public static final HashMap<String, Supplier<Item>> map = new HashMap<>();
   * 
   * public static final Supplier<Item> singlebladed = Suppliers
   * .memoize(() -> new SingleBladeSaberItem(new Item.Properties()));
   * 
   * static {
   * //map.put("asdf", asdf);
   * //map.put("protosaber", protosaber);
   * map.put("single_bladed_saber", singlebladed);
   * }
   */

}
