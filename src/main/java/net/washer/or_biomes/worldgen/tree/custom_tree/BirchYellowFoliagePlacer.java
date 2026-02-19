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
public class BirchYellowFoliagePlacer extends FoliagePlacer {

    public static final MapCodec<BirchYellowFoliagePlacer> CODEC =
            RecordCodecBuilder.mapCodec(instance ->
                    foliagePlacerParts(instance)
                            .and(IntProvider.codec(1, 16).fieldOf("radius").forGetter(p -> p.radius))
                            .apply(instance, BirchYellowFoliagePlacer::new)
            );

    private final IntProvider radius;
    private final IntProvider leavesHeight;

    public BirchYellowFoliagePlacer(IntProvider radius, IntProvider offset, IntProvider leavesHeight) {
        super(radius, offset);
        this.radius = radius;
        this.leavesHeight = leavesHeight;

    }

    @Override
    protected FoliagePlacerType<?> type() {
        return ModFoliagePlacerTypes.BIRCH_YELLOW_FOLIAGE_PLACER.get();
    }

    @Override
    protected void createFoliage(LevelSimulatedReader level,
                                 FoliageSetter blockSetter,
                                 RandomSource random,
                                 TreeConfiguration config,
                                 int maxFreeTreeHeight,
                                 FoliageAttachment attachment,
                                 int foliageHeight,
                                 int foliageRadius,
                                 int offset)
    {
        BlockPos center = attachment.pos();

        // 👇 如果是顶部（radiusOffset == 0）→ 用球形
        if (attachment.radiusOffset() == 0) {
            int r = this.radius.getMaxValue();

            for (int y = 0; y <= leavesHeight.getMaxValue(); y++) {
                int layerRadius = r - y;
                if (layerRadius == 1) break;
                for (int x = -layerRadius; x <= layerRadius; x++) {
                    for (int z = -layerRadius; z <= layerRadius; z++) {

                        if ((Math.abs(x) == layerRadius && z == 0) || (Math.abs(z) == layerRadius && x == 0)) {
                            continue;
                        }

                        if (Math.abs(x) + Math.abs(z) <= layerRadius) {
                            BlockPos leafPos = center.offset(x, y, z);
                            tryPlaceLeaf(level, blockSetter, random, config, leafPos);
                        }
                    }
                }
            }
        }
        else {
            // 👇 中段 & 底部 → 用 Blob 风格
            int r = Math.max(1, 3 + attachment.radiusOffset());

            for (int y = 0; y <= (3 - attachment.radiusOffset()); y++) {
                int layerRadius = r - y;
                if (layerRadius == 0) break;
                for (int x = -layerRadius; x <= layerRadius; x++) {
                    for (int z = -layerRadius; z <= layerRadius; z++) {

                        if (Math.abs(x) + Math.abs(z) <= layerRadius) {
                            BlockPos leafPos = center.offset(x, y, z);
                            tryPlaceLeaf(level, blockSetter, random, config, leafPos);
                        }
                    }
                }
            }
        }
    }

    @Override
    public int foliageHeight(RandomSource random, int height, TreeConfiguration config) {
        return radius.sample(random) * 2;
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource random, int localX, int localY, int localZ, int range, boolean large) {
        return false;
    }
}
