package net.washer.or_biomes.worldgen.biome;

import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.sounds.Music;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.washer.or_biomes.worldgen.feature.ModPlacedFeatures;
import org.jetbrains.annotations.Nullable;

/**
 * @author 洗衣机Washer
 * @version 1.0.0
 */
// 原版的生物群系的配置位于OverworldBiomes类下。
public class MapleForestOverworldBiomes {
    // 一个辅助的函数用于根据温度判断段生物群系的天空的颜色
    protected static int calculateSkyColor(float pTemperature) {
        return rgbToColor(96, 144, 214);
    }
    // 一个构造方法 用于返回一个biome实例
    private static Biome MapleForestOverworldBiomes(
            boolean pHasPercipitation,// 是否有降水
            float pTemperature,// 温度
            float pDownfall, // 降水量
            MobSpawnSettings.Builder pMobSpawnSettings,// 生物群系生物生成设置
            BiomeGenerationSettings.Builder pGenerationSettings,// 生物群系生成设置
            @Nullable Music pBackgroundMusic // 生物群系背景音乐
    ) {
        return MapleForestOverworldBiomes(pHasPercipitation, pTemperature, pDownfall, rgbToColor(121, 192, 224), rgbToColor(121, 192, 224), rgbToColor(138, 186, 56), null, pMobSpawnSettings, pGenerationSettings, pBackgroundMusic);
    }
    // 另一个构造方法 同样返回 biome 能配置的更多
    private static Biome MapleForestOverworldBiomes(
            boolean pHasPrecipitation, //是否有下雨
            float pTemperature,// 温度
            float pDownfall, // 降水量
            int pWaterColor, // 水的颜色
            int pWaterFogColor, // 水的雾颜色
            @Nullable Integer pGrassColorOverride, // 草方块颜色
            @Nullable Integer pFoliageColorOverride, // 树叶颜色
            MobSpawnSettings.Builder pMobSpawnSettings,
            BiomeGenerationSettings.Builder pGenerationSettings,
            @Nullable Music pBackgroundMusic
    ) {
        BiomeSpecialEffects.Builder biomespecialeffects$builder = new BiomeSpecialEffects.Builder()
                .waterColor(pWaterColor)
                .waterFogColor(pWaterFogColor)
                .fogColor(12638463)
                .skyColor(calculateSkyColor(pTemperature))
                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                .backgroundMusic(pBackgroundMusic);
        if (pGrassColorOverride != null) {
            biomespecialeffects$builder.grassColorOverride(pGrassColorOverride);
        }

        if (pFoliageColorOverride != null) {
            biomespecialeffects$builder.foliageColorOverride(pFoliageColorOverride);
        }

        return new Biome.BiomeBuilder()
                .hasPrecipitation(pHasPrecipitation)
                .temperature(pTemperature)
                .downfall(pDownfall)
                .specialEffects(biomespecialeffects$builder.build())
                .mobSpawnSettings(pMobSpawnSettings.build())
                .generationSettings(pGenerationSettings.build())
                .build();
    }

    // 添加一个自己的生物群系，使用了一些原版的方法。具体的内容自己点到方法里面看下把，不是很难，都是一些重复的配置的内容。
    public static Biome mapleForestBiome(HolderGetter<PlacedFeature> pPlacedFeatures,
                                         HolderGetter<ConfiguredWorldCarver<?>> pWorldCarvers)
    {
        // 创建一个MobSpawnSettings.Builder对象，用于配置生物生成设置。
        MobSpawnSettings.Builder mobBuilder = new MobSpawnSettings.Builder();

        BiomeDefaultFeatures.farmAnimals(mobBuilder);
        BiomeDefaultFeatures.commonSpawns(mobBuilder);

        // 用于配置生物群系生成设置。
        BiomeGenerationSettings.Builder genBuilder = new BiomeGenerationSettings.Builder(pPlacedFeatures, pWorldCarvers);
        // 一些通用的配置
        globalOverworldGeneration(genBuilder);

        // 矿物
        BiomeDefaultFeatures.addDefaultOres(genBuilder);
        BiomeDefaultFeatures.addDefaultSoftDisks(genBuilder);
        // 岩石堆
        genBuilder.addFeature(
                GenerationStep.Decoration.VEGETAL_DECORATION,
                ModPlacedFeatures.ROCK_PILE_PLACED);
        // 🌳 树木（重点）
        genBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.ORANGE_MAPLE_PLACED);
        genBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.ORANGE_MAPLE_FORKED_PLACED);
        genBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.RED_MAPLE_PLACED);
        genBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.RED_MAPLE_FORKED_PLACED);
        genBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.HUGE_ORANGE_MUSHROOM_PLACED);
        // 原版桦树
        //genBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.BIRCH_TALL);
        // 🍄 地表植物
        genBuilder.addFeature(
                GenerationStep.Decoration.VEGETAL_DECORATION,
                ModPlacedFeatures.ORANGE_MUSHROOM_PLACED);
        // 向日葵和南瓜
        genBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                pPlacedFeatures.getOrThrow(ModPlacedFeatures.DENSE_SUNFLOWER_PATCH));

        genBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                pPlacedFeatures.getOrThrow(ModPlacedFeatures.SPARSE_PUMPKIN_PATCH));
//        genBuilder.addFeature(
//                GenerationStep.Decoration.VEGETAL_DECORATION,
//                ModPlacedFeatures.MAPLE_LEAF_PATCH);
        //BiomeDefaultFeatures.addDefaultFlowers(genBuilder);
        //BiomeDefaultFeatures.addDefaultSeagrass(genBuilder);
        BiomeDefaultFeatures.addDefaultGrass(genBuilder);
        BiomeDefaultFeatures.addDefaultMushrooms(genBuilder);
        //BiomeDefaultFeatures.addDefaultExtraVegetation(genBuilder);

        // 调用构造返回biome
        return MapleForestOverworldBiomes(true, 0.6F, 0.15F,
                mobBuilder, genBuilder, null);
    }

    private static void globalOverworldGeneration(BiomeGenerationSettings.Builder pGenerationSettings) {
        BiomeDefaultFeatures.addDefaultCarversAndLakes(pGenerationSettings);
        BiomeDefaultFeatures.addDefaultCrystalFormations(pGenerationSettings);
        BiomeDefaultFeatures.addDefaultMonsterRoom(pGenerationSettings);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(pGenerationSettings);
        BiomeDefaultFeatures.addDefaultSprings(pGenerationSettings);
        BiomeDefaultFeatures.addSurfaceFreezing(pGenerationSettings);
    }

    public static int rgbToColor(int r, int g, int b) {
        return (r << 16) | (g << 8) | b;
    }

}
