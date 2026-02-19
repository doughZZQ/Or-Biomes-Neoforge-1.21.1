package net.washer.or_biomes.worldgen;

import net.minecraft.resources.ResourceLocation;
import net.washer.or_biomes.OrBiomes;
import net.washer.or_biomes.worldgen.biome.ColdBirchForestOverworldRegion;
import net.washer.or_biomes.worldgen.biome.FirForestOverworldRegion;
import net.washer.or_biomes.worldgen.biome.GlenOverworldRegion;
import net.washer.or_biomes.worldgen.biome.MapleForestOverworldRegion;
import terrablender.api.Regions;

/**
 * @author 洗衣机Washer
 * @version 1.0.0
 */
public class ModTerraBlender {
    public static void registerBiome(){
        // 第一个参数我们的region，第二个参数是维度的定位符，第三个是权重。
        // 其中权重的数值设置你可以参考原版进行设置
        Regions.register(new MapleForestOverworldRegion(ResourceLocation.fromNamespaceAndPath(OrBiomes.MOD_ID,"maple_forest"),3));
        Regions.register(new ColdBirchForestOverworldRegion(ResourceLocation.fromNamespaceAndPath(OrBiomes.MOD_ID,"cold_birch_forest"),3));
        Regions.register(new FirForestOverworldRegion(ResourceLocation.fromNamespaceAndPath(OrBiomes.MOD_ID,"fir_forest"),3));
        Regions.register(new GlenOverworldRegion(ResourceLocation.fromNamespaceAndPath(OrBiomes.MOD_ID,"glen"),2));
    }
}
