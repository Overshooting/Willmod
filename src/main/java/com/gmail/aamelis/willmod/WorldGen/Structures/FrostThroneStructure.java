package com.gmail.aamelis.willmod.WorldGen.Structures;

import com.gmail.aamelis.willmod.Registries.StructuresInit;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pools.DimensionPadding;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasLookup;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;

import java.util.Optional;

public class FrostThroneStructure extends Structure {

    public static final MapCodec<FrostThroneStructure> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(FrostThroneStructure.settingsCodec(instance),

                    StructureTemplatePool.CODEC.fieldOf("start_pool")
                            .forGetter(structure -> structure.startPool),

                    Codec.intRange(0, 30).fieldOf("size")
                            .forGetter(structure -> structure.size),

                    HeightProvider.CODEC.fieldOf("start_height")
                            .forGetter(structure -> structure.startHeight),

                    Codec.intRange(1, 128).fieldOf("max_distance_from_center")
                            .forGetter(structure -> structure.maxDistanceFromCenter),

                    DimensionPadding.CODEC.optionalFieldOf("dimension_padding", JigsawStructure.DEFAULT_DIMENSION_PADDING)
                            .forGetter(structure -> structure.dimensionPadding),

                    LiquidSettings.CODEC.optionalFieldOf("liquid_settings", JigsawStructure.DEFAULT_LIQUID_SETTINGS)
                            .forGetter(structure -> structure.liquidSettings)
            ).apply(instance, FrostThroneStructure::new));

    private final Holder<StructureTemplatePool> startPool;
    private final int size;
    private final HeightProvider startHeight;
    private final int maxDistanceFromCenter;
    private final DimensionPadding dimensionPadding;
    private final LiquidSettings liquidSettings;

    public FrostThroneStructure(
            StructureSettings settings,
            Holder<StructureTemplatePool> startPool,
            int size,
            HeightProvider startHeight,
            int maxDistanceFromCenter,
            DimensionPadding dimensionPadding,
            LiquidSettings liquidSettings
    ) {
        super(settings);
        this.startPool = startPool;
        this.size = size;
        this.startHeight = startHeight;
        this.maxDistanceFromCenter = maxDistanceFromCenter;
        this.dimensionPadding = dimensionPadding;
        this.liquidSettings = liquidSettings;
    }

    private static BlockPos extraSpawningChecks(GenerationContext context) {
        ChunkPos chunkPos = context.chunkPos();
        ChunkGenerator generator = context.chunkGenerator();
        LevelHeightAccessor heightAccessor = context.heightAccessor();

        int x = chunkPos.getMiddleBlockX();
        int z = chunkPos.getMiddleBlockZ();

        NoiseColumn column = generator.getBaseColumn(x, z, heightAccessor, context.randomState());

        int minY = 30;
        int maxY = 80;

        int validY = -1;

        for (int y = minY; y <= maxY; y++) {
            BlockState state = column.getBlock(y);
            BlockState upperState = column.getBlock(y+ 6);

            if (!state.isAir() && !upperState.isAir() && !state.is(Blocks.LAVA) && !upperState.is(Blocks.LAVA)) {
                validY = y + 1;
            }
        }

        if (validY == -1) {
            return null;
        } else {
            return new BlockPos(x, validY, z);
        }
    }

    @Override
    public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        BlockPos validSpawnPos = extraSpawningChecks(context);

        if (validSpawnPos == null) {
            return Optional.empty();
        } else {
            validSpawnPos = extraSpawningChecks(context);
        }

        return JigsawPlacement.addPieces(
                context,
                this.startPool,
                Optional.empty(),
                this.size,
                validSpawnPos,
                false,
                Optional.empty(),
                this.maxDistanceFromCenter,
                PoolAliasLookup.EMPTY,
                this.dimensionPadding,
                this.liquidSettings
        );
    }

    @Override
    public StructureType<?> type() {
        return StructuresInit.FROST_THRONE_STRUCTURE.get();
    }
}
