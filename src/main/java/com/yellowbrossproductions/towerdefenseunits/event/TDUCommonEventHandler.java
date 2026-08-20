package com.yellowbrossproductions.towerdefenseunits.event;

import com.yellowbrossproductions.towerdefenseunits.TowerDefenseUnits;
import com.yellowbrossproductions.towerdefenseunits.entities.units.Turret;
import com.yellowbrossproductions.towerdefenseunits.init.TDUEffects;
import com.yellowbrossproductions.towerdefenseunits.init.TDUEntityTypes;
import com.yellowbrossproductions.towerdefenseunits.packet.PacketHandler;
import com.yellowbrossproductions.towerdefenseunits.util.EntityUtil;
import com.yellowbrossproductions.towerdefenseunits.util.RandomTradeBuilder;
import com.yellowbrossproductions.towerdefenseunits.init.TDUVillagerProfessions;
import com.yellowbrossproductions.towerdefenseunits.util.UnitExplosion;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = TowerDefenseUnits.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TDUCommonEventHandler {

    @Mod.EventBusSubscriber(modid = TowerDefenseUnits.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class CommonModEvents {
        @SubscribeEvent
        public static void onCommonSetup(FMLCommonSetupEvent event) {
            PacketHandler.init();
        }

        @SubscribeEvent
        public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
            SpawnPlacements.register(TDUEntityTypes.Turret.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PathfinderMob::checkMobSpawnRules);
        }

        @SubscribeEvent
        public static void createAttributes(EntityAttributeCreationEvent event) {
            event.put(TDUEntityTypes.Turret.get(), Turret.createAttributes().build());
        }
    }

    @SubscribeEvent
    public static void addVillagerTrades(VillagerTradesEvent event) {
        if (event.getType() == TDUVillagerProfessions.UNITOLOGIST.get()) {
            event.getTrades().get(1).add(new RandomTradeBuilder(12, 2, 1.0F).setPrice(Items.COBBLESTONE, 3, 5).setForSale(Items.EMERALD, 1, 1).build());
            event.getTrades().get(2).add(new RandomTradeBuilder(12, 1, 1.0F).setPrice(Items.IRON_PICKAXE, 1, 1).setForSale(Items.EMERALD, 4, 6).build());
        }
    }

    @SubscribeEvent
    public static void hurtEffects(LivingHurtEvent event) {
        LivingEntity mob = event.getEntity();
        Entity source = event.getSource().getEntity();
        if (source instanceof LivingEntity mob2) {
            if (mob2.hasEffect(TDUEffects.SPICY.get()) && !mob.fireImmune() && !(mob instanceof Player)) {
                MobEffectInstance mobeffectinstance = new MobEffectInstance(TDUEffects.SPICY.get(), 160, 0, false, false, false);
                mob.addEffect(mobeffectinstance);
            }
        }
    }

    @SubscribeEvent
    public static void effects1(LivingEvent.LivingTickEvent event) {
        LivingEntity mob = event.getEntity();
        if (mob.hasEffect(TDUEffects.SPICY.get())) {
            if (mob.deathTime > 0) {
                mob.deathTime = 19;
                if (!mob.level().isClientSide) {
                    UnitExplosion.create(mob, 2.0F);
                }
            }
        }
    }
}
