package com.gmail.aamelis.willmod.Items.Tools;

import com.gmail.aamelis.willmod.Tiers.ModTiers;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class BetterWillPickaxe extends PickaxeItem {

    public BetterWillPickaxe() {
        super(ModTiers.RYU_TIER, new Item.Properties()
                .attributes(PickaxeItem.createAttributes(ModTiers.RYU_TIER, 1, -0.75f))
                        .rarity(Rarity.EPIC)
                );
    }

    @Override
    @ParametersAreNonnullByDefault
    public void appendHoverText(ItemStack stack, TooltipContext ttc, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.literal("Shatter The Veil").withStyle(ChatFormatting.DARK_PURPLE));

        super.appendHoverText(stack, ttc, tooltip, flagIn);
    }

    @Override
    @ParametersAreNonnullByDefault
    @NotNull
    public Component getName(ItemStack stack) {
        return super.getName(stack).copy().withStyle(ChatFormatting.AQUA);
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level level, Player player) {
        if (level.isClientSide) return;

        if (EnchantmentHelper.getItemEnchantmentLevel(
                level.registryAccess().registryOrThrow(Registries.ENCHANTMENT)
                        .getHolderOrThrow(Enchantments.SILK_TOUCH),
                stack
        ) == 0) {

            var enchants = level.registryAccess()
                    .registryOrThrow(Registries.ENCHANTMENT);

            stack.enchant(enchants.getHolderOrThrow(Enchantments.SILK_TOUCH), 1);
        }

        super.onCraftedBy(stack, level, player);
    }
}
