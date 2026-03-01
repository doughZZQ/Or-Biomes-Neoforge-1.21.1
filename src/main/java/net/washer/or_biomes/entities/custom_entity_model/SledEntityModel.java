package net.washer.or_biomes.entities.custom_entity_model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.washer.or_biomes.OrBiomes;
import net.washer.or_biomes.entities.custom_entity.SledEntity;

/**
 * @author 洗衣机Washer
 * @version 1.0.0
 */
public class SledEntityModel extends EntityModel<SledEntity> {

    public static final ModelResourceLocation SLED_MODEL_LOCATION =
            new ModelResourceLocation(
                    ResourceLocation.fromNamespaceAndPath(OrBiomes.MOD_ID, "entity/sled"),
                    "inventory"
            );

    private final ModelPart root;

    public SledEntityModel(ModelPart root) {
        this.root = root;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition sled = root.addOrReplaceChild("sled",
                CubeListBuilder.create()

                        // 主底板
                        .texOffs(1, 0)
                        .addBox(-6F, -2F, -4F, 12F, 2F, 24F)

                        // 后靠背
                        .texOffs(13, 6)
                        .addBox(-6F, -8F, -4F, 12F, 6F, 2F)

                        // 前挡板
                        .texOffs(12, 0)
                        .addBox(-6F, -10F, -2F, 12F, 2F, 3F)

                        // 左滑轨
                        .texOffs(1, 14)
                        .addBox(-5F, 0F, -2F, 1F, 2F, 20F)

                        // 右滑轨
                        .texOffs(1, 14)
                        .addBox(4F, 0F, -2F, 1F, 2F, 20F)

                        // 左侧支撑
                        .texOffs(1, 13)
                        .addBox(-6F, -4F, 1F, 0F, 2F, 14F)

                        // 右侧支撑
                        .texOffs(1, 13)
                        .addBox(6F, -4F, 1F, 0F, 2F, 14F)

                        // 装饰板（底部第一层）
                        .texOffs(0, 0)
                        .addBox(-6F, -3F, 0F, 12F, 0F, 12F)

                        // 装饰板（底部第二层火箭贴图）
                        .texOffs(0, 0)
                        .addBox(-6F, -3F, 0F, 12F, 0F, 12F),

                PartPose.offset(0F, -2F, -12F)
        );

        return LayerDefinition.create(mesh, 32, 32);
    }

    @Override
    public void setupAnim(SledEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        root.render(poseStack, buffer, packedLight, packedOverlay, color);
    }

    public void renderModel(SledEntity entity,
                            PoseStack poseStack,
                            MultiBufferSource buffer,
                            int packedLight) {

        Minecraft mc = Minecraft.getInstance();
        BlockRenderDispatcher dispatcher = mc.getBlockRenderer();

        BakedModel bakedModel =
                mc.getModelManager().getModel(SLED_MODEL_LOCATION);

        RenderType renderType = RenderType.cutout();

        dispatcher.getModelRenderer().renderModel(
                poseStack.last(),
                buffer.getBuffer(net.minecraft.client.renderer.RenderType.solid()),
                null,
                bakedModel,
                1f, 1f, 1f,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                ModelData.EMPTY,
                renderType
        );
    }
}
