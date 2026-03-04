//package net.washer.or_biomes.datagen;
//
//import net.minecraft.data.PackOutput;
//import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
//import net.neoforged.neoforge.common.data.ExistingFileHelper;
//import net.neoforged.neoforge.registries.DeferredBlock;
//import net.washer.or_biomes.OrBiomes;
//import net.washer.or_biomes.blocks.ModBlocks;
//
//import java.util.function.Supplier;
//
// //**
// * @author 洗衣机Washer
// * @version 1.0.0
// */
//public class ModBlockStateProvider extends BlockStateProvider {
//
//    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
//        super(output, OrBiomes.MOD_ID, exFileHelper);
//    }
//
//    @Override
//    protected void registerStatesAndModels() {
//        blockWithItem(ModBlocks.BIRCH_PAPER_LANTERN_BLOCK);
//    }
//
//    private void blockWithItem(Supplier<?> deferredBlock) {
//        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
//    }
//}
