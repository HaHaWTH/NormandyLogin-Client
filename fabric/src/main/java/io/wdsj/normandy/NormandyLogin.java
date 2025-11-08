package io.wdsj.normandy;

import io.wdsj.normandy.config.FabricConfigs;
import io.wdsj.normandy.network.FabricNetworking;
import io.wdsj.normandy.network.ModPackets;
import io.wdsj.normandy.network.c2s.C2STokenHandshakePacket;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;

public class NormandyLogin implements ModInitializer {
    @Override
    public void onInitialize() {
        FabricConfigs.register();
        FabricNetworking.registerPackets();

        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            if (client.isLocalServer()) return;
            ModPackets.sendToServer(new C2STokenHandshakePacket(Constants.PROTOCOL_VERSION));
            Constants.LOGGER.info("Sent token handshake packet");
        });
    }
}
