package com.gmail.aamelis.willmod.Blocks;

import com.gmail.aamelis.willmod.Blocks.entities.WillForgeCoreBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.Nullable;

public class WillForgeSupportBlock extends Block {

    public WillForgeSupportBlock() {
        super(BlockBehaviour.Properties.of()
                .strength(0.5f, 1f)
                .friction(0.8f)
                .sound(SoundType.SCULK_SHRIEKER)
                .mapColor(MapColor.COLOR_CYAN));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        super.onRemove(state, level, pos, newState, movedByPiston);

        runCoreCheck(level, pos);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        if (level.getBlockState(pos.north()).getBlock() instanceof WillForgeSupportBlock || level.getBlockState(pos.south()).getBlock() instanceof WillForgeSupportBlock || level.getBlockState(pos.east()).getBlock() instanceof WillForgeSupportBlock) {
            runCoreCheck(level, pos);
        }
    }

    private void runCoreCheck(Level level, BlockPos pos) {
        for (int x = pos.getX() - 2; x <= pos.getX() + 2; x++) {
            for (int y = pos.getY() - 1; y <= pos.getY() + 1; y++) {
                for (int z = pos.getZ() - 2; z <= pos.getZ() + 2; z++) {
                    if (level.getBlockEntity(new BlockPos(x, y, z)) instanceof WillForgeCoreBlockEntity thisCoreEntity) {
                        thisCoreEntity.runSupportsCheck();
                    }
                }
            }
        }
    }
}
