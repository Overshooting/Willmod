package com.gmail.aamelis.willmod.Blocks.entities;

import com.gmail.aamelis.willmod.Registries.BlockEntitiesInit;
import com.gmail.aamelis.willmod.Registries.ItemsInit;
import com.gmail.aamelis.willmod.Screens.WillForgeMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class WillForgeBlockEntity extends BlockEntity implements MenuProvider {
    public final ItemStackHandler itemInventory =  new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (level != null && !level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 100;
    private boolean isEnabled = false;



    public WillForgeBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntitiesInit.WILL_FORGE_BLOCK_ENTITY.get(), pos, blockState);
        data = new ContainerData() {
            @Override
            public int get(int i) {
                return switch(i) {
                    case 0 -> WillForgeBlockEntity.this.progress;
                    case 1 -> WillForgeBlockEntity.this.maxProgress;
                    case 2 -> WillForgeBlockEntity.this.encodeEnabledState();
                    default -> 0;
                };
            }

            @Override
            public void set(int i, int value) {
                switch(i) {
                    case 0: WillForgeBlockEntity.this.progress = value;
                    case 1: WillForgeBlockEntity.this.maxProgress = value;
                    case 2: WillForgeBlockEntity.this.decodeEnabledState(value);
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.willmod.will_forge_block");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new WillForgeMenu(i, inventory, this, this.data);
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemInventory.getSlots());
        for (int i = 0; i < itemInventory.getSlots(); i++) {
            inventory.setItem(i, itemInventory.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        tag.put("inventory", itemInventory.serializeNBT(registries));
        tag.putInt("will_forge.progress", progress);
        tag.putInt("will_forge.max_progress", maxProgress);
        tag.putInt("will_forge_is_enabled", encodeEnabledState());

        super.saveAdditional(tag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);

        itemInventory.deserializeNBT(registries, tag.getCompound("inventory"));
        progress = tag.getInt("will_forge.progress");
        maxProgress = tag.getInt("will_forge.max_progress");
        decodeEnabledState(tag.getInt("will_forge_is_enabled"));
    }

    protected int encodeEnabledState() {
        return isEnabled ? 1 : 0;
    }

    protected void decodeEnabledState(int i) {
        setEnabledState(i == 1);
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        if (isEnabled) {
            ItemStack demoOutput = new ItemStack(ItemsInit.WILL_FACE_BLOCK_ITEM.get());

            if (hasRecipe(demoOutput)) {
                progress++;
                setChanged(level, pos, state);

                if (progress >= maxProgress) {
                    itemInventory.extractItem(INPUT_SLOT, 1, false);
                    itemInventory.setStackInSlot(OUTPUT_SLOT, new ItemStack(demoOutput.getItem(),
                            itemInventory.getStackInSlot(OUTPUT_SLOT).getCount() + demoOutput.getCount()));

                    setChanged(level, pos, state);

                    progress = 0;
                    maxProgress = 100;
                }

            } else {
                progress = 0;
                maxProgress = 100;
            }
        }
    }

    private boolean hasRecipe(ItemStack desiredOutput) {
        int demoCraftingAmount = 1;
        return itemInventory.getStackInSlot(INPUT_SLOT).is(ItemsInit.WILL_SHARD) &&
                outputIsAvailable(desiredOutput, demoCraftingAmount);
    }

    private boolean outputIsAvailable(ItemStack desiredOutput, int craftingAmount) {
        ItemStack outputStack = itemInventory.getStackInSlot(OUTPUT_SLOT);

        return outputStack.isEmpty() || (outputStack.is(ItemsInit.WILL_FACE_BLOCK_ITEM) &&
                outputStack.getCount() + craftingAmount <= outputStack.getMaxStackSize());
    }

    public void setEnabledState(boolean state) {
        isEnabled = state;
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
