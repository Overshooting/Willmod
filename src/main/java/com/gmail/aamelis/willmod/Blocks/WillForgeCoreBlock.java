package com.gmail.aamelis.willmod.Blocks;

import com.gmail.aamelis.willmod.Blocks.entities.WillForgeCoreBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class WillForgeCoreBlock extends BaseEntityBlock {
    public static final MapCodec<WillForgeCoreBlock> CODEC = simpleCodec(WillForgeCoreBlock::new);

    public WillForgeCoreBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new WillForgeCoreBlockEntity(blockPos, blockState);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        WillForgeCoreBlockEntity thisBlockEntity = (WillForgeCoreBlockEntity) level.getBlockEntity(pos);

        if (thisBlockEntity != null && thisBlockEntity.hasForge() && thisBlockEntity.getForgeEntity() != null) thisBlockEntity.getForgeEntity().setEnabledState(false);

        super.onRemove(state, level, pos, newState, movedByPiston);
    }
}
