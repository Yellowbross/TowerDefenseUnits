package com.yellowbrossproductions.towerdefenseunits.client.render.projectile;

import com.yellowbrossproductions.towerdefenseunits.entities.projectiles.UnitArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class UnitArrowRenderer extends ArrowRenderer<UnitArrow> {
    public static final ResourceLocation NORMAL_ARROW_LOCATION = new ResourceLocation("textures/entity/projectiles/arrow.png");

    public UnitArrowRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public ResourceLocation getTextureLocation(UnitArrow p_114482_) {
        return NORMAL_ARROW_LOCATION;
    }
}
