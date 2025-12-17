package com.gmail.aamelis.willmod.Registries;

import com.gmail.aamelis.willmod.WillModFinalRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class CreativeTabsInit {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, WillModFinalRegistry.MODID);

    public static final Supplier<CreativeModeTab> ALL_ITEMS_TAB = CREATIVE_MODE_TABS.register("willmod_all_items_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ItemsInit.WILL_SHARD.get()))
                    .title(Component.translatable("creativetab.willmod.all_items"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ItemsInit.WILL_PHONE);
                        output.accept(ItemsInit.WILL_PICKAXE);
                        output.accept(ItemsInit.WILL_SHARD);

                    }).build());

    public static final Supplier<CreativeModeTab> ALL_BLOCKS_TAB = CREATIVE_MODE_TABS.register("willmod_all_blocks_tab",
            () -> CreativeModeTab.builder()
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(WillModFinalRegistry.MODID, "willmod_all_items_tab"))
                    .icon(() -> new ItemStack(BlocksInit.WILL_FACE_BLOCK.get()))
                    .title(Component.translatable("creativetab.willmod.all_items"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(BlocksInit.WILL_FACE_BLOCK);
                        output.accept(BlocksInit.WILL_FORGE_BLOCK);
                        output.accept(BlocksInit.WILL_FORGE_SUPPORT_BLOCK);
                        output.accept(BlocksInit.WILL_FORGE_CORE_BLOCK);
                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
