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
public class MapleSaplingBlock extends SaplingBlock {

    // 定义两种不同的 TreeGrower
    private final TreeGrower orangeGrower;
    private final TreeGrower redGrower;
    private final TreeGrower orangeForkedGrower;
    private final TreeGrower redForkedGrower;

    public MapleSaplingBlock() {
        super(ModTreeGrowers.ORANGE_MAPLE, Properties.ofFullCopy(Blocks.OAK_SAPLING));
        orangeGrower = ModTreeGrowers.ORANGE_MAPLE;
        redGrower = ModTreeGrowers.RED_MAPLE;
        orangeForkedGrower = ModTreeGrowers.ORANGE_MAPLE_FORKED;
        redForkedGrower = ModTreeGrowers.RED_MAPLE_FORKED;
    }

    @Override
    public void advanceTree(ServerLevel level, BlockPos pos, BlockState state, RandomSource random) {
        // 在树苗准备生长的瞬间，随机替换 TreeGrower
        TreeGrower selectedGrower = switch (random.nextInt(4)) {
            case 1 -> orangeForkedGrower;
            case 2 -> redGrower;
            case 3 -> redForkedGrower;
            default -> orangeGrower;
        };

        // 调用底层的生长逻辑
        selectedGrower.growTree(level, level.getChunkSource().getGenerator(), pos, state, random);
    }
}
