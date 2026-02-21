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
public class FrostedSpruceForestOverworldRegion extends Region {

    public FrostedSpruceForestOverworldRegion(ResourceLocation name, int weight) {
        super(name, RegionType.OVERWORLD, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {

        VanillaParameterOverlayBuilder builder = new VanillaParameterOverlayBuilder();
        // Overlap Vanilla's parameters with our own for our COLD_BLUE biome.
        // The parameters for this biome are chosen arbitrarily.
        new ParameterUtils.ParameterPointListBuilder()
                .temperature(ParameterUtils.Temperature.FROZEN)
                .humidity(ParameterUtils.Humidity.WET)
                .continentalness(ParameterUtils.Continentalness.FAR_INLAND)
                .erosion(ParameterUtils.Erosion.EROSION_2, ParameterUtils.Erosion.EROSION_3)
                .depth(ParameterUtils.Depth.SURFACE)
                .weirdness(ParameterUtils.Weirdness.MID_SLICE_VARIANT_ASCENDING)
                .build().forEach(point -> builder.add(point, ModBiomeData.FROSTED_SPRUCE_FOREST_BIOME));

        // Add our points to the mapper
        builder.build().forEach(mapper);
    }
}
