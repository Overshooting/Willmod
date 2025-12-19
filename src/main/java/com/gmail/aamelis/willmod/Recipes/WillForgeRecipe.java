package com.gmail.aamelis.willmod.Recipes;

import com.gmail.aamelis.willmod.Registries.RecipesInit;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public record WillForgeRecipe(Ingredient inputItem, ItemStack output) implements Recipe<WillForgeRecipeInput> {

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(inputItem);
        return list;
    }

    @Override
    public boolean matches(WillForgeRecipeInput willForgeRecipeInput, Level level) {
        if (level.isClientSide()) return false;

        return inputItem.test(willForgeRecipeInput.getItem(0));
    }

    @Override
    public ItemStack assemble(WillForgeRecipeInput willForgeRecipeInput, HolderLookup.Provider provider) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipesInit.WILL_FORGE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipesInit.WILL_FORGE_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<WillForgeRecipe> {
        public static final MapCodec<WillForgeRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(WillForgeRecipe::inputItem),
                ItemStack.CODEC.fieldOf("result").forGetter(WillForgeRecipe::output)
        ).apply(inst, WillForgeRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, WillForgeRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC, WillForgeRecipe::inputItem,
                        ItemStack.STREAM_CODEC, WillForgeRecipe::output,
                        WillForgeRecipe::new
                );

        @Override
        public MapCodec<WillForgeRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, WillForgeRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
