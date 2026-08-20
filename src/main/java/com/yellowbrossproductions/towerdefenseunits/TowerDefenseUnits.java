package com.yellowbrossproductions.towerdefenseunits;

import com.mojang.logging.LogUtils;
import com.yellowbrossproductions.towerdefenseunits.config.Config;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.slf4j.Logger;

@Mod(TowerDefenseUnits.MODID)
public class TowerDefenseUnits {
    public static final String MODID = "towerdefenseunits";
    public static final Logger LOGGER = LogUtils.getLogger();

    public TowerDefenseUnits()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        Config.loadConfig(Config.client_config, FMLPaths.CONFIGDIR.get().resolve("towerdefenseunits-client.toml").toString());
        Config.loadConfig(Config.common_config, FMLPaths.CONFIGDIR.get().resolve("towerdefenseunits-common.toml").toString());

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }
}
