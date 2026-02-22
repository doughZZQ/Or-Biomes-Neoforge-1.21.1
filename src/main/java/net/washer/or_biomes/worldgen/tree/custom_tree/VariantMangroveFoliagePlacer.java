package net.washer.or_biomes.worldgen.tree.custom_tree;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.washer.or_biomes.worldgen.tree.ModFoliagePlacerTypes;

/**
 * @author 洗衣机Washer
 * @version 1.0.0
 */
public class VariantMangroveFoliagePlacer extends FoliagePlacer {

    public static final MapCodec<VariantMangroveFoliagePlacer> CODEC =
            RecordCodecBuilder.mapCodec(instance ->
                    foliagePlacerParts(instance)
                            .apply(instance, VariantMangroveFoliagePlacer::new)
            );

    public VariantMangroveFoliagePlacer(IntProvider radius, IntProvider offset) {
        super(radius, offset);
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return ModFoliagePlacerTypes.VARIANT_MANGROVE_FOLIAGE_PLACER.get();
    }

    @Override
    protected void createFoliage(
            LevelSimulatedReader level,
            FoliageSetter setter,
            RandomSource random,
            TreeConfiguration config,
            int trunkHeight,
            FoliageAttachment attachment,
            int foliageHeight,
            int radius,
            int offset
    ) {

        BlockPos center = attachment.pos();

        int mainRadius = 5;

        placeLayer(level, setter, random, config, center.above(2), mainRadius - 2);

        // 上层圆盘
        placeLayer(level, setter, random, config, center.above(1), mainRadius - 1);

        // 下层略小
        placeLayer(level, setter, random, config, center, mainRadius);
        addVinesAround(level, setter, random, config, center, mainRadius - 1);

        // 下垂效果
        addHangingLeaves(level, setter, random, config, center, mainRadius);
    }

    private void placeLayer(
            LevelSimulatedReader level,
            FoliageSetter setter,
            RandomSource random,
            TreeConfiguration config,
            BlockPos center,
            int radius
    ) {

        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {

                if (x * x + z * z <= radius * radius) {

                    BlockPos leafPos = center.offset(x, 0, z);

                    tryPlaceLeaf(level, setter, random, config, leafPos);
                }
            }
        }
    }

    private void addHangingLeaves(
            LevelSimulatedReader level,
            FoliageSetter setter,
            RandomSource random,
            TreeConfiguration config,
            BlockPos center,
            int radius
    ) {

        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {

                if (x * x + z * z <= radius * radius) {

                    if (random.nextFloat() < 0.2f) {

                        BlockPos pos = center.offset(x, -2, z);

                        tryPlaceLeaf(level, setter, random, config, pos);
                    }
                }
            }
        }
    }

    private void addVinesAround(
            LevelSimulatedReader level,
            FoliageSetter setter,
            RandomSource random,
            TreeConfiguration config,
            BlockPos center,
            int radius
    ) {

        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {

                // 只处理最外圈
                if (x * x + z * z == radius * radius) {

                    BlockPos leafPos = center.offset(x, 0, z);

                    for (Direction dir : Direction.Plane.HORIZONTAL) {
                        BlockPos vinePos = leafPos.relative(dir);

                        if (random.nextFloat() < 0.7f) {

                            placeVineColumn(level, setter, random, vinePos, dir);
                        }
                    }
                }
            }
        }
    }

    private void placeVineColumn(
            LevelSimulatedReader level,
            FoliageSetter setter,
            RandomSource random,
            BlockPos startPos,
            Direction attachSide
    ) {

        int length = 3 + random.nextInt(4);

        BlockPos current = startPos;

        for (int i = 0; i < length; i++) {
            if (!TreeFeature.isAirOrLeaves(level, current)) break;

            BlockState vine = Blocks.VINE.defaultBlockState();

            vine = switch (attachSide) {
                case NORTH -> vine.setValue(VineBlock.SOUTH, true);
                case SOUTH -> vine.setValue(VineBlock.NORTH, true);
                case EAST  -> vine.setValue(VineBlock.WEST, true);
                case WEST  -> vine.setValue(VineBlock.EAST, true);
                default -> vine;
            };

            setter.set(current, vine);

            current = current.below();
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
