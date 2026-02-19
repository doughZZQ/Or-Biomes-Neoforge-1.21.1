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
public class TieredSpruceTrunkPlacer extends TrunkPlacer {

    public static final MapCodec<TieredSpruceTrunkPlacer> CODEC =
            RecordCodecBuilder.mapCodec(instance ->
                    trunkPlacerParts(instance).apply(instance, TieredSpruceTrunkPlacer::new)
            );

    public TieredSpruceTrunkPlacer(int baseHeight, int heightRandA, int heightRandB) {
        super(baseHeight, heightRandA, heightRandB);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return ModTrunkPlacerTypes.TIERED_SPRUCE_TRUNK_PLACER.get();
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader level,
                                                            BiConsumer<BlockPos, BlockState> blockSetter,
                                                            RandomSource random,
                                                            int freeTreeHeight,
                                                            BlockPos pos,
                                                            TreeConfiguration config
    ) {

        setDirtAt(level, blockSetter, random, pos.below(), config);

        List<FoliagePlacer.FoliageAttachment> attachments = new ArrayList<>();

        // 生成主干
        for (int i = 0; i < freeTreeHeight; ++i) {
            placeLog(level, blockSetter, random, pos.above(i), config);
        }

        List<Integer> heightList = getHeightList(freeTreeHeight);

        int index = 0;
        for (int hei : heightList) {
            attachments.add(new FoliagePlacer.FoliageAttachment(
                    pos.above(hei),
                    index,
                    false
            ));
            index++;
        }

        return attachments;
    }


    public static List<Integer> getHeightList(int treeHeight) {
        switch (treeHeight) {
            case 12 -> {
                return List.of(3, 6, 9, 12);
            }
            case 13 -> {
                return List.of(4, 7, 10, 13);
            }
            case 14 -> {
                return List.of(5, 8, 11, 14);
            }
            case 15 -> {
                return List.of(6, 9, 12, 15);
            }
            case 16 -> {
                return List.of(6, 9, 13, 16);
            }
            default -> {
                return List.of(3, 6, 9, 12);
            }
        }
    }
}
