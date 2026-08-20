package com.yellowbrossproductions.towerdefenseunits.entities.projectiles;

import com.yellowbrossproductions.towerdefenseunits.init.TDUEntityTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class UnitArrow extends AbstractArrow {
    public boolean explosive = false;
    public boolean chilling = false;

    public UnitArrow(EntityType<? extends AbstractArrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public UnitArrow(Level pLevel, LivingEntity pLivingEntity, Vec3 pos) {
        super(TDUEntityTypes.UnitArrow.get(), pLevel);
        this.setPos(pos);
        this.setOwner(pLivingEntity);
    }

    @Override
    protected ItemStack getPickupItem() {
        return Items.ARROW.getDefaultInstance();
    }

    @Override
    protected boolean canHitEntity(Entity pEntity) {
        return pEntity instanceof Enemy && super.canHitEntity(pEntity);
    }
}
