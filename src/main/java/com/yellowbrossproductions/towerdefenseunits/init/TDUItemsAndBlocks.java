package com.yellowbrossproductions.towerdefenseunits.init;

import com.yellowbrossproductions.towerdefenseunits.TowerDefenseUnits;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TDUItemsAndBlocks {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TowerDefenseUnits.MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, TowerDefenseUnits.MOD_ID);

    // Blocks
    public static final RegistryObject<Block> UNIT_STATION = BLOCKS.register("unit_station",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(2.5F).sound(SoundType.METAL)));

    // Block Items
    public static final RegistryObject<BlockItem> UNIT_STATION_ITEM = ITEMS.register("unit_station",
            () -> new BlockItem(UNIT_STATION.get(), new Item.Properties()));
}
