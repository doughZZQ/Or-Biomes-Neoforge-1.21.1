package net.washer.or_biomes.worldgen.tree.custom_tree;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
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
public class FirTrunkPlacer extends TrunkPlacer {

    public static final MapCodec<FirTrunkPlacer> CODEC =
            RecordCodecBuilder.mapCodec(instance ->
                    trunkPlacerParts(instance)
                            .apply(instance, FirTrunkPlacer::new)
            );

    public FirTrunkPlacer(int baseHeight, int heightRandA, int heightRandB) {
        super(baseHeight, heightRandA, heightRandB);
    }


    @Override
    protected TrunkPlacerType<?> type() {
        return ModTrunkPlacerTypes.FIR_TRUNK_PLACER.get();
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(
            LevelSimulatedReader level,
            BiConsumer<BlockPos, BlockState> setter,
            RandomSource random,
            int height,
            BlockPos pos,
            TreeConfiguration config
    ) {
        setDirtAt(level, setter, random, pos.below(), config);
        List<FoliagePlacer.FoliageAttachment> attachments = new ArrayList<>();

        // 生成主干
        for (int y = 0; y < height; y++) {
            BlockPos logPos = pos.above(y);
            setter.accept(logPos, config.trunkProvider.getState(random, logPos));
        }

        List<Integer> heightList = getHeightList(height);
        int offset1 = 2;
        // 四个主要层
        attachments.add(new FoliagePlacer.FoliageAttachment(pos.above(heightList.get(0) - offset1), 0, false));
        attachments.add(new FoliagePlacer.FoliageAttachment(pos.above(heightList.get(1) - offset1), 1, false));
        attachments.add(new FoliagePlacer.FoliageAttachment(pos.above(heightList.get(2) - offset1), 2, false));
        attachments.add(new FoliagePlacer.FoliageAttachment(pos.above(heightList.get(3) - offset1), 3, false));
        if (height >= 20) {
            attachments.add(new FoliagePlacer.FoliageAttachment(pos.above(heightList.get(4) - offset1), 4, false));
        }
        return attachments;
    }

    public static List<Integer> getHeightList(int treeHeight) {

        switch (treeHeight) {
            case 15 -> {
                return List.of(6, 9, 12, 15);
            }
            case 16 -> {
                return List.of(6, 9, 13, 16);
            }
            case 17 -> {
                return List.of(7, 11, 14, 17);
            }
            case 18 -> {
                return List.of(7, 11, 15, 18);
            }
            case 19 -> {
                return List.of(7, 11, 15, 19);
            }
            case 20 -> {
                return List.of(6, 9, 13, 16, 20);
            }
            case 21 -> {
                return List.of(6, 10, 14, 18, 21);
            }
            case 22 -> {
                return List.of(7, 11, 15, 18, 22);
            }
            default -> {
                return List.of(7, 11, 15, 18, 22);
            }
        }
    }
}
