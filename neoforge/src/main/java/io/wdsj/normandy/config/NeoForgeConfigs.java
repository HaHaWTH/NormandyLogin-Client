package io.wdsj.normandy.config;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;

import static io.wdsj.normandy.config.NormandyCommonConfig.COMMON;

public class NeoForgeConfigs {
    public static void register(ModContainer container) {
        container.registerConfig(ModConfig.Type.CLIENT, COMMON.getRight());
    }
}
