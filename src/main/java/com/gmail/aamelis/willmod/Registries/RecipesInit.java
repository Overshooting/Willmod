package com.gmail.aamelis.willmod.Registries;

import com.gmail.aamelis.willmod.Recipes.WillForgeRecipe;
import com.gmail.aamelis.willmod.WillModFinalRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RecipesInit {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, WillModFinalRegistry.MODID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, WillModFinalRegistry.MODID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<WillForgeRecipe>> WILL_FORGE_SERIALIZER = SERIALIZERS.register("will_forge", WillForgeRecipe.Serializer::new);

    public static final DeferredHolder<RecipeType<?>, RecipeType<WillForgeRecipe>> WILL_FORGE_TYPE = RECIPE_TYPES.register("will_forge", () ->
            new RecipeType<WillForgeRecipe>() {
                @Override
                public String toString() {
                    return "will_forge";
                }
            });

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
        RECIPE_TYPES.register(eventBus);
    }

}
