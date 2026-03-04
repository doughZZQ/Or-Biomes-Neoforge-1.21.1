//package net.washer.or_biomes.datagen;
//
//import net.minecraft.core.Holder;
//import net.minecraft.core.HolderLookup;
//import net.minecraft.data.loot.BlockLootSubProvider;
//import net.minecraft.world.flag.FeatureFlagSet;
//import net.minecraft.world.flag.FeatureFlags;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.level.block.Block;
//import net.washer.or_biomes.blocks.ModBlocks;
//
//import java.util.Set;
//
// //**
// * @author 洗衣机Washer
// * @version 1.0.0
// */
//public class ModBlockLootTableProvider extends BlockLootSubProvider {
//
//    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
//        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
//    }
//
//    @Override
//    protected void generate() {
//        dropSelf(ModBlocks.BIRCH_PAPER_LANTERN_BLOCK.get());
//        dropSelf(ModBlocks.JUNGLE_PAPER_LANTERN_BLOCK.get());
//        dropSelf(ModBlocks.MAPLE_PAPER_LANTERN_BLOCK.get());
//        dropSelf(ModBlocks.MANGROVE_PAPER_LANTERN_BLOCK.get());
//        dropSelf(ModBlocks.BAOBAB_PAPER_LANTERN_BLOCK.get());
//        dropSelf(ModBlocks.ACACIA_PAPER_LANTERN_BLOCK.get());
//
//
//    }
//
//    @Override
//    protected Iterable<Block> getKnownBlocks() {
//        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
//    }
//}
