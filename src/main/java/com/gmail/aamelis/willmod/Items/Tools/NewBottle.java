package com.gmail.aamelis.willmod.Items.Tools;

import com.gmail.aamelis.willmod.Registries.ItemsInit;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.List;

public class NewBottle extends BottleItem {

    public NewBottle() {
        super(new Item.Properties());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        List<AreaEffectCloud> list = level.getEntitiesOfClass(
                AreaEffectCloud.class,
                player.getBoundingBox().inflate(2.0),
                p_350149_ -> p_350149_ != null && p_350149_.isAlive() && p_350149_.getOwner() instanceof EnderDragon
        );
        ItemStack itemstack = player.getItemInHand(hand);
        if (!list.isEmpty()) {
            AreaEffectCloud areaeffectcloud = list.get(0);
            areaeffectcloud.setRadius(areaeffectcloud.getRadius() - 0.5F);
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL_DRAGONBREATH, SoundSource.NEUTRAL, 1.0F, 1.0F);
            level.gameEvent(player, GameEvent.FLUID_PICKUP, player.position());
            if (player instanceof ServerPlayer serverplayer) {
                CriteriaTriggers.PLAYER_INTERACTED_WITH_ENTITY.trigger(serverplayer, itemstack, areaeffectcloud);
            }

            return InteractionResultHolder.sidedSuccess(
                    this.turnBottleIntoItem(itemstack, player, new ItemStack(Items.DRAGON_BREATH)), level.isClientSide()
            );
        } else {
            BlockHitResult blockhitresult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
            if (blockhitresult.getType() == HitResult.Type.MISS) {
                return InteractionResultHolder.pass(itemstack);
            } else {
                if (blockhitresult.getType() == HitResult.Type.BLOCK) {
                    BlockPos blockpos = blockhitresult.getBlockPos();
                    if (!level.mayInteract(player, blockpos)) {
                        return InteractionResultHolder.pass(itemstack);
                    }

                    if (level.getFluidState(blockpos).is(FluidTags.WATER)) {
                        level.playSound(
                                player, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.NEUTRAL, 1.0F, 1.0F
                        );
                        level.gameEvent(player, GameEvent.FLUID_PICKUP, blockpos);
                        return InteractionResultHolder.sidedSuccess(
                                super.turnBottleIntoItem(itemstack, player, new ItemStack(ItemsInit.NEW_WATER_BOTTLE.get())), level.isClientSide()
                        );
                    }
                }

                return InteractionResultHolder.pass(itemstack);
            }
        }

    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.willmod.new_bottle").withStyle(ChatFormatting.GRAY));

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
