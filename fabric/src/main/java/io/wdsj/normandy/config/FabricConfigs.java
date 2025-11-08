package io.wdsj.normandy.config;

import fuzs.forgeconfigapiport.fabric.impl.core.ConfigRegistryImpl;
import io.wdsj.normandy.Constants;
import net.neoforged.fml.config.ModConfig;

import static io.wdsj.normandy.config.NormandyCommonConfig.COMMON;

public class FabricConfigs {
    public static void register() {
        ConfigRegistryImpl.INSTANCE.register(Constants.MOD_ID, ModConfig.Type.CLIENT, COMMON.getRight());
    }
}
