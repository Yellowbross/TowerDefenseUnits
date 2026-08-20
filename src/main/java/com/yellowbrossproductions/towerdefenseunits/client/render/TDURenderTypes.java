package com.yellowbrossproductions.towerdefenseunits.client.render;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.BiFunction;

@OnlyIn(Dist.CLIENT)
public abstract class TDURenderTypes extends RenderType {

    public TDURenderTypes(String pName, VertexFormat pFormat, VertexFormat.Mode pMode, int pBufferSize, boolean pAffectsCrumbling, boolean pSortOnUpload, Runnable pSetupState, Runnable pClearState) {
        super(pName, pFormat, pMode, pBufferSize, pAffectsCrumbling, pSortOnUpload, pSetupState, pClearState);
    }

    private static final BiFunction<ResourceLocation, Boolean, RenderType> TWO_DIMENSIONAL_EFFECTS = Util.memoize((resourceLocation, compositeState) -> {
        CompositeState rendertype$compositestate = CompositeState.builder()
                .setShaderState(RenderStateShard.RENDERTYPE_EYES_SHADER)
                .setTextureState(new TextureStateShard(resourceLocation, false, false))
                .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
                .setCullState(RenderStateShard.NO_CULL)
                .setWriteMaskState(RenderStateShard.COLOR_DEPTH_WRITE)
                .setDepthTestState(RenderStateShard.LEQUAL_DEPTH_TEST)
                .createCompositeState(compositeState);
        return create("twoDimensionalEffects", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, true, true, rendertype$compositestate);
    });

    public static RenderType twoDimensionalEffects(ResourceLocation resourceLocation, boolean compositeState) {
        return TWO_DIMENSIONAL_EFFECTS.apply(resourceLocation, compositeState);
    }

    public static RenderType getMask(ResourceLocation location) {
        CompositeState rendertype = CompositeState.builder().setShaderState(RENDERTYPE_ENTITY_GLINT_DIRECT_SHADER).setTextureState(new TextureStateShard(location, true, false)).setWriteMaskState(COLOR_WRITE).setCullState(NO_CULL).setDepthTestState(EQUAL_DEPTH_TEST).setTransparencyState(GLINT_TRANSPARENCY).setTexturingState(ENTITY_GLINT_TEXTURING).createCompositeState(false);

        return create("mask",
                DefaultVertexFormat.POSITION_TEX,
                VertexFormat.Mode.QUADS,
                256,
                true,
                true,
                rendertype);
    }
}
