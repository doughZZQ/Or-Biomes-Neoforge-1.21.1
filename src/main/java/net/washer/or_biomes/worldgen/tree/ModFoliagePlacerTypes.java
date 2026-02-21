package net.washer.or_biomes.worldgen.tree;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.washer.or_biomes.OrBiomes;
import net.washer.or_biomes.worldgen.tree.custom_tree.*;

/**
 * @author 洗衣机Washer
 * @version 1.0.0
 */
public class ModFoliagePlacerTypes {
    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACERS =
            DeferredRegister.create(Registries.FOLIAGE_PLACER_TYPE, OrBiomes.MOD_ID);

    public static final DeferredHolder<FoliagePlacerType<?>, FoliagePlacerType<MapleHybridFoliagePlacer>> MAPLE_HYBRID_FOLIAGE_PLACER =
            FOLIAGE_PLACERS.register("maple_hybrid_foliage_placer",
                    () -> new FoliagePlacerType<>(MapleHybridFoliagePlacer.CODEC));

    public static final DeferredHolder<FoliagePlacerType<?>, FoliagePlacerType<BirchYellowFoliagePlacer>> BIRCH_YELLOW_FOLIAGE_PLACER =
            FOLIAGE_PLACERS.register("birch_yellow_foliage_placer",
                    () -> new FoliagePlacerType<>(BirchYellowFoliagePlacer.CODEC));

    public static final DeferredHolder<FoliagePlacerType<?>, FoliagePlacerType<FirFoliagePlacer>> FIR_FOLIAGE_PLACER =
            FOLIAGE_PLACERS.register("fir_foliage_placer",
                    () -> new FoliagePlacerType<>(FirFoliagePlacer.CODEC));

    public static final DeferredHolder<FoliagePlacerType<?>, FoliagePlacerType<TieredSpruceFoliagePlacer>> TIERED_SPRUCE_FOLIAGE_PLACER =
            FOLIAGE_PLACERS.register("tiered_spruce_foliage_placer",
                    () -> new FoliagePlacerType<>(TieredSpruceFoliagePlacer.CODEC));

    public static final DeferredHolder<FoliagePlacerType<?>, FoliagePlacerType<BaobabTreeFoliagePlacer>> BAOBAB_TREE_FOLIAGE_PLACER =
            FOLIAGE_PLACERS.register("baobab_tree_foliage_placer",
                    () -> new FoliagePlacerType<>(BaobabTreeFoliagePlacer.CODEC));

    public static final DeferredHolder<FoliagePlacerType<?>, FoliagePlacerType<BayouFoliagePlacer>> BAYOU_FOLIAGE_PLACER =
            FOLIAGE_PLACERS.register("bayou_foliage_placer",
                    () -> new FoliagePlacerType<>(BayouFoliagePlacer.CODEC));


    public static void register(IEventBus eventBus) {
        FOLIAGE_PLACERS.register(eventBus);
    }
}
