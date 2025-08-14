package com.gmail.aamelis.willmod.Items;

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
        tooltip.add(Component.literal("Strips William's Essence from Ice Blocks"));

        super.appendHoverText(stack, ttc, tooltip, flagIn);
    }

}
