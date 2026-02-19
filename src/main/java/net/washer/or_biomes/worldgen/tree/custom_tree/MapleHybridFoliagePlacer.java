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
public class MapleHybridFoliagePlacer extends FoliagePlacer {

    public static final MapCodec<MapleHybridFoliagePlacer> CODEC =
            RecordCodecBuilder.mapCodec(instance ->
                    foliagePlacerParts(instance)
                            .and(IntProvider.codec(1, 16).fieldOf("radius").forGetter(p -> p.radius))
                            .apply(instance, MapleHybridFoliagePlacer::new)
            );

    private final IntProvider radius;

    public MapleHybridFoliagePlacer(IntProvider radius, IntProvider offset, IntProvider leavesHeight) {
        super(radius, offset);
        this.radius = radius;
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return ModFoliagePlacerTypes.MAPLE_HYBRID_FOLIAGE_PLACER.get();
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
            int r = this.radius.sample(random);

            for (int y = -r; y <= r; y++) {
                for (int x = -r; x <= r; x++) {
                    for (int z = -r; z <= r; z++) {

                        // 球体公式
                        if (x * x + (int) (y * y * 0.8f) + z * z <= r * r - random.nextInt(2)) {

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

            for (int y = 0; y <= 2; y++) {
                int layerRadius = r - y;

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
    public int foliageHeight(RandomSource random, int trunkHeight, TreeConfiguration config) {
        return radius.sample(random) * 2;
    }

    @Override
    protected boolean shouldSkipLocation(
            RandomSource random,
            int x,
            int y,
            int z,
            int radius,
            boolean giantTrunk
    ) {
        return false; // 不跳过任何位置
    }
}
