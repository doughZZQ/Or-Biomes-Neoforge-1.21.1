package net.washer.or_biomes.worldgen.tree.custom_tree;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.washer.or_biomes.worldgen.tree.ModTrunkPlacerTypes;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * @author 洗衣机Washer
 * @version 1.0.0
 */
public class MapleTrunkPlacer extends TrunkPlacer {

    public static final MapCodec<MapleTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(instance ->
            trunkPlacerParts(instance).apply(instance, MapleTrunkPlacer::new)
    );

    public MapleTrunkPlacer(int baseHeight, int heightRandA, int heightRandB) {
        super(baseHeight, heightRandA, heightRandB);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return ModTrunkPlacerTypes.MAPLE_TRUNK_PLACER.get();
    }

    @Override
    public @NotNull List<FoliagePlacer.FoliageAttachment> placeTrunk(
            LevelSimulatedReader level,
            BiConsumer<BlockPos, BlockState> blockSetter,
            RandomSource random,
            int freeTreeHeight,
            BlockPos startPos,
            TreeConfiguration config)
    {
        setDirtAt(level, blockSetter, random, startPos.below(), config);
        List<FoliagePlacer.FoliageAttachment> attachments = new ArrayList<>();

        int bottomBump = (freeTreeHeight / 5) + (random.nextBoolean() ? 1 : -1);
        int bottomBump2 = bottomBump + (random.nextInt(1, 3));
        int centerBump = (freeTreeHeight / 2) - 1 + (random.nextBoolean() ? 1 : -1);
        int topBump = (freeTreeHeight - 6) + (random.nextBoolean() ? 1 : -1);
        int topBump2 = topBump + (random.nextBoolean() ? 1 : -1);
        // 1️⃣ 生成主干
        for (int i = 0; i < freeTreeHeight; i++) {
            placeLog(level, blockSetter, random, startPos.above(i), config);
        }
        placeBumpLog(level, blockSetter, random, config, startPos.above(bottomBump));
        placeBumpLog(level, blockSetter, random, config, startPos.above(centerBump));
        placeBumpLog(level, blockSetter, random, config, startPos.above(topBump));
        placeBumpLog(level, blockSetter, random, config, startPos.above(topBump2));

        // 2️⃣ 顶部大树冠
        BlockPos topPos = startPos.above(freeTreeHeight);
        attachments.add(new FoliagePlacer.FoliageAttachment(topPos, 0, false));

        // 3️⃣ 中段环形叶团
        attachments.add(new FoliagePlacer.FoliageAttachment(
                startPos.above(centerBump),
                -1,  // 负值会让叶子更贴近主干
                false
        ));

        // 4️⃣ 底部小叶团
        attachments.add(new FoliagePlacer.FoliageAttachment(
                startPos.above(bottomBump),
                -2,
                false
        ));
        attachments.add(new FoliagePlacer.FoliageAttachment(
                startPos.above(bottomBump2),
                -2,
                false
        ));

        return attachments;
    }

    private void placeBumpLog(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter,
                              RandomSource random, TreeConfiguration config, BlockPos pos) {
        Direction dir = switch (random.nextInt(4)) {
            case 1 -> Direction.SOUTH;
            case 2 -> Direction.EAST;
            case 3 -> Direction.WEST;
            default -> Direction.NORTH;
        };

        BlockPos pos2 = pos.offset(dir.getNormal());
        if (this.validTreePos(level, pos2)) {
            BlockState state = config.trunkProvider.getState(random, pos2);
            if (state.hasProperty(RotatedPillarBlock.AXIS)) {
                if (dir == Direction.NORTH || dir == Direction.SOUTH) {
                    state = state.setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z);
                }
                else {
                    state = state.setValue(RotatedPillarBlock.AXIS, Direction.Axis.X);
                }
            }
            blockSetter.accept(pos2, state);
        }
    }
}
