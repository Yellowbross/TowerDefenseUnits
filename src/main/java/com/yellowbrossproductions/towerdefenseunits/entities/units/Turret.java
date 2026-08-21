package com.yellowbrossproductions.towerdefenseunits.entities.units;

import com.yellowbrossproductions.towerdefenseunits.entities.AbstractUnit;
import com.yellowbrossproductions.towerdefenseunits.entities.projectiles.UnitArrow;
import com.yellowbrossproductions.towerdefenseunits.init.TDUItemsAndBlocks;
import com.yellowbrossproductions.towerdefenseunits.init.TDUSoundEvents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Turret extends AbstractUnit implements RangedAttackMob {
    private int shootAnimationTicks;
    public AnimationState shootAnimationState = new AnimationState();

    public Turret(EntityType<? extends AbstractGolem> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new RangedAttackGoal(this, 1.0D, 25, 30.0F));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Mob.class, 10, true, false, (pEntity) -> pEntity instanceof Enemy && CONFIG_PREDICATE.test(pEntity)));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.0F)
                .add(Attributes.MAX_HEALTH, 35.0D)
                .add(Attributes.ATTACK_DAMAGE, 0.0D)
                .add(Attributes.FOLLOW_RANGE, 20.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }

    @Override
    public void performRangedAttack(LivingEntity pTarget, float pVelocity) {
        this.playSound(SoundEvents.DISPENSER_LAUNCH, 1.0F, 1.0F);
        this.fireProjectile(pTarget, pVelocity, 8.0F);
        this.setAnimationState("shoot");
        this.shootAnimationTicks = 20;
    }

    public void fireProjectile(LivingEntity livingEntity, float v, float inaccuracy) {
        if (this.level().isClientSide) return;

        UnitArrow arrow = new UnitArrow(this.level(), this, this.position().add(0, this.getEyeHeight(), 0));
        double d0 = livingEntity.getX() - this.getX();
        double d1 = livingEntity.getY(0.3333333333333333D) - arrow.getY();
        double d2 = livingEntity.getZ() - this.getZ();
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);
        arrow.shoot(d0, d1 + d3 * (double) 0.2F, d2, 1.6F, inaccuracy);
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putShort("life", (short) 1100);
        arrow.readAdditionalSaveData(compoundTag);
        this.level().addFreshEntity(arrow);
    }

    @Override
    public void updateAnimations() {
        this.shootAnimationState.animateWhen(this.getAnimationState().equals("shoot"), this.tickCount);
    }

    @Override
    public void tick() {
        super.tick();

        this.shootAnimationTicks--;
        if (this.shootAnimationTicks == 0) {
            this.setAnimationState("none");
        }
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.STONE_BREAK;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.STONE_BREAK;
    }

    @Override
    public void die(DamageSource pDamageSource) {
        this.level().levelEvent(2001, this.blockPosition(), Block.getId(Blocks.STONE_SLAB.defaultBlockState()));
        this.level().levelEvent(2001, this.blockPosition(), Block.getId(Blocks.OAK_FENCE.defaultBlockState()));
        this.level().levelEvent(2001, this.blockPosition().above(), Block.getId(Blocks.DISPENSER.defaultBlockState()));
        if (!this.level().isClientSide) {
            this.remove(RemovalReason.KILLED);
        }
        super.die(pDamageSource);
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource.getEntity() instanceof Player player) {
            if (player.getMainHandItem().getItem() instanceof PickaxeItem) {
                if (this.isAlive()) {
                    ItemEntity item = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), TDUItemsAndBlocks.TURRET.get().getDefaultInstance());
                    item.setNoPickUpDelay();
                    if (this.hasCustomName()) {
                        item.getItem().setHoverName(this.getCustomName());
                    }
                    CompoundTag compoundNBT = item.getItem().getOrCreateTagElement("unit_data");
                    this.addAdditionalSaveData(compoundNBT);

                    this.level().addFreshEntity(item);
                    this.kill();
                }

                return super.hurt(pSource, pAmount);
            }
        }
        return super.hurt(pSource, pAmount);
    }

    public static void information(List<Component> tooltip, boolean cutTheCrap) {
        tooltip.add(Component.translatable("tooltip.towerdefenseunits.turret_hint"));
        if (!cutTheCrap) {
            tooltip.add(Component.translatable("tooltip.towerdefenseunits.turret_ultimate_hint"));
        }
    }

    @Override
    public void tickUltra() {
        super.tickUltra();
        for (int i = 0; i < 5; i++) {
            this.playSound(SoundEvents.DISPENSER_LAUNCH, 1.0F, this.getVoicePitch());
            if (!this.level().isClientSide) {
                UnitArrow arrow = new UnitArrow(this.level(), this, this.position().add(0, this.getEyeHeight(), 0));
                arrow.setDeltaMovement(
                        -1.0D + this.random.nextDouble() + this.random.nextDouble(),
                        3.0D,
                        -1.0D + this.random.nextDouble() + this.random.nextDouble());
                arrow.explosive = true;
                this.level().addFreshEntity(arrow);
            }
        }
        if (this.getUsingUltraTicks() > 100) {
            this.endUltra();
        }
    }
}
