package com.gmail.aamelis.willmod.Items.Tools;

import com.gmail.aamelis.willmod.Registries.EffectsInit;
import com.gmail.aamelis.willmod.Tiers.ModTiers;
import com.gmail.aamelis.willmod.WillModFinalRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
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
                                -0.45f).withModifierAdded(Attributes.ENTITY_INTERACTION_RANGE,
                        new AttributeModifier(ResourceLocation.fromNamespaceAndPath(WillModFinalRegistry.MODID, "will_sword"), 2, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)));
    }

    @Override
    @ParametersAreNonnullByDefault
    public void appendHoverText(ItemStack stack, TooltipContext ttc, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.translatable("tooltip.willmod.will_sword").withStyle(ChatFormatting.DARK_PURPLE));

        super.appendHoverText(stack, ttc, tooltip, flagIn);
    }

    @Override
    @ParametersAreNonnullByDefault
    @NotNull
    public Component getName(ItemStack stack) {
        return super.getName(stack).copy().withStyle(ChatFormatting.AQUA);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.addEffect(new MobEffectInstance(EffectsInit.FREEZING_EFFECT, 150));

        return super.hurtEnemy(stack, target, attacker);
    }
}
