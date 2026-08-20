package com.yellowbrossproductions.towerdefenseunits.util;

import com.yellowbrossproductions.towerdefenseunits.TowerDefenseUnits;
import com.yellowbrossproductions.towerdefenseunits.packet.PacketHandler;
import com.yellowbrossproductions.towerdefenseunits.packet.ParticlePacket;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;

public class EntityUtil {
    public static void makeAParticle(Level level, ParticleOptions particleType, boolean forceAlwaysRender, Vec3 spawnPosition, Vec3 motion) {
        if (!level.isClientSide) {
            for (ServerPlayer serverPlayer : ((ServerLevel)level).players()) {
                if (serverPlayer.distanceToSqr(spawnPosition) < 4096.0D) {
                    ParticlePacket packet = new ParticlePacket();

                    packet.queueParticle(particleType, forceAlwaysRender, spawnPosition, motion);

                    PacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> serverPlayer), packet);
                }
            }
        }
    }

    public static boolean doesMobHaveIgnoredTarget(Mob attacker) {
        if (attacker.getTarget() != null) {
            return attacker.getTarget().getType().is(TowerDefenseUnits.IGNORED_TARGETS);
        } else {
            return false;
        }
    }
}
