package io.wdsj.normandy.network.c2s;

import io.wdsj.normandy.network.ModPackets;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

public record C2SPublicKeySharePacket(String publicKey) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<C2SPublicKeySharePacket> TYPE = new CustomPacketPayload.Type<>(ModPackets.C2S_PUBLIC_KEY_SHARE);
    public static final StreamCodec<FriendlyByteBuf, C2SPublicKeySharePacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, C2SPublicKeySharePacket::publicKey,
            C2SPublicKeySharePacket::new
    );

    @Override
    public @NotNull Type<C2SPublicKeySharePacket> type() {
        return TYPE;
    }
}
