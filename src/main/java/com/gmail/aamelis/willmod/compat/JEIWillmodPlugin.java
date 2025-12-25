package com.gmail.aamelis.willmod.compat;

import com.gmail.aamelis.willmod.Recipes.WillForgeRecipe;
import com.gmail.aamelis.willmod.Registries.ItemsInit;
import com.gmail.aamelis.willmod.Registries.RecipesInit;
import com.gmail.aamelis.willmod.Screens.WillForgeScreen;
import com.gmail.aamelis.willmod.WillModFinalRegistry;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

@JeiPlugin
public class JEIWillmodPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(WillModFinalRegistry.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new WillForgeRecipeCategory(
                registration.getJeiHelpers().getGuiHelper()
        ));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<WillForgeRecipe> willForgeRecipes = recipeManager
                .getAllRecipesFor(RecipesInit.WILL_FORGE_TYPE.get()).stream().map(RecipeHolder::value).toList();

        registration.addRecipes(WillForgeRecipeCategory.WILL_FORGE_RECIPE_RECIPE_TYPE, willForgeRecipes);

        registration.addIngredientInfo(new ItemStack(ItemsInit.WILL_SHARD.get()),
                VanillaTypes.ITEM_STACK,
                Component.translatable("jei.willmod.will_shard_info"));

        registration.addIngredientInfo(new ItemStack(ItemsInit.EXTINGUISHED_FROST_CORE.get()),
                VanillaTypes.ITEM_STACK,
                Component.translatable("jei.willmod.igloo_info"));
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(WillForgeScreen.class, 74, 30, 22, 20,
                WillForgeRecipeCategory.WILL_FORGE_RECIPE_RECIPE_TYPE);
    }
}
