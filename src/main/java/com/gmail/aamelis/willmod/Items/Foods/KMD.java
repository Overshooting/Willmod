package com.gmail.aamelis.willmod.Items.Foods;

import com.gmail.aamelis.willmod.Registries.ItemsInit;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class KMD extends Item {
    public static final Item HELD_ITEM = ItemsInit.KMSAUCE.get();

    public KMD() {
        super(new Item.Properties()
                .stacksTo(1)
                .durability(257)
                .food(new FoodProperties.Builder().build()));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if (!level.isClientSide() && livingEntity instanceof Player player) {
            feedStoredFood(stack, player);
        }

        return stack;
    }

    private void feedStoredFood(ItemStack stack, Player player) {

        FoodProperties props = HELD_ITEM.getFoodProperties(new ItemStack(HELD_ITEM), player);
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
        return stack.getDamageValue() >= stack.getMaxDamage() - 1;
    }

    public static boolean isFull(ItemStack stack) {
        return stack.getDamageValue() == 0;
    }

    public static int getAmount(ItemStack stack) {
        return stack.getMaxDamage() - 1 - stack.getDamageValue();
    }

    public static void setAmount(ItemStack stack, int amount) {
        int max = stack.getMaxDamage() - 1;
        stack.setDamageValue(max - Mth.clamp(amount, 0, max));
    }

    public static void incrementFoodCount(ItemStack stack) {
        if (isFull(stack)) return;

        stack.setDamageValue(stack.getDamageValue() - 1);
    }


}
