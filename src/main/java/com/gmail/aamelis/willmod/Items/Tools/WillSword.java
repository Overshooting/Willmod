package com.gmail.aamelis.willmod.Items.Tools;

import com.gmail.aamelis.willmod.Tiers.ModTiers;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class WillSword extends SwordItem {

    public WillSword() {
        super(ModTiers.RYU_TIER, new Item.Properties()
                .rarity(Rarity.EPIC)
                .attributes(SwordItem.createAttributes(
                        ModTiers.RYU_TIER,
                        11,
                                -0.45f)));
    }

    @Override
    @ParametersAreNonnullByDefault
    public void appendHoverText(ItemStack stack, TooltipContext ttc, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.literal("Ikorose, Shinso").withStyle(ChatFormatting.DARK_PURPLE));

        super.appendHoverText(stack, ttc, tooltip, flagIn);
    }

    @Override
    @ParametersAreNonnullByDefault
    @NotNull
    public Component getName(ItemStack stack) {
        return super.getName(stack).copy().withStyle(ChatFormatting.AQUA);
    }
}
