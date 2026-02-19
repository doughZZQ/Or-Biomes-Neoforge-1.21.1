package net.washer.or_biomes.worldgen.biome;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;

/**
 * @author 洗衣机Washer
 * @version 1.0.0
 */
public class ColdBirchForestSurfaceRules {
    //  SurfaceRules.RuleSource 表面生成的规则，这里我们使用makeStateRule来创建一个SurfaceRules.RuleSource
    private static final SurfaceRules.RuleSource DIRT = makeStateRule(Blocks.DIRT);
    private static final SurfaceRules.RuleSource GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK);

    // 静态方法，用于产生我们的surface规则。
    public static SurfaceRules.RuleSource makeRules() {
        // 第一个规则是一个条件规则，表示水面上的得的位置
        SurfaceRules.ConditionSource isAtOrAboveWaterLevel = SurfaceRules.waterBlockCheck(-1, 0);
        // 使用第一个规则表示如果isAtOrAboveWaterLevel满足条件则使用GRASS_BLOCK方块。否则使用DIRT
        SurfaceRules.RuleSource grassSurface = SurfaceRules.sequence(SurfaceRules.ifTrue(isAtOrAboveWaterLevel, GRASS_BLOCK), DIRT);

        // 返回我们的SurfaceRules.RuleSource
        return SurfaceRules.sequence(
                // 满足生物群系情况下，在地板使用RUBY_BLOCK和在天花板使用DIAMOND_ORE
                // 这里的地板是指在生成地表的时候的地板和天花板
                SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomeData.COLD_BIRCH_FOREST_BIOME),
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, grassSurface))),

                // 这是一条原版的默认规则，如果不是这个生物群系，那么使用默认的草方块
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, grassSurface)
        );
    }
    // 这个makeStateRule也很简单，就是返回对应方块默认状态的 SurfaceRules.RuleSource
    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }
}
