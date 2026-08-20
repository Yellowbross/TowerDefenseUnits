package com.yellowbrossproductions.towerdefenseunits.packet;


import com.yellowbrossproductions.towerdefenseunits.TowerDefenseUnits;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

// code adapted from The Twilight Forest
public class PacketHandler {
    private static final String PROTOCOL_VERSION = "2";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            TowerDefenseUnits.prefix("channel"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    @SuppressWarnings({"UnusedAssignment", "Convert2Lambda", "Anonymous2MethodRef"})
    public static void init() {
        int id = 0;
        CHANNEL.registerMessage(id++, ParticlePacket.class, ParticlePacket::encode, ParticlePacket::new, ParticlePacket.Handler::onMessage);
    }
}
