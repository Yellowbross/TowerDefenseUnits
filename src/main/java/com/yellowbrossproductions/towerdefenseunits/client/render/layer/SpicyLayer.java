package com.yellowbrossproductions.towerdefenseunits.client.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.yellowbrossproductions.towerdefenseunits.TowerDefenseUnits;
import com.yellowbrossproductions.towerdefenseunits.client.render.TDURenderTypes;
import com.yellowbrossproductions.towerdefenseunits.init.TDUEffects;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpicyLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    public static final ResourceLocation POISON = new ResourceLocation(TowerDefenseUnits.MOD_ID, "textures/entity/misc/layers/spicy.png");

    public SpicyLayer(RenderLayerParent renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn, T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!entitylivingbaseIn.isInvisible() && entitylivingbaseIn.hasEffect(TDUEffects.SPICY.get())) {
            EntityModel<T> entityModel = this.getParentModel();
            entityModel.prepareMobModel(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks);
            this.getParentModel().copyPropertiesTo(entityModel);
            VertexConsumer vertexConsumer = bufferIn.getBuffer(TDURenderTypes.getMask(POISON));
            entityModel.setupAnim(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            entityModel.renderToBuffer(poseStack, vertexConsumer, packedLightIn, OverlayTexture.NO_OVERLAY,
                    1.0F, 1.0F, 1.0F, 0.25F);
        }
    }
}
