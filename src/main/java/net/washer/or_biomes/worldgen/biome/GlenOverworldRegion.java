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
public class GlenOverworldRegion extends Region {

    public GlenOverworldRegion(ResourceLocation name, int weight) {
        // name，维度，权重
        super(name, RegionType.OVERWORLD, weight);
    }
    // 重写 addBiome 方法，这个方法修改原版的主世界的维度中生成生物群系的方法
    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
//        this.addModifiedVanillaOverworldBiomes(mapper,modifiedVanillaOverworldBuilder -> {
//            modifiedVanillaOverworldBuilder.replaceBiome(Biomes.FOREST, ModBiomeData.MAPLE_FOREST_BIOME);
//        });
//        this.addBiome(
//                mapper,
//                // temperature
//                Climate.Parameter.span(0.6F, 0.7F),
//
//                // humidity
//                Climate.Parameter.span(0.5F, 0.65F),
//
//                // continentalness
//                Climate.Parameter.span(0.45F, 0.6F),
//
//                // erosion
//                Climate.Parameter.span(-0.1F, 0.3F),
//
//                // weirdness
//                Climate.Parameter.span(-0.2F, 0.2F),
//
//                // depth
//                Climate.Parameter.span(0.35F, 0.55F),
//
//                // offset
//                -0.2F,
//
//                ModBiomeData.GLEN_BIOME
//        );

        VanillaParameterOverlayBuilder builder = new VanillaParameterOverlayBuilder();
        // Overlap Vanilla's parameters with our own for our COLD_BLUE biome.
        // The parameters for this biome are chosen arbitrarily.
        new ParameterUtils.ParameterPointListBuilder()
                .temperature(ParameterUtils.Temperature.span(ParameterUtils.Temperature.COOL, ParameterUtils.Temperature.NEUTRAL))
                .humidity(ParameterUtils.Humidity.span(ParameterUtils.Humidity.NEUTRAL, ParameterUtils.Humidity.WET))
                .continentalness(ParameterUtils.Continentalness.FAR_INLAND)
                .erosion(ParameterUtils.Erosion.EROSION_4, ParameterUtils.Erosion.EROSION_5)
                .depth(ParameterUtils.Depth.SURFACE)
                .weirdness(ParameterUtils.Weirdness.VALLEY)
                .build().forEach(point -> builder.add(point, ModBiomeData.GLEN_BIOME));

        // Add our points to the mapper
        builder.build().forEach(mapper);
    }
}
