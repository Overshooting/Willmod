package com.gmail.aamelis.willmod.Items.Foods.KMD;

import com.gmail.aamelis.willmod.Registries.ItemsInit;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class SuperKMD extends AbstractKMD{

    public SuperKMD() {
        super(ItemsInit.SUPER_KMSAUCE.get(), 256);
    }

    @Override
    public Component getName(ItemStack stack) {
        return super.getName(stack).copy().withStyle(ChatFormatting.AQUA);
    }
}
