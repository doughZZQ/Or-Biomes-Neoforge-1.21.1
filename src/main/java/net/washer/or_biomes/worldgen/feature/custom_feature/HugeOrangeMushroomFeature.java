package net.washer.or_biomes.worldgen.feature.custom_feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.AbstractHugeMushroomFeature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

/**
 * @author 洗衣机Washer
 * @version 1.0.0
 */
public class HugeOrangeMushroomFeature extends AbstractHugeMushroomFeature {

    public HugeOrangeMushroomFeature(Codec<HugeMushroomFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    protected int getTreeRadiusForHeight(int p_65094_, int height, int foliageRadius, int y) {
        return 0;
    }

    @Override
    protected void makeCap(LevelAccessor level,
                           RandomSource random,
                           BlockPos pos,
                           int height,
                           BlockPos.MutableBlockPos mutablePos,
                           HugeMushroomFeatureConfiguration config)
    {
        int radius = 3;

        BlockPos top = pos.above(height);

        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {

                if (Math.abs(x) + Math.abs(z) <= radius + 1) {

                    mutablePos.set(top).move(x, 0, z);

                    this.setBlock(level, mutablePos,
                            config.capProvider.getState(random, pos));
                }
            }
        }

        // 第二层
        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {

                mutablePos.set(top).move(x, 1, z);

                this.setBlock(level, mutablePos,
                        config.capProvider.getState(random, pos));
            }
        }
    }

    @Override
    protected void placeTrunk(LevelAccessor level,
                              RandomSource random,
                              BlockPos pos,
                              HugeMushroomFeatureConfiguration config,
                              int height,
                              BlockPos.MutableBlockPos mutablePos)
    {
        for (int y = 0; y < height; y++) {

            mutablePos.set(pos).move(Direction.UP, y);

            this.setBlock(level, mutablePos, config.stemProvider.getState(random, pos));

            // 在中间层随机生成分支
            if (y > 1 && y < height - 1) {
                tryGenerateBranch(level, random, pos, mutablePos, config);
            }
        }
    }

    @Override
    public boolean place(FeaturePlaceContext<HugeMushroomFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();
        HugeMushroomFeatureConfiguration config = context.config();

        int height = 4 + random.nextInt(3); // 4~6格高

        // 检查是否可以生成
        if (!this.isValidPosition(level, origin, height, origin.mutable(), config)) {
            return false;
        }

        BlockPos.MutableBlockPos mutablePos = origin.mutable();

        // 生成主干
        placeTrunk(level, random, origin, config, height, mutablePos);

        // 生成顶部伞盖
        makeCap(level, random, origin, height, mutablePos, config);

        return true;
    }

    private void tryGenerateBranch(LevelAccessor level,
                                   RandomSource random,
                                   BlockPos pos,
                                   BlockPos origin,
                                   HugeMushroomFeatureConfiguration config) {

        for (Direction dir : Direction.Plane.HORIZONTAL) {

            if (random.nextFloat() < 0.4f) { // 40%概率生成分支

                int length = 1;// + random.nextInt(2); // 1~2格

                BlockPos.MutableBlockPos branchPos = origin.mutable();

                for (int i = 0; i < length; i++) {

                    branchPos.move(dir);

                    this.setBlock(level, branchPos,
                            config.stemProvider.getState(random, origin));
                }

                int radius = 1;

                BlockPos top = branchPos.above(1);

                for (int x = -radius; x <= radius; x++) {
                    for (int z = -radius; z <= radius; z++) {

                        if (Math.abs(x) + Math.abs(z) <= radius + 1) {

                            this.setBlock(level, top.offset(x, 0, z),
                                    config.capProvider.getState(random, pos));
                        }
                    }
                }
            }
        }
    }

}
