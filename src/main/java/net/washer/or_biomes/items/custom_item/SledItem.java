package net.washer.or_biomes.items.custom_item;

import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
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

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        BlockHitResult hitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.ANY);
        if (hitResult.getType() != HitResult.Type.BLOCK) {
            return InteractionResultHolder.pass(stack);
        } else {
            Vec3 spawnAt = hitResult.getLocation().add((double) 0.0F, 0.01, (double) 0.0F);
            SledEntity sled = (SledEntity) ((EntityType) ModEntities.SLED_ENTITY.get()).create(level);
            if (sled == null) {
                return InteractionResultHolder.fail(stack);
            } else {
                //sled.setVariant(this.variant);
                sled.moveTo(spawnAt.x, spawnAt.y, spawnAt.z, player.getYRot(), 0.0F);
                if (!level.noCollision(sled, sled.getBoundingBox())) {
                    return InteractionResultHolder.fail(stack);
                } else {
                    if (!level.isClientSide) {
                        level.addFreshEntity(sled);
                        level.gameEvent(player, GameEvent.ENTITY_PLACE, BlockPos.containing(spawnAt));
                        if (!player.getAbilities().instabuild) {
                            stack.shrink(1);
                        }
                    }

                    player.awardStat(Stats.ITEM_USED.get(this));
                    return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
                }
            }
        }
    }
}
