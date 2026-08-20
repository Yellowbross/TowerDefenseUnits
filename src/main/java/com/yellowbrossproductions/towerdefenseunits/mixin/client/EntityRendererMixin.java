package com.yellowbrossproductions.towerdefenseunits.mixin.client;

import com.yellowbrossproductions.towerdefenseunits.init.TDUEffects;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin {
    @Unique
    private final RandomSource random = RandomSource.create();

    @Inject(method = "getRenderOffset(Lnet/minecraft/world/entity/Entity;F)Lnet/minecraft/world/phys/Vec3;", at = @At("RETURN"), cancellable = true)
    private void onGetRenderOffset(Entity pEntity, float pPartialTicks, CallbackInfoReturnable<Vec3> cir) {
        if (pEntity instanceof LivingEntity living && living.getHealth() > 0) {
            Vec3 originalOffset = cir.getReturnValue();
            Vec3 customOffset = new Vec3(
                    this.random.nextGaussian() * 0.03D,
                    0.0D,
                    this.random.nextGaussian() * 0.03D);
            if (living.hasEffect(TDUEffects.SPICY.get()) && originalOffset != null) cir.setReturnValue(originalOffset.add(customOffset));
        }
    }
}
