package com.gmail.aamelis.willmod.Registries;

import com.gmail.aamelis.willmod.Items.WillPhone;
import com.gmail.aamelis.willmod.Items.WillPickaxe;
import com.gmail.aamelis.willmod.Items.WillShard;
import com.gmail.aamelis.willmod.WillModFinalRegistry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemsInit {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(WillModFinalRegistry.MODID);

    public static final DeferredItem<Item> WILL_FACE_BLOCK_ITEM = ITEMS.register("williams_face_block",
            () -> createBlockItem(BlocksInit.WILL_FACE_BLOCK));

    public static final DeferredItem<Item> WILL_FORGE_BLOCK_ITEM = ITEMS.register("will_forge_block",
            () -> createBlockItem(BlocksInit.WILL_FORGE_BLOCK));

    public static final DeferredItem<Item> WILL_SHARD = ITEMS.register("will_shard", WillShard::new);

    public static final DeferredItem<Item> WILL_PICKAXE = ITEMS.register("will_pickaxe", WillPickaxe::new);

    public static final DeferredItem<Item> WILL_PHONE = ITEMS.register("will_phone", WillPhone::new);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    public static BlockItem createBlockItem(DeferredBlock<? extends Block> block) {
        return new BlockItem(block.get(), new Item.Properties());
    }
}