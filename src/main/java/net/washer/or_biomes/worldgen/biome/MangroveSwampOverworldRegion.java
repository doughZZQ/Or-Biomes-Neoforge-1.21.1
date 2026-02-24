package net.washer.or_biomes.worldgen.biome;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.ParameterUtils;
import terrablender.api.Region;
import terrablender.api.RegionType;
import terrablender.api.VanillaParameterOverlayBuilder;

import java.util.function.Consumer;

/**
 * @author 洗衣机Washer
 * @version 1.0.0
 */
public class MangroveSwampOverworldRegion extends Region {

    public MangroveSwampOverworldRegion(ResourceLocation name, int weight) {
        super(name, RegionType.OVERWORLD, weight);
    }

    // 重写 addBiome 方法，这个方法修改原版的主世界的维度中生成生物群系的方法
    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {

        VanillaParameterOverlayBuilder builder = new VanillaParameterOverlayBuilder();
        // Overlap Vanilla's parameters with our own for our COLD_BLUE biome.
        // The parameters for this biome are chosen arbitrarily.
        new ParameterUtils.ParameterPointListBuilder()
                .temperature(ParameterUtils.Temperature.HOT)
                .humidity(ParameterUtils.Humidity.HUMID)
                .continentalness(ParameterUtils.Continentalness.NEAR_INLAND)
                .erosion(ParameterUtils.Erosion.EROSION_4)
                .depth(ParameterUtils.Depth.SURFACE)
                .weirdness(ParameterUtils.Weirdness.VALLEY)
                .build().forEach(point -> builder.add(point, ModBiomeData.MANGROVE_SWAMP_BIOME));

        // Add our points to the mapper
        builder.build().forEach(mapper);
    }
}
