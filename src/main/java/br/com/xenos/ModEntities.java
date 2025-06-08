package br.com.xenos;

import br.com.xenos.entity.XenosEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(Registries.ENTITY_TYPE, Xenos.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<XenosEntity>> XENOS =
            ENTITY_TYPES.register("xenos", () -> EntityType.Builder.of(XenosEntity::new, MobCategory.CREATURE)
                    .sized(0.6f, 1.8f)
                    .build("xenos"));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}