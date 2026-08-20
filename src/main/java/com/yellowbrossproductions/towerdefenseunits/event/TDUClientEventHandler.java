package com.yellowbrossproductions.towerdefenseunits.event;

import com.yellowbrossproductions.towerdefenseunits.TowerDefenseUnits;
import com.yellowbrossproductions.towerdefenseunits.client.render.layer.SpicyLayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TowerDefenseUnits.MOD_ID, value = Dist.CLIENT)
public class TDUClientEventHandler {

    @Mod.EventBusSubscriber(modid = TowerDefenseUnits.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void addLayers(EntityRenderersEvent.AddLayers event) {
            Minecraft.getInstance().getEntityRenderDispatcher().renderers.values().forEach(renderer -> {
                if (renderer instanceof LivingEntityRenderer) {
                    ((LivingEntityRenderer<?, ?>) renderer).addLayer(new SpicyLayer<>((LivingEntityRenderer<?, ?>) renderer));
                }
            });
        }
    }


}
