package io.wdsj.normandy.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class NormandyCommonConfig {
    public static final Pair<NormandyCommonConfig, ModConfigSpec> COMMON = new ModConfigSpec.Builder().configure(NormandyCommonConfig::new);

    public final ModConfigSpec.BooleanValue storeTokenInUserHome;

    public NormandyCommonConfig(ModConfigSpec.Builder builder) {
        builder.push("storage");

        this.storeTokenInUserHome = builder.define("store_token_in_user_home", false);

        builder.pop();
    }

    public static NormandyCommonConfig common() {
        return COMMON.getLeft();
    }
}