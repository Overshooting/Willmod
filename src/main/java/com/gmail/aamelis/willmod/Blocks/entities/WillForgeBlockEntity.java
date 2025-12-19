package com.gmail.aamelis.willmod.Blocks.entities;

import com.gmail.aamelis.willmod.Recipes.WillForgeRecipe;
import com.gmail.aamelis.willmod.Recipes.WillForgeRecipeInput;
import com.gmail.aamelis.willmod.Registries.BlockEntitiesInit;
import com.gmail.aamelis.willmod.Registries.RecipesInit;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

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
    private boolean isEnabled = false, hasCoreBlock = false;



    public WillForgeBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntitiesInit.WILL_FORGE_BLOCK_ENTITY.get(), pos, blockState);
        data = new ContainerData() {
            @Override
            public int get(int i) {
                return switch(i) {
                    case 0 -> WillForgeBlockEntity.this.progress;
                    case 1 -> WillForgeBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int i, int value) {
                switch(i) {
                    case 0: WillForgeBlockEntity.this.progress = value;
                    case 1: WillForgeBlockEntity.this.maxProgress = value;
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
        tag.putBoolean("will_forge.isEnabled", isEnabled);
        tag.putBoolean("will_forge.hasCoreBlock", hasCoreBlock);

        super.saveAdditional(tag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);

        itemInventory.deserializeNBT(registries, tag.getCompound("inventory"));
        progress = tag.getInt("will_forge.progress");
        maxProgress = tag.getInt("will_forge.max_progress");
        isEnabled = tag.getBoolean("will_forge.isEnabled");
        hasCoreBlock = isEnabled && tag.getBoolean("will_forge.hasCoreBlock");
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        if (isEnabled && !level.isClientSide()) {
            if (hasRecipe()) {
                progress++;
                setChanged(level, pos, state);

                if (progress >= maxProgress) {
                    craftItem();

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

    private void craftItem() {
        Optional<RecipeHolder<WillForgeRecipe>> recipe = getCurrentRecipe();
        ItemStack output = recipe.get().value().output();

        itemInventory.extractItem(INPUT_SLOT, 1, false);
        itemInventory.setStackInSlot(OUTPUT_SLOT, new ItemStack(output.getItem(),
                itemInventory.getStackInSlot(OUTPUT_SLOT).getCount() + output.getCount()));
    }

    private boolean hasRecipe() {
        Optional<RecipeHolder<WillForgeRecipe>> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) return false;

        ItemStack output = recipe.get().value().output();
        return outputIsAvailable(output, output.getCount());
    }

    private Optional<RecipeHolder<WillForgeRecipe>> getCurrentRecipe() {
        if (this.level != null) return this.level.getRecipeManager().getRecipeFor(RecipesInit.WILL_FORGE_TYPE.get(), new WillForgeRecipeInput(itemInventory.getStackInSlot(INPUT_SLOT)), level);
        return Optional.empty();
    }

    private boolean outputIsAvailable(ItemStack desiredOutput, int craftingAmount) {
        ItemStack outputStack = itemInventory.getStackInSlot(OUTPUT_SLOT);

        return outputStack.isEmpty() || (outputStack.is(desiredOutput.getItem()) &&
                outputStack.getCount() + craftingAmount <= outputStack.getMaxStackSize());
    }

    public void setEnabledState(boolean enabled) {
        if (level != null && !level.isClientSide() && isEnabled != enabled) {

            isEnabled = enabled;
            hasCoreBlock = isEnabled;
            setChanged(level, getBlockPos(), getBlockState());
        }
    }

    public boolean hasCoreBlock() {
        return hasCoreBlock;
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
