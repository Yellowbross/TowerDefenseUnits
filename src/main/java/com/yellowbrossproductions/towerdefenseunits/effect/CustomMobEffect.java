package com.yellowbrossproductions.towerdefenseunits.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nullable;

// Code lent by TheDarkPeasant
public class CustomMobEffect extends MobEffect {
    public final boolean syncToClients;

    public CustomMobEffect(MobEffectCategory pCategory, int pColor, boolean pSyncToClients) {
        super(pCategory, pColor);
        this.syncToClients = pSyncToClients;
    }

    public void onAdded(MobEffectInstance pEffect, LivingEntity pAffected, @Nullable Entity pSource) {
    }

    public void onRemoved(MobEffectInstance pEffect, LivingEntity pAffected, boolean pWasForceful) {
    }
}
