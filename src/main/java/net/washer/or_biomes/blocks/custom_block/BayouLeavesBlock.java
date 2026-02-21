package net.washer.or_biomes.blocks.custom_block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;

/**
 * @author 洗衣机Washer
 * @version 1.0.0
 */
public class BayouLeavesBlock extends LeavesBlock {

    public BayouLeavesBlock() {
        super(Properties.ofFullCopy(Blocks.MANGROVE_LEAVES));
        // 必须设置默认状态，否则可能会在生成时导致空指针或逻辑错误
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(DISTANCE, 7)              // 默认最远距离（准备枯萎）
                .setValue(PERSISTENT, false)        // 默认非持久
                .setValue(WATERLOGGED, false));
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 60;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 30;
    }
}
