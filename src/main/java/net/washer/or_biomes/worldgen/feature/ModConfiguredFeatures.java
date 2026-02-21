package net.washer.or_biomes.worldgen.feature;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.washer.or_biomes.OrBiomes;
import net.washer.or_biomes.blocks.ModBlocks;
import net.washer.or_biomes.worldgen.tree.custom_tree.*;

/**
 * @author 洗衣机Washer
 * @version 1.0.0
 */
public class ModConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> ORANGE_MAPLE_KEY = registerKey("orange_maple");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RED_MAPLE_KEY = registerKey("red_maple");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORANGE_MAPLE_FORKED_KEY = registerKey("orange_maple_forked");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RED_MAPLE_FORKED_KEY = registerKey("red_maple_forked");
    public static final ResourceKey<ConfiguredFeature<?, ?>> YELLOW_BIRCH_KEY = registerKey("yellow_birch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> HUGE_ORANGE_MUSHROOM_KEY = registerKey("huge_orange_mushroom");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORANGE_MUSHROOM_KEY = registerKey("orange_mushroom");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ROCK_PILE_KEY = registerKey("rock_pile");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GREEN_FIR_KEY = registerKey("green_fir");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORANGE_FIR_KEY = registerKey("orange_fir");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PALM_TREE_KEY = registerKey("palm_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MARIGOLD_KEY = registerKey("marigold");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TIERED_SPRUCE_KEY = registerKey("tiered_spruce");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BAOBAB_TREE_KEY = registerKey("baobab_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TALL_BIRCH_KEY = registerKey("tall_birch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FROSTED_SPRUCE_KEY = registerKey("frosted_spruce");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BAYOU_TREE_KEY = registerKey("bayou_tree");
    //===================================================================

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {

        register(context, ORANGE_MAPLE_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.MAPLE_LOG_BLOCK.get()),
                new MapleTrunkPlacer(10, 4, 4),

                BlockStateProvider.simple(ModBlocks.MAPLE_LEAVES_ORANGE_BLOCK.get()),
                new MapleHybridFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), ConstantInt.of(2)),

                new TwoLayersFeatureSize(1, 0, 1)).build()
        );

        register(context, RED_MAPLE_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.MAPLE_LOG_BLOCK.get()),
                new MapleTrunkPlacer(10, 4, 4), //这里应该使用哪个TrunkPlacer的子类？参数应该怎么填？

                BlockStateProvider.simple(ModBlocks.MAPLE_LEAVES_RED_BLOCK.get()),
                new MapleHybridFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), ConstantInt.of(2)), //这里应该使用哪个FoliagePlacer的子类？参数应该怎么填？

                new TwoLayersFeatureSize(1, 0, 1)).build() //这里应该使用哪个FeatureSize的子类？参数应该怎么填？
        );

        register(context, ORANGE_MAPLE_FORKED_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.MAPLE_LOG_BLOCK.get()),
                new MapleForkedTrunkPlacer(3, 2, 1),

                BlockStateProvider.simple(ModBlocks.MAPLE_LEAVES_ORANGE_BLOCK.get()),
                new MapleHybridFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), ConstantInt.of(6)
                ),

                new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build()
        );

        register(context, RED_MAPLE_FORKED_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.MAPLE_LOG_BLOCK.get()),
                new MapleForkedTrunkPlacer(3, 2, 1),

                BlockStateProvider.simple(ModBlocks.MAPLE_LEAVES_RED_BLOCK.get()),
                new MapleHybridFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), ConstantInt.of(6)
                ),

                new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build()
        );

        register(context, YELLOW_BIRCH_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(Blocks.BIRCH_LOG),
                new BirchTallTrunkPlacer(8, 6, 5),

                BlockStateProvider.simple(ModBlocks.BIRCH_LEAVES_YELLOW_BLOCK.get()),
                new BirchYellowFoliagePlacer(ConstantInt.of(4), ConstantInt.of(0), ConstantInt.of(3)
                ),

                new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build()
        );

        register(context, HUGE_ORANGE_MUSHROOM_KEY, ModFeatureTypes.HUGE_ORANGE_MUSHROOM.get(), new HugeMushroomFeatureConfiguration(
                BlockStateProvider.simple(ModBlocks.HUGE_ORANGE_MUSHROOM_BLOCK.get()),
                BlockStateProvider.simple(ModBlocks.ORANGE_MUSHROOM_STEM_BLOCK.get()),
                3 // foliage radius
            )
        );

        register(context, ORANGE_MUSHROOM_KEY, Feature.RANDOM_PATCH, FeatureUtils.simpleRandomPatchConfiguration(
                                6, // 尝试次数
                                PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
                                                BlockStateProvider.simple(ModBlocks.ORANGE_MUSHROOM_BLOCK.get()))
                                ))
        );

        register(context, ROCK_PILE_KEY, ModFeatureTypes.ROCK_PILE.get(), new NoneFeatureConfiguration());

        register(context, GREEN_FIR_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.FIR_LOG_BLOCK.get()),
                new FirTrunkPlacer(15, 4, 3),

                BlockStateProvider.simple(ModBlocks.FIR_LEAVES_GREEN_BLOCK.get()),
                new FirFoliagePlacer(
                        ConstantInt.of(2),
                        ConstantInt.of(0)
                ),

                new TwoLayersFeatureSize(1, 0, 2)).ignoreVines().build()
        );

        register(context, ORANGE_FIR_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.FIR_LOG_BLOCK.get()),
                new FirTrunkPlacer(15, 4, 3),

                BlockStateProvider.simple(ModBlocks.FIR_LEAVES_ORANGE_BLOCK.get()),
                new FirFoliagePlacer(
                        ConstantInt.of(2),
                        ConstantInt.of(0)
                ),

                new TwoLayersFeatureSize(1, 0, 2)).ignoreVines().build()
        );

        register(context, PALM_TREE_KEY, ModFeatureTypes.PALM_TREE.get(), NoneFeatureConfiguration.INSTANCE);

        register(context, MARIGOLD_KEY, Feature.RANDOM_PATCH,
                FeatureUtils.simpleRandomPatchConfiguration(
                        3, // 尝试次数
                        PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
                                BlockStateProvider.simple(ModBlocks.MARIGOLD_BLOCK.get()))
                        ))
        );

        register(context, TIERED_SPRUCE_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(Blocks.SPRUCE_LOG),
                new TieredSpruceTrunkPlacer(12, 2, 2),
                BlockStateProvider.simple(Blocks.SPRUCE_LEAVES),
                new TieredSpruceFoliagePlacer(
                        ConstantInt.of(0),
                        ConstantInt.of(0)
                ),
                new TwoLayersFeatureSize(1, 0, 2)
        )
                .ignoreVines()
                .build()
        );

        register(context, BAOBAB_TREE_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(ModBlocks.BAOBAB_TREE_LOG_BLOCK.get()),
                        new BaobabTreeTrunkPlacer(12, 2, 1),
                        BlockStateProvider.simple(ModBlocks.BAOBAB_TREE_LEAVES_BLOCK.get()),
                        new BaobabTreeFoliagePlacer(
                                ConstantInt.of(0),
                                ConstantInt.of(0)
                        ),
                        new TwoLayersFeatureSize(1, 0, 2)
                )
                        .ignoreVines()
                        .build()
        );

        register(context, TALL_BIRCH_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(Blocks.BIRCH_LOG),
                new BirchTallTrunkPlacer(8, 6, 5),

                BlockStateProvider.simple(Blocks.BIRCH_LEAVES),
                new BirchYellowFoliagePlacer(ConstantInt.of(4), ConstantInt.of(0), ConstantInt.of(3)
                ),

                new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build()
        );

        register(context, FROSTED_SPRUCE_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(Blocks.SPRUCE_LOG),
                        new TieredSpruceTrunkPlacer(12, 2, 2),
                        BlockStateProvider.simple(ModBlocks.FROSTED_SPRUCE_LEAVES_BLOCK.get()),
                        new TieredSpruceFoliagePlacer(
                                ConstantInt.of(0),
                                ConstantInt.of(0)
                        ),
                        new TwoLayersFeatureSize(1, 0, 2)
                )
                        .ignoreVines()
                        .build()
        );

        register(context, BAYOU_TREE_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(ModBlocks.BAYOU_LOG_BLOCK.get()),
                        new BayouTrunkPlacer(16, 2, 2),
                        BlockStateProvider.simple(ModBlocks.BAYOU_LEAVES_BLOCK.get()),
                        new BayouFoliagePlacer(
                                ConstantInt.of(0),
                                ConstantInt.of(0)
                        ),
                        new TwoLayersFeatureSize(1, 0, 1)
                )
                        .ignoreVines()
                        .build()
        );
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(OrBiomes.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key,
                                                                                          F feature, FC configuration)
    {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
