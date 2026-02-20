package net.washer.or_biomes.worldgen.biome;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

/**
 * @author 洗衣机Washer
 * @version 1.0.0
 */
public class TallBirchForestSurfaceRules {

    public static SurfaceRules.RuleSource makeRules() {

        // ===== 常用条件 =====
        SurfaceRules.ConditionSource isTallBirchForest =
                SurfaceRules.isBiome(ModBiomeData.TALL_BIRCH_FOREST_BIOME);

        SurfaceRules.ConditionSource atSurface =
                SurfaceRules.ON_FLOOR;

        SurfaceRules.ConditionSource underSurface =
                SurfaceRules.UNDER_FLOOR;

        SurfaceRules.ConditionSource veryDeep =
                SurfaceRules.yBlockCheck(
                        VerticalAnchor.absolute(0),
                        0
                );

        // ===== 方块状态 =====
        SurfaceRules.RuleSource grass =
                SurfaceRules.state(Blocks.GRASS_BLOCK.defaultBlockState());

        SurfaceRules.RuleSource dirt =
                SurfaceRules.state(Blocks.DIRT.defaultBlockState());

        SurfaceRules.RuleSource stone =
                SurfaceRules.state(Blocks.STONE.defaultBlockState());

        /*
         * 规则逻辑：
         *
         * 1. 如果是tall_birch_forest：
         *    - 表面：
         *         草方块
         *    - 次表层 -> 泥土
         *    - 深层 -> 石头
         */

        return SurfaceRules.sequence(

                SurfaceRules.ifTrue(isTallBirchForest,

                        SurfaceRules.sequence(

                                // ===== 表层规则 =====
                                SurfaceRules.ifTrue(atSurface,
                                        SurfaceRules.sequence(grass)
                                ),

                                // ===== 次表层 =====
                                SurfaceRules.ifTrue(underSurface, dirt),

                                // ===== 更深层 =====
                                SurfaceRules.ifTrue(veryDeep, stone)
                        )
                )
        );
    }

    private static SurfaceRules.RuleSource makeStateRule(Block block)
    {
        return SurfaceRules.state(block.defaultBlockState());
    }
}
