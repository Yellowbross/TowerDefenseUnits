package com.yellowbrossproductions.towerdefenseunits.client.render.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class RenderUtil {
    public static void translatePoseStackToPart(HierarchicalModel<? extends Entity> model, PoseStack poseStack, String name) {
        model.getAnyDescendantWithName(name).ifPresent(part -> {
            List<ModelPart> parts = new ArrayList<>();
            model.root().getAllParts().forEach(part1 -> getPart(model, parts, part1, name));
            for (int i = parts.size() - 1; i >= 0; --i) {
                ModelPart part1 = parts.get(i);
                part1.translateAndRotate(poseStack);
            }
            part.translateAndRotate(poseStack);
        });
    }

    private static void getPart(HierarchicalModel<? extends Entity> model, List<ModelPart> parts, ModelPart part, String name) {
        if (part.hasChild(name)) {
            parts.add(part);
            model.root().getAllParts().forEach(part1 -> {
                for (Map.Entry<String, ModelPart> entry : part1.children.entrySet()) {
                    if (entry.getValue() == part) {
                        getPart(model, parts, part1, entry.getKey());
                        break;
                    }
                }
            });
        }
    }

    public static float getDefaultBodyRot(Entity entity, float partialTick) {
        float bodyRot;
        float headRot;

        if (entity instanceof LivingEntity living) {
            bodyRot = Mth.rotLerp(partialTick, living.yBodyRotO, living.yBodyRot);
            headRot = Mth.rotLerp(partialTick, living.yHeadRotO, living.yHeadRot);
        } else {
            bodyRot = Mth.rotLerp(partialTick, entity.yRotO, entity.getYRot());
            headRot = bodyRot;
        }

        if (entity.isPassenger() && entity.getVehicle() instanceof LivingEntity vehicle && entity.getVehicle().shouldRiderSit()) {
            bodyRot = Mth.rotLerp(partialTick, vehicle.yBodyRotO, vehicle.yBodyRot);
            float delta = Mth.wrapDegrees(headRot - bodyRot);
            delta = Mth.clamp(delta, -85, 85);
            bodyRot = headRot - delta;
            if (delta * delta > 2500) bodyRot += delta * 0.2F;
        }

        return bodyRot;
    }

    public static void drawSprite(PoseStack poseStack, VertexConsumer builder, float transparency, float startWidth, float startHeight, float endWidth, float endHeight, float textureWidth, float textureHeight) {
        float minU = startWidth / textureWidth;
        float minV = startHeight / textureHeight;
        float maxU = endWidth / textureWidth;
        float maxV = endHeight / textureHeight;
        float alpha = 1 - transparency;
        Matrix4f pose = poseStack.last().pose();
        Matrix3f normal = poseStack.last().normal();
        drawVertex(pose, normal, builder, 1, 1, 0, minU, minV, alpha, 15);
        drawVertex(pose, normal, builder, 1, -1, 0, minU, maxV, alpha, 15);
        drawVertex(pose, normal, builder, -1, -1, 0, maxU, maxV, alpha, 15);
        drawVertex(pose, normal, builder, -1, 1, 0, maxU, minV, alpha, 15);
    }

    public static void drawVertex(Matrix4f matrix, Matrix3f normals, VertexConsumer vertexBuilder, float offsetX, float offsetY, float offsetZ, float textureX, float textureY, float alpha, int packedLightIn) {
        vertexBuilder.vertex(matrix, offsetX, offsetY, offsetZ).color(1, 1, 1, 1 * alpha).uv(textureX, textureY).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLightIn).normal(normals, 0.0F, 1.0F, 0.0F).endVertex();
    }
}
