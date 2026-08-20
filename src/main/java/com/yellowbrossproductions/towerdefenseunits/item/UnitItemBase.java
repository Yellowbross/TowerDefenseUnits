package com.yellowbrossproductions.towerdefenseunits.item;

import com.mojang.blaze3d.platform.InputConstants;
import com.yellowbrossproductions.towerdefenseunits.entities.AbstractUnit;
import com.yellowbrossproductions.towerdefenseunits.entities.units.Turret;
import com.yellowbrossproductions.towerdefenseunits.init.TDUEntityTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class UnitItemBase extends Item {
    private final String name;

    public UnitItemBase(String name) {
        super(new Properties());
        this.name = name;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, level, tooltip, flagIn);
        if (!InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_LEFT_SHIFT)) {
            this.information(tooltip, true);
            tooltip.add(Component.translatable("tooltip.towerdefenseunits.shift"));
        } else {
            this.information(tooltip, false);
        }
    }

    public void information(List<Component> tooltip, boolean cutTheCrap) {
        switch (this.name) {
            case "turret" -> Turret.information(tooltip, cutTheCrap);
        }
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        BlockPos blockPos1;
        Direction direction = context.getClickedFace();
        Player player = context.getPlayer();
        BlockState blockState = level.getBlockState(blockPos);
        ItemStack itemStack = context.getItemInHand();
        if (blockState.getCollisionShape(level, blockPos).isEmpty()) {
            blockPos1 = blockPos;
        } else {
            blockPos1 = blockPos.relative(direction);
        }
        assert player != null;

        AbstractUnit entity = null;
        CompoundTag compoundNBT = player.getMainHandItem().getTagElement("unit_data");
        if (compoundNBT == null) {
            compoundNBT = new CompoundTag();
        }

        switch (this.name) {
            case "turret" -> {
                entity = TDUEntityTypes.Turret.get().create(level);
                player.playSound(SoundEvents.STONE_PLACE, 1.0F, 1.0F);
            }
        }
        if (entity != null) {
            entity.moveTo(blockPos1, 0.0F, 0.0F);
            if (itemStack.hasCustomHoverName()) entity.setCustomName(itemStack.getHoverName());
            entity.readAdditionalSaveData(compoundNBT);
            level.addFreshEntity(entity);
            entity.setUnitDirection(this.getUnitRotation(player, entity));
        }

        if (!player.isCreative()) {
            itemStack.shrink(1);
        }

        player.swing(player.getUsedItemHand());

        return InteractionResult.CONSUME;
    }

    // code borrowed from Mowzie's Mobs
    public double getAngleBetweenEntities(Entity first, Entity second) {
        return Math.atan2(second.getZ() - first.getZ(), second.getX() - first.getX()) * (180 / Math.PI) + 90;
    }

    public float getUnitRotation(Player player, AbstractUnit unit) {
        float angle = (float) getAngleBetweenEntities(unit, player) + 225;
        int direction = (int) (angle / 90) % 4 + 1;
        return (direction - 1) * 90;
    }
}
