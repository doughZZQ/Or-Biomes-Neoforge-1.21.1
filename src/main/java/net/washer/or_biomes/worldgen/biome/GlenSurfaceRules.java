package net.washer.or_biomes.worldgen.biome;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

/**
 * @author 洗衣机Washer
 * @version 1.0.0
 */
public class GlenSurfaceRules {

    private static final SurfaceRules.RuleSource DIRT = makeStateRule(Blocks.DIRT);
    private static final SurfaceRules.RuleSource GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK);
    private static final SurfaceRules.RuleSource SAND = makeStateRule(Blocks.SAND);
    private static final SurfaceRules.RuleSource BLUE_TERRACOTTA = makeStateRule(Blocks.BLUE_TERRACOTTA);

    public static SurfaceRules.RuleSource makeRules() {

        // ===== 常用条件 =====
        SurfaceRules.ConditionSource isGlen =
                SurfaceRules.isBiome(ModBiomeData.GLEN_BIOME);

        SurfaceRules.ConditionSource atSurface =
                SurfaceRules.ON_FLOOR;

        SurfaceRules.ConditionSource underSurface =
                SurfaceRules.UNDER_FLOOR;

        SurfaceRules.ConditionSource veryDeep =
                SurfaceRules.yBlockCheck(
                        VerticalAnchor.absolute(0),
                        0
                );

        SurfaceRules.ConditionSource waterCheck =
                SurfaceRules.waterBlockCheck(0, 0);

        // ===== 方块状态 =====
        SurfaceRules.RuleSource grass =
                SurfaceRules.state(Blocks.GRASS_BLOCK.defaultBlockState());

        SurfaceRules.RuleSource dirt =
                SurfaceRules.state(Blocks.DIRT.defaultBlockState());

        SurfaceRules.RuleSource stone =
                SurfaceRules.state(Blocks.STONE.defaultBlockState());

        SurfaceRules.RuleSource sand =
                SurfaceRules.state(Blocks.SAND.defaultBlockState());

        SurfaceRules.RuleSource moss =
                SurfaceRules.state(Blocks.MOSS_BLOCK.defaultBlockState());

        /*
         * 规则逻辑：
         *
         * 1. 如果是幽谷：
         *    - 水下表面 -> 沙子
         *    - 表面：
         *         15%概率生成苔藓块
         *         否则草方块
         *    - 次表层 -> 泥土
         *    - 深层 -> 石头
         */

        return SurfaceRules.sequence(

                SurfaceRules.ifTrue(isGlen,

                        SurfaceRules.sequence(

//                                // ===== 水下生成沙子 =====
//                                SurfaceRules.ifTrue(waterCheck, sand),

                                // ===== 表层规则 =====
                                SurfaceRules.ifTrue(atSurface,

                                        SurfaceRules.sequence(
                                                // 15%苔藓
//                                                SurfaceRules.ifTrue(
//                                                        SurfaceRules.noiseCondition(Noises.PATCH, 0.15D), moss),
                                                grass
                                        )
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
