package net.washer.or_biomes.blocks.custom_block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * @author 洗衣机Washer
 * @version 1.0.0
 */
public class ToxicMushroomBlock extends Block {

    public static final DirectionProperty FACING = BlockStateProperties.VERTICAL_DIRECTION;

    // 正常蘑菇 shape（站在地面）
    protected static final VoxelShape UP_SHAPE = Block.box(2, 0, 2, 14, 14, 14);

    // 倒挂蘑菇 shape（挂在天花板）
    protected static final VoxelShape DOWN_SHAPE = Block.box(2, 2, 2, 14, 16, 14);

    public ToxicMushroomBlock() {
        super(
                BlockBehaviour.Properties
                .of()
                .noCollission()
                .instabreak()
                .sound(SoundType.FUNGUS)
        );
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.UP));
    }

    // 注册属性
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    // 决定放置方向
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {

        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();

        // 优先判断上方是否是实心方块 → 倒挂
        if (level.getBlockState(pos.above()).isSolid()) {
            return this.defaultBlockState().setValue(FACING, Direction.DOWN);
        }

        // 否则判断下方是否是实心方块 → 正常放置
        if (level.getBlockState(pos.below()).isSolid()) {
            return this.defaultBlockState().setValue(FACING, Direction.UP);
        }

        return null; // 两边都不满足则不能放置
    }

    // 自定义碰撞体积
    @Override
    public VoxelShape getShape(
                                BlockState state, BlockGetter level,
                                BlockPos pos, CollisionContext context
    ) {

        return state.getValue(FACING) == Direction.DOWN
                ? DOWN_SHAPE
                : UP_SHAPE;
    }

    // 存活检测
    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {

        Direction facing = state.getValue(FACING);

        if (facing == Direction.UP) {
            return level.getBlockState(pos.below()).isSolid();
        } else {
            return level.getBlockState(pos.above()).isSolid();
        }
    }

    // 当邻居更新时检查是否仍然存活
    @Override
    public BlockState updateShape(BlockState state,
                                  Direction direction,
                                  BlockState neighborState,
                                  LevelAccessor level,
                                  BlockPos pos,
                                  BlockPos neighborPos
    ) {

        if (!this.canSurvive(state, level, pos)) {
            return Blocks.AIR.defaultBlockState();
        }

        return state;
    }
}
