package com.gmail.aamelis.willmod.Items;

import com.gmail.aamelis.willmod.Tiers.ModTiers;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class WillAxe extends AxeItem {

    public WillAxe() {
        super(ModTiers.RYU_TIER, new Item.Properties()
                .rarity(Rarity.EPIC)
                .attributes(AxeItem.createAttributes(ModTiers.RYU_TIER, 11, -1f)));
    }

    @Override
    @ParametersAreNonnullByDefault
    public void appendHoverText(ItemStack stack, TooltipContext ttc, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.literal("Ryu Will Reign").withStyle(ChatFormatting.DARK_PURPLE));

        super.appendHoverText(stack, ttc, tooltip, flagIn);
    }

    @Override
    @ParametersAreNonnullByDefault
    @NotNull
    public Component getName(ItemStack stack) {
        return super.getName(stack).copy().withStyle(ChatFormatting.AQUA);
    }
}
