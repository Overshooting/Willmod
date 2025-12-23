package com.gmail.aamelis.willmod.Tiers;

import com.gmail.aamelis.willmod.Registries.ItemsInit;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

public class ModTiers {

    public static final Tier RYU_TIER = new Tier() {
        @Override
        public int getUses() {
            return 3000;
        }

        @Override
        public float getAttackDamageBonus() {
            return 4.0f;
        }

        @Override
        public float getSpeed() {
            return 9.0f;
        }

        @Override
        public int getEnchantmentValue() {
            return 15;
        }

        @Override
        public TagKey<Block> getIncorrectBlocksForDrops() {
            return BlockTags.INCORRECT_FOR_NETHERITE_TOOL;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of(ItemsInit.CHILLING_AMALGAM);
        }
    };

}
