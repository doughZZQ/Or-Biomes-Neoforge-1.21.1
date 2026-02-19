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
public class GreenFirSaplingBlock extends SaplingBlock {

    private final TreeGrower greenFirGrower;
    private final TreeGrower orangeFirGrower;

    public GreenFirSaplingBlock() {
        super(ModTreeGrowers.GREEN_FIR, Properties.ofFullCopy(Blocks.SPRUCE_SAPLING));
        this.greenFirGrower = ModTreeGrowers.GREEN_FIR;
        this.orangeFirGrower = ModTreeGrowers.ORANGE_FIR;
    }

    @Override
    public void advanceTree(ServerLevel level, BlockPos pos, BlockState state, RandomSource random) {

        TreeGrower selectedGrower = switch (random.nextInt(2)) {
            case 1 -> orangeFirGrower;
            default -> greenFirGrower;
        };
        // 调用底层的生长逻辑
        selectedGrower.growTree(level, level.getChunkSource().getGenerator(), pos, state, random);
    }
}
