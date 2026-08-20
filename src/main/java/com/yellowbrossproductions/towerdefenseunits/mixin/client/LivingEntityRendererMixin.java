package com.yellowbrossproductions.towerdefenseunits.mixin.client;

import com.yellowbrossproductions.towerdefenseunits.init.TDUEffects;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin {
    @Inject(method = "isShaking(Lnet/minecraft/world/entity/LivingEntity;)Z", at = @At("HEAD"), cancellable = true)
    private void onIsShaking(LivingEntity pEntity, CallbackInfoReturnable<Boolean> cir) {
        if (pEntity.hasEffect(TDUEffects.SPICY.get())) {
            cir.setReturnValue(true);
        }
    }
}