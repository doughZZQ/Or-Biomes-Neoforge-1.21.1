package net.washer.or_biomes.worldgen.tree;

import net.minecraft.world.level.block.grower.TreeGrower;
import net.washer.or_biomes.OrBiomes;
import net.washer.or_biomes.worldgen.feature.ModConfiguredFeatures;

import java.util.Optional;

/**
 * @author 洗衣机Washer
 * @version 1.0.0
 */
public class ModTreeGrowers {
    public static final TreeGrower ORANGE_MAPLE = new TreeGrower(OrBiomes.MOD_ID + ":orange_maple", Optional.empty(),
            Optional.of(ModConfiguredFeatures.ORANGE_MAPLE_KEY), Optional.empty());

    public static final TreeGrower RED_MAPLE = new TreeGrower(OrBiomes.MOD_ID + ":red_maple", Optional.empty(),
            Optional.of(ModConfiguredFeatures.RED_MAPLE_KEY), Optional.empty());

    public static final TreeGrower ORANGE_MAPLE_FORKED = new TreeGrower(OrBiomes.MOD_ID + ":orange_maple_forked", Optional.empty(),
            Optional.of(ModConfiguredFeatures.ORANGE_MAPLE_FORKED_KEY), Optional.empty());

    public static final TreeGrower RED_MAPLE_FORKED = new TreeGrower(OrBiomes.MOD_ID + ":red_maple_forked", Optional.empty(),
            Optional.of(ModConfiguredFeatures.RED_MAPLE_FORKED_KEY), Optional.empty());

    public static final TreeGrower YELLOW_BIRCH = new TreeGrower(OrBiomes.MOD_ID + ":yellow_birch", Optional.empty(),
            Optional.of(ModConfiguredFeatures.YELLOW_BIRCH_KEY), Optional.empty());

    public static final TreeGrower GREEN_FIR = new TreeGrower(OrBiomes.MOD_ID + ":green_fir", Optional.empty(),
            Optional.of(ModConfiguredFeatures.GREEN_FIR_KEY), Optional.empty());

    public static final TreeGrower ORANGE_FIR = new TreeGrower(OrBiomes.MOD_ID + ":orange_fir", Optional.empty(),
            Optional.of(ModConfiguredFeatures.ORANGE_FIR_KEY), Optional.empty());

    public static final TreeGrower PALM_TREE = new TreeGrower(OrBiomes.MOD_ID + ":palm_tree", Optional.empty(),
            Optional.of(ModConfiguredFeatures.PALM_TREE_KEY), Optional.empty());

    public static final TreeGrower TIERED_SPRUCE = new TreeGrower(OrBiomes.MOD_ID + ":tiered_spruce", Optional.empty(),
            Optional.of(ModConfiguredFeatures.TIERED_SPRUCE_KEY), Optional.empty());

    public static final TreeGrower BAOBAB_TREE = new TreeGrower(OrBiomes.MOD_ID + ":baobab_tree", Optional.empty(),
            Optional.of(ModConfiguredFeatures.BAOBAB_TREE_KEY), Optional.empty());

//    private static int randomInteger() {
//        Random random = new Random();
//        return random.nextInt(2);
//    }
}
