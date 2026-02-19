package net.washer.or_biomes.worldgen.feature;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.washer.or_biomes.OrBiomes;
import net.washer.or_biomes.worldgen.feature.custom_feature.HugeOrangeMushroomFeature;
import net.washer.or_biomes.worldgen.feature.custom_feature.OasisLakeFeature;
import net.washer.or_biomes.worldgen.feature.custom_feature.PalmTreeStructureFeature;
import net.washer.or_biomes.worldgen.feature.custom_feature.RockPileFeature;

/**
 * @author 洗衣机Washer
 * @version 1.0.0
 */
public class ModFeatureTypes {
    public static final DeferredRegister<Feature<?>> MOD_FEATURES =
            DeferredRegister.create(Registries.FEATURE, OrBiomes.MOD_ID);

    public static final DeferredHolder<Feature<?>, HugeOrangeMushroomFeature> HUGE_ORANGE_MUSHROOM =
            MOD_FEATURES.register("huge_orange_mushroom",
                    () -> new HugeOrangeMushroomFeature(HugeMushroomFeatureConfiguration.CODEC));

    public static final DeferredHolder<Feature<?>, RockPileFeature> ROCK_PILE =
            MOD_FEATURES.register("rock_pile",
                    () -> new RockPileFeature(NoneFeatureConfiguration.CODEC));

    public static final DeferredHolder<Feature<?>, Feature<NoneFeatureConfiguration>> PALM_TREE =
            MOD_FEATURES.register("palm_tree",
                    () -> new PalmTreeStructureFeature(NoneFeatureConfiguration.CODEC));


    public static void register(IEventBus eventBus) {
        MOD_FEATURES.register(eventBus);
    }
}
