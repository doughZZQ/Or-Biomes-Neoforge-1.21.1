package net.washer.or_biomes.worldgen.tree.custom_tree;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.washer.or_biomes.worldgen.tree.ModFoliagePlacerTypes;

import java.util.function.BiConsumer;

/**
 * @author 洗衣机Washer
 * @version 1.0.0
 */
public class PrimalOakFoliagePlacer extends FoliagePlacer {

    public static final MapCodec<PrimalOakFoliagePlacer> CODEC =
            RecordCodecBuilder.mapCodec(instance ->
                    foliagePlacerParts(instance)
                            .apply(instance, PrimalOakFoliagePlacer::new));

    public PrimalOakFoliagePlacer(IntProvider radius, IntProvider offset) {
        super(radius, offset);
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return ModFoliagePlacerTypes.PRIMAL_OAK_FOLIAGE_PLACER.get();
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

        int maxLayer = 4 + random.nextInt(2); // 巨大树冠
        int r = 2;
        for (int y = 0; y <= maxLayer; y++) {
            r = switch (y) {
                case 0 -> 1;
                case 1, 2 -> 4;
                case 3 -> 3;
                case 4 -> 2;
                default -> r;
            };
            for (int x = -r - 1; x <= r + 1; x++) {
                for (int z = -r - 1; z <= r + 1; z++) {

                    if (!(y == 0 || y == 3)) {
                        if ((Math.abs(x) == r && z == 0) || (Math.abs(z) == r && x == 0)) {
                            continue;
                        }
                    }

                    if (Math.abs(x) + Math.abs(z) <= r) {
                        BlockPos leafPos = center.offset(x, y, z);
                        tryPlaceLeaf(level, setter, random, config, leafPos);
                    }
                    else {
                        BlockPos vinePos = center.offset(x, y, z);
                        placeVineColumn(level, setter, random, vinePos, Direction.WEST);
                        placeVineColumn(level, setter, random, vinePos, Direction.EAST);
                        placeVineColumn(level, setter, random, vinePos, Direction.NORTH);
                        placeVineColumn(level, setter, random, vinePos, Direction.SOUTH);
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

            if (!level.isStateAtPosition(current, BlockState::isAir)) continue;
            BlockPos attachPos = current.relative(attachSide.getOpposite());
            if (!level.isStateAtPosition(current.above(), state -> state.is(Blocks.VINE))) {
                if (level.isStateAtPosition(attachPos, state ->
                        state.isAir() || state.is(Blocks.VINE))) continue;
            }

            if (random.nextFloat() > 0.5f) continue;

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
    public int foliageHeight(RandomSource random, int height, TreeConfiguration config) {
        return 6;
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource random, int localX, int localY, int localZ, int range, boolean large) {
        return false;
    }
}
