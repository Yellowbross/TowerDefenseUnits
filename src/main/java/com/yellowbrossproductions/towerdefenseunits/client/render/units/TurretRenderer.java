package com.yellowbrossproductions.towerdefenseunits.client.render.units;

import com.mojang.blaze3d.vertex.PoseStack;
import com.yellowbrossproductions.towerdefenseunits.TowerDefenseUnits;
import com.yellowbrossproductions.towerdefenseunits.client.model.units.TurretModel;
import com.yellowbrossproductions.towerdefenseunits.client.render.AbstractUnitRenderer;
import com.yellowbrossproductions.towerdefenseunits.entities.units.Turret;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TurretRenderer extends AbstractUnitRenderer<Turret, TurretModel<Turret>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(TowerDefenseUnits.MOD_ID, "textures/entity/units/offense/turret.png");

    public TurretRenderer(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new TurretModel<>(renderManagerIn.bakeLayer(TurretModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    protected void scale(Turret pLivingEntity, PoseStack pPoseStack, float pPartialTickTime) {
        pPoseStack.scale(0.6f, 0.6f, 0.6f);
    }

    @Override
    public ResourceLocation getTextureLocation(Turret pEntity) {
        return TEXTURE;
    }
}
