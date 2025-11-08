package io.wdsj.normandy.network.c2s;

import io.wdsj.normandy.network.ModPackets;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

public record C2STokenHandshakePacket(int protocolVersion) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<C2STokenHandshakePacket> TYPE = new CustomPacketPayload.Type<>(ModPackets.C2S_TOKEN_HANDSHAKE);

    public static final StreamCodec<FriendlyByteBuf, C2STokenHandshakePacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, C2STokenHandshakePacket::protocolVersion,
            C2STokenHandshakePacket::new
    );

    @Override
    public @NotNull Type<C2STokenHandshakePacket> type() {
        return TYPE;
    }
}
