package com.gmail.aamelis.willmod.Entities.Mobs.WIP;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class WillSoul extends Zombie {

    public WillSoul(EntityType<? extends Zombie> pEntityType, Level p_34274_) {
        super(pEntityType, p_34274_);
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 200)
                .add(Attributes.MOVEMENT_SPEED, 0.5)
                .add(Attributes.ATTACK_SPEED, 0.5)
                .add(Attributes.ATTACK_DAMAGE, 20)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.1)
                .add(Attributes.ARMOR, 0.1)
                .add(Attributes.ARMOR_TOUGHNESS, 0.5)
                .add(Attributes.FOLLOW_RANGE, 400)
                .add(Attributes.ATTACK_KNOCKBACK, 1)
                .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE, 0);
    }

}
