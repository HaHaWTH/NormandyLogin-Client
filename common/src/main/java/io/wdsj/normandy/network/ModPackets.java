package io.wdsj.normandy.network;

import io.wdsj.normandy.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public class ModPackets {
    public static final ResourceLocation C2S_TOKEN_HANDSHAKE = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "handshake");
    public static final ResourceLocation C2S_CHALLENGE_RESPONSE = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "challenge_response");
    public static final ResourceLocation C2S_PUBLIC_KEY_SHARE = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "public_key_share");

    public static final ResourceLocation S2C_LOGIN_CHALLENGE = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "login_challenge");
    public static final ResourceLocation S2C_KEY_GENERATION_REQUEST = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "key_gen_request");
    public static final ResourceLocation S2C_TOKEN_HANDSHAKE_ACK = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "handshake_ack");

    public static void sendToServer(CustomPacketPayload payload) {
        var client = Minecraft.getInstance();
        var connection = client.getConnection();
        if (connection != null) {
            connection.send(new ServerboundCustomPayloadPacket(payload));
        }
    }
}
