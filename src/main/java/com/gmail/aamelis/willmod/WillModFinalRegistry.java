package com.gmail.aamelis.willmod;

import com.gmail.aamelis.willmod.Registries.*;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@Mod(WillModFinalRegistry.MODID)
public class WillModFinalRegistry {
    public static final String MODID = "willmod";

    public WillModFinalRegistry(IEventBus modEventBus, ModContainer modContainer) {

        BlocksInit.register(modEventBus);
        ItemsInit.register(modEventBus);
        SoundsInit.register(modEventBus);

        modEventBus.addListener(this::addCreative);

    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(ItemsInit.WILL_FACE_BLOCK_ITEM.get());
        }
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(ItemsInit.WILL_PICKAXE.get());
            event.accept(ItemsInit.WILL_PHONE.get());
        }
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ItemsInit.WILL_SHARD.get());
        }
    }
}