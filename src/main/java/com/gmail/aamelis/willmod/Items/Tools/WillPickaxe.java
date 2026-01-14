package com.gmail.aamelis.willmod.Items.Tools;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class WillPickaxe extends PickaxeItem {

    public WillPickaxe() {
        super(Tiers.WOOD, new Item.Properties()
                .attributes(PickaxeItem.createAttributes(Tiers.WOOD, 1, -0.8f))
                .durability(256));
    }

    @Override
    @ParametersAreNonnullByDefault
    public void appendHoverText(ItemStack stack, TooltipContext ttc, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.translatable("tooltip.willmod.will_pickaxe"));

        super.appendHoverText(stack, ttc, tooltip, flagIn);
    }

}
