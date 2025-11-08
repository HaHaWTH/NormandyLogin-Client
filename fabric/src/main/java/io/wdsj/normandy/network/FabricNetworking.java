package io.wdsj.normandy.network;

import io.wdsj.normandy.network.c2s.C2SChallengeResponsePacket;
import io.wdsj.normandy.network.c2s.C2SPublicKeySharePacket;
import io.wdsj.normandy.network.c2s.C2STokenHandshakePacket;
import io.wdsj.normandy.network.s2c.S2CKeyGenRequestPacket;
import io.wdsj.normandy.network.s2c.S2CLoginChallengePacket;
import io.wdsj.normandy.network.s2c.S2CTokenHandshakeAckPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class FabricNetworking {
    public static void registerPackets() {
        PayloadTypeRegistry.playC2S().register(C2SChallengeResponsePacket.TYPE, C2SChallengeResponsePacket.STREAM_CODEC);
        PayloadTypeRegistry.playC2S().register(C2SPublicKeySharePacket.TYPE, C2SPublicKeySharePacket.STREAM_CODEC);
        PayloadTypeRegistry.playC2S().register(C2STokenHandshakePacket.TYPE, C2STokenHandshakePacket.STREAM_CODEC);

        PayloadTypeRegistry.playS2C().register(S2CLoginChallengePacket.TYPE, S2CLoginChallengePacket.STREAM_CODEC);
        PayloadTypeRegistry.playS2C().register(S2CKeyGenRequestPacket.TYPE, S2CKeyGenRequestPacket.STREAM_CODEC);
        PayloadTypeRegistry.playS2C().register(S2CTokenHandshakeAckPacket.TYPE, S2CTokenHandshakeAckPacket.STREAM_CODEC);

        ClientPlayNetworking.registerGlobalReceiver(S2CLoginChallengePacket.TYPE, CommonClientPacketHandler::handleLoginChallenge);
        ClientPlayNetworking.registerGlobalReceiver(S2CKeyGenRequestPacket.TYPE, CommonClientPacketHandler::handleKeyGenerationRequest);
        ClientPlayNetworking.registerGlobalReceiver(S2CTokenHandshakeAckPacket.TYPE, CommonClientPacketHandler::handleHandshakeAck);
    }
}
