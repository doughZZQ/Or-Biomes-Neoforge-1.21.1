package net.washer.or_biomes.blocks.custom_block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.washer.or_biomes.worldgen.feature.ModConfiguredFeatures;

/**
 * @author 洗衣机Washer
 * @version 1.0.0
 */
public class OrangeMushroomBlock extends MushroomBlock {

    public OrangeMushroomBlock() {
        super(ModConfiguredFeatures.HUGE_ORANGE_MUSHROOM_KEY, Properties.ofFullCopy(Blocks.BROWN_MUSHROOM));
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {

        return state.is(Blocks.MYCELIUM)
                || state.is(Blocks.GRASS_BLOCK)
                || state.is(Blocks.DIRT)
                || state.is(Blocks.COARSE_DIRT)
                || state.is(Blocks.PODZOL)
                || state.is(Blocks.ROOTED_DIRT)
                || state.is(Blocks.MUD);
    }

    @Override
    public boolean canSurvive(BlockState state,
                              LevelReader level,
                              BlockPos pos) {

        BlockPos below = pos.below();
        BlockState belowState = level.getBlockState(below);

        return this.mayPlaceOn(belowState, level, below);
    }

}
