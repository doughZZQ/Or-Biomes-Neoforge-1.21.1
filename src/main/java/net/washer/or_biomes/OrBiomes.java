package net.washer.or_biomes;

import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.washer.or_biomes.blocks.ModBlocks;
import net.washer.or_biomes.items.ModCreativeTab;
import net.washer.or_biomes.items.ModItems;
import net.washer.or_biomes.worldgen.ModTerraBlender;
import net.washer.or_biomes.worldgen.biome.ColdBirchForestSurfaceRules;
import net.washer.or_biomes.worldgen.biome.FirForestSurfaceRules;
import net.washer.or_biomes.worldgen.biome.GlenSurfaceRules;
import net.washer.or_biomes.worldgen.biome.MapleForestSurfaceRules;
import net.washer.or_biomes.worldgen.feature.ModFeatureTypes;
import net.washer.or_biomes.worldgen.tree.ModFoliagePlacerTypes;
import net.washer.or_biomes.worldgen.tree.ModTrunkPlacerTypes;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import terrablender.api.SurfaceRuleManager;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(OrBiomes.MOD_ID)
public class OrBiomes {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "or_biomes";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public OrBiomes(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        //================================================
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModCreativeTab.register(modEventBus);

        ModTrunkPlacerTypes.register(modEventBus);
        ModFoliagePlacerTypes.register(modEventBus);
        ModFeatureTypes.register(modEventBus);

        ModTerraBlender.registerBiome();
        //================================================

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (OrBiomes) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        // 给主世界添加新的surfaceRule
        event.enqueueWork(()->{
            SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, OrBiomes.MOD_ID, MapleForestSurfaceRules.makeRules());
            SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, OrBiomes.MOD_ID, ColdBirchForestSurfaceRules.makeRules());
            SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, OrBiomes.MOD_ID, FirForestSurfaceRules.makeRules());
            SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, OrBiomes.MOD_ID, GlenSurfaceRules.makeRules());
        });
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    @EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

        }
    }
}
