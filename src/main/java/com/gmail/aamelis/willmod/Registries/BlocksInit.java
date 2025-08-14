package com.gmail.aamelis.willmod.Registries;

import com.gmail.aamelis.willmod.Blocks.WillFaceBlock;
import com.gmail.aamelis.willmod.WillModFinalRegistry;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.*;

public class BlocksInit {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(WillModFinalRegistry.MODID);

    public static final DeferredBlock<Block> WILL_FACE_BLOCK = BLOCKS.register("williams_face_block", WillFaceBlock::new);

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}