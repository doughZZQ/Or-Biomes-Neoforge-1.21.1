package net.washer.or_biomes.datagen;

import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.washer.or_biomes.OrBiomes;
import net.washer.or_biomes.worldgen.biome.ModBiomeData;
import net.washer.or_biomes.worldgen.modifier.ModBiomeModifiers;
import net.washer.or_biomes.worldgen.feature.ModConfiguredFeatures;
import net.washer.or_biomes.worldgen.feature.ModPlacedFeatures;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * @author 洗衣机Washer
 * @version 1.0.0
 */
public class ModDatapackProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()

            .add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiers::bootstrap)
            .add(Registries.BIOME, ModBiomeData::bootstrap);

    public ModDatapackProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(OrBiomes.MOD_ID));
    }
}
