package net.washer.or_biomes.worldgen.tree.custom_tree;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.washer.or_biomes.worldgen.tree.ModTrunkPlacerTypes;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * @author 洗衣机Washer
 * @version 1.0.0
 */
public class BirchTallTrunkPlacer extends TrunkPlacer {

    public static final MapCodec<BirchTallTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(instance ->
            trunkPlacerParts(instance).apply(instance, BirchTallTrunkPlacer::new)
    );

    public BirchTallTrunkPlacer(int baseHeight, int heightRandA, int heightRandB) {
        super(baseHeight, heightRandA, heightRandB);
    }

    @Override
    protected @NotNull TrunkPlacerType<?> type() {
        return ModTrunkPlacerTypes.BIRCH_TALL_TRUNK_PLACER.get();
    }

    @Override
    public @NotNull List<FoliagePlacer.FoliageAttachment> placeTrunk(
            LevelSimulatedReader level,
            BiConsumer<BlockPos, BlockState> blockSetter,
            RandomSource random,
            int freeTreeHeight,
            BlockPos startPos,
            TreeConfiguration config)
    {
        setDirtAt(level, blockSetter, random, startPos.below(), config);
        List<FoliagePlacer.FoliageAttachment> attachments = new ArrayList<>();
        //分支的预设高度列表
        List<Integer> bumpList = getBumpListByHeight(freeTreeHeight);
        int size_ = bumpList.size();
        //分支的实际高度列表
        List<List<Integer>> bumpHeightList = new ArrayList<>();
        //每个分支的偏移方向列表
        List<List<Direction>> bumpDirList = new ArrayList<>();

        for (int i = 0; i < size_; i++) {
            bumpHeightList.add(new ArrayList<>());
            bumpDirList.add(new ArrayList<>());
        }

        // 1️⃣ 生成主干
        for (int i = 0; i < freeTreeHeight; i++) {
            placeLog(level, blockSetter, random, startPos.above(i), config);
        }
        // 生成分支
        int hei = 0;
        for (int i = 0; i < bumpList.size(); i++) {
            bumpHeightList.set(i, new ArrayList<>());
            bumpDirList.set(i, new ArrayList<>());

            hei = bumpList.get(i) + random.nextInt(3) - 1;
            Direction prevDir = placeBumpLog(level, blockSetter, random, config,
                    startPos.above(hei));
            bumpHeightList.get(i).add(hei);
            bumpDirList.get(i).add(prevDir);

            hei = bumpList.get(i) + random.nextInt(3) - 1;
            Direction nowDir = placeBumpLog(level, blockSetter, random, config,
                    startPos.above(hei), prevDir);
            bumpHeightList.get(i).add(hei);
            bumpDirList.get(i).add(nowDir);
        }

        // 2️⃣ 顶部大树冠
        BlockPos topPos = startPos.above(freeTreeHeight - 1);
        attachments.add(new FoliagePlacer.FoliageAttachment(topPos, 0, false));

        for (int i = 0; i < bumpHeightList.size(); i++) {
            for (int j = 0; j < bumpHeightList.get(i).size(); j++) {
                BlockPos posi;
                if ((i - bumpHeightList.size()) < -2) {
                    posi = startPos.above(bumpHeightList.get(i).get(j) + 1).offset(bumpDirList.get(i).get(j).getNormal());
                }
                else {
                    posi = startPos.above(bumpHeightList.get(i).get(j)).offset(bumpDirList.get(i).get(j).getNormal());
                }

                attachments.add(new FoliagePlacer.FoliageAttachment(
                        posi,
                        (Math.max((i - bumpHeightList.size()), -2)),  // 负值会让叶子更贴近主干
                        false
                ));
            }
        }

        return attachments;
    }

    private Direction placeBumpLog(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter,
                              RandomSource random, TreeConfiguration config, BlockPos pos)
    {
        Direction dir = switch (random.nextInt(4)) {
            case 1 -> Direction.SOUTH;
            case 2 -> Direction.EAST;
            case 3 -> Direction.WEST;
            default -> Direction.NORTH;
        };

        BlockPos pos2 = pos.offset(dir.getNormal());
        if (this.validTreePos(level, pos2)) {
            BlockState state = config.trunkProvider.getState(random, pos2);
            blockSetter.accept(pos2, state);
        }

        return dir;
    }

    private Direction placeBumpLog(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter,
                                   RandomSource random, TreeConfiguration config, BlockPos pos, Direction prevDir)
    {
        Direction dir = prevDir.getOpposite();
        BlockPos pos2 = pos.offset(dir.getNormal());
        if (this.validTreePos(level, pos2)) {
            BlockState state = config.trunkProvider.getState(random, pos2);
            blockSetter.accept(pos2, state);
        }

        return dir;
    }

    public static List<Integer> getBumpListByHeight(int treeHeight) {
        if (treeHeight <= 8) {
            return List.of(4);
        }
        else if (treeHeight <= 11) {
            return List.of(5, 8);
        }
        else if (treeHeight <= 15) {
            return List.of(4, 7, 12);
        }
        else {
            return List.of(4, 7, 12, 15);
        }
    }
}
