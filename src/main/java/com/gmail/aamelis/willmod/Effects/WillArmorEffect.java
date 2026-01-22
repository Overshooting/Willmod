package com.gmail.aamelis.willmod.Effects;

import com.gmail.aamelis.willmod.WillModFinalRegistry;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

public class WillArmorEffect extends MobEffect {

    public WillArmorEffect(MobEffectCategory category, int color) {
        super(category, color);
        this.addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE,
                        ResourceLocation.fromNamespaceAndPath(WillModFinalRegistry.MODID, "will_armor_effect"), 1f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                .addAttributeModifier(Attributes.BLOCK_INTERACTION_RANGE,
                        ResourceLocation.fromNamespaceAndPath(WillModFinalRegistry.MODID, "will_armor_effect"), 2, AttributeModifier.Operation.ADD_VALUE)
                .addAttributeModifier(Attributes.ENTITY_INTERACTION_RANGE,
                        ResourceLocation.fromNamespaceAndPath(WillModFinalRegistry.MODID, "will_armor_effect"), 2, AttributeModifier.Operation.ADD_VALUE)
                .addAttributeModifier(Attributes.BURNING_TIME,
                        ResourceLocation.fromNamespaceAndPath(WillModFinalRegistry.MODID, "will_armor_effect"), -1f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                .addAttributeModifier(Attributes.SWEEPING_DAMAGE_RATIO,
                        ResourceLocation.fromNamespaceAndPath(WillModFinalRegistry.MODID, "will_armor_effect"), 0.5f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                .addAttributeModifier(Attributes.SAFE_FALL_DISTANCE,
                        ResourceLocation.fromNamespaceAndPath(WillModFinalRegistry.MODID, "will_armor_effect"), 1f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (livingEntity.isAlive()) {
            return true;
        }

        return super.applyEffectTick(livingEntity, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }


}
