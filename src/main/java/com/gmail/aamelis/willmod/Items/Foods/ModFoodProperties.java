package com.gmail.aamelis.willmod.Items.Foods;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Items;

public class ModFoodProperties {

    public static final FoodProperties KIMCHI = new FoodProperties.Builder().nutrition(2).saturationModifier(0.15f).fast().build();

    public static final FoodProperties GARLIC = new FoodProperties.Builder().nutrition(2).saturationModifier(0.25f).build();

    public static final FoodProperties CABBAGE = new FoodProperties.Builder().nutrition(1).saturationModifier(0.1f).fast().build();

    public static final FoodProperties KMSAUCE = new FoodProperties.Builder().nutrition(2).saturationModifier(0.25f).alwaysEdible().usingConvertsTo(Items.GLASS_BOTTLE).build();



}
