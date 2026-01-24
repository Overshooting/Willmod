package com.gmail.aamelis.willmod.Items.Foods.KMD;

import com.gmail.aamelis.willmod.Registries.ItemsInit;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class KMD extends AbstractKMD {

    public KMD() {
        super(ItemsInit.KMSAUCE.get(), 128);
    }

    @Override
    public Component getName(ItemStack stack) {
        return super.getName(stack).copy().withStyle(ChatFormatting.BLUE);
    }
}
