package com.yellowbrossproductions.towerdefenseunits.event;

import com.yellowbrossproductions.towerdefenseunits.TowerDefenseUnits;
import com.yellowbrossproductions.towerdefenseunits.client.model.units.TurretModel;
import com.yellowbrossproductions.towerdefenseunits.client.render.projectile.UnitArrowRenderer;
import com.yellowbrossproductions.towerdefenseunits.client.render.layer.SpicyLayer;
import com.yellowbrossproductions.towerdefenseunits.client.render.units.TurretRenderer;
import com.yellowbrossproductions.towerdefenseunits.init.TDUEntityTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
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

        @SubscribeEvent
        public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(TurretModel.LAYER_LOCATION, TurretModel::createBodyLayer);
        }

        @SubscribeEvent
        public static void onClientSetup(EntityRenderersEvent.RegisterRenderers event) {
            // Units
            event.registerEntityRenderer(TDUEntityTypes.Turret.get(), TurretRenderer::new);

            // Projectiles
            event.registerEntityRenderer(TDUEntityTypes.UnitArrow.get(), UnitArrowRenderer::new);
        }
    }


}
