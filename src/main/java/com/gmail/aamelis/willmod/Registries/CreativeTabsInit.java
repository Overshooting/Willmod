package com.gmail.aamelis.willmod.Registries;

import com.gmail.aamelis.willmod.WillModFinalRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CreativeTabsInit {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, WillModFinalRegistry.MODID);

    public static final Supplier<CreativeModeTab> ALL_INGREDIENTS_TAB = CREATIVE_MODE_TABS.register("willmod_all_ingredients_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ItemsInit.WILL_SHARD.get()))
                    .title(Component.translatable("creativetab.willmod.all_items"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ItemsInit.WILL_PHONE);
                        output.accept(ItemsInit.WILL_PICKAXE);
                        output.accept(ItemsInit.WILL_SHARD);
                        output.accept(ItemsInit.EXTINGUISHED_FROST_CORE);
                        output.accept(ItemsInit.ACTIVATED_FROST_CORE);
                        output.accept(ItemsInit.CHILLING_AMALGAM);
                        output.accept(ItemsInit.WILL_INGOT);

                    }).build());

    public static final Supplier<CreativeModeTab> ALL_BLOCKS_TAB = CREATIVE_MODE_TABS.register("willmod_all_blocks_tab",
            () -> CreativeModeTab.builder()
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(WillModFinalRegistry.MODID, "willmod_all_ingredients_tab"))
                    .icon(() -> new ItemStack(BlocksInit.WILL_FACE_BLOCK.get()))
                    .title(Component.translatable("creativetab.willmod.all_blocks"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(BlocksInit.WILL_FACE_BLOCK);
                        output.accept(BlocksInit.WILL_FORGE_BLOCK);
                        output.accept(BlocksInit.WILL_FORGE_SUPPORT_BLOCK);
                        output.accept(BlocksInit.WILL_FORGE_CORE_BLOCK);

                    }).build());

    public static final Supplier<CreativeModeTab> ALL_TOOLS_TAB = CREATIVE_MODE_TABS.register("willmod_all_tools_tab",
            (() -> CreativeModeTab.builder()
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(WillModFinalRegistry.MODID, "willmod_all_blocks_tab"))
                    .icon(() -> new ItemStack(ItemsInit.WILL_SWORD.get()))
                    .title(Component.translatable("creativetab.willmod.all_tools"))
                    .displayItems((itemDisplayParameters, output) -> {
                        var enchants = Minecraft.getInstance().level.registryAccess().registryOrThrow(Registries.ENCHANTMENT);

                        output.accept(ItemsInit.WILL_PICKAXE);
                        output.accept(ItemsInit.WILL_SWORD);
                        output.accept(ItemsInit.WILL_PHONE);

                        ItemStack betterPickaxeItemStack = new ItemStack(ItemsInit.BETTER_WILL_PICKAXE.get());
                        betterPickaxeItemStack.enchant(enchants.getHolderOrThrow(Enchantments.SILK_TOUCH), 1);
                        betterPickaxeItemStack.enchant(enchants.getHolderOrThrow(Enchantments.SILK_TOUCH), 1);

                        output.accept(betterPickaxeItemStack);
                        output.accept(ItemsInit.WILL_AXE);
                        output.accept(ItemsInit.WILL_HELMET);
                        output.accept(ItemsInit.WILL_CHESTPLATE);
                        output.accept(ItemsInit.WILL_LEGGINGS);

                        ItemStack willBootsItemStack = new ItemStack(ItemsInit.WILL_BOOTS.get());
                        willBootsItemStack.enchant(enchants.getHolderOrThrow(Enchantments.FROST_WALKER), 2);

                        output.accept(willBootsItemStack);

                    }).build()));

    public static final Supplier<CreativeModeTab> ALL_FOODS_TAB = CREATIVE_MODE_TABS.register("willmod_all_foods_tab",
            (() -> CreativeModeTab.builder()
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(WillModFinalRegistry.MODID, "willmod_all_tools_tab"))
                    .icon(() -> new ItemStack(ItemsInit.KIMCHI.get()))
                    .title(Component.translatable("creativetab.willmod.all_foods"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ItemsInit.KIMCHI);
                        output.accept(ItemsInit.GARLIC);
                        output.accept(ItemsInit.GARLIC_SEEDS);
                        output.accept(ItemsInit.CABBAGE_SEEDS);
                        output.accept(ItemsInit.CABBAGE);
                        output.accept(ItemsInit.KMSAUCE);

                    }).build()));

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
