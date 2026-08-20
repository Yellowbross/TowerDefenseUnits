package com.yellowbrossproductions.towerdefenseunits.init;

import com.google.common.collect.ImmutableSet;
import com.yellowbrossproductions.towerdefenseunits.TowerDefenseUnits;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;
import java.util.function.Predicate;

public class TDUVillagerProfessions {

    public static final DeferredRegister<VillagerProfession> DEFERRED_REGISTER_VILLAGER = DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, TowerDefenseUnits.MOD_ID);
    public static final DeferredRegister<PoiType> DEFERRED_REGISTER_POI = DeferredRegister.create(ForgeRegistries.POI_TYPES, TowerDefenseUnits.MOD_ID);

    public static final RegistryObject<PoiType> STATION = DEFERRED_REGISTER_POI.register("unitologist", () -> new PoiType(getStation(), 1, 1));

    private static Set<BlockState> getStation() {
        return ImmutableSet.of(TDUItemsAndBlocks.UNIT_STATION.get()).stream().flatMap((freezercrap) -> {
            return freezercrap.getStateDefinition().getPossibleStates().stream();
        }).collect(ImmutableSet.toImmutableSet());
    }

    public static final RegistryObject<VillagerProfession> UNITOLOGIST = DEFERRED_REGISTER_VILLAGER.register("unitologist", () -> buildVillagerProfession());

    private static VillagerProfession buildVillagerProfession() {
        Predicate<Holder<PoiType>> heldJobSite = (poiType) -> {
            return poiType == TDUVillagerProfessions.STATION.getHolder().get();
        };
        Predicate<Holder<PoiType>> acquirableJobSite = (poiType) -> {
            return poiType == TDUVillagerProfessions.STATION.getHolder().get();
        };
        return new VillagerProfession("unitologist", heldJobSite, acquirableJobSite, ImmutableSet.of(), ImmutableSet.of(), SoundEvents.IRON_TRAPDOOR_OPEN);
    }
}
