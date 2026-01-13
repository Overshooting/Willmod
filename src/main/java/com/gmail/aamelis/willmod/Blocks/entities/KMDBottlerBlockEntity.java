package com.gmail.aamelis.willmod.Blocks.entities;

import com.gmail.aamelis.willmod.Items.Foods.KMD;
import com.gmail.aamelis.willmod.Registries.BlockEntitiesInit;
import com.gmail.aamelis.willmod.Screens.KMDBottlerMenu;
import com.gmail.aamelis.willmod.Screens.WillForgeMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class KMDBottlerBlockEntity extends BlockEntity implements MenuProvider {

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


    public KMDBottlerBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntitiesInit.KMD_BOTTLER_BLOCK_ENTITY.get(), pos, blockState);
    }


    @Override
    public Component getDisplayName() {
        return Component.translatable("block.willmod.kmd_bottler_block");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new KMDBottlerMenu(i, inventory, this);
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemInventory.getSlots());
        for (int i = 0; i < itemInventory.getSlots(); i++) {
            inventory.setItem(i, itemInventory.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        if (!level.isClientSide() && hasRecipe()) {
            addItem(itemInventory.getStackInSlot(INPUT_SLOT), itemInventory.getStackInSlot(OUTPUT_SLOT));
        }
    }

    private void addItem(ItemStack inputItem, ItemStack outputItem) {
        if (!((KMD) outputItem.getItem()).isFull(outputItem)) {
            itemInventory.setStackInSlot(INPUT_SLOT, new ItemStack(inputItem.getItem(), inputItem.getCount() - 1));
            ((KMD)outputItem.getItem()).incrementFoodCount(outputItem);
        }
    }

    private boolean hasRecipe() {
        return false;
    }
}
