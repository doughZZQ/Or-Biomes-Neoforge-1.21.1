package net.washer.or_biomes.items.custom_item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.washer.or_biomes.entities.ModEntities;
import net.washer.or_biomes.entities.custom_entity.SledEntity;

/**
 * @author 洗衣机Washer
 * @version 1.0.0
 */
public class SledItem extends Item {

    public SledItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {

        Level level = context.getLevel();

        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        BlockPos clickedPos = context.getClickedPos();
        Player player = context.getPlayer();

        if (player == null) return InteractionResult.FAIL;

        // 放置在点击方块上方
        BlockPos spawnPos = clickedPos.above();

        SledEntity sled = new SledEntity(
                ModEntities.SLED_ENTITY.get(),
                level
        );

        // 设置位置（中心）
        sled.setPos(
                spawnPos.getX() + 0.5,
                spawnPos.getY(),
                spawnPos.getZ() + 0.5
        );

        // 朝向跟随玩家
        sled.setYRot(player.getYRot());

        // 检查碰撞
        if (!level.noCollision(sled)) {
            return InteractionResult.FAIL;
        }

        level.addFreshEntity(sled);

        // 非创造模式消耗物品
        if (!player.getAbilities().instabuild) {
            context.getItemInHand().shrink(1);
        }

        return InteractionResult.CONSUME;
    }
}
