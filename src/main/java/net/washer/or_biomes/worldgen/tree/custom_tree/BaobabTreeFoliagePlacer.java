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
public class BaobabTreeFoliagePlacer extends FoliagePlacer {

    public static final MapCodec<BaobabTreeFoliagePlacer> CODEC =
            RecordCodecBuilder.mapCodec(instance ->
                    foliagePlacerParts(instance)
                            .apply(instance, BaobabTreeFoliagePlacer::new)
            );

    public BaobabTreeFoliagePlacer(IntProvider radius, IntProvider offset) {
        super(radius, offset);
    }


    @Override
    protected FoliagePlacerType<?> type() {
        return ModFoliagePlacerTypes.BAOBAB_TREE_FOLIAGE_PLACER.get();
    }

    @Override
    protected void createFoliage(
            LevelSimulatedReader level,
            FoliageSetter foliageSetter,
            RandomSource random,
            TreeConfiguration config,
            int maxFreeTreeHeight,
            FoliageAttachment attachment,
            int foliageHeight,
            int foliageRadius,
            int offset
    ) {
        BlockPos center = attachment.pos();

        placeLeavesCircle(level, foliageSetter, random, config, center, 2);
    }

    private void placeLeavesCircle(LevelSimulatedReader level,
                                   FoliageSetter setter,
                                   RandomSource random,
                                   TreeConfiguration config,
                                   BlockPos center,
                                   int layerRadius) {

        for (int y = 0; y < layerRadius; y++) {
            int radius = layerRadius - y;
            if (y == layerRadius - 1 || radius < 1) radius = 1;
            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    if (x * x + z * z <= radius * radius) {
                        BlockPos leafPos = center.offset(x, y, z);
                        tryPlaceLeaf(level, setter, random, config, leafPos);
                    }
                }
            }
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
