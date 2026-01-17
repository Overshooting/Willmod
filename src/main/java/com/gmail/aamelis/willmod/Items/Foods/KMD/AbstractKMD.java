package com.gmail.aamelis.willmod.Items.Foods.KMD;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

import java.util.List;

public abstract class AbstractKMD extends Item {

    private final Item heldItem;
    private final int maxCapacity;

    public AbstractKMD(Item item, int capacity) {
        super(new Item.Properties()
                .stacksTo(1)
                .durability(capacity + 2)
                .food(new FoodProperties.Builder().build())
                .setNoRepair());

        heldItem = item;
        maxCapacity = capacity;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if (!level.isClientSide() && livingEntity instanceof Player player) {
            feedStoredFood(stack, player);
        }

        return stack;
    }

    private void feedStoredFood(ItemStack stack, Player player) {

        FoodProperties props = heldItem.getFoodProperties(new ItemStack(heldItem), player);
        if (props == null) return;

        player.getFoodData().eat(props.nutrition(), props.saturation());

        for (FoodProperties.PossibleEffect effect : props.effects()) {
            if (player.getRandom().nextFloat() < effect.probability()) {
                player.addEffect(new MobEffectInstance(effect.effect()));
            }
        }

        setAmount(stack, getAmount(stack) - 1);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (isEmpty(stack)) {
            return InteractionResultHolder.fail(stack);
        }

        return super.use(level, player, hand);
    }

    public static boolean isEmpty(ItemStack stack) {
        return stack.getDamageValue() >= getMaxCapacity((AbstractKMD)stack.getItem()) + 1;
    }

    public static boolean isFull(ItemStack stack) {
        return stack.getDamageValue() == 1;
    }

    public static int getAmount(ItemStack stack) {
        return Math.max(0, getMaxCapacity((AbstractKMD)stack.getItem()) + 1 - stack.getDamageValue());
    }

    public static void setAmount(ItemStack stack, int amount) {
        AbstractKMD item = (AbstractKMD) stack.getItem();

        int clamped = Mth.clamp(amount, 0, item.maxCapacity);
        stack.setDamageValue((item.maxCapacity + 1) - clamped);
    }

    public static void incrementFoodCount(ItemStack stack) {
        if (isFull(stack)) return;

        stack.setDamageValue(stack.getDamageValue() - 1);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (getAmount(stack) == getMaxCapacity((AbstractKMD) stack.getItem()) + 1) {
            return;
        }

        String tooltip = isEmpty(stack) ? "Empty" : getAmount(stack) + " Charges";

        tooltipComponents.add(Component.literal(tooltip).withStyle(ChatFormatting.GRAY));

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level level, Player player) {
        super.onCraftedBy(stack, level, player);

        setAmount(stack, 0);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (stack.getDamageValue() == 0) {
            stack.setDamageValue(maxCapacity);
        };
    }

    public static Item getHeldItem(AbstractKMD kmdItem) {
        return kmdItem.heldItem;
    }

    public static int getMaxCapacity(AbstractKMD kmdItem) {
        return kmdItem.maxCapacity;
    }


}
