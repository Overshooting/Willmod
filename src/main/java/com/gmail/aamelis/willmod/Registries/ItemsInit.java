package com.gmail.aamelis.willmod.Registries;

import com.gmail.aamelis.willmod.Items.*;
import com.gmail.aamelis.willmod.WillModFinalRegistry;
import net.minecraft.world.item.ArmorItem;
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

    public static final DeferredItem<Item> WILL_FORGE_SUPPORT_BLOCK_ITEM = ITEMS.register("will_forge_support_block",
            () -> createBlockItem(BlocksInit.WILL_FORGE_SUPPORT_BLOCK));

    public static final DeferredItem<Item> WILL_FORGE_CORE_BLOCK_ITEM = ITEMS.register("will_forge_core_block",
            () -> createBlockItem(BlocksInit.WILL_FORGE_CORE_BLOCK));

    public static final DeferredItem<Item> WILL_SHARD = ITEMS.register("will_shard", WillShard::new);

    public static final DeferredItem<Item> WILL_PICKAXE = ITEMS.register("will_pickaxe", WillPickaxe::new);

    public static final DeferredItem<Item> WILL_PHONE = ITEMS.register("will_phone", WillPhone::new);

    public static final DeferredItem<Item> EXTINGUISHED_FROST_CORE = ITEMS.register("extinguished_frost_core", ExtinguishedFrostCore::new);

    public static final DeferredItem<Item> ACTIVATED_FROST_CORE = ITEMS.register("activated_frost_core", ActivatedFrostCore::new);

    public static final DeferredItem<Item> CHILLING_AMALGAM = ITEMS.register("chilling_amalgam", ChillingAmalgam::new);

    public static final DeferredItem<Item> WILL_INGOT = ITEMS.register("will_ingot", WillIngot::new);

    public static final DeferredItem<Item> WILL_SWORD = ITEMS.register("will_sword", WillSword::new);

    public static final DeferredItem<Item> BETTER_WILL_PICKAXE = ITEMS.register("better_will_pickaxe", BetterWillPickaxe::new);

    public static final DeferredItem<Item> WILL_AXE = ITEMS.register("will_axe", WillAxe::new);

    public static final DeferredItem<Item> WILL_HELMET = ITEMS.register("will_helmet", () ->
            new WillArmor(ArmorItem.Type.HELMET));

    public static final DeferredItem<Item> WILL_CHESTPLATE = ITEMS.register("will_chestplate", () ->
            new WillArmor(ArmorItem.Type.CHESTPLATE));

    public static final DeferredItem<Item> WILL_LEGGINGS = ITEMS.register("will_leggings", () ->
            new WillArmor(ArmorItem.Type.LEGGINGS));

    public static final DeferredItem<Item> WILL_BOOTS = ITEMS.register("will_boots", () ->
            new WillArmor(ArmorItem.Type.LEGGINGS));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    public static BlockItem createBlockItem(DeferredBlock<? extends Block> block) {
        return new BlockItem(block.get(), new Item.Properties());
    }
}