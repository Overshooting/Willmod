package com.gmail.aamelis.willmod.Registries;

import com.gmail.aamelis.willmod.WillModFinalRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.Map;

public class ArmorMaterialsInit {

    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS =
            DeferredRegister.create(Registries.ARMOR_MATERIAL, WillModFinalRegistry.MODID);

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> RYU_ARMOR =
            ARMOR_MATERIALS.register("ryu_armor", () ->
                    new ArmorMaterial(
                            Map.of(
                                    ArmorItem.Type.BOOTS, 5,
                                    ArmorItem.Type.LEGGINGS, 8,
                                    ArmorItem.Type.CHESTPLATE, 10,
                                    ArmorItem.Type.HELMET, 5
                            ),
                            18,
                            SoundEvents.ARMOR_EQUIP_NETHERITE,
                            () -> Ingredient.of(ItemsInit.CHILLING_AMALGAM),
                            List.of(
                                    new ArmorMaterial.Layer(
                                            ResourceLocation.fromNamespaceAndPath(WillModFinalRegistry.MODID, "ryu_armor")
                                    )
                            ),
                            3.5F,
                            0.15F
                    )
            );

    public static void register(IEventBus bus) {
        ARMOR_MATERIALS.register(bus);
    }

}
