package net.washer.or_biomes.entities.custom_entity_renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.washer.or_biomes.OrBiomes;
import net.washer.or_biomes.client.ClientModEvents;
import net.washer.or_biomes.entities.custom_entity.SledEntity;
import net.washer.or_biomes.entities.custom_entity_model.SledEntityModel;

/**
 * @author 洗衣机Washer
 * @version 1.0.0
 */
public class SledEntityRenderer extends EntityRenderer<SledEntity> {

    private static final ResourceLocation SLED_LOCATION =
            ResourceLocation.fromNamespaceAndPath(OrBiomes.MOD_ID, "textures/entity/oak_sled.png");

    private final SledEntityModel model;

    public SledEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new SledEntityModel(context.bakeLayer(ClientModEvents.SLED_LAYER));
        this.shadowRadius = 0.8f;
    }

    @Override
    public void render(SledEntity entity,
                       float entityYaw,
                       float partialTick,
                       PoseStack poseStack,
                       MultiBufferSource buffer,
                       int packedLight
    ) {

        poseStack.pushPose();

        poseStack.mulPose(Axis.YP.rotationDegrees(180.0f - entity.getYRot()));
        poseStack.mulPose(Axis.XP.rotationDegrees(entity.getSledPitch() + entity.getSledRoll()));
        poseStack.mulPose(Axis.ZP.rotationDegrees(180.0f));

        VertexConsumer consumer = buffer.getBuffer(
                RenderType.entityCutout(SLED_LOCATION)
        );

        model.renderToBuffer(
                poseStack,
                consumer,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                rgbToColor(255, 255, 255)
        );

        poseStack.popPose();

        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(SledEntity entity) {
        return SLED_LOCATION;
    }

    public static int rgbToColor(int r, int g, int b) {
        return (r << 16) | (g << 8) | b;
    }
}
