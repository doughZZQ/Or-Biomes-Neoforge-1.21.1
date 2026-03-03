package net.washer.or_biomes.entities.custom_entity_renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.washer.or_biomes.OrBiomes;
import net.washer.or_biomes.entities.custom_entity.SledEntity;
import net.washer.or_biomes.entities.custom_entity_model.SledEntityModel;
import net.washer.or_biomes.items.ModItems;

/**
 * @author 洗衣机Washer
 * @version 1.0.0
 */
public class SledEntityRenderer extends EntityRenderer<SledEntity> {

    private static final ModelResourceLocation SLED_MODEL = ModelResourceLocation.standalone(
            ResourceLocation.fromNamespaceAndPath(OrBiomes.MOD_ID, "item/sled_entity_base"));

    private final ItemRenderer itemRenderer;

    public SledEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.8F;
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(SledEntity entity,
                       float entityYaw,
                       float partialTick,
                       PoseStack poseStack,
                       MultiBufferSource buffer,
                       int packedLight
    ) {
        ItemStack stack = new ItemStack(ModItems.SLED);
        BakedModel baseModel = Minecraft.getInstance().getModelManager().getModel(SLED_MODEL);

        poseStack.pushPose();

        poseStack.mulPose(Axis.YP.rotationDegrees(180.0f - entity.getYRot()));
        poseStack.mulPose(Axis.XP.rotationDegrees(entity.getSledPitch() + entity.getSledRoll()));
        //poseStack.mulPose(Axis.ZP.rotationDegrees(180.0f));
        poseStack.translate(0.0F, 0.8F, -0.4F);
        poseStack.scale(1.6F, 1.6F, 1.6F);

        this.itemRenderer.render(stack, ItemDisplayContext.FIXED, false, poseStack, buffer, packedLight, 0, baseModel);

        poseStack.popPose();

        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(SledEntity entity) {
        return ResourceLocation.withDefaultNamespace("textures/atlas/blocks.png");
    }

    public static int rgbToColor(int r, int g, int b) {
        return (r << 16) | (g << 8) | b;
    }
}
