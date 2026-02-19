package net.washer.or_biomes.blocks.custom_block;

import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

/**
 * @author 洗衣机Washer
 * @version 1.0.0
 */
public class MarigoldBlock extends DoublePlantBlock {

    public MarigoldBlock() {
        super(BlockBehaviour.Properties
                .of()
                .mapColor(MapColor.PLANT)
                .noCollission()
                .instabreak()
                .sound(SoundType.GRASS)
                .offsetType(BlockBehaviour.OffsetType.XZ)
                .pushReaction(PushReaction.DESTROY)
        );
    }
}
