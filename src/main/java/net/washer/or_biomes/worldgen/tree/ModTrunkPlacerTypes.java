package net.washer.or_biomes.worldgen.tree;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.washer.or_biomes.OrBiomes;
import net.washer.or_biomes.worldgen.tree.custom_tree.*;

/**
 * @author 洗衣机Washer
 * @version 1.0.0
 */
public class ModTrunkPlacerTypes {

    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACERS =
            DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, OrBiomes.MOD_ID);

    public static final DeferredHolder<TrunkPlacerType<?>, TrunkPlacerType<MapleTrunkPlacer>> MAPLE_TRUNK_PLACER =
            TRUNK_PLACERS.register("maple_trunk_placer",
                    () -> new TrunkPlacerType<>(MapleTrunkPlacer.CODEC));

    public static final DeferredHolder<TrunkPlacerType<?>, TrunkPlacerType<MapleForkedTrunkPlacer>> MAPLE_FORKED_TRUNK_PLACER =
            TRUNK_PLACERS.register("maple_forked_trunk_placer",
                    () -> new TrunkPlacerType<>(MapleForkedTrunkPlacer.CODEC));

    public static final DeferredHolder<TrunkPlacerType<?>, TrunkPlacerType<BirchTallTrunkPlacer>> BIRCH_TALL_TRUNK_PLACER =
            TRUNK_PLACERS.register("birch_tall_trunk_placer",
                    () -> new TrunkPlacerType<>(BirchTallTrunkPlacer.CODEC));

    public static final DeferredHolder<TrunkPlacerType<?>, TrunkPlacerType<FirTrunkPlacer>> FIR_TRUNK_PLACER =
            TRUNK_PLACERS.register("fir_trunk_placer",
                    () -> new TrunkPlacerType<>(FirTrunkPlacer.CODEC));

    public static final DeferredHolder<TrunkPlacerType<?>, TrunkPlacerType<TieredSpruceTrunkPlacer>> TIERED_SPRUCE_TRUNK_PLACER =
            TRUNK_PLACERS.register("tiered_spruce_trunk_placer",
                    () -> new TrunkPlacerType<>(TieredSpruceTrunkPlacer.CODEC));

    public static final DeferredHolder<TrunkPlacerType<?>, TrunkPlacerType<BaobabTreeTrunkPlacer>> BAOBAB_TREE_TRUNK_PLACER =
            TRUNK_PLACERS.register("baobab_tree_trunk_placer",
                    () -> new TrunkPlacerType<>(BaobabTreeTrunkPlacer.CODEC));


    public static void register(IEventBus eventBus) {
        TRUNK_PLACERS.register(eventBus);
    }
}
