package net.washer.or_biomes.blocks.custom_block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.washer.or_biomes.worldgen.tree.ModTreeGrowers;

/**
 * @author 洗衣机Washer
 * @version 1.0.0
 */
public class BaobabTreeSaplingBlock extends SaplingBlock {

    private final TreeGrower baobabTreeGrower;

    public BaobabTreeSaplingBlock() {
        super(ModTreeGrowers.BAOBAB_TREE, Properties.ofFullCopy(Blocks.ACACIA_SAPLING));
        this.baobabTreeGrower = ModTreeGrowers.BAOBAB_TREE;

    }

    @Override
    public void advanceTree(ServerLevel level, BlockPos pos, BlockState state, RandomSource random) {
        // 调用底层的生长逻辑
        baobabTreeGrower.growTree(level, level.getChunkSource().getGenerator(), pos, state, random);
    }
}
