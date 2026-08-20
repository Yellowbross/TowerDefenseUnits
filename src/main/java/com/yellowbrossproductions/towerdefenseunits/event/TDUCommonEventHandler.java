package com.yellowbrossproductions.towerdefenseunits.event;

import com.yellowbrossproductions.towerdefenseunits.TowerDefenseUnits;
import com.yellowbrossproductions.towerdefenseunits.util.RandomTradeBuilder;
import com.yellowbrossproductions.towerdefenseunits.init.TDUVillagerProfessions;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TowerDefenseUnits.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TDUCommonEventHandler {

    @Mod.EventBusSubscriber(modid = TowerDefenseUnits.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class CommonModEvents {

    }

    @SubscribeEvent
    public static void addVillagerTrades(VillagerTradesEvent event) {
        if (event.getType() == TDUVillagerProfessions.UNITOLOGIST.get()) {
            event.getTrades().get(1).add(new RandomTradeBuilder(12, 2, 1.0F).setPrice(Items.COBBLESTONE, 3, 5).setForSale(Items.EMERALD, 1, 1).build());
            event.getTrades().get(2).add(new RandomTradeBuilder(12, 1, 1.0F).setPrice(Items.IRON_PICKAXE, 1, 1).setForSale(Items.EMERALD, 4, 6).build());
        }
    }
}
