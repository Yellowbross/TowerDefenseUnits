package com.yellowbrossproductions.towerdefenseunits.entities.projectiles;

import com.yellowbrossproductions.towerdefenseunits.init.TDUEntityTypes;
import com.yellowbrossproductions.towerdefenseunits.util.UnitExplosion;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
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

    @Override
    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        if (this.explosive) {
            this.explode();
        }
    }

    private void explode() {
        if (this.level().isClientSide) return;

        double range = 4;
        for (Direction direction : Direction.values()) {
            UnitExplosion.create(this.level(), this.getOwner(),
                    this.getX() + direction.getStepX() * range,
                    this.getY() + direction.getStepY() * range,
                    this.getZ() + direction.getStepZ() * range,
                    2.5f,
                    false);
        }
        this.discard();
    }
}
