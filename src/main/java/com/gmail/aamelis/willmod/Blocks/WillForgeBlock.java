package com.gmail.aamelis.willmod.Blocks;

import com.gmail.aamelis.willmod.Blocks.entities.WillForgeBlockEntity;
import com.gmail.aamelis.willmod.Blocks.entities.WillForgeCoreBlockEntity;
import com.gmail.aamelis.willmod.Registries.BlockEntitiesInit;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class WillForgeBlock extends BaseEntityBlock {
    public static final MapCodec<WillForgeBlock> CODEC = simpleCodec(WillForgeBlock::new);

    public WillForgeBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new WillForgeBlockEntity(blockPos, blockState);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof WillForgeBlockEntity willForgeBlockEntity) {
                willForgeBlockEntity.drops();
                if (willForgeBlockEntity.hasCoreBlock()) {
                    checkForCoreBlock(level, pos, true);
                }
            }
        }

        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);

        checkForCoreBlock(level, pos, false);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!level.isClientSide()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof WillForgeBlockEntity willForgeBlockEntity) {
                ((ServerPlayer) player).openMenu(new SimpleMenuProvider(willForgeBlockEntity, Component.literal("Will Forge")), pos);
            } else {
                throw new IllegalStateException("Container not found");
            }
        }

        return ItemInteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, BlockEntitiesInit.WILL_FORGE_BLOCK_ENTITY.get(),
                (newLevel, pos, newState, blockEntity) -> blockEntity.tick(level, pos, state));
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);

        checkForCoreBlock(level, pos, false);
    }

    private void checkForCoreBlock(Level level, BlockPos pos, boolean removeSignature) {
        int xFlag;
        if (removeSignature) {
            xFlag = Integer.MAX_VALUE;
        } else {
            xFlag = pos.getX();
        }

        if (level.getBlockEntity(pos.east()) instanceof WillForgeCoreBlockEntity coreBlockEntity) {
            coreBlockEntity.setForgePos(xFlag, pos.getY(), pos.getZ());
            coreBlockEntity.runSupportsCheck();
        } else if (level.getBlockEntity(pos.west()) instanceof WillForgeCoreBlockEntity coreBlockEntity) {
            coreBlockEntity.setForgePos(xFlag, pos.getY(), pos.getZ());
            coreBlockEntity.runSupportsCheck();
        } else if (level.getBlockEntity(pos.north()) instanceof WillForgeCoreBlockEntity coreBlockEntity) {
            coreBlockEntity.setForgePos(xFlag, pos.getY(), pos.getZ());
            coreBlockEntity.runSupportsCheck();
        } else if (level.getBlockEntity(pos.south()) instanceof WillForgeCoreBlockEntity coreBlockEntity) {
            coreBlockEntity.setForgePos(xFlag, pos.getY(), pos.getZ());
            coreBlockEntity.runSupportsCheck();
        }
    }

}
