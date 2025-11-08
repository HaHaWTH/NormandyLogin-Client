package io.wdsj.normandy.network;

import io.wdsj.normandy.Constants;
import io.wdsj.normandy.network.c2s.C2SChallengeResponsePacket;
import io.wdsj.normandy.network.c2s.C2SPublicKeySharePacket;
import io.wdsj.normandy.network.s2c.S2CKeyGenRequestPacket;
import io.wdsj.normandy.network.s2c.S2CLoginChallengePacket;
import io.wdsj.normandy.network.s2c.S2CTokenHandshakeAckPacket;
import io.wdsj.normandy.util.CryptoUtils;
import io.wdsj.normandy.util.KeyManager;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import java.security.KeyPair;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class CommonClientPacketHandler {
    public static void handleLoginChallenge(S2CLoginChallengePacket payload, Object... ignored) {
        String challenge = payload.challenge();
        var client = Minecraft.getInstance();
        client.execute(() -> {
            Constants.LOGGER.info("Received login challenge from server.");
            Optional<String> serverAddress = Optional.ofNullable(client.getCurrentServer()).map(s -> s.ip);
            if (serverAddress.isEmpty() && !payload.useUUID()) {
                Constants.LOGGER.error("Received key generation request but no server address found!");
                return;
            }
            UUID uuid = Objects.requireNonNull(client.player).getUUID();
            CompletableFuture.supplyAsync(() -> {
                try {
                    KeyPair keyPair = KeyManager.loadKeyPair(client.gameDirectory, payload.useUUID() ? new UUID(payload.uuidMax(), payload.uuidMin()).toString() : serverAddress.get(), uuid);
                    if (keyPair == null) {
                        Constants.sendClientMessage(Component.literal("Received challenge but no key found!"));
                        return null;
                    }
                    Constants.LOGGER.info("Signed challenge and sent response.");
                    return CryptoUtils.sign(challenge, keyPair.getPrivate());
                } catch (Exception e) {
                    Constants.LOGGER.error("Failed to sign challenge.", e);
                }
                return null;
            }, Constants.WORKER_POOL).thenAccept(signature -> {
                if (signature != null) {
                    ModPackets.sendToServer(new C2SChallengeResponsePacket(signature));
                }
            });
        });
    }

    public static void handleKeyGenerationRequest(S2CKeyGenRequestPacket packet, Object... ignored) {
        var client = Minecraft.getInstance();
        client.execute(() -> {
            Constants.LOGGER.info("Received key generation request from server.");
            boolean useUUID = packet.useUUID();
            Optional<String> serverAddress = Optional.ofNullable(client.getCurrentServer()).map(s -> s.ip);
            if (serverAddress.isEmpty() && !useUUID) {
                Constants.LOGGER.error("Received key generation request but no server address found!");
                return;
            }
            UUID playerUuid = Objects.requireNonNull(client.player).getUUID();

            CompletableFuture.supplyAsync(() -> {
                try {
                    KeyPair keyPair = CryptoUtils.generateRsaKeyPair();
                    KeyManager.saveKeyPair(client.gameDirectory, useUUID ? new UUID(packet.uuidMax(), packet.uuidMin()).toString() : serverAddress.get(), playerUuid, keyPair);

                    return CryptoUtils.keyToString(keyPair.getPublic());
                } catch (Exception ignoredEx) {
                }
                return null;
            }, Constants.WORKER_POOL).thenAccept(publicKeyStr -> {
                if (publicKeyStr == null) {
                    Constants.LOGGER.error("Failed to generate key pair!");
                    return;
                }
                ModPackets.sendToServer(new C2SPublicKeySharePacket(publicKeyStr));
                Constants.sendClientMessage(Component.empty()
                        .append(Component.literal("New key pair generated. Public key sent to server.")
                                .withStyle(ChatFormatting.GREEN)));
            });
        });
    }

    public static void handleHandshakeAck(S2CTokenHandshakeAckPacket packet, Object... ignored) {
        var client = Minecraft.getInstance();
        client.execute(() -> {
            Constants.LOGGER.info("Received handshake ack from server.");
            if (Constants.PROTOCOL_VERSION != packet.serverProtocolVersion()) {
                Constants.sendClientMessage(Component.literal("The server is using a different protocol version (" + packet.serverProtocolVersion() + "), You're on (" + Constants.PROTOCOL_VERSION + "). Things may not work properly.")
                        .withStyle(ChatFormatting.RED));
                Constants.LOGGER.warn("The server is using a different protocol version ({}), the client is on (" + Constants.PROTOCOL_VERSION + "). Things may not work properly.", packet.serverProtocolVersion());
            }
        });
    }
}
