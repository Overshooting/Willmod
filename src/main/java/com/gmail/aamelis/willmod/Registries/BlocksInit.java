package com.gmail.aamelis.willmod.Registries;

import com.gmail.aamelis.willmod.Blocks.WillFaceBlock;
import com.gmail.aamelis.willmod.Blocks.WillForgeBlock;
import com.gmail.aamelis.willmod.Blocks.WillForgeCoreBlock;
import com.gmail.aamelis.willmod.Blocks.WillForgeSupportBlock;
import com.gmail.aamelis.willmod.WillModFinalRegistry;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.*;

public class BlocksInit {

    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(WillModFinalRegistry.MODID);

    public static final DeferredBlock<Block> WILL_FACE_BLOCK = BLOCKS.register("williams_face_block", WillFaceBlock::new);

    public static final DeferredBlock<Block> WILL_FORGE_SUPPORT_BLOCK = BLOCKS.register("will_forge_support_block", WillForgeSupportBlock::new);

    public static final DeferredBlock<BaseEntityBlock> WILL_FORGE_BLOCK = BLOCKS.register("will_forge_block", () -> new WillForgeBlock(
            BlockBehaviour.Properties.of()
                    .strength(1f, 1f)
                    .friction(0.95f)
                    .sound(SoundType.SCULK_SHRIEKER)
                    .mapColor(MapColor.COLOR_CYAN)
                    .lightLevel(level -> 7)
            )
    );

    public static final DeferredBlock<BaseEntityBlock> WILL_FORGE_CORE_BLOCK = BLOCKS.register("will_forge_core_block", () -> new WillForgeCoreBlock(
            BlockBehaviour.Properties.of()
                    .strength(1f, 1f)
                    .friction(0.1f)
                    .sound(SoundType.ANCIENT_DEBRIS)
                    .mapColor(MapColor.COLOR_BLUE)
            )
    );

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}