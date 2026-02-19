package net.washer.or_biomes.worldgen.feature.custom_feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

/**
 * @author 洗衣机Washer
 * @version 1.0.0
 */
public class RockPileFeature extends Feature<NoneFeatureConfiguration> {

    public RockPileFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();

        // 找到地表
        BlockPos surface = level.getHeightmapPos(
                Heightmap.Types.WORLD_SURFACE_WG,
                origin
        );

        // 随机选择岩石
        BlockState rockState = getRandomRock(random);

        // 随机大小
        int baseRadius = 2;// + random.nextInt(2); // 2 半径
        int height = 2 + random.nextInt(3);     // 2~4 层

        for (int y = 0; y < height; y++) {

            int radius = baseRadius - ((y > 1) ? random.nextInt(y) : 0);
            if (radius <= 0) break;
            int o = 0;
            if (y >= height / 2) {
                o = 1;
            }

            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {

                    if ((Math.abs(x) >= (radius - o) || Math.abs(z) >= (radius - o)) && random.nextFloat() > 0.4f) {
                        continue;
                    }

                    if (x * x + z * z <= radius * radius) {
                        // 让形状不规则
                        BlockPos pos = surface.offset(x, y, z);

                        if (level.getBlockState(pos.below()).isSolid()) {
                            level.setBlock(pos, rockState, 2);
                        }
                    }
                }
            }
        }

        return false;
    }

    private BlockState getRandomRock(RandomSource random) {
        return switch (random.nextInt(3)) {
            case 0 -> Blocks.GRANITE.defaultBlockState();
            case 1 -> Blocks.DIORITE.defaultBlockState();
            default -> Blocks.ANDESITE.defaultBlockState();
        };
    }
}
