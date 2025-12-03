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
        CreativeTabsInit.register(modEventBus);
        BlockEntitiesInit.register(modEventBus);

    }
}