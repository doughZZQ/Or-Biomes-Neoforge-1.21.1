package net.washer.or_biomes.worldgen.tree.custom_tree;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.washer.or_biomes.worldgen.tree.ModFoliagePlacerTypes;

/**
 * @author 洗衣机Washer
 * @version 1.0.0
 */
public class TieredSpruceFoliagePlacer extends FoliagePlacer {

    public static final MapCodec<TieredSpruceFoliagePlacer> CODEC =
            RecordCodecBuilder.mapCodec(instance ->
                    foliagePlacerParts(instance)
                            .apply(instance, TieredSpruceFoliagePlacer::new)
            );

    public TieredSpruceFoliagePlacer(IntProvider radius, IntProvider offset) {
        super(radius, offset);
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return ModFoliagePlacerTypes.TIERED_SPRUCE_FOLIAGE_PLACER.get();
    }

    @Override
    protected void createFoliage(LevelSimulatedReader level,
                                 FoliageSetter foliageSetter,
                                 RandomSource random,
                                 TreeConfiguration config,
                                 int trunkHeight,
                                 FoliageAttachment attachment,
                                 int foliageHeight,
                                 int radius,
                                 int offset) {

        BlockPos center = attachment.pos();

        int layerIndex = attachment.radiusOffset();

        int layerRadius = switch (layerIndex) {
            case 0 -> 4;
            case 1, 2 -> 3;
            default -> 1;
        };

        placeLeavesCircle(level, foliageSetter, random, config, center, layerRadius);
    }

    private void placeLeavesCircle(LevelSimulatedReader level,
                                   FoliageSetter setter,
                                   RandomSource random,
                                   TreeConfiguration config,
                                   BlockPos center,
                                   int layerRadius) {

        int hei = (layerRadius == 1) ? 2 : 3;

        for (int y = 0; y < hei; y++) {
            int radius = layerRadius - y;
            if (y == hei - 1 || radius < 1) radius = 1;
            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    if (x * x + z * z <= radius * radius) {
                        BlockPos leafPos = center.offset(x, y, z);
                        tryPlaceLeaf(level, setter, random, config, leafPos);
                    }
                }
            }
        }

        if (layerRadius == 1) {
            BlockPos pos1 = center.above(hei - 1);
            tryPlaceLeaf(level, setter, random, config, pos1);
            pos1 = pos1.above();
            tryPlaceLeaf(level, setter, random, config, pos1);
        }
    }

    @Override
    public int foliageHeight(RandomSource random, int trunkHeight, TreeConfiguration config) {
        return trunkHeight;
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource random,
                                         int localX,
                                         int localY,
                                         int localZ,
                                         int range,
                                         boolean large) {
        return false;
    }
}
