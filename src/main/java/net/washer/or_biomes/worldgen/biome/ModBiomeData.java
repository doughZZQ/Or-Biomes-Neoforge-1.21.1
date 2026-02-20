package net.washer.or_biomes.worldgen.biome;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.washer.or_biomes.OrBiomes;
import net.washer.or_biomes.worldgen.feature.ModPlacedFeatures;

/**
 * @author 洗衣机Washer
 * @version 1.0.0
 */
public class ModBiomeData {
    // 依旧添加一个对应biome的key
    public static final ResourceKey<Biome> MAPLE_FOREST_BIOME = register("maple_forest_biome");
    public static final ResourceKey<Biome> COLD_BIRCH_FOREST_BIOME = register("cold_birch_forest_biome");
    public static final ResourceKey<Biome> FIR_FOREST_BIOME = register("fir_forest_biome");
    public static final ResourceKey<Biome> GLEN_BIOME = register("glen_biome");
    public static final ResourceKey<Biome> TALL_BIRCH_FOREST_BIOME = register("tall_birch_forest_biome");

    // 对应大的create方法
    private static ResourceKey<Biome> register(String pKey) {
        return ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(OrBiomes.MOD_ID, pKey));
    }

    // bootstrap和之前的一样
    public static void bootstrap(BootstrapContext<Biome> pContext) {
        // 获得placefeature的holdgetter
        HolderGetter<ConfiguredWorldCarver<?>> carverGetter = pContext.lookup(Registries.CONFIGURED_CARVER);
        HolderGetter<PlacedFeature> placedFeatureGetter = pContext.lookup(Registries.PLACED_FEATURE);

        // 给数据生成注册我们的biome
        register(pContext, ModBiomeData.MAPLE_FOREST_BIOME, MapleForestOverworldBiomes.mapleForestBiome(placedFeatureGetter, carverGetter));
        register(pContext, ModBiomeData.COLD_BIRCH_FOREST_BIOME, ColdBirchForestOverworldBiomes.coldBirchForestBiome(placedFeatureGetter, carverGetter));
        register(pContext, ModBiomeData.FIR_FOREST_BIOME, FirForestOverworldBiomes.firForestBiome(placedFeatureGetter, carverGetter));
        register(pContext, ModBiomeData.GLEN_BIOME, GlenOverworldBiomes.glenBiome(placedFeatureGetter, carverGetter));
        register(pContext, ModBiomeData.TALL_BIRCH_FOREST_BIOME, TallBirchForestOverworldBiomes.tallBirchForestBiome(placedFeatureGetter, carverGetter));
    }


    private static void register(BootstrapContext<Biome> context, ResourceKey<Biome> key, Biome biome)
    {
        context.register(key, biome);
    }
}
