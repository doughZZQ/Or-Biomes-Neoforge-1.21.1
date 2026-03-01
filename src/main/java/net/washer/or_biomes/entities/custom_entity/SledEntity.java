package net.washer.or_biomes.entities.custom_entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

/**
 * @author 洗衣机Washer
 * @version 1.0.0
 */
public class SledEntity extends Entity {

    private static final double ACCELERATION = 0.03;
    private static final double FRICTION = 0.98;
    private static final double GRAVITY = 0.08;
    private static final double MIN_CLIMB_SPEED = 0.25;

    private double sledSpeed = 0;
    private float sledPitch = 0f;
    private float sledRoll = 0f;

    public SledEntity(EntityType<?> type, Level level) {
        super(type, level);
        this.blocksBuilding = true;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        sledSpeed = tag.getDouble("Speed");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putDouble("Speed", sledSpeed);
    }

    // ===============================
    // 右键骑乘
    // ===============================

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {

        if (!level().isClientSide) {
            player.startRiding(this);
        }

        return InteractionResult.sidedSuccess(level().isClientSide);
    }

    // ===============================
    // 玩家站立位置
    // ===============================

    @Override
    public Vec3 getPassengerRidingPosition(Entity passenger) {
        return this.position().add(0, 0.2, 0.0);
    }

    @Override
    public boolean canAddPassenger(Entity entity) {
        return this.getPassengers().isEmpty();
    }

    @Override
    public LivingEntity getControllingPassenger() {
        return (LivingEntity) this.getFirstPassenger();
    }

    // ===============================
    // 主 tick 逻辑
    // ===============================

    @Override
    public void tick() {
        super.tick();

        if (this.isVehicle() && this.getControllingPassenger() instanceof Player player) {

            // 1️⃣ 让雪橇朝向跟随玩家
            this.setYRot(player.getYRot());
            this.setYHeadRot(player.getYRot());
            this.setYBodyRot(player.getYRot());

            // 玩家输入
            float forwardInput = player.zza;
            // 3️⃣ 根据雪橇自身朝向计算方向
            Vec3 look = Vec3.directionFromRotation(0, this.getYRot());

            sledSpeed += forwardInput * ACCELERATION;

            handlePhysics(look);
        }

        move(MoverType.SELF, this.getDeltaMovement());

        sledSpeed *= FRICTION;

//        if (Math.abs(sledSpeed) < 0.001) {
//            sledSpeed = 0;
//        }
    }

    // ===============================
    // 坡度与物理系统
    // ===============================

    private void handlePhysics(Vec3 look) {

        Vec3 forward = look.normalize();

        BlockPos curPos = BlockPos.containing(
                this.getX(),
                this.getY(),
                this.getZ()
        );
        BlockPos frontPos = BlockPos.containing(
                this.getX() + forward.x,
                this.getY(),
                this.getZ() + forward.z
        );

        BlockState frontBlock = level().getBlockState(frontPos);
        BlockState frontUpBlock = level().getBlockState(frontPos.above());

        boolean oneBlockSlope =
                frontBlock.isSolid() &&
                        frontUpBlock.isAir();

        boolean wall =
                frontBlock.isSolid() &&
                        frontUpBlock.isSolid();

        BlockPos downFront = frontPos.below();
        BlockPos downCurrent = curPos.below();
        BlockState downFrontBlock = level().getBlockState(downFront);
        BlockState downCurrentBlock = level().getBlockState(downCurrent);

        boolean downhill = downFrontBlock.isAir();// && !downCurrentBlock.isAir();
        boolean backDown = downCurrentBlock.isAir();

        double moveX = 0;
        double moveY = 0;
        double moveZ = 0;

        // ===============================
        // 墙阻挡
        // ===============================
        if (sledSpeed >= MIN_CLIMB_SPEED && wall) {
            sledSpeed = 0;
            setDeltaMovement(Vec3.ZERO);
            return;
        }

        if (sledSpeed >= 0) {
            // ===============================
            // 上坡
            // ===============================
            if (oneBlockSlope) {

                if (sledSpeed <= MIN_CLIMB_SPEED) {
                    sledSpeed -= 0.03; // 倒退
                }

                double move = sledSpeed * 0.707;

                moveX = forward.x * move;
                moveY += move;
                moveZ = forward.z * move;

                sledPitch = (float) (Math.abs(forward.z) * 45f);
                sledRoll = (float) (Math.abs(forward.x) * 45f);
            }
            // ===============================
            // 下坡
            // ===============================
            else if (downhill) {

                double move = sledSpeed * 0.707;

                moveX = forward.x * move;
                moveY -= move;
                moveZ = forward.z * move;

                sledPitch = (float) (Math.abs(forward.z) * -45f);
                sledRoll = (float) (Math.abs(forward.x) * -45f);

                //sledSpeed += 0.02; // 重力加速
            }
            // ===============================
            // 平地
            // ===============================
            else {

                moveX = forward.x * sledSpeed;
                moveZ = forward.z * sledSpeed;
                moveY = 0;

                sledPitch = 0f;
                sledRoll = 0f;
            }
        }
        else {
            if (backDown) {

                sledSpeed -= 0.03; // 倒退
                double move = sledSpeed * 0.707;

                moveX = forward.x * move;
                moveY += move;
                moveZ = forward.z * move;

                sledPitch = (float) (Math.abs(forward.z) * 45f);
                sledRoll = (float) (Math.abs(forward.x) * 45f);
            }
            else {

                moveX = forward.x * sledSpeed;
                moveZ = forward.z * sledSpeed;
                moveY = 0;

                sledPitch = 0f;
                sledRoll = 0f;
            }
        }

        moveY -= GRAVITY;
        setDeltaMovement(moveX, moveY, moveZ);
    }

    // ===============================
    // 渲染用 Pitch
    // ===============================

    public float getSledPitch() {
        return sledPitch;
    }

    public float getSledRoll() {
        return sledRoll;
    }

    @Override
    protected void positionRider(Entity passenger, MoveFunction moveFunction) {
        Vec3 vec = this.position().add(0, 0.1, 0.0);
        moveFunction.accept(passenger, vec.x, vec.y, vec.z);
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    @Override
    public boolean canControlVehicle() {
        return true;
    }

}
