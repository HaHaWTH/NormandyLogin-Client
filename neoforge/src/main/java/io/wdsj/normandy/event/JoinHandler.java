package io.wdsj.normandy.event;

import io.wdsj.normandy.Constants;
import io.wdsj.normandy.network.c2s.C2STokenHandshakePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;

@EventBusSubscriber(modid = Constants.MOD_ID)
public class JoinHandler {
    @SubscribeEvent
    public static void onJoin(ClientPlayerNetworkEvent.LoggingIn event) {
        var client = Minecraft.getInstance();
        if (client.isLocalServer()) return;
        client.execute(() -> {
            ClientPacketListener connection = client.getConnection();
            if (connection != null) {
                connection.send(new C2STokenHandshakePacket(Constants.PROTOCOL_VERSION));
                Constants.LOGGER.info("Sent token handshake packet");
            }
        });
    }
}
