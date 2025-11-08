package io.wdsj.normandy.event;

import io.wdsj.normandy.Constants;
import io.wdsj.normandy.network.CommonClientPacketHandler;
import io.wdsj.normandy.network.c2s.C2SChallengeResponsePacket;
import io.wdsj.normandy.network.c2s.C2SPublicKeySharePacket;
import io.wdsj.normandy.network.c2s.C2STokenHandshakePacket;
import io.wdsj.normandy.network.s2c.S2CKeyGenRequestPacket;
import io.wdsj.normandy.network.s2c.S2CLoginChallengePacket;
import io.wdsj.normandy.network.s2c.S2CTokenHandshakeAckPacket;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = Constants.MOD_ID)
public class ClientEventHandler {
    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(Constants.MOD_ID).optional();
        registrar.playToServer(C2SPublicKeySharePacket.TYPE, C2SPublicKeySharePacket.STREAM_CODEC, (packet, context) -> {});
        registrar.playToServer(C2STokenHandshakePacket.TYPE, C2STokenHandshakePacket.STREAM_CODEC, (packet, context) -> {});
        registrar.playToServer(C2SChallengeResponsePacket.TYPE, C2SChallengeResponsePacket.STREAM_CODEC, (packet, context) -> {});

        registrar.playToClient(S2CKeyGenRequestPacket.TYPE, S2CKeyGenRequestPacket.STREAM_CODEC, CommonClientPacketHandler::handleKeyGenerationRequest);
        registrar.playToClient(S2CLoginChallengePacket.TYPE, S2CLoginChallengePacket.STREAM_CODEC, CommonClientPacketHandler::handleLoginChallenge);
        registrar.playToClient(S2CTokenHandshakeAckPacket.TYPE, S2CTokenHandshakeAckPacket.STREAM_CODEC, CommonClientPacketHandler::handleHandshakeAck);
    }

}