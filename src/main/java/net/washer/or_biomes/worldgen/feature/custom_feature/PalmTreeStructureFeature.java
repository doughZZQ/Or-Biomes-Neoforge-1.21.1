package net.washer.or_biomes.worldgen.feature.custom_feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.washer.or_biomes.OrBiomes;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 洗衣机Washer
 * @version 1.0.0
 */
public class PalmTreeStructureFeature extends Feature<NoneFeatureConfiguration> {

    private final List<WeightedStructure> structures = new ArrayList<>();

    public PalmTreeStructureFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);

        // 添加结构 + 权重
        structures.add(new WeightedStructure("trees/palm_tree_0", 3));
        structures.add(new WeightedStructure("trees/palm_tree_1", 3));
        structures.add(new WeightedStructure("trees/palm_tree_2", 3));
        structures.add(new WeightedStructure("trees/palm_tree_3", 5));
        structures.add(new WeightedStructure("trees/palm_tree_4", 5));
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {

        ServerLevel level = context.level().getLevel();
        BlockPos pos = context.origin();
        RandomSource random = context.random();

        // 选择一个结构
        WeightedStructure chosen = getRandomStructure(random);

        chosen.load(level);

        StructurePlaceSettings settings = new StructurePlaceSettings()
                .setRotation(Rotation.getRandom(random))
                .setIgnoreEntities(true)
                .setKnownShape(true);

        chosen.template.placeInWorld(
                level,
                pos,
                pos,
                settings,
                random,
                Block.UPDATE_ALL
        );

        return true;
    }

    private WeightedStructure getRandomStructure(RandomSource random) {

        int totalWeight = structures.stream().mapToInt(s -> s.weight).sum();
        int value = random.nextInt(totalWeight);

        int current = 0;

        for (WeightedStructure s : structures) {
            current += s.weight;
            if (value < current) {
                return s;
            }
        }

        return structures.get(0);
    }


    private static class WeightedStructure {

        final String path;
        final int weight;
        StructureTemplate template;

        WeightedStructure(String path, int weight) {
            this.path = path;
            this.weight = weight;
        }

        void load(ServerLevel level) {
            template = level.getStructureManager()
                    .getOrCreate(ResourceLocation.fromNamespaceAndPath(OrBiomes.MOD_ID, path));
        }
    }
}
