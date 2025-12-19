package com.gmail.aamelis.willmod.Blocks.entities;

import com.gmail.aamelis.willmod.Blocks.WillForgeSupportBlock;
import com.gmail.aamelis.willmod.Registries.BlockEntitiesInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class WillForgeCoreBlockEntity extends BlockEntity {

    private int forgeX, forgeY, forgeZ;

    public WillForgeCoreBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntitiesInit.WILL_FORGE_CORE_BLOCK_ENTITY.get(), pos, blockState);

        forgeX = Integer.MAX_VALUE;
        forgeY = 0;
        forgeZ = 0;
    }

    public void setForgePos(int newX, int newY, int newZ) {
        if (newX == Integer.MAX_VALUE) {
            forgeX = newX;
            forgeY = newY;
            forgeZ = newZ;
        } else if (level != null && !level.isClientSide() && level.getBlockEntity(new BlockPos(newX, newY, newZ)) instanceof WillForgeBlockEntity) {
            forgeX = newX;
            forgeY = newY;
            forgeZ = newZ;
            setChanged(level, getBlockPos(), getBlockState());
            runSupportsCheck();
        }
    }

    public WillForgeBlockEntity getForgeEntity() {
        return forgeX == Integer.MAX_VALUE || level == null ? null : (WillForgeBlockEntity) level.getBlockEntity(new BlockPos(forgeX, forgeY, forgeZ));
    }

    public boolean hasForge() {
        return forgeX != Integer.MAX_VALUE;
    }

    public void runSupportsCheck() {
        if (level == null || level.isClientSide() || forgeX == Integer.MAX_VALUE) return;
        int thisBlockX = getBlockPos().getX(), thisBlockY = getBlockPos().getY(), thisBlockZ = getBlockPos().getZ(), supportBlocksCount = 0;

        for (int x = thisBlockX - 1; x <= thisBlockX + 1; x++) {
           for (int y = thisBlockY - 1; y <= thisBlockY + 1; y++) {
               for (int z = thisBlockZ - 1; z <= thisBlockZ + 1; z++) {
                   if ((x != thisBlockX || y != thisBlockY || z != thisBlockZ) && level.getBlockState(new BlockPos(x, y, z)).getBlock() instanceof WillForgeSupportBlock) {
                       supportBlocksCount++;
                   }
               }
           }
        }

        if (getForgeEntity() != null && supportBlocksCount == 25) {
            getForgeEntity().setEnabledState(true);
        } else if (getForgeEntity() == null) {
        } else if (supportBlocksCount != 25) {
            getForgeEntity().setEnabledState(false);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);

        tag.putInt("will_forge_core.forge_x", forgeX);
        tag.putInt("will_forge_core.forge_y", forgeY);
        tag.putInt("will_forge_core.forge_z", forgeZ);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);

        forgeX = tag.getInt("will_forge_core.forge_x");
        forgeY =tag.getInt("will_forge_core.forge_y");
        forgeZ = tag.getInt("will_forge_core.forge_z");

        if (level != null && !level.isClientSide() && !(level.getBlockEntity(new BlockPos(forgeX, forgeY, forgeZ)) instanceof WillForgeBlockEntity)) {
            forgeX = Integer.MAX_VALUE;
        }
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onLoad() {
        super.onLoad();

        runSupportsCheck();
    }
}
