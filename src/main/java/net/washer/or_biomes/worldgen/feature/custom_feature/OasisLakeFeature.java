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
public class OasisLakeFeature extends Feature<NoneFeatureConfiguration> {

    public OasisLakeFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {

        BlockPos center = context.origin();
        WorldGenLevel level = context.level();

        int lakeCount = 1 + context.random().nextInt(2);

        for (int i = 0; i < lakeCount; i++) {
            BlockPos lakePos = center.offset(
                    context.random().nextInt(8) - 4,
                    0,
                    context.random().nextInt(8) - 4
            );

            generateLake(level, lakePos, context.random());

        }

        return true;
    }

    private void generateLake(WorldGenLevel level, BlockPos center, RandomSource random) {

        int radius = 4 + random.nextInt(3);

        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {

                if (x*x + z*z <= radius*radius) {

                    BlockPos pos = center.offset(x, 0, z);

                    level.setBlock(pos.below(), Blocks.WATER.defaultBlockState(), 2);
                    level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);

                    // 湖底
                    level.setBlock(pos.below(2),
                            Blocks.SAND.defaultBlockState(), 2);

                    if (random.nextFloat() < 0.6f)
                        level.setBlock(pos.below(), Blocks.SEAGRASS.defaultBlockState(), 2);

//                    if (random.nextFloat() < 0.4f)
//                        placeCattail(...);

                    if (random.nextFloat() < 0.5f)
                        level.setBlock(pos.below(), Blocks.LILY_PAD.defaultBlockState(), 2);
                }
            }
        }
    }

}

