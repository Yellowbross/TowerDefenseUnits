package com.yellowbrossproductions.towerdefenseunits;

import com.mojang.logging.LogUtils;
import com.yellowbrossproductions.towerdefenseunits.config.Config;
import com.yellowbrossproductions.towerdefenseunits.init.TDUEffects;
import com.yellowbrossproductions.towerdefenseunits.init.TDUItemsAndBlocks;
import com.yellowbrossproductions.towerdefenseunits.init.TDUSoundEvents;
import com.yellowbrossproductions.towerdefenseunits.init.TDUVillagerProfessions;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

@Mod(TowerDefenseUnits.MOD_ID)
public class TowerDefenseUnits {
    public static final String MOD_ID = "towerdefenseunits";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static TagKey<EntityType<?>> UNITS = TagKey.create(ForgeRegistries.ENTITY_TYPES.getRegistryKey(), new ResourceLocation(TowerDefenseUnits.MOD_ID, "units"));
    public static TagKey<EntityType<?>> IGNORED_TARGETS = TagKey.create(ForgeRegistries.ENTITY_TYPES.getRegistryKey(), new ResourceLocation(TowerDefenseUnits.MOD_ID, "ignored_targets"));
    public static TagKey<EntityType<?>> MEND_CANNOT_HEAL = TagKey.create(ForgeRegistries.ENTITY_TYPES.getRegistryKey(), new ResourceLocation(TowerDefenseUnits.MOD_ID, "mend_cannot_heal"));
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    public static final RegistryObject<CreativeModeTab> TDU_GROUP = CREATIVE_MODE_TABS.register("towerdefenseunitstab", () -> CreativeModeTab.builder()
            .icon(() -> TDUItemsAndBlocks.UNIT_STATION_ITEM.get().getDefaultInstance())
            .title(Component.translatable("itemGroup.towerdefenseunitstab"))
            .displayItems((parameters, output) -> {
                output.accept(TDUItemsAndBlocks.UNIT_STATION_ITEM.get());
            })
            .build());

    public TowerDefenseUnits()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        TDUItemsAndBlocks.ITEMS.register(modEventBus);
        TDUItemsAndBlocks.BLOCKS.register(modEventBus);
        TDUEffects.EFFECTS.register(modEventBus);
        TDUSoundEvents.SOUND_EVENTS.register(modEventBus);
        TDUVillagerProfessions.DEFERRED_REGISTER_POI.register(FMLJavaModLoadingContext.get().getModEventBus());
        TDUVillagerProfessions.DEFERRED_REGISTER_VILLAGER.register(FMLJavaModLoadingContext.get().getModEventBus());

        Config.loadConfig(Config.client_config, FMLPaths.CONFIGDIR.get().resolve("towerdefenseunits-client.toml").toString());
        Config.loadConfig(Config.common_config, FMLPaths.CONFIGDIR.get().resolve("towerdefenseunits-common.toml").toString());

        CREATIVE_MODE_TABS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }
}
