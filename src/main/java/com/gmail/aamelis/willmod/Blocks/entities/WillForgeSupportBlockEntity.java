package com.gmail.aamelis.willmod.Blocks.entities;

import com.gmail.aamelis.willmod.Blocks.WillForgeBlock;
import com.gmail.aamelis.willmod.Blocks.WillForgeSupportBlock;
import com.gmail.aamelis.willmod.Registries.BlockEntitiesInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class WillForgeSupportBlockEntity extends BlockEntity {

    private WillForgeBlockEntity forgeBlockEntity;
    private WillForgeSupportBlockEntity centerBlockEntity;
    private int surroundingSupportBlocks;
    private ContainerData data;

    public WillForgeSupportBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntitiesInit.WILL_FORGE_SUPPORT_BLOCK_ENTITY.get(), pos, blockState);

        surroundingSupportBlocks = 0;
        data = new ContainerData() {
            @Override
            public int get(int i) {
                return switch(i) {
                    case 0 -> WillForgeSupportBlockEntity.this.surroundingSupportBlocks;
                    default -> 0;
                };
            }

            @Override
            public void set(int i, int value) {
                switch(i) {
                    case 0: WillForgeSupportBlockEntity.this.surroundingSupportBlocks = value;
                }
            }

            @Override
            public int getCount() {
                return 1;
            }
        };
    }

    public void fireCheckState(Level level) {
        if (centerBlockEntity != null) {
            surroundingSupportBlocks = 0;

            for (int x = getBlockPos().getX() - 1; x <= getBlockPos().getX() + 1; x++) {
                for (int y = getBlockPos().getY() - 1; y <= getBlockPos().getY() + 1; y++) {
                    for (int z = getBlockPos().getZ() - 1; z <= getBlockPos().getZ() + 1; z++) {
                        if (x != getBlockPos().getX() || y != getBlockPos().getY() || z != getBlockPos().getZ()) {
                            BlockEntity blockEntityCheck = level.getBlockEntity(new BlockPos(x, y, z));

                            if (blockEntityCheck instanceof WillForgeSupportBlockEntity thisBlockEntity) {
                                System.out.println("SupportEntityFound Incrementing SurroundingSupports");

                                surroundingSupportBlocks++;
                                thisBlockEntity.setCenterBlockEntity(centerBlockEntity);
                            }
                        } else {
                            System.out.println("ConditionsNotMet Simples: " + x + ", " + y + ", " + z + "Pos: " + getBlockPos().getX() + ", " + getBlockPos().getY() + ", " + getBlockPos().getZ() + " && blockEntityMatch: " + (level.getBlockEntity(new BlockPos(x, y, z)) instanceof WillForgeSupportBlockEntity thisBlockEntity));
                        }
                    }
                }
            }

            System.out.println("Level Found. Surrounding Support Blocks of centerblock at " + getBlockPos().getX() + ", " + getBlockPos().getY() + ", " + getBlockPos().getZ() + ": " + surroundingSupportBlocks);

            forgeBlockEntity.setEnabledState(surroundingSupportBlocks == 25);
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }
        System.out.println("NotACenterBlock");
    }

    public void setForgeBlockEntity(WillForgeBlockEntity newForgeBlockEntity, Level level) {
        forgeBlockEntity = newForgeBlockEntity;
        centerBlockEntity = this;
        fireCheckState(level);
    }

    public boolean hasCenterBlockEntity() {
        return centerBlockEntity != null;
    }

    public void blockBroken(Level level) {
        if (surroundingSupportBlocks > 0) {
            surroundingSupportBlocks--;
            fireCheckState(level);
        }
    }

    public void fireRemoveState(Level level) {
        if (centerBlockEntity != null) {
            centerBlockEntity.blockBroken(level);
        }
    }

    public void setCenterBlockEntity(WillForgeSupportBlockEntity thisCenterBlockEntity) {
        centerBlockEntity = thisCenterBlockEntity;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        tag.putInt("surrounding_support_blocks", surroundingSupportBlocks);

        super.saveAdditional(tag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);

        surroundingSupportBlocks = tag.getInt("surrounding_support_blocks");
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
