package com.yellowbrossproductions.towerdefenseunits.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.yellowbrossproductions.towerdefenseunits.TowerDefenseUnits;
import com.yellowbrossproductions.towerdefenseunits.client.render.util.RenderUtil;
import com.yellowbrossproductions.towerdefenseunits.entities.AbstractUnit;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractUnitRenderer<T extends AbstractUnit, M extends EntityModel<T>> extends MobRenderer<T, M> {
    final int ULTRA_TEXTURE_WIDTH = 64;
    final int ULTRA_TEXTURE_HEIGHT = 32;
    final int ULTRA_SPRITE_SIZE = 32;

    public AbstractUnitRenderer(EntityRendererProvider.Context pContext, M pModel, float pShadowRadius) {
        super(pContext, pModel, pShadowRadius);
    }

    @Override
    protected float getFlipDegrees(T pLivingEntity) {
        return 0.0F;
    }

    @Override
    public void render(T pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
        pPoseStack.pushPose();

        int ultra = pEntity.getUsingUltraTicks();
        if (ultra > 0) {
            VertexConsumer sprite = pBuffer.getBuffer(TDURenderTypes.twoDimensionalEffects(new ResourceLocation(TowerDefenseUnits.MOD_ID, "textures/entity/units/ultra_effects.png"), false));

            if (ultra <= 10) renderFlash1(pPoseStack, sprite, pEntity.clientFlashTicks, pPartialTicks);
            renderFlash2(pPoseStack, sprite, pEntity.tickCount, pPartialTicks);
        }

        pPoseStack.popPose();
    }

    @Override
    protected float getWhiteOverlayProgress(T pLivingEntity, float pPartialTicks) {
        int flash = pLivingEntity.clientFlashTicks;
        if (flash > 0) return Mth.clamp(((flash - pPartialTicks) / 10.0F), 0.0F, 1.0F);
        return super.getWhiteOverlayProgress(pLivingEntity, pPartialTicks);
    }

    private void renderFlash1(PoseStack poseStack, VertexConsumer buffer, float ticks, float partialTick) {
        poseStack.pushPose();
        poseStack.translate(0, 1, 0);
        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        float age = Math.max(ticks - partialTick, 0);

        float size = 2.5f;
        float calculation = Math.max(size * (age / 15.0F), 0);
        poseStack.scale(calculation, calculation, calculation);
        RenderUtil.drawSprite(poseStack, buffer, Mth.clamp(1 - (age / 15.0F), 0, 1), ULTRA_SPRITE_SIZE, 0, ULTRA_SPRITE_SIZE + ULTRA_SPRITE_SIZE, ULTRA_SPRITE_SIZE, ULTRA_TEXTURE_WIDTH, ULTRA_TEXTURE_HEIGHT);
        poseStack.popPose();
    }

    private void renderFlash2(PoseStack poseStack, VertexConsumer buffer, float ticks, float partialTick) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.XP.rotationDegrees(90.0f));
        float age = ticks + partialTick;

        float size = 1.5f;
        float mult = 15.0f;
        poseStack.scale(size, size, size);
        poseStack.translate(0, 0, -0.01);
        poseStack.mulPose(Axis.ZP.rotationDegrees(age * mult));
        RenderUtil.drawSprite(poseStack, buffer, 0, 0, 0, ULTRA_SPRITE_SIZE, ULTRA_SPRITE_SIZE, ULTRA_TEXTURE_WIDTH, ULTRA_TEXTURE_HEIGHT);
        poseStack.popPose();
    }
}
