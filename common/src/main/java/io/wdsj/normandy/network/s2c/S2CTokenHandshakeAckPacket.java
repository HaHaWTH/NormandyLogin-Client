package io.wdsj.normandy.network.s2c;

import io.wdsj.normandy.network.ModPackets;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

/**
 * Sent by the server to acknowledge the client's token handshake.
 */
public record S2CTokenHandshakeAckPacket
        (int serverProtocolVersion) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<S2CTokenHandshakeAckPacket> TYPE = new CustomPacketPayload.Type<>(ModPackets.S2C_TOKEN_HANDSHAKE_ACK);
    public static final StreamCodec<FriendlyByteBuf, S2CTokenHandshakeAckPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, S2CTokenHandshakeAckPacket::serverProtocolVersion,
            S2CTokenHandshakeAckPacket::new
    );

    @Override
    public @NotNull Type<S2CTokenHandshakeAckPacket> type() {
        return TYPE;
    }
}
