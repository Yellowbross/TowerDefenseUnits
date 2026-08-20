package com.yellowbrossproductions.towerdefenseunits.init;

import com.yellowbrossproductions.towerdefenseunits.TowerDefenseUnits;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TDUSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, TowerDefenseUnits.MOD_ID);

    public static final RegistryObject<SoundEvent> HUGE_EXPLOSION = addSoundsToRegistry("huge_explosion");
    public static final RegistryObject<SoundEvent> CHILLED = addSoundsToRegistry("chilled");

    public static final RegistryObject<SoundEvent> AGGRO_WHISTLE_BLOWN = addSoundsToRegistry("item.aggro_whistle.blown");

    public static final RegistryObject<SoundEvent> ENTITY_FORCEFIELD_IDLE = addSoundsToRegistry("entity.forcefield.idle");
    public static final RegistryObject<SoundEvent> ENTITY_FORCEFIELD_DAMAGE = addSoundsToRegistry("entity.forcefield.damage");
    public static final RegistryObject<SoundEvent> ENTITY_FORCEFIELD_EXPLODE = addSoundsToRegistry("entity.forcefield.explode");

    public static final RegistryObject<SoundEvent> ENTITY_CATADYNAMITE_PREPARE_ULTRA = addSoundsToRegistry("entity.catadynamite.prepare_ultra");
    public static final RegistryObject<SoundEvent> ENTITY_CATADYNAMITE_SHOOT = addSoundsToRegistry("entity.catadynamite.shoot");

    public static final RegistryObject<SoundEvent> ENTITY_REINFORCEDSKELETON_IDLE = addSoundsToRegistry("entity.reinforced_skeleton.idle");
    public static final RegistryObject<SoundEvent> ENTITY_REINFORCEDSKELETON_HURT = addSoundsToRegistry("entity.reinforced_skeleton.hurt");
    public static final RegistryObject<SoundEvent> ENTITY_REINFORCEDSKELETON_DEATH = addSoundsToRegistry("entity.reinforced_skeleton.death");

    public static final RegistryObject<SoundEvent> ENTITY_REINFORCEDZOMBIE_IDLE = addSoundsToRegistry("entity.reinforced_zombie.idle");
    public static final RegistryObject<SoundEvent> ENTITY_REINFORCEDZOMBIE_HURT = addSoundsToRegistry("entity.reinforced_zombie.hurt");
    public static final RegistryObject<SoundEvent> ENTITY_REINFORCEDZOMBIE_DEATH = addSoundsToRegistry("entity.reinforced_zombie.death");

    public static final RegistryObject<SoundEvent> ENTITY_REINFORCEDGIANT_IDLE = addSoundsToRegistry("entity.reinforced_giant.idle");
    public static final RegistryObject<SoundEvent> ENTITY_REINFORCEDGIANT_HURT = addSoundsToRegistry("entity.reinforced_giant.hurt");
    public static final RegistryObject<SoundEvent> ENTITY_REINFORCEDGIANT_DEATH = addSoundsToRegistry("entity.reinforced_giant.death");

    public static final RegistryObject<SoundEvent> ENTITY_REINFORCEDSKELEMAGE_IDLE = addSoundsToRegistry("entity.reinforced_skelemage.idle");
    public static final RegistryObject<SoundEvent> ENTITY_REINFORCEDSKELEMAGE_HURT = addSoundsToRegistry("entity.reinforced_skelemage.hurt");
    public static final RegistryObject<SoundEvent> ENTITY_REINFORCEDSKELEMAGE_DEATH = addSoundsToRegistry("entity.reinforced_skelemage.death");
    public static final RegistryObject<SoundEvent> ENTITY_REINFORCEDSKELEMAGE_PREPARE_SPELL = addSoundsToRegistry("entity.reinforced_skelemage.prepare_spell");
    public static final RegistryObject<SoundEvent> ENTITY_REINFORCEDSKELEMAGE_CAST_SPELL = addSoundsToRegistry("entity.reinforced_skelemage.cast_spell");

    public static final RegistryObject<SoundEvent> ENTITY_REINFORCEDZOMBMAGE_IDLE = addSoundsToRegistry("entity.reinforced_zombmage.idle");
    public static final RegistryObject<SoundEvent> ENTITY_REINFORCEDZOMBMAGE_HURT = addSoundsToRegistry("entity.reinforced_zombmage.hurt");
    public static final RegistryObject<SoundEvent> ENTITY_REINFORCEDZOMBMAGE_DEATH = addSoundsToRegistry("entity.reinforced_zombmage.death");
    public static final RegistryObject<SoundEvent> ENTITY_REINFORCEDZOMBMAGE_PREPARE_SPELL = addSoundsToRegistry("entity.reinforced_zombmage.prepare_spell");

    public static final RegistryObject<SoundEvent> ENTITY_REINFORCEDCREEPER_HURT = addSoundsToRegistry("entity.reinforced_creeper.hurt");
    public static final RegistryObject<SoundEvent> ENTITY_REINFORCEDCREEPER_DEATH = addSoundsToRegistry("entity.reinforced_creeper.death");
    public static final RegistryObject<SoundEvent> ENTITY_REINFORCEDCREEPER_PRIMED = addSoundsToRegistry("entity.reinforced_creeper.primed");

    private static RegistryObject<SoundEvent> addSoundsToRegistry(String soundId) {
        ResourceLocation name = new ResourceLocation(TowerDefenseUnits.MOD_ID, soundId);
        return SOUND_EVENTS.register(soundId, () -> SoundEvent.createVariableRangeEvent(name));
    }
}
