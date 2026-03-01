package net.washer.or_biomes.entities;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.washer.or_biomes.OrBiomes;
import net.washer.or_biomes.entities.custom_entity.SledEntity;

import java.util.function.Supplier;

/**
 * @author 洗衣机Washer
 * @version 1.0.0
 */
public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(Registries.ENTITY_TYPE, OrBiomes.MOD_ID);

    public static final Supplier<EntityType<SledEntity>> SLED_ENTITY =
            ENTITY_TYPES.register("sled_entity",
                    () -> EntityType.Builder.of(SledEntity::new, MobCategory.MISC)
                            .sized(1.6f, 0.8f)
                            .clientTrackingRange(10)
                            .updateInterval(1)
                            .build("sled_entity"));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
