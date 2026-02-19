package net.washer.or_biomes.worldgen.biome;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.function.Consumer;

/**
 * @author 洗衣机Washer
 * @version 1.0.0
 */
public class MapleForestOverworldRegion extends Region {

    public MapleForestOverworldRegion(ResourceLocation name, int weight) {
        // name，维度，权重
        super(name, RegionType.OVERWORLD, weight);
    }
    // 重写 addBiome 方法，这个方法修改原版的主世界的维度中生成生物群系的方法
    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
//        this.addModifiedVanillaOverworldBiomes(mapper,modifiedVanillaOverworldBuilder -> {
//            modifiedVanillaOverworldBuilder.replaceBiome(Biomes.FOREST, ModBiomeData.MAPLE_FOREST_BIOME);
//        });
        this.addBiome(
                mapper,
                // temperature
                Climate.Parameter.span(0.6F, 0.7F),

                // humidity
                Climate.Parameter.span(0.5F, 0.65F),

                // continentalness
                Climate.Parameter.span(0.45F, 0.6F),

                // erosion
                Climate.Parameter.span(-0.1F, 0.3F),

                // weirdness
                Climate.Parameter.span(-0.2F, 0.2F),

                // depth
                Climate.Parameter.span(0.35F, 0.55F),

                // offset
                -0.2F,

                ModBiomeData.MAPLE_FOREST_BIOME
        );
    }
}
