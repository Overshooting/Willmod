package com.gmail.aamelis.willmod.compat;

import com.gmail.aamelis.willmod.Recipes.WillForgeRecipe;
import com.gmail.aamelis.willmod.Registries.BlocksInit;
import com.gmail.aamelis.willmod.WillModFinalRegistry;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class WillForgeRecipeCategory implements IRecipeCategory<WillForgeRecipe> {

    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(WillModFinalRegistry.MODID, "will_forge");
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(WillModFinalRegistry.MODID,
            "textures/gui/will_forge/will_forge_screen.png");

    public static final RecipeType<WillForgeRecipe> WILL_FORGE_RECIPE_RECIPE_TYPE =
            new RecipeType<>(UID, WillForgeRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public WillForgeRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 82);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(BlocksInit.WILL_FORGE_BLOCK));
    }

    @Override
    public RecipeType<WillForgeRecipe> getRecipeType() {
        return WILL_FORGE_RECIPE_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.willmod.will_forge_block");
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, WillForgeRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 54, 34).addIngredients(recipe.getIngredients().get(0));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 104, 34).addItemStack(recipe.getResultItem(null));
    }

    @Override
    public void draw(WillForgeRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);
        background.draw(guiGraphics);
    }

    @Override
    public int getWidth() {
        return 176;
    }

    @Override
    public int getHeight() {
        return 82;
    }
}
