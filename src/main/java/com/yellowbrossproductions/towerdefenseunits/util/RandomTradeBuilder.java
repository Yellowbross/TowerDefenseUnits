package com.yellowbrossproductions.towerdefenseunits.util;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;

import java.util.function.Function;

// code borrowed from https://github.com/Naetheline/KingVillager/blob/master/src/main/java/softcat/kingvillager/Profession/RandomTradeBuilder.java
public class RandomTradeBuilder {
    protected Function<RandomSource, ItemStack> price;
    protected Function<RandomSource, ItemStack> price2;
    protected Function<RandomSource, ItemStack> forSale;

    protected final int maxTrades;
    protected final int xp;
    protected final float priceMult;

    public RandomTradeBuilder(int maxTrades, int xp, float priceMult)
    {
        this.price = null;
        this.price2 = (random) -> ItemStack.EMPTY;
        this.forSale = null;
        this.maxTrades = maxTrades;
        this.xp = xp;
        this.priceMult = priceMult;
    }

    public RandomTradeBuilder setPrice(Function<RandomSource, ItemStack> price)
    {
        this.price = price;
        return this;
    }

    public RandomTradeBuilder setPrice(Item item, int min, int max)
    {
        return this.setPrice(RandomTradeBuilder.createFunction(item, min, max));
    }

    public RandomTradeBuilder setPrice2(Function<RandomSource, ItemStack> price2)
    {
        this.price2 = price2;
        return this;
    }

    public RandomTradeBuilder setPrice2(Item item, int min, int max)
    {
        return this.setPrice2(RandomTradeBuilder.createFunction(item, min, max));
    }

    public RandomTradeBuilder setForSale(Function<RandomSource, ItemStack> forSale)
    {
        this.forSale = forSale;
        return this;
    }

    public RandomTradeBuilder setForSale(Item item, int min, int max)
    {
        return this.setForSale(RandomTradeBuilder.createFunction(item, min, max));
    }

    public RandomTradeBuilder setEmeraldPrice(int emeralds)
    {
        return this.setPrice((random) -> new ItemStack(Items.EMERALD, emeralds));
    }

    public RandomTradeBuilder setEmeraldPriceFor(int emeralds, Item item, int amt)
    {
        this.setEmeraldPrice(emeralds);
        return this.setForSale((random) -> new ItemStack(item, amt));
    }

    public RandomTradeBuilder setEmeraldPriceFor(int emeralds, Item item)
    {
        return this.setEmeraldPriceFor(emeralds, item, 1);
    }

    public RandomTradeBuilder setEmeraldPrice(int min, int max)
    {
        return this.setPrice(Items.EMERALD, min, max);
    }

    public RandomTradeBuilder setEmeraldPriceFor(int min, int max, Item item, int amt)
    {
        this.setEmeraldPrice(min, max);
        return this.setForSale((random) -> new ItemStack(item, amt));
    }

    public RandomTradeBuilder setEmeraldPriceFor(int min, int max, Item item)
    {
        return this.setEmeraldPriceFor(min, max, item, 1);
    }

    public boolean canBuild()
    {
        return this.price != null && this.forSale != null;
    }

    public VillagerTrades.ItemListing build()
    {
        return (entity, random) -> !this.canBuild() ? null : new MerchantOffer(this.price.apply(random), this.price2.apply(random), this.forSale.apply(random), this.maxTrades, this.xp, this.priceMult);
    }

    public static Function<RandomSource, ItemStack> createFunction(Item item, int min, int max)
    {
        return (random) -> new ItemStack(item, random.nextInt(max) + min);
    }
}
