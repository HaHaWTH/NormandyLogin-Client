package io.wdsj.normandy.network.s2c;

import io.wdsj.normandy.network.ModPackets;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

public record S2CLoginChallengePacket(boolean useUUID, long uuidMin, long uuidMax, String challenge) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<S2CLoginChallengePacket> TYPE = new CustomPacketPayload.Type<>(ModPackets.S2C_LOGIN_CHALLENGE);
    public static final StreamCodec<FriendlyByteBuf, S2CLoginChallengePacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, S2CLoginChallengePacket::useUUID,
            ByteBufCodecs.LONG, S2CLoginChallengePacket::uuidMin,
            ByteBufCodecs.LONG, S2CLoginChallengePacket::uuidMax,
            ByteBufCodecs.STRING_UTF8, S2CLoginChallengePacket::challenge,
            S2CLoginChallengePacket::new
    );

    @Override
    public @NotNull Type<S2CLoginChallengePacket> type() {
        return TYPE;
    }
}
