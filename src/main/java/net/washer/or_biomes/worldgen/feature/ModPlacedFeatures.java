package net.washer.or_biomes.worldgen.feature;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.washer.or_biomes.OrBiomes;

import java.util.List;

/**
 * @author 洗衣机Washer
 * @version 1.0.0
 */
public class ModPlacedFeatures {

    public static final ResourceKey<PlacedFeature> ROCK_PILE_PLACED = registerKey("rock_pile");
    public static final ResourceKey<PlacedFeature> ORANGE_MAPLE_PLACED = registerKey("orange_maple");
    public static final ResourceKey<PlacedFeature> RED_MAPLE_PLACED = registerKey("red_maple");
    public static final ResourceKey<PlacedFeature> ORANGE_MAPLE_FORKED_PLACED = registerKey("orange_maple_forked");
    public static final ResourceKey<PlacedFeature> RED_MAPLE_FORKED_PLACED = registerKey("red_maple_forked");
    public static final ResourceKey<PlacedFeature> YELLOW_BIRCH_PLACED = registerKey("yellow_birch");
    public static final ResourceKey<PlacedFeature> HUGE_ORANGE_MUSHROOM_PLACED = registerKey("huge_orange_mushroom");
    public static final ResourceKey<PlacedFeature> ORANGE_MUSHROOM_PLACED = registerKey("orange_mushroom");
    public static final ResourceKey<PlacedFeature> GREEN_FIR_PLACED = registerKey("green_fir");
    public static final ResourceKey<PlacedFeature> ORANGE_FIR_PLACED = registerKey("orange_fir");
    public static final ResourceKey<PlacedFeature> PALM_TREE_PLACED = registerKey("palm_tree");
    public static final ResourceKey<PlacedFeature> MARIGOLD_PLACED = registerKey("marigold");
    public static final ResourceKey<PlacedFeature> TIERED_SPRUCE_PLACED = registerKey("tiered_spruce");
    public static final ResourceKey<PlacedFeature> BAOBAB_TREE_PLACED = registerKey("baobab_tree");
    //====================================================================

    // 向日葵：每2个区块生成一次
    public static final ResourceKey<PlacedFeature> DENSE_SUNFLOWER_PATCH = ResourceKey.create(
            Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(OrBiomes.MOD_ID, "dense_sunflower_patch"));
    // 南瓜：每8个区块生成一次
    public static final ResourceKey<PlacedFeature> SPARSE_PUMPKIN_PATCH = ResourceKey.create(
            Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(OrBiomes.MOD_ID, "sparse_pumpkin_patch"));

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        context.register(
                ORANGE_MUSHROOM_PLACED,
                new PlacedFeature(
                        configuredFeatures.getOrThrow(ModConfiguredFeatures.ORANGE_MUSHROOM_KEY),
                        List.of(
                                CountPlacement.of(UniformInt.of(2, 4)),  // 每区块7次
                                //PlacementUtils.countExtra(3, 0.3f, 1),
                                SurfaceWaterDepthFilter.forMaxDepth(0),
                                InSquarePlacement.spread(),
                                HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR_WG),
                                BiomeFilter.biome()
                        )
                )
        );

        context.register(
                ROCK_PILE_PLACED,
                new PlacedFeature(
                        configuredFeatures.getOrThrow(ModConfiguredFeatures.ROCK_PILE_KEY),
                        List.of(
                                RarityFilter.onAverageOnceEvery(7),
                                CountPlacement.of(1), // 每区块尝试 2 次
                                SurfaceWaterDepthFilter.forMaxDepth(0),
                                InSquarePlacement.spread(),
                                HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR_WG),
                                BiomeFilter.biome()
                        )
                )
        );

        context.register(
                ORANGE_MAPLE_PLACED,
                new PlacedFeature(
                        configuredFeatures.getOrThrow(ModConfiguredFeatures.ORANGE_MAPLE_KEY),
                        List.of(
                                RarityFilter.onAverageOnceEvery(2),
                                CountPlacement.of(1), // 每区块尝试 5 次
                                SurfaceWaterDepthFilter.forMaxDepth(0),
                                InSquarePlacement.spread(),
                                HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR_WG),
                                BiomeFilter.biome()
                        )
                )
        );

        context.register(
                RED_MAPLE_PLACED,
                new PlacedFeature(
                        configuredFeatures.getOrThrow(ModConfiguredFeatures.RED_MAPLE_KEY),
                        List.of(
                                RarityFilter.onAverageOnceEvery(2),
                                CountPlacement.of(1), // 每区块尝试 5 次
                                SurfaceWaterDepthFilter.forMaxDepth(0),
                                InSquarePlacement.spread(),
                                HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR_WG),
                                BiomeFilter.biome()
                        )
                )
        );

        context.register(
                ORANGE_MAPLE_FORKED_PLACED,
                new PlacedFeature(
                        configuredFeatures.getOrThrow(ModConfiguredFeatures.ORANGE_MAPLE_FORKED_KEY),
                        List.of(
                                RarityFilter.onAverageOnceEvery(2),
                                CountPlacement.of(1), // 每区块尝试 1 次
                                SurfaceWaterDepthFilter.forMaxDepth(0),
                                InSquarePlacement.spread(),
                                HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR_WG),
                                BiomeFilter.biome()
                        )
                )
        );

        context.register(
                RED_MAPLE_FORKED_PLACED,
                new PlacedFeature(
                        configuredFeatures.getOrThrow(ModConfiguredFeatures.RED_MAPLE_FORKED_KEY),
                        List.of(
                                RarityFilter.onAverageOnceEvery(2),
                                CountPlacement.of(1), // 每区块尝试 1 次
                                SurfaceWaterDepthFilter.forMaxDepth(0),
                                InSquarePlacement.spread(),
                                HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR_WG),
                                BiomeFilter.biome()
                        )
                )
        );

        context.register(
                YELLOW_BIRCH_PLACED,
                new PlacedFeature(
                        configuredFeatures.getOrThrow(ModConfiguredFeatures.YELLOW_BIRCH_KEY),
                        List.of(
                                //RarityFilter.onAverageOnceEvery(1),
                                CountPlacement.of(UniformInt.of(1, 2)), // 每区块尝试 1 次
                                SurfaceWaterDepthFilter.forMaxDepth(0),
                                InSquarePlacement.spread(),
                                HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR_WG),
                                BiomeFilter.biome()
                        )
                )
        );

        context.register(
                HUGE_ORANGE_MUSHROOM_PLACED,
                new PlacedFeature(
                        configuredFeatures.getOrThrow(ModConfiguredFeatures.HUGE_ORANGE_MUSHROOM_KEY),
                        List.of(
                                RarityFilter.onAverageOnceEvery(4), // 1. 稀有度过滤器：平均每 3 个区块才会有树木生成尝试
                                CountPlacement.of(1), // 则在该区块尝试生成 2 棵树，有 10% 概率多生成 1 棵
                                SurfaceWaterDepthFilter.forMaxDepth(0),
                                InSquarePlacement.spread(),
                                HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR_WG),
                                BiomeFilter.biome()
                        )
                )
        );

        // 注册向日葵
        context.register(DENSE_SUNFLOWER_PATCH,
                new PlacedFeature(
                configuredFeatures.getOrThrow(VegetationFeatures.PATCH_SUNFLOWER), // 引用原版的参数配置
                List.of(
                        RarityFilter.onAverageOnceEvery(5), // 每5个区块一次
                        CountPlacement.of(UniformInt.of(0, 1)),
                        //PlacementUtils.countExtra(3, 0.3f, 1),
                        SurfaceWaterDepthFilter.forMaxDepth(0),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                        BiomeFilter.biome()
                )
        ));

        // 注册南瓜
        context.register(SPARSE_PUMPKIN_PATCH, new PlacedFeature(
                configuredFeatures.getOrThrow(VegetationFeatures.PATCH_PUMPKIN),
                List.of(
                        RarityFilter.onAverageOnceEvery(15), // 每15个区块一次
                        CountPlacement.of(UniformInt.of(0, 1)),
                        //PlacementUtils.countExtra(0, 0.3f, 1),
                        SurfaceWaterDepthFilter.forMaxDepth(0),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                        BiomeFilter.biome()
                )
        ));

        context.register(GREEN_FIR_PLACED,
                new PlacedFeature(
                        configuredFeatures.getOrThrow(ModConfiguredFeatures.GREEN_FIR_KEY),
                        List.of(
                                SurfaceWaterDepthFilter.forMaxDepth(0),
                                CountPlacement.of(UniformInt.of(1, 2)), // 每区块尝试 1~2 次
                                InSquarePlacement.spread(),
                                HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR_WG),
                                BiomeFilter.biome()
                        )
                )
        );

        context.register(ORANGE_FIR_PLACED,
                new PlacedFeature(
                        configuredFeatures.getOrThrow(ModConfiguredFeatures.ORANGE_FIR_KEY),
                        List.of(
                                SurfaceWaterDepthFilter.forMaxDepth(0),
                                CountPlacement.of(UniformInt.of(1, 2)), // 每区块尝试 1~2 次
                                InSquarePlacement.spread(),
                                HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR_WG),
                                BiomeFilter.biome()
                        )
                )
        );

        context.register(
                PALM_TREE_PLACED,
                new PlacedFeature(
                        configuredFeatures.getOrThrow(ModConfiguredFeatures.PALM_TREE_KEY),
                        List.of(
                                SurfaceWaterDepthFilter.forMaxDepth(0),
                                RarityFilter.onAverageOnceEvery(4),
                                CountPlacement.of(UniformInt.of(0, 1)), // 每区块尝试 0~1 次
                                InSquarePlacement.spread(),
                                HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE),
                                BiomeFilter.biome()
                        )
                )
        );

        context.register(
                MARIGOLD_PLACED,
                new PlacedFeature(
                        configuredFeatures.getOrThrow(ModConfiguredFeatures.MARIGOLD_KEY),
                        List.of(
                                CountPlacement.of(UniformInt.of(2, 4)),  // 每区块7次
                                //PlacementUtils.countExtra(3, 0.3f, 1),
                                SurfaceWaterDepthFilter.forMaxDepth(0),
                                InSquarePlacement.spread(),
                                HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE),
                                BiomeFilter.biome()
                        )
                )
        );

        context.register(
                TIERED_SPRUCE_PLACED,
                new PlacedFeature(
                        configuredFeatures.getOrThrow(ModConfiguredFeatures.TIERED_SPRUCE_KEY),
                        List.of(
                                RarityFilter.onAverageOnceEvery(5),
                                CountPlacement.of(UniformInt.of(0, 1)), // 每5个区块尝试 0-1 次
                                SurfaceWaterDepthFilter.forMaxDepth(0),
                                InSquarePlacement.spread(),
                                HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR_WG),
                                BiomeFilter.biome()
                        )
                )
        );

        context.register(
                BAOBAB_TREE_PLACED,
                new PlacedFeature(
                        configuredFeatures.getOrThrow(ModConfiguredFeatures.BAOBAB_TREE_KEY),
                        List.of(
                                RarityFilter.onAverageOnceEvery(6),
                                CountPlacement.of(UniformInt.of(0, 1)), // 每5个区块尝试 0-1 次
                                SurfaceWaterDepthFilter.forMaxDepth(0),
                                InSquarePlacement.spread(),
                                //HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR_WG),
                                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                                BiomeFilter.biome()
                        )
                )
        );
    }

    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(OrBiomes.MOD_ID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
