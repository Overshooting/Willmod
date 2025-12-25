package com.gmail.aamelis.willmod.Items;

import com.gmail.aamelis.willmod.Registries.SoundsInit;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;


public class WillPhone extends Item {

    public WillPhone() {
        super(new Properties());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_) {
        ItemStack itemstack = p_41433_.getItemInHand(p_41434_);
        if (!p_41432_.isClientSide()) {
            p_41432_.playSound(null, 0.1, 0.1, 0.1, SoundsInit.WILL_PHONE_USE.get(), SoundSource.MASTER, 100f, 2.5f);
            p_41433_.setTicksFrozen(400);
            return InteractionResultHolder.success(itemstack);
        }
        return InteractionResultHolder.pass(itemstack);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void appendHoverText(ItemStack stack, TooltipContext ttc, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.literal("Call the Biggest Defender").withStyle(ChatFormatting.DARK_PURPLE));

        super.appendHoverText(stack, ttc, tooltip, flagIn);
    }

    @Override
    public Component getName(ItemStack stack) {
        return super.getName(stack).copy().withStyle(ChatFormatting.AQUA);
    }
}
