package net.washer.or_biomes.worldgen.tree.custom_tree;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
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
public class MapleForkedTrunkPlacer extends TrunkPlacer {
    public static final MapCodec<MapleForkedTrunkPlacer> CODEC =
            RecordCodecBuilder.mapCodec(instance ->
                    trunkPlacerParts(instance)
                            .apply(instance, MapleForkedTrunkPlacer::new)
            );

    public MapleForkedTrunkPlacer(int baseHeight, int heightRandA, int heightRandB) {
        super(baseHeight, heightRandA, heightRandB);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return ModTrunkPlacerTypes.MAPLE_FORKED_TRUNK_PLACER.get();
    }

    @Override
    public @NotNull List<FoliagePlacer.FoliageAttachment> placeTrunk(
            LevelSimulatedReader level,
            BiConsumer<BlockPos, BlockState> blockSetter,
            RandomSource random,
            int height,
            BlockPos startPos,
            TreeConfiguration config)
    {
        setDirtAt(level, blockSetter, random, startPos.below(), config);
        List<FoliagePlacer.FoliageAttachment> attachments = new ArrayList<>();

        int forkHeight = random.nextInt(3, 4);

        // 1️⃣ 下半段直立
        for (int i = 0; i < forkHeight; i++) {
            placeLog(level, blockSetter, random, startPos.above(i), config);
        }

        BlockPos forkStart = startPos.above(forkHeight);

        // 2️⃣ 随机选择三个不同方向
        Direction dir1 = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        Direction dir2 = dir1.getOpposite();
        Direction dir3 = random.nextBoolean() ? dir1.getClockWise() : dir2.getClockWise();

        // 3️⃣ 生成分叉
        attachments.add(generateBranch(height, blockSetter, random, forkStart, dir1, config));
        attachments.add(generateBranch(height, blockSetter, random, forkStart, dir2, config));
        attachments.add(generateBranch(height, blockSetter, random, forkStart, dir3, config));

        int bottomBump = random.nextInt(0, 3);
        // 4️⃣ 底部小叶团
        attachments.add(new BlobFoliagePlacer.FoliageAttachment(
                startPos.above(bottomBump),
                -2,
                false
        ));

        return attachments;
    }

    private FoliagePlacer.FoliageAttachment generateBranch(
            int height,
            BiConsumer<BlockPos, BlockState> blockSetter,
            RandomSource random,
            BlockPos start,
            Direction dir,
            TreeConfiguration config
    ) {

        BlockPos pos = start;

        int branchLength = height - random.nextInt(1, 2);
        int must = 0;

        for (int i = 0; i < branchLength; i++) {

            if (must >= 2 || random.nextBoolean()) {
                pos = pos.relative(dir);
                pos = pos.relative(dir.getClockWise());
                must = 0;
            }
            else if (i != 0) {
                pos = pos.above(1);
                must++;
            }

//            // 固定树枝方向
            BlockState state = config.trunkProvider.getState(random, pos);
//
//            if (state.hasProperty(RotatedPillarBlock.AXIS)) {
//                state = state.setValue(RotatedPillarBlock.AXIS, dir.getAxis());
//            }

            if (state.hasProperty(RotatedPillarBlock.AXIS)) {
                state = state.setValue(RotatedPillarBlock.AXIS, Direction.Axis.Y);
            }

            blockSetter.accept(pos, state);
            pos = pos.above(1);
            blockSetter.accept(pos, state);
        }

        // 分叉末端放叶团
        return new FoliagePlacer.FoliageAttachment(pos, 0, false);
    }
}
