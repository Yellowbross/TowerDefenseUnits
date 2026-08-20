package com.yellowbrossproductions.towerdefenseunits.entities;

import com.yellowbrossproductions.towerdefenseunits.client.model.animation.ICanBeAnimated;
import com.yellowbrossproductions.towerdefenseunits.config.TowerDefenseConfig;
import com.yellowbrossproductions.towerdefenseunits.init.TDUSoundEvents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidType;

import java.util.function.Predicate;

public class AbstractUnit extends AbstractGolem implements ICanBeAnimated {
    public static final Predicate<LivingEntity> CONFIG_PREDICATE = (pEntity) -> (pEntity instanceof Mob &&
            TowerDefenseConfig.unitTargetList_whiteorblack.get() == (TowerDefenseConfig.unitTargetList.get().contains(pEntity.getEncodeId()) || TowerDefenseConfig.unitTargetList.get().contains(pEntity.getType().getKey(pEntity.getType()).getNamespace())));
    protected static final EntityDataAccessor<String> ANIMATION_STATE = SynchedEntityData.defineId(AbstractUnit.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Integer> FUSION_TYPE = SynchedEntityData.defineId(AbstractUnit.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> UNIT_DIRECTION = SynchedEntityData.defineId(AbstractUnit.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Integer> USING_ULTRA_TICKS = SynchedEntityData.defineId(AbstractUnit.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> FLASH_TICKS = SynchedEntityData.defineId(AbstractUnit.class, EntityDataSerializers.INT);
    public boolean isAllowedToMove = false;

    public AbstractUnit(EntityType<? extends AbstractGolem> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ANIMATION_STATE, "none");
        this.entityData.define(FUSION_TYPE, 0);
        this.entityData.define(UNIT_DIRECTION, 0.0F);
        this.entityData.define(USING_ULTRA_TICKS, 0);
        this.entityData.define(FLASH_TICKS, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("FusionType", this.getFusionType());
        pCompound.putFloat("UnitDirection", this.getUnitDirection());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setFusionType(pCompound.getInt("FusionType"));
        this.setUnitDirection(pCompound.getInt("UnitDirection"));
    }

    public float getUnitDirection() {
        return this.entityData.get(UNIT_DIRECTION);
    }

    public void setUnitDirection(float pUnitDirection) {
        this.entityData.set(UNIT_DIRECTION, pUnitDirection);
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (this.getUsingUltraTicks() > 0 && !pSource.is(DamageTypes.GENERIC_KILL)) {
            return false;
        }
        if (!(pSource.getEntity() instanceof Enemy) && pSource.getEntity() != null && !(pSource.getEntity() instanceof Player)) {
            return false;
        }
        return super.hurt(pSource, pAmount);
    }

    @Override
    public boolean doHurtTarget(Entity pEntity) {
        if (!(pEntity instanceof Enemy)) {
            return false;
        }
        return super.doHurtTarget(pEntity);
    }

    @Override
    public void push(Entity pEntity) {
        if (this.isAllowedToMove) super.push(pEntity);
    }

    @Override
    protected void doPush(Entity p_20971_) {
        if (this.isAllowedToMove) super.doPush(p_20971_);
    }

    @Override
    public void tick() {
        if (!this.isAllowedToMove) this.setDeltaMovement(0.0D, this.getDeltaMovement().y, 0.0D);

        if (this.getUsingUltraTicks() > 0) this.tickUltra();
        this.setFlashTicks(this.getFlashTicks() - 1);

        super.tick();

        this.setYRot(this.getUnitDirection());
        this.yBodyRot = this.getYRot();
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    protected float getJumpPower() {
        return 0.0F;
    }

    public String getAnimationState() {
        return this.entityData.get(ANIMATION_STATE);
    }

    public void setAnimationState(String input) {
        this.entityData.set(ANIMATION_STATE, input);
    }

    @Override
    public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
        return false;
    }

    @Override
    public boolean canDrownInFluidType(FluidType type) {
        return false;
    }

    public int getFusionType() {
        return this.entityData.get(FUSION_TYPE);
    }

    public void setFusionType(int i) {
        this.entityData.set(FUSION_TYPE, i);
    }

    public void updateAnimations() {}

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (ANIMATION_STATE.equals(pKey)) {
            if (this.level().isClientSide) {
                this.updateAnimations();
            }
        }

        super.onSyncedDataUpdated(pKey);
    }

    @Override
    public boolean canBeLeashed(Player player) {
        return false;
    }

    @Override
    protected InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (itemstack.is(Items.DIAMOND_BLOCK)) {
            pPlayer.swing(pHand);
            this.startUltra();
        }
        return super.mobInteract(pPlayer, pHand);
    }

    public int getUsingUltraTicks() {
        return this.entityData.get(USING_ULTRA_TICKS);
    }

    public void setUsingUltraTicks(int i) {
        if (!this.level().isClientSide) this.entityData.set(USING_ULTRA_TICKS, i);
    }

    public int getFlashTicks() {
        return this.entityData.get(FLASH_TICKS);
    }

    public void setFlashTicks(int i) {
        if (!this.level().isClientSide) this.entityData.set(FLASH_TICKS, i);
    }

    public void startUltra() {
        this.playSound(TDUSoundEvents.ULTRA.get(), 2.0F, 1.0F);
        this.setFlashTicks(15);
        this.setUsingUltraTicks(1);
    }

    public void tickUltra() {
        this.setUsingUltraTicks(this.getUsingUltraTicks() + 1);
    }
}
