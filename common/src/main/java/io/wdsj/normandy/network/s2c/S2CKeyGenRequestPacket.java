package io.wdsj.normandy.network.s2c;

import io.wdsj.normandy.network.ModPackets;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

/**
 * Sent by the server to request the client to generate a key pair.
 * UUID may not be provided if the server does not want to use UUIDs.
 */
public record S2CKeyGenRequestPacket(boolean useUUID, long uuidMin, long uuidMax) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<S2CKeyGenRequestPacket> TYPE = new CustomPacketPayload.Type<>(ModPackets.S2C_KEY_GENERATION_REQUEST);
    public static final StreamCodec<FriendlyByteBuf, S2CKeyGenRequestPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, S2CKeyGenRequestPacket::useUUID,
            ByteBufCodecs.LONG, S2CKeyGenRequestPacket::uuidMin,
            ByteBufCodecs.LONG, S2CKeyGenRequestPacket::uuidMax,
            S2CKeyGenRequestPacket::new
    );
    @Override
    public @NotNull Type<S2CKeyGenRequestPacket> type() {
        return TYPE;
    }
}
