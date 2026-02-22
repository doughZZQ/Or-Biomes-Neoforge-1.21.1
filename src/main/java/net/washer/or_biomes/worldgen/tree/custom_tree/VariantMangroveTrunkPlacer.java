package net.washer.or_biomes.worldgen.tree.custom_tree;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.washer.or_biomes.worldgen.tree.ModTrunkPlacerTypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * @author 洗衣机Washer
 * @version 1.0.0
 */
public class VariantMangroveTrunkPlacer extends TrunkPlacer {

    public static final MapCodec<VariantMangroveTrunkPlacer> CODEC =
            RecordCodecBuilder.mapCodec(instance ->
                    trunkPlacerParts(instance)
                            .apply(instance, VariantMangroveTrunkPlacer::new)
            );

    public VariantMangroveTrunkPlacer(int baseHeight, int heightRandA, int heightRandB) {
        super(baseHeight, heightRandA, heightRandB);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return ModTrunkPlacerTypes.VARIANT_MANGROVE_TRUNK_PLACER.get();
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(
            LevelSimulatedReader level,
            BiConsumer<BlockPos, BlockState> blockSetter,
            RandomSource random,
            int height,
            BlockPos pos,
            TreeConfiguration config
    ) {

        setDirtAt(level, blockSetter, random, pos.below(), config);
        List<FoliagePlacer.FoliageAttachment> attachments = new ArrayList<>();

        List<Integer> heightList = getHeightList(height);
        for (Direction dir : List.of(Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST)) {
            int hei0 = 3 + random.nextInt(3);
            int h = heightList.get(0);
            for (int y = h - 1; y >= 0; y--) {
                BlockPos logPos = pos.above(y);
                Direction oDir = dir.getOpposite();
                BlockPos pos0;
                if (y > hei0) {
                    pos0 = logPos.offset(oDir.getStepX() * (h - y), oDir.getStepY() * (h - y), oDir.getStepZ() * (h - y));
                } else {
                    pos0 = logPos.offset(oDir.getStepX() * (h - hei0), oDir.getStepY() * (h - hei0), oDir.getStepZ() * (h - hei0));
                }
                blockSetter.accept(pos0, Blocks.MANGROVE_ROOTS.defaultBlockState());
            }
        }

        for (int y = heightList.get(0) - 1; y < heightList.get(3); y++) {
            BlockPos logPos = pos.above(y);
            placeLog(level, blockSetter, random, logPos, config);
        }

        List<Integer> indexList = getBumpDirIndex(3);
        for (int i = 0; i < 2; i++) {
            BlockPos pos1 = pos.above(heightList.get(1) - 1);
            Vec3i dir0 = getBumpDir(indexList.get(i));
            int len = 2 + random.nextInt(3);
            for (int j = 1; j <= len; j++) {
                BlockPos pos2 = pos1.offset(dir0.getX() * j, j + (i * 3), dir0.getZ() * j);
                placeLog(level, blockSetter, random, pos2, config);
                pos2 = pos2.offset(dir0);
                placeLog(level, blockSetter, random, pos2, config);
                if (j == len) {
                    attachments.add(new FoliagePlacer.FoliageAttachment(pos2, 0, false));
                }
            }
        }

        {
            BlockPos pos1 = pos.above(heightList.get(2) - 1);
            Vec3i dir0 = getBumpDir(indexList.get(2));
            int len = 2 + random.nextInt(2);
            for (int j = 1; j <= len; j++) {
                BlockPos pos2 = pos1.offset(dir0.getX() * j, j, dir0.getZ() * j);
                placeLog(level, blockSetter, random, pos2, config);
                pos2 = pos2.offset(dir0);
                placeLog(level, blockSetter, random, pos2, config);
                if (j == len) {
                    attachments.add(new FoliagePlacer.FoliageAttachment(pos2, -1, false));
                }
            }
        }

        attachments.add(new FoliagePlacer.FoliageAttachment(pos.above(heightList.get(3) - 1), 0, false));

        return attachments;
    }

//    private void generateRoots(
//            LevelSimulatedReader level,
//            BiConsumer<BlockPos, BlockState> blockSetter,
//            RandomSource random,
//            BlockPos base,
//            TreeConfiguration config
//    ) {
//
//        int rootCount = 4;
//
//        for (int i = 0; i < rootCount; i++) {
//
//            Direction dir = Direction.Plane.HORIZONTAL.getRandomDirection(random);
//
//            BlockPos current = base;
//
//            int length = 5 + random.nextInt(3);
//
//            for (int j = 0; j < length; j++) {
//
//                current = current.relative(dir).below();
//
//                blockSetter.accept(
//                        current,
//                        Blocks.MANGROVE_ROOTS.defaultBlockState()
//                );
//            }
//        }
//
//        // 中心竖直根
//        for (int i = 0; i < 3; i++) {
//            blockSetter.accept(
//                    base.above(i),
//                    Blocks.MANGROVE_ROOTS.defaultBlockState()
//            );
//        }
//    }

    public static List<Integer> getHeightList(int treeHeight) {

        switch (treeHeight) {
            case 22 -> {
                return List.of(9, 13, 18, 22);
            }
            case 23 -> {
                return List.of(9, 13, 18, 23);
            }
            case 24 -> {
                return List.of(9, 13, 19, 24);
            }
            case 25 -> {
                return List.of(9, 13, 19, 25);
            }
            case 26 -> {
                return List.of(9, 13, 20, 26);
            }
            default -> {
                return List.of(9, 13, 18, 22);
            }
        }
    }

    public static Vec3i getBumpDir(int index) {
        return switch (index) {
            case 0 -> Direction.NORTH.getNormal();
            case 1 -> Direction.SOUTH.getNormal();
            case 2 -> Direction.WEST.getNormal();
            case 3 -> Direction.EAST.getNormal();
            case 4 -> new Vec3i(1, 0, -1);
            case 5 -> new Vec3i(-1, 0, -1);
            case 6 -> new Vec3i(1, 0, 1);
            case 7 -> new Vec3i(-1, 0, 1);
            default -> Direction.NORTH.getNormal();
        };
    }

    public static List<Integer> getBumpDirIndex(int count) {
        List<Integer> numbers = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7);
        Collections.shuffle(numbers); // 随机打乱
        List<Integer> indexList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            indexList.add(numbers.get(i));
        }
        return indexList;
    }
}
