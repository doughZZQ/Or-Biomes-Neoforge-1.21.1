package net.washer.or_biomes.worldgen.modifier;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.washer.or_biomes.OrBiomes;
import net.washer.or_biomes.worldgen.biome.ModBiomeData;
import net.washer.or_biomes.worldgen.feature.ModPlacedFeatures;

/**
 * @author 洗衣机Washer
 * @version 1.0.0
 */
public class ModBiomeModifiers {

    public static final ResourceKey<BiomeModifier> ADD_ROCK_PILE = registerKey("add_rock_pile");
    public static final ResourceKey<BiomeModifier> ADD_ORANGE_MAPLE = registerKey("add_orange_maple");
    public static final ResourceKey<BiomeModifier> ADD_RED_MAPLE = registerKey("add_red_maple");
    public static final ResourceKey<BiomeModifier> ADD_ORANGE_MAPLE_FORKED = registerKey("add_orange_maple_forked");
    public static final ResourceKey<BiomeModifier> ADD_RED_MAPLE_FORKED = registerKey("add_red_maple_forked");
    public static final ResourceKey<BiomeModifier> ADD_YELLOW_BIRCH = registerKey("add_yellow_birch");
    public static final ResourceKey<BiomeModifier> ADD_HUGE_ORANGE_MUSHROOM = registerKey("add_huge_orange_mushroom");
    public static final ResourceKey<BiomeModifier> ADD_ORANGE_MUSHROOM = registerKey("add_orange_mushroom");
    public static final ResourceKey<BiomeModifier> ADD_GREEN_FIR = registerKey("add_green_fir");
    public static final ResourceKey<BiomeModifier> ADD_ORANGE_FIR = registerKey("add_orange_fir");
    public static final ResourceKey<BiomeModifier> ADD_TIERED_SPRUCE = registerKey("add_tiered_spruce");
    public static final ResourceKey<BiomeModifier> ADD_BAOBAB_TREE_TO_SAVANNA = registerKey("add_baobab_tree_to_savanna");
    public static final ResourceKey<BiomeModifier> ADD_TALL_BIRCH = registerKey("add_tall_birch");
    public static final ResourceKey<BiomeModifier> ADD_FROSTED_SPRUCE = registerKey("add_frosted_spruce");

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        // CF -> PF -> BM
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        Holder<Biome> mapleForestBiomes = context.lookup(Registries.BIOME).getOrThrow(ModBiomeData.MAPLE_FOREST_BIOME);
        Holder<Biome> coldBirchForestBiomes = context.lookup(Registries.BIOME).getOrThrow(ModBiomeData.COLD_BIRCH_FOREST_BIOME);
        Holder<Biome> firForestBiomes = context.lookup(Registries.BIOME).getOrThrow(ModBiomeData.FIR_FOREST_BIOME);
        Holder<Biome> glenBiomes = context.lookup(Registries.BIOME).getOrThrow(ModBiomeData.GLEN_BIOME);
        Holder<Biome> tallBirchForestBiomes = context.lookup(Registries.BIOME).getOrThrow(ModBiomeData.TALL_BIRCH_FOREST_BIOME);
        Holder<Biome> frostedSpruceForestBiomes = context.lookup(Registries.BIOME).getOrThrow(ModBiomeData.FROSTED_SPRUCE_FOREST_BIOME);

        context.register(
                ADD_ROCK_PILE,
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(mapleForestBiomes, coldBirchForestBiomes, firForestBiomes, glenBiomes),
                        HolderSet.direct(
                                placedFeatures.getOrThrow(ModPlacedFeatures.ROCK_PILE_PLACED)
                        ),
                        GenerationStep.Decoration.LOCAL_MODIFICATIONS
                )
        );

        context.register(
                ADD_ORANGE_MAPLE,
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(mapleForestBiomes),
                        HolderSet.direct(
                                placedFeatures.getOrThrow(ModPlacedFeatures.ORANGE_MAPLE_PLACED)
                        ),
                        GenerationStep.Decoration.LOCAL_MODIFICATIONS
                )
        );

        context.register(
                ADD_ORANGE_MAPLE_FORKED,
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(mapleForestBiomes),
                        HolderSet.direct(
                                placedFeatures.getOrThrow(ModPlacedFeatures.ORANGE_MAPLE_FORKED_PLACED)
                        ),
                        GenerationStep.Decoration.LOCAL_MODIFICATIONS
                )
        );

        context.register(
                ADD_RED_MAPLE,
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(mapleForestBiomes),
                        HolderSet.direct(
                                placedFeatures.getOrThrow(ModPlacedFeatures.RED_MAPLE_PLACED)
                        ),
                        GenerationStep.Decoration.LOCAL_MODIFICATIONS
                )
        );

        context.register(
                ADD_RED_MAPLE_FORKED,
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(mapleForestBiomes),
                        HolderSet.direct(
                                placedFeatures.getOrThrow(ModPlacedFeatures.RED_MAPLE_FORKED_PLACED)
                        ),
                        GenerationStep.Decoration.LOCAL_MODIFICATIONS
                )
        );

        context.register(
                ADD_HUGE_ORANGE_MUSHROOM,
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(mapleForestBiomes),
                        HolderSet.direct(
                                placedFeatures.getOrThrow(ModPlacedFeatures.HUGE_ORANGE_MUSHROOM_PLACED)
                        ),
                        GenerationStep.Decoration.LOCAL_MODIFICATIONS
                )
        );

        context.register(
                ADD_ORANGE_MUSHROOM,
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(mapleForestBiomes, coldBirchForestBiomes, firForestBiomes),
                        HolderSet.direct(
                                placedFeatures.getOrThrow(ModPlacedFeatures.ORANGE_MUSHROOM_PLACED)
                        ),
                        GenerationStep.Decoration.LOCAL_MODIFICATIONS
                )
        );

        context.register(
                ADD_YELLOW_BIRCH,
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(coldBirchForestBiomes),
                        HolderSet.direct(
                                placedFeatures.getOrThrow(ModPlacedFeatures.YELLOW_BIRCH_PLACED)
                        ),
                        GenerationStep.Decoration.LOCAL_MODIFICATIONS
                )
        );

        context.register(
                ADD_GREEN_FIR,
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(firForestBiomes),
                        HolderSet.direct(
                                placedFeatures.getOrThrow(ModPlacedFeatures.GREEN_FIR_PLACED)
                        ),
                        GenerationStep.Decoration.LOCAL_MODIFICATIONS
                )
        );

        context.register(
                ADD_ORANGE_FIR,
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(firForestBiomes),
                        HolderSet.direct(
                                placedFeatures.getOrThrow(ModPlacedFeatures.ORANGE_FIR_PLACED)
                        ),
                        GenerationStep.Decoration.LOCAL_MODIFICATIONS
                )
        );

        context.register(
                ADD_TIERED_SPRUCE,
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(glenBiomes),
                        HolderSet.direct(
                                placedFeatures.getOrThrow(ModPlacedFeatures.TIERED_SPRUCE_PLACED)
                        ),
                        GenerationStep.Decoration.LOCAL_MODIFICATIONS
                )
        );

        context.register(
                ADD_BAOBAB_TREE_TO_SAVANNA,
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        context.lookup(Registries.BIOME).getOrThrow(BiomeTags.IS_SAVANNA),
                        HolderSet.direct(
                                placedFeatures.getOrThrow(ModPlacedFeatures.BAOBAB_TREE_PLACED)
                        ),
                        GenerationStep.Decoration.LOCAL_MODIFICATIONS
                )
        );

        context.register(
                ADD_TALL_BIRCH,
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(tallBirchForestBiomes),
                        HolderSet.direct(
                                placedFeatures.getOrThrow(ModPlacedFeatures.TALL_BIRCH_PLACED)
                        ),
                        GenerationStep.Decoration.LOCAL_MODIFICATIONS
                )
        );

        context.register(
                ADD_FROSTED_SPRUCE,
                new BiomeModifiers.AddFeaturesBiomeModifier(
                        HolderSet.direct(frostedSpruceForestBiomes),
                        HolderSet.direct(
                                placedFeatures.getOrThrow(ModPlacedFeatures.FROSTED_SPRUCE_PLACED)
                        ),
                        GenerationStep.Decoration.LOCAL_MODIFICATIONS
                )
        );
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(OrBiomes.MOD_ID, name));
    }
}
