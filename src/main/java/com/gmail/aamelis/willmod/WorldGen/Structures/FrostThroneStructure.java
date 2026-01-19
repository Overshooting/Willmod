package com.gmail.aamelis.willmod.WorldGen.Structures;

import com.gmail.aamelis.willmod.Registries.StructuresInit;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
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

    private static boolean extraSpawningChecks(GenerationContext context) {
        ChunkPos chunkPos = context.chunkPos();
        ChunkGenerator generator = context.chunkGenerator();
        LevelHeightAccessor heightAccessor = context.heightAccessor();

        int x = chunkPos.getMiddleBlockX();
        int z = chunkPos.getMiddleBlockZ();

        NoiseColumn column = generator.getBaseColumn(x, z, heightAccessor, context.randomState());

        int minY = generator.getMinY();
        int maxY = 120;

        System.out.println("Searching valid thickness at: " + x + ", " + z + " from: " + minY + " to " + maxY);

        int topSolidY = -1;
        int bottomSolidY = -1;

        for (int y = maxY - 1; y >= minY; y--) {
            BlockState state = column.getBlock(y - minY);

            if (!state.isAir()) {
                if (topSolidY == -1) {
                    topSolidY = y;
                }
                bottomSolidY = y;
            }
        }

        System.out.println("Final thickness pos found: " + x + ", " + topSolidY + ", " + z);

        // No terrain at all
        if (topSolidY == -1) {
            System.out.println("No terrain found: " + topSolidY);
            return false;
        }

        return (topSolidY - bottomSolidY) >= 5;
    }

    @Override
    public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {

        if (!extraSpawningChecks(context)) {
            return Optional.empty();
        }

        ChunkPos chunkPos = context.chunkPos();
        ChunkGenerator generator = context.chunkGenerator();
        LevelHeightAccessor heightAccessor = context.heightAccessor();

        int x = chunkPos.getMiddleBlockX();
        int z = chunkPos.getMiddleBlockZ();

        var column = generator.getBaseColumn(x, z, heightAccessor, context.randomState());

        int minY = generator.getMinY();
        int maxY = 120;

        System.out.println("Searching valid location at: " + x + ", " + z + " from: " + minY + " to " + maxY);

        int topSolidY = -1;

        for (int y = maxY - 1; y >= minY; y--) {
            if (!column.getBlock(y - minY).isAir()) {
                System.out.println("Found first valid block at: " + x + ", " + y + ", " + z);
                topSolidY = y;
                break;
            }
        }

        if (topSolidY == -1) return Optional.empty();

        // Structure dimensions
        int structureHeight = 5;
        int burialDepth = 5;

        int startY = topSolidY - burialDepth - structureHeight;

        BlockPos startPos = new BlockPos(
                chunkPos.getMinBlockX(),
                startY,
                chunkPos.getMinBlockZ()
        );

        System.out.println("StartPos for Frost Throne found: " + chunkPos.getMinBlockX() + ", " + startY + ", " + chunkPos.getMinBlockZ());

        return JigsawPlacement.addPieces(
                context,
                this.startPool,
                Optional.empty(),
                this.size,
                startPos,
                false,                       // DO NOT expand / roof snap
                Optional.empty(),            // NEVER use heightmaps in Nether
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
