package io.wdsj.normandy.network.c2s;

import io.wdsj.normandy.network.ModPackets;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

public record C2SChallengeResponsePacket(String signature) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<C2SChallengeResponsePacket> TYPE = new CustomPacketPayload.Type<>(ModPackets.C2S_CHALLENGE_RESPONSE);

    public static final StreamCodec<FriendlyByteBuf, C2SChallengeResponsePacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, C2SChallengeResponsePacket::signature,
            C2SChallengeResponsePacket::new
    );

    @Override
    public @NotNull Type<C2SChallengeResponsePacket> type() {
        return TYPE;
    }
}