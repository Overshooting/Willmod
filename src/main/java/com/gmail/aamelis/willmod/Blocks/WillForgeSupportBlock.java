package com.gmail.aamelis.willmod.Blocks;

import com.gmail.aamelis.willmod.Blocks.entities.WillForgeBlockEntity;
import com.gmail.aamelis.willmod.Blocks.entities.WillForgeSupportBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class WillForgeSupportBlock extends BaseEntityBlock {
    public static final MapCodec<WillForgeSupportBlock> CODEC = simpleCodec(WillForgeSupportBlock::new);

    public WillForgeSupportBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new WillForgeSupportBlockEntity(blockPos, blockState);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        super.onRemove(state, level, pos, newState, movedByPiston);

        runForgeCheck(level, pos);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        if (level.getBlockState(pos.north()).getBlock() instanceof WillForgeSupportBlock || level.getBlockState(pos.south()).getBlock() instanceof WillForgeSupportBlock || level.getBlockState(pos.east()).getBlock() instanceof WillForgeSupportBlock) {
            runForgeCheck(level, pos);
        }
    }

    private void runForgeCheck(Level level, BlockPos pos) {
        for (int x = pos.getX() - 2; x <= pos.getX() + 2; x++) {
            for (int y = pos.getY() - 1; y <= pos.getY() + 1; y++) {
                for (int z = pos.getZ() - 2; z <= pos.getZ() + 2; z++) {
                    if (level.getBlockEntity(new BlockPos(x, y, z)) instanceof WillForgeBlockEntity thisForgeEntity) {
                        thisForgeEntity.fireCheckState(level);
                    }
                }
            }
        }
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }
}
