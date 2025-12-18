package com.gmail.aamelis.willmod;

import com.gmail.aamelis.willmod.Registries.*;
import com.gmail.aamelis.willmod.Screens.WillForgeScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@Mod(WillModFinalRegistry.MODID)
public class WillModFinalRegistry {
    public static final String MODID = "willmod";

    public WillModFinalRegistry(IEventBus modEventBus, ModContainer modContainer) {
        BlocksInit.register(modEventBus);
        ItemsInit.register(modEventBus);
        SoundsInit.register(modEventBus);
        CreativeTabsInit.register(modEventBus);
        BlockEntitiesInit.register(modEventBus);
        MenuTypesInit.register(modEventBus);
        RecipesInit.register(modEventBus);

    }

    @EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void registerScreens(RegisterMenuScreensEvent event) {
            event.register(MenuTypesInit.WILL_FORGE_MENU.get(), WillForgeScreen::new);
        }
    }
}