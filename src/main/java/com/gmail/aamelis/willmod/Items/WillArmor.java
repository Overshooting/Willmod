package com.gmail.aamelis.willmod.Items;

import com.gmail.aamelis.willmod.Registries.ArmorMaterialsInit;
import com.gmail.aamelis.willmod.Registries.ItemsInit;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class WillArmor extends ArmorItem {

    public WillArmor(ArmorItem.Type type) {
        super(
                ArmorMaterialsInit.RYU_ARMOR,
                type,
                new Item.Properties()
                        .rarity(Rarity.EPIC)
                        .stacksTo(1)
        );
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level level, Player player) {
        if (level.isClientSide) return;

        if (stack.getItem() == ItemsInit.WILL_BOOTS.get()) {
            if (EnchantmentHelper.getItemEnchantmentLevel(
                    level.registryAccess().registryOrThrow(Registries.ENCHANTMENT)
                            .getHolderOrThrow(Enchantments.FROST_WALKER),
                    stack
            ) == 0) {

                var enchants = level.registryAccess()
                        .registryOrThrow(Registries.ENCHANTMENT);

                stack.enchant(enchants.getHolderOrThrow(Enchantments.FROST_WALKER), 2);
            }
        }

        super.onCraftedBy(stack, level, player);
    }

    @Override
    public Component getName(ItemStack stack) {
        return super.getName(stack).copy().withStyle(ChatFormatting.AQUA);
    }

}
