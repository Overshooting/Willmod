package com.gmail.aamelis.willmod.Items.Foods;

import com.gmail.aamelis.willmod.Registries.ItemsInit;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;

public class NewWaterBottle extends Item {
    public NewWaterBottle() {
        super(new Item.Properties()
                .food(new FoodProperties.Builder().nutrition(0).saturationModifier(0f).alwaysEdible().usingConvertsTo(ItemsInit.NEW_BOTTLE).build())
                .craftRemainder(ItemsInit.NEW_BOTTLE.get())
                .stacksTo(64));
    }



    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }
}
