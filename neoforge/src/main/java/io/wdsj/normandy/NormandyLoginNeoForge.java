package io.wdsj.normandy;

import io.wdsj.normandy.config.NeoForgeConfigs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class NormandyLoginNeoForge {
    private final ModContainer modContainer;
    public NormandyLoginNeoForge(IEventBus eventBus, ModContainer modContainer) {
        this.modContainer = modContainer;
        NeoForgeConfigs.register(modContainer);
    }
}