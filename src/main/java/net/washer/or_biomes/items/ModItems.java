package net.washer.or_biomes.items;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.washer.or_biomes.OrBiomes;
import net.washer.or_biomes.items.custom_item.SledItem;

/**
 * @author 洗衣机Washer
 * @version 1.0.0
 */
public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, OrBiomes.MOD_ID);

    public static final DeferredHolder<Item, SledItem> SLED =
            ITEMS.register("sled",
                    () -> new SledItem(
                            new Item.Properties().stacksTo(1)
                    ));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
