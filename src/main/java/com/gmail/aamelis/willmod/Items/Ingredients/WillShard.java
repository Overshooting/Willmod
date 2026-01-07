package com.gmail.aamelis.willmod.Items.Ingredients;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class WillShard extends Item {

    public WillShard() {
        super(new Properties());
    }

    @Override
    public Component getName(ItemStack stack) {
        return super.getName(stack).copy().withStyle(ChatFormatting.BLUE);
    }
}
