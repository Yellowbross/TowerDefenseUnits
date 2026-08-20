package com.yellowbrossproductions.towerdefenseunits.init;

import com.yellowbrossproductions.towerdefenseunits.TowerDefenseUnits;
import com.yellowbrossproductions.towerdefenseunits.entities.projectiles.UnitArrow;
import com.yellowbrossproductions.towerdefenseunits.entities.units.Turret;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TDUEntityTypes {
    public static DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, TowerDefenseUnits.MOD_ID);

    // Units
    public static final RegistryObject<EntityType<Turret>> Turret = ENTITY_TYPES.register("turret",
            () -> EntityType.Builder.of(Turret::new, MobCategory.MISC)
                    .sized(0.6f, 1.5f)
                    .build(new ResourceLocation(TowerDefenseUnits.MOD_ID, "turret").toString()));

    // Projectiles
    public static final RegistryObject<EntityType<UnitArrow>> UnitArrow = ENTITY_TYPES.register("unit_arrow", () -> EntityType.Builder.<UnitArrow>of(UnitArrow::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build(new ResourceLocation(TowerDefenseUnits.MOD_ID, "unit_arrow").toString()));
}
