package com.gmail.aamelis.willmod.Registries;

import com.gmail.aamelis.willmod.Blocks.entities.WillForgeBlockEntity;
import com.gmail.aamelis.willmod.Blocks.entities.WillForgeCoreBlockEntity;
import com.gmail.aamelis.willmod.WillModFinalRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class BlockEntitiesInit {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, WillModFinalRegistry.MODID);

    public static final Supplier<BlockEntityType<WillForgeBlockEntity>> WILL_FORGE_BLOCK_ENTITY = BLOCK_ENTITIES.register("will_forge_block_entity", () ->
            BlockEntityType.Builder.of(WillForgeBlockEntity::new, BlocksInit.WILL_FORGE_BLOCK.get()).build(null));

    public static final Supplier<BlockEntityType<WillForgeCoreBlockEntity>> WILL_FORGE_CORE_BLOCK_ENTITY = BLOCK_ENTITIES.register("will_forge_core_block_entity", () ->
            BlockEntityType.Builder.of(WillForgeCoreBlockEntity::new, BlocksInit.WILL_FORGE_CORE_BLOCK.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

}
