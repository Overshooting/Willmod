package com.gmail.aamelis.willmod.Registries;

import com.gmail.aamelis.willmod.WillModFinalRegistry;
import com.gmail.aamelis.willmod.WorldGen.Structures.FrostThroneStructure;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class StructuresInit {

    public static final DeferredRegister<StructureType<?>> STRUCTURES = DeferredRegister.create(Registries.STRUCTURE_TYPE, WillModFinalRegistry.MODID);

    public static final DeferredHolder<StructureType<?>, StructureType<FrostThroneStructure>> FROST_THRONE_STRUCTURE = STRUCTURES.register("frost_throne_structure", () -> explicitStructureTypeTyping(FrostThroneStructure.CODEC));

    private static <T extends Structure> StructureType<T> explicitStructureTypeTyping(MapCodec<T> structureCodec) {
        return () -> structureCodec;
    }

    public static void register(IEventBus eventBus) {
        STRUCTURES.register(eventBus);
    }
}
