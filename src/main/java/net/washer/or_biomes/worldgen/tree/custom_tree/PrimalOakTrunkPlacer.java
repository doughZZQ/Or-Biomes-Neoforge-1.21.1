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
public class PrimalOakTrunkPlacer extends TrunkPlacer {

    public static final MapCodec<PrimalOakTrunkPlacer> CODEC =
            RecordCodecBuilder.mapCodec(instance ->
                    trunkPlacerParts(instance).apply(instance, PrimalOakTrunkPlacer::new));

    public PrimalOakTrunkPlacer(int baseHeight, int heightRandA, int heightRandB) {
        super(baseHeight, heightRandA, heightRandB);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return ModTrunkPlacerTypes.PRIMAL_OAK_TRUNK_PLACER.get();
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(
            LevelSimulatedReader level,
            BiConsumer<BlockPos, BlockState> blockSetter,
            RandomSource random,
            int height,
            BlockPos pos,
            TreeConfiguration config) {

        setDirtAt(level, blockSetter, random, pos.below(), config);
        List<FoliagePlacer.FoliageAttachment> attachments = new ArrayList<>();

        // ===== 1️⃣ 生成底部分叉根 =====
        int roots = 2 + random.nextInt(2);

        for (int i = 0; i < roots; i++) {
            Direction dir = Direction.Plane.HORIZONTAL.getRandomDirection(random);
            BlockPos rootPos = pos.relative(dir);

            placeLog(level, blockSetter, random, rootPos, config);
        }

        // ===== 2️⃣ 主干 =====
        for (int y = 0; y < height; y++) {
            placeLog(level, blockSetter, random, pos.above(y), config);

            // 中部少量叶子
            if (y > height / 3 && y < height / 2 && random.nextFloat() < 0.8f) {
                Direction randomDir = Direction.Plane.HORIZONTAL.getRandomDirection(random);
                placeLog(level, blockSetter, random, pos.above(y - 1).relative(randomDir), config);
                blockSetter.accept(
                        pos.above(y).relative(randomDir),
                        config.foliageProvider.getState(random, pos)
                );
            }
        }

        // ===== 3️⃣ 树顶 attachment =====
        int i0 = 1 + random.nextInt(2);
        Vec3i dir0 = getBumpDir(getBumpDirIndex(1).getFirst());
        BlockPos pos0 = pos.above(height - 1);
        for (int y = 0; y < i0; y++) {
            pos0 = pos0.above(1).offset(dir0);
            placeLog(level, blockSetter, random, pos0, config);
            placeLog(level, blockSetter, random, pos0.below(), config);
        }
        attachments.add(new FoliagePlacer.FoliageAttachment(pos0, 0, false));

        i0 = i0 + random.nextInt(2);
        pos0 = pos.above(height - 1);
        Vec3i dir1 = new Vec3i(-dir0.getX(), 0, -dir0.getZ());
        for (int y = 0; y < i0; y++) {
            pos0 = pos0.above(1).offset(dir1);
            placeLog(level, blockSetter, random, pos0, config);
            placeLog(level, blockSetter, random, pos0.below(), config);
        }
        attachments.add(new FoliagePlacer.FoliageAttachment(pos0, 0, false));

        return attachments;
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
