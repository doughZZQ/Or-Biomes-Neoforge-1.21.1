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
import java.util.List;
import java.util.function.BiConsumer;

/**
 * @author 洗衣机Washer
 * @version 1.0.0
 */
public class BaobabTreeTrunkPlacer extends TrunkPlacer {

    public static final MapCodec<BaobabTreeTrunkPlacer> CODEC =
            RecordCodecBuilder.mapCodec(instance ->
                    trunkPlacerParts(instance).apply(instance, BaobabTreeTrunkPlacer::new)
            );

    public BaobabTreeTrunkPlacer(int baseHeight, int heightRandA, int heightRandB) {
        super(baseHeight, heightRandA, heightRandB);
    }


    @Override
    protected TrunkPlacerType<?> type() {
        return ModTrunkPlacerTypes.BAOBAB_TREE_TRUNK_PLACER.get();
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

        List<Integer> treeLayer = getLayerList(freeTreeHeight);
        int hei = 0;
        // 1️⃣ 生成底部树桩
        for (int i = 0; i < treeLayer.get(0); i++) {
            BlockPos pos0 = pos.above(i);
            if (i != treeLayer.get(0) - 1) {
                for (int x = -2; x <= 2; x++) {
                    for (int z = -2; z <= 2; z++) {
                        if (Math.abs(x) + Math.abs(z) <= 2) {
                            placeLog(level, blockSetter, random, pos0.offset(x, 0, z), config);
                        }
                    }
                }
            }
            else {
                for (int x = -2; x <= 2; x++) {
                    for (int z = -2; z <= 2; z++) {
                        if (Math.abs(x) + Math.abs(z) <= 2) {
                            if ((Math.abs(x) == 2 || Math.abs(z) == 2) && random.nextBoolean()) continue;
                            placeLog(level, blockSetter, random, pos0.offset(x, 0, z), config);
                        }
                    }
                }
            }
            hei++;
        }
        //生成中部树干
        for (int i = hei; i < treeLayer.get(1); i++) {
            BlockPos pos0 = pos.above(i);
            if (i != treeLayer.get(1) - 1) {
                for (int x = -1; x <= 1; x++) {
                    for (int z = -1; z <= 1; z++) {
                        placeLog(level, blockSetter, random, pos0.offset(x, 0, z), config);
                    }
                }
            }
            else {
                for (int x = -1; x <= 1; x++) {
                    for (int z = -1; z <= 1; z++) {
                        if ((!(x == 0 || z == 0)) && random.nextBoolean()) continue;
                        placeLog(level, blockSetter, random, pos0.offset(x, 0, z), config);
                    }
                }
            }
            hei++;
        }
        //生成高部树枝
        for (Direction dir : List.of(Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST)) {
            int heig = treeLayer.get(2) - 1 + random.nextInt(0, 3);
            for (int i = hei; i < heig; i++) {
                Vec3i vec0 = new Vec3i(dir.getStepX() * (i - hei + 1), dir.getStepY() + i, dir.getStepZ() * (i - hei + 1));
                BlockPos pos0 = pos.offset(vec0);
                placeLog(level, blockSetter, random, pos0, config);
                if (i == heig - 1) {
                    //radiusOffset: 负值会让叶子更贴近主干，但这里没用到
                    attachments.add(new FoliagePlacer.FoliageAttachment(pos0, 0, false));
                }
            }
        }

        return attachments;
    }


    public static List<Integer> getLayerList(int treeHeight) {
        switch (treeHeight) {
            case 12 -> {
                return List.of(4, 9, 12);
            }
            case 13 -> {
                return List.of(4, 10, 13);
            }
            case 14 -> {
                return List.of(5, 10, 14);
            }
            case 15 -> {
                return List.of(6, 11, 15);
            }
            default -> {
                return List.of(4, 9, 12);
            }
        }
    }
}
