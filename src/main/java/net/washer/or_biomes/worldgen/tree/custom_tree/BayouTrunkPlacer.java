package net.washer.or_biomes.worldgen.tree.custom_tree;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
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
public class BayouTrunkPlacer extends TrunkPlacer {

    public static final MapCodec<BayouTrunkPlacer> CODEC =
            RecordCodecBuilder.mapCodec(instance ->
                    trunkPlacerParts(instance)
                            .apply(instance, BayouTrunkPlacer::new)
            );

    public BayouTrunkPlacer(int baseHeight, int heightRandA, int heightRandB) {
        super(baseHeight, heightRandA, heightRandB);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return ModTrunkPlacerTypes.BAYOU_TRUNK_PLACER.get();
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(
            LevelSimulatedReader level,
            BiConsumer<BlockPos, BlockState> blockSetter,
            RandomSource random,
            int freeTreeHeight,
            BlockPos pos,
            TreeConfiguration config
    ) {
        setDirtAt(level, blockSetter, random, pos.below(), config);
        List<FoliagePlacer.FoliageAttachment> attachments = new ArrayList<>();

        List<Integer> heightList = getHeightList(freeTreeHeight);
        for (Direction dir : List.of(Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST)) {
            int hei0 = 3 + random.nextInt(2);
            int h = heightList.get(0);
            for (int y = h - 1; y >= 0; y--) {
                BlockPos logPos = pos.above(y);
                Direction oDir = dir.getOpposite();
                if (y > hei0) {
                    BlockPos pos0 = logPos.offset(oDir.getStepX() * (h - y), oDir.getStepY() * (h - y), oDir.getStepZ() * (h - y));
                    placeLog(level, blockSetter, random, pos0, config);
                } else {
                    BlockPos pos0 = logPos.offset(oDir.getStepX() * (h - hei0), oDir.getStepY() * (h - hei0), oDir.getStepZ() * (h - hei0));
                    placeLog(level, blockSetter, random, pos0, config);
                }
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
            int len = 3 + random.nextInt(2);
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


    public static List<Integer> getHeightList(int treeHeight) {

        switch (treeHeight) {
            case 16 -> {
                return List.of(7, 10, 12, 16);
            }
            case 17 -> {
                return List.of(7, 10, 13, 17);
            }
            case 18 -> {
                return List.of(8, 11, 14, 18);
            }
            case 19 -> {
                return List.of(8, 12, 15, 19);
            }
            case 20 -> {
                return List.of(9, 13, 15, 20);
            }
            default -> {
                return List.of(9, 13, 15, 20);
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
