package com.yellowbrossproductions.towerdefenseunits.init;

import com.yellowbrossproductions.towerdefenseunits.TowerDefenseUnits;
import com.yellowbrossproductions.towerdefenseunits.effect.SpicyEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TDUEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, TowerDefenseUnits.MOD_ID);

    public static final RegistryObject<MobEffect> SPICY = EFFECTS.register("spicy", SpicyEffect::new);
}
