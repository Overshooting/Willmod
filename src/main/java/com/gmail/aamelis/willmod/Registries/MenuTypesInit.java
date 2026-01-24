package com.gmail.aamelis.willmod.Registries;

import com.gmail.aamelis.willmod.Screens.KMDBottlerMenu;
import com.gmail.aamelis.willmod.Screens.WillForgeMenu;
import com.gmail.aamelis.willmod.WillModFinalRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MenuTypesInit {

    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, WillModFinalRegistry.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<WillForgeMenu>> WILL_FORGE_MENU =
            MENUS.register("will_forge_menu", () -> IMenuTypeExtension.create(WillForgeMenu::new));

    public static final DeferredHolder<MenuType<?>, MenuType<KMDBottlerMenu>> KMD_BOTTLER_MENU =
            MENUS.register("kmd_bottler_menu", () -> IMenuTypeExtension.create(KMDBottlerMenu::new));

    public static void register(IEventBus bus) {
        MENUS.register(bus);
    }

}
