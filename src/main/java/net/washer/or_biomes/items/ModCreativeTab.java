package net.washer.or_biomes.items;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.washer.or_biomes.OrBiomes;
import net.washer.or_biomes.blocks.ModBlocks;

import java.util.function.Supplier;

/**
 * @author 洗衣机Washer
 * @version 1.0.0
 */
public class ModCreativeTab {
    // 对应的注册器
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, OrBiomes.MOD_ID);
    // 这个string是鼠标移动到tab上的显示的内容。
    public static final String EXAMPLE_MOD_TAB_STRING = "itemGroup.or_biomes.custom_item_group";
    // 添加一个tab，title标题，icon显示图标，displayItem是指tab中添加的内容，这里传入一个lammabd表达式，通过poutput添加物品，这里添加了我们的ruby
    public static final Supplier<CreativeModeTab> EXAMPLE_TAB  = CREATIVE_MODE_TABS.register("or_biomes_tab",() -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .title(Component.translatable(EXAMPLE_MOD_TAB_STRING))
            .icon(()-> ModBlocks.STRATA_BLOCK.get().asItem().getDefaultInstance())
            .displayItems((pParameters, pOutput) -> {

                pOutput.accept(ModBlocks.STRATA_BLOCK.get());
                pOutput.accept(ModBlocks.DRIED_SILT_BLOCK.get());
                pOutput.accept(ModBlocks.LIMESTONE_BLOCK.get());
                pOutput.accept(ModBlocks.MAPLE_LOG_BLOCK.get());
                pOutput.accept(ModBlocks.MAPLE_LEAVES_ORANGE_BLOCK.get());
                pOutput.accept(ModBlocks.MAPLE_LEAVES_RED_BLOCK.get());
                pOutput.accept(ModBlocks.MAPLE_SAPLING_BLOCK.get());
                pOutput.accept(ModBlocks.ORANGE_MUSHROOM_BLOCK.get());
                pOutput.accept(ModBlocks.HUGE_ORANGE_MUSHROOM_BLOCK.get());
                pOutput.accept(ModBlocks.ORANGE_MUSHROOM_STEM_BLOCK.get());
                pOutput.accept(ModBlocks.BIRCH_LEAVES_YELLOW_BLOCK.get());
                pOutput.accept(ModBlocks.YELLOW_BIRCH_SAPLING_BLOCK.get());
                pOutput.accept(ModBlocks.FIR_LOG_BLOCK.get());
                pOutput.accept(ModBlocks.FIR_LEAVES_GREEN_BLOCK.get());
                pOutput.accept(ModBlocks.FIR_LEAVES_ORANGE_BLOCK.get());
                pOutput.accept(ModBlocks.GREEN_FIR_SAPLING_BLOCK.get());
                pOutput.accept(ModBlocks.PALM_LOG_BLOCK.get());
                pOutput.accept(ModBlocks.PALM_CROWN_BLOCK.get());
                pOutput.accept(ModBlocks.PALM_LEAVES_BLOCK.get());
                pOutput.accept(ModBlocks.PALM_SAPLING_BLOCK.get());
                pOutput.accept(ModBlocks.MARIGOLD_BLOCK.get());
                pOutput.accept(ModBlocks.TIERED_SPRUCE_SAPLING_BLOCK.get());
                pOutput.accept(ModBlocks.BAOBAB_TREE_LOG_BLOCK.get());
                pOutput.accept(ModBlocks.BAOBAB_TREE_LEAVES_BLOCK.get());
                pOutput.accept(ModBlocks.BAOBAB_TREE_SAPLING_BLOCK.get());
                pOutput.accept(ModBlocks.TALL_BIRCH_SAPLING_BLOCK.get());
                pOutput.accept(ModBlocks.FROSTED_SPRUCE_LEAVES_BLOCK.get());
                pOutput.accept(ModBlocks.FROSTED_SPRUCE_SAPLING_BLOCK.get());
            })
            .build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
