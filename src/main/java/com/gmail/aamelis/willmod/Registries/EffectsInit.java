package com.gmail.aamelis.willmod.Registries;

import com.gmail.aamelis.willmod.Effects.FreezingEffect;
import com.gmail.aamelis.willmod.Effects.WillArmorEffect;
import com.gmail.aamelis.willmod.WillModFinalRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class EffectsInit {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, WillModFinalRegistry.MODID);

    public static final DeferredHolder<MobEffect, FreezingEffect> FREEZING_EFFECT = MOB_EFFECTS.register("freezing_effect", () ->
            new FreezingEffect(MobEffectCategory.HARMFUL, 0x31dbe0));

    public static final Holder<MobEffect> WILL_ARMOR_EFFECT = MOB_EFFECTS.register("will_armor_effect", () ->
            new WillArmorEffect(MobEffectCategory.BENEFICIAL, 0x0044FF));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }

}
