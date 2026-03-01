package net.washer.or_biomes.client;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.washer.or_biomes.OrBiomes;
import net.washer.or_biomes.entities.ModEntities;
import net.washer.or_biomes.entities.custom_entity_model.SledEntityModel;
import net.washer.or_biomes.entities.custom_entity_renderer.SledEntityRenderer;

/**
 * @author 洗衣机Washer
 * @version 1.0.0
 */
@EventBusSubscriber(modid = OrBiomes.MOD_ID, value = Dist.CLIENT)
public class ClientModEvents {

    // ===============================
    // 实体渲染器注册
    // ===============================

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(
                ModEntities.SLED_ENTITY.get(),
                SledEntityRenderer::new
        );
    }

    public static final ModelLayerLocation SLED_LAYER =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(OrBiomes.MOD_ID, "entity/sled"), "inventory");

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(
                SLED_LAYER,
                SledEntityModel::createBodyLayer
        );
    }

    // ===============================
    // 可选：注册模型层（如果用LayerDefinition）
    // JSON模型不需要
    // ===============================

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        // JSON模型不需要
    }

    // ===============================
    // 模型加载监听（一般不需要）
    // ===============================

    @SubscribeEvent
    public static void onModelBake(ModelEvent.ModifyBakingResult event) {
        // 如果要替换BakedModel可以在这里处理
    }
}
