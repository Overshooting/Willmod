package com.gmail.aamelis.willmod.Blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

public class WillFaceBlock extends Block {

    public WillFaceBlock() {
        super(BlockBehaviour.Properties.of()
                .strength(0.5f)
                .mapColor(MapColor.STONE)
                .sound(SoundType.GLASS)
                .friction(0.98f));
    }

}
