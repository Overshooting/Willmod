package com.gmail.aamelis.willmod.datagen;

import com.gmail.aamelis.willmod.Loot.AddItemModifier;
import com.gmail.aamelis.willmod.Registries.ItemsInit;
import com.gmail.aamelis.willmod.WillModFinalRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.WeatherCheck;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class ModGlobalLootModifierProvider extends GlobalLootModifierProvider {

    public ModGlobalLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, WillModFinalRegistry.MODID);
    }

    @Override
    protected void start() {
        this.add("willshard_to_stray",
                new AddItemModifier(new LootItemCondition[] {
                            new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("entities/stray")).build(),
                            WeatherCheck.weather().setRaining(true).setThundering(true).build()
                        },
                        ItemsInit.WILL_SHARD.get()));


    }
}
