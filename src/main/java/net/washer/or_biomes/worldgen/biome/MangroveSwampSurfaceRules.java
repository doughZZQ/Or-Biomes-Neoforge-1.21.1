package net.washer.or_biomes.worldgen.biome;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

/**
 * @author 洗衣机Washer
 * @version 1.0.0
 */
public class MangroveSwampSurfaceRules {

    private static final SurfaceRules.RuleSource WATER =
            SurfaceRules.state(Blocks.WATER.defaultBlockState());

    private static final SurfaceRules.RuleSource MUD =
            SurfaceRules.state(Blocks.MUD.defaultBlockState());

    private static final SurfaceRules.RuleSource GRAVEL =
            SurfaceRules.state(Blocks.GRAVEL.defaultBlockState());

    private static final SurfaceRules.RuleSource STONE =
            SurfaceRules.state(Blocks.STONE.defaultBlockState());


    public static SurfaceRules.RuleSource makeRules() {

        // 条件：在地表（世界表面高度图）
        SurfaceRules.ConditionSource onSurface =
                SurfaceRules.ON_FLOOR;

        // 条件：在水面以下
        SurfaceRules.ConditionSource belowWater =
                SurfaceRules.waterBlockCheck(-1, 0);

        return SurfaceRules.sequence(

                // ===== 河湾树沼泽群系限定 =====
                SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(ModBiomeData.MANGROVE_SWAMP_BIOME),

                        SurfaceRules.sequence(

                                // ---------- 表层：浅水 ----------
                                SurfaceRules.ifTrue(
                                        onSurface,
                                        MUD
                                ),

                                // ---------- 水下第一层：泥巴 ----------
                                SurfaceRules.ifTrue(
                                        SurfaceRules.waterBlockCheck(0, 0),
                                        MUD
                                ),

                                // ---------- 泥巴下 1~3 格：砂砾 ----------
                                SurfaceRules.ifTrue(
                                        SurfaceRules.verticalGradient(
                                                "mangrove_swamp_gravel",
                                                VerticalAnchor.absolute(58),
                                                VerticalAnchor.absolute(52)
                                        ),
                                        GRAVEL
                                ),

                                // ---------- 更深处：石头 ----------
                                STONE
                        )
                )
        );
    }
}
