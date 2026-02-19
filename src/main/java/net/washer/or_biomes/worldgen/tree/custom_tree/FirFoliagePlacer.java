package net.washer.or_biomes.worldgen.tree.custom_tree;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.washer.or_biomes.worldgen.tree.ModFoliagePlacerTypes;

import java.util.List;

/**
 * @author 洗衣机Washer
 * @version 1.0.0
 */
public class FirFoliagePlacer extends FoliagePlacer {

    public static final MapCodec<FirFoliagePlacer> CODEC =
            RecordCodecBuilder.mapCodec(instance ->
                    foliagePlacerParts(instance)
                            .apply(instance, FirFoliagePlacer::new)
            );

    public FirFoliagePlacer(IntProvider radius, IntProvider offset) {
        super(radius, offset);
    }


    @Override
    protected FoliagePlacerType<?> type() {
        return ModFoliagePlacerTypes.FIR_FOLIAGE_PLACER.get();
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
            int foliageRadius,
            int offset
    ) {
        BlockPos center = attachment.pos();

        int ratio = attachment.radiusOffset();

        int baseRadius;
        int layerHeight;
        boolean isUp;

        if (ratio == 0) {
            baseRadius = 2;
            layerHeight = 3 + random.nextInt(2);
            isUp = false;
        }
        else if (ratio == 1) {
            baseRadius = 2;
            layerHeight = 3;
            isUp = false;
        }
        else if (ratio == 2) {
            baseRadius = 1;
            layerHeight = 2 + random.nextInt(2);
            isUp = true;
        }
        else {
            baseRadius = 1;
            layerHeight = 2; // 顶部尖塔
            isUp = true;
        }

        if (trunkHeight >= 20) {
            if (ratio == 2) {
                baseRadius = 2;
                layerHeight = 3;
                isUp = false;
            }
            else if (ratio == 3) {
                baseRadius = 1;
                layerHeight = 2;
                isUp = true;
            }
            else if (ratio == 4) {
                baseRadius = 1;
                layerHeight = 2; // 顶部尖塔
                isUp = true;
            }
        }

        for (int y = 0; y < layerHeight; y++) {

            int currentRadius = baseRadius - y;
            if (currentRadius < 1) currentRadius = 1;

            for (int dx = -currentRadius; dx <= currentRadius; dx++) {
                for (int dz = -currentRadius; dz <= currentRadius; dz++) {

                    double dist = Math.sqrt(dx * dx + dz * dz);

                    if (dist <= currentRadius + 0.5) {

                        // 留出主干空间
                        if (dx == 0 && dz == 0) continue;
                        if ((y == layerHeight - 1) && isUp) {
                            if ((Math.abs(dx) != 0 && Math.abs(dz) != 0) && random.nextBoolean()) continue;
                        }

                        BlockPos leafPos = center.offset(dx, y, dz);
                        tryPlaceLeaf(level, setter, random, config, leafPos);
                    }
                }
            }
        }

        for (Direction dir : List.of(Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST)) {
            if (ratio == 1) {
                Vec3i offset2 = switch (dir) {
                    case NORTH -> new Vec3i(1, -1, -2);
                    case SOUTH -> new Vec3i(-1, -1, 2);
                    case WEST -> new Vec3i(-2, -1, -1);
                    case EAST -> new Vec3i(2, -1, 1);
                    default -> Vec3i.ZERO;
                };
                Vec3i offset3;
                if (random.nextBoolean()) {
                    if (dir == Direction.NORTH || dir == Direction.WEST) {
                        offset3 = new Vec3i(offset2.getX(), offset2.getY(), offset2.getZ() - 1);
                    }
                    else {
                        offset3 = new Vec3i(offset2.getX(), offset2.getY(), offset2.getZ() + 1);
                    }
                }
                else {
                    if (dir == Direction.SOUTH || dir == Direction.WEST) {
                        offset3 = new Vec3i(offset2.getX() - 1, offset2.getY(), offset2.getZ());
                    }
                    else {
                        offset3 = new Vec3i(offset2.getX() + 1, offset2.getY(), offset2.getZ());
                    }
                }
                BlockPos leafPos = center.offset(offset2);
                tryPlaceLeaf(level, setter, random, config, leafPos);
                leafPos = center.offset(offset3);
                tryPlaceLeaf(level, setter, random, config, leafPos);
            }
            else {
                Vec3i offset2;
                if (!isUp) {
                    offset2 = new Vec3i(dir.getStepX() * 2, -1, dir.getStepZ() * 2);
                } else {
                    offset2 = new Vec3i(dir.getStepX() * 2, 0, dir.getStepZ() * 2);
                }
                BlockPos leafPos = center.offset(offset2);
                tryPlaceLeaf(level, setter, random, config, leafPos);
                Vec3i offset3;
                if (!isUp) {
                    offset3 = dir.getNormal();
                    tryPlaceLeaf(level, setter, random, config, leafPos.offset(offset3));
                } else {
                    tryPlaceLeaf(level, setter, random, config, leafPos.below());
                }
            }
        }

        if (trunkHeight >= 20) {
            if (ratio == 4) {
                BlockPos pos1 = center.above(layerHeight);
                tryPlaceLeaf(level, setter, random, config, pos1);
                pos1 = pos1.above();
                tryPlaceLeaf(level, setter, random, config, pos1);
            }
        }
        else {
            if (ratio == 3) {
                BlockPos pos1 = center.above(layerHeight);
                tryPlaceLeaf(level, setter, random, config, pos1);
                pos1 = pos1.above();
                tryPlaceLeaf(level, setter, random, config, pos1);
            }
        }
    }

    @Override
    public int foliageHeight(RandomSource random, int height, TreeConfiguration config) {
        return 4;
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource random, int dx, int y, int dz, int radius, boolean giantTrunk) {
        return false;
    }
}
