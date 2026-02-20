package net.washer.or_biomes.blocks;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.HugeBrownMushroomFeature;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.washer.or_biomes.OrBiomes;
import net.washer.or_biomes.blocks.custom_block.*;

import java.util.function.Supplier;

import static net.washer.or_biomes.items.ModItems.ITEMS;

/**
 * @author 洗衣机Washer
 * @version 1.0.0
 */
public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, OrBiomes.MOD_ID);

    //===========================================================
    public static final Supplier<StrataBlock> STRATA_BLOCK = registerBlock("strata_block", StrataBlock::new);

    //===========================================================
    public static final Supplier<MapleLogBlock> MAPLE_LOG_BLOCK = registerBlock("maple_log_block", MapleLogBlock::new);
    public static final Supplier<MapleLeavesOrangeBlock> MAPLE_LEAVES_ORANGE_BLOCK = registerBlock("maple_leaves_orange_block", MapleLeavesOrangeBlock::new);
    public static final Supplier<MapleLeavesRedBlock> MAPLE_LEAVES_RED_BLOCK = registerBlock("maple_leaves_red_block", MapleLeavesRedBlock::new);
    public static final Supplier<MapleSaplingBlock> MAPLE_SAPLING_BLOCK = registerBlock("maple_sapling_block", MapleSaplingBlock::new);

    //===========================================================
    public static final Supplier<BirchLeavesYellowBlock> BIRCH_LEAVES_YELLOW_BLOCK = registerBlock("birch_leaves_yellow_block", BirchLeavesYellowBlock::new);
    public static final Supplier<YellowBirchSaplingBlock> YELLOW_BIRCH_SAPLING_BLOCK = registerBlock("yellow_birch_sapling_block", YellowBirchSaplingBlock::new);
    public static final Supplier<TallBirchSaplingBlock> TALL_BIRCH_SAPLING_BLOCK = registerBlock("tall_birch_sapling_block", TallBirchSaplingBlock::new);

    //===========================================================
    public static final Supplier<FirLogBlock> FIR_LOG_BLOCK = registerBlock("fir_log_block", FirLogBlock::new);
    public static final Supplier<FirLeavesGreenBlock> FIR_LEAVES_GREEN_BLOCK = registerBlock("fir_leaves_green_block", FirLeavesGreenBlock::new);
    public static final Supplier<FirLeavesOrangeBlock> FIR_LEAVES_ORANGE_BLOCK = registerBlock("fir_leaves_orange_block", FirLeavesOrangeBlock::new);
    public static final Supplier<GreenFirSaplingBlock> GREEN_FIR_SAPLING_BLOCK = registerBlock("green_fir_sapling_block", GreenFirSaplingBlock::new);

    //===========================================================
    public static final Supplier<OrangeMushroomBlock> ORANGE_MUSHROOM_BLOCK = registerBlock("orange_mushroom_block", OrangeMushroomBlock::new);
    public static final Supplier<HugeOrangeMushroomBlock> HUGE_ORANGE_MUSHROOM_BLOCK = registerBlock("huge_orange_mushroom_block", HugeOrangeMushroomBlock::new);
    public static final Supplier<OrangeMushroomStemBlock> ORANGE_MUSHROOM_STEM_BLOCK = registerBlock("orange_mushroom_stem_block", OrangeMushroomStemBlock::new);

    //===========================================================
    public static final Supplier<DriedSiltBlock> DRIED_SILT_BLOCK = registerBlock("dried_silt_block", DriedSiltBlock::new);
    public static final Supplier<LimestoneBlock> LIMESTONE_BLOCK = registerBlock("limestone_block", LimestoneBlock::new);

    //===========================================================
    public static final Supplier<PalmLogBlock> PALM_LOG_BLOCK = registerBlock("palm_log_block", PalmLogBlock::new);
    public static final Supplier<PalmCrownBlock> PALM_CROWN_BLOCK = registerBlock("palm_crown_block", PalmCrownBlock::new);
    public static final Supplier<PalmLeavesBlock> PALM_LEAVES_BLOCK = registerBlock("palm_leaves_block", PalmLeavesBlock::new);
    public static final Supplier<PalmSaplingBlock> PALM_SAPLING_BLOCK = registerBlock("palm_sapling_block", PalmSaplingBlock::new);

    //===========================================================
    public static final Supplier<MarigoldBlock> MARIGOLD_BLOCK = registerBlock("marigold_block", MarigoldBlock::new);

    //===========================================================
    public static final Supplier<TieredSpruceSaplingBlock> TIERED_SPRUCE_SAPLING_BLOCK = registerBlock("tiered_spruce_sapling_block", TieredSpruceSaplingBlock::new);

    //===========================================================
    public static final Supplier<BaobabTreeLogBlock> BAOBAB_TREE_LOG_BLOCK = registerBlock("baobab_tree_log_block", BaobabTreeLogBlock::new);
    public static final Supplier<BaobabTreeLeavesBlock> BAOBAB_TREE_LEAVES_BLOCK = registerBlock("baobab_tree_leaves_block", BaobabTreeLeavesBlock::new);
    public static final Supplier<BaobabTreeSaplingBlock> BAOBAB_TREE_SAPLING_BLOCK = registerBlock("baobab_tree_sapling_block", BaobabTreeSaplingBlock::new);

    //===========================================================
    public static final Supplier<FrostedSpruceLeavesBlock> FROSTED_SPRUCE_LEAVES_BLOCK = registerBlock("frosted_spruce_leaves_block", FrostedSpruceLeavesBlock::new);
    public static final Supplier<FrostedSpruceSaplingBlock> FROSTED_SPRUCE_SAPLING_BLOCK = registerBlock("frosted_spruce_sapling_block", FrostedSpruceSaplingBlock::new);

    //===========================================================

    public static <T extends Block> Supplier<T> registerBlock(String name, Supplier<T> block){
        Supplier<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    public static <T extends Block> void registerBlockItem(String name, Supplier<T> block){
        registerBlockItem(name, block, new Item.Properties());
    }

    public static <T extends Block> void registerBlockItem(String name, Supplier<T> block, Item.Properties properties){
        ITEMS.register(name, () -> new BlockItem(block.get(), properties));
    }


    public static void register(IEventBus eventBus) {
        //Blocks.PINK_PETALS
        BLOCKS.register(eventBus);
    }
}
