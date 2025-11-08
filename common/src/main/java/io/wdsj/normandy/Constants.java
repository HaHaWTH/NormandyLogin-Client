package io.wdsj.normandy;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Constants {
    public static final String MOD_ID = "normandylogin";
    public static final String MOD_NAME = "Normandy Login";
    public static final Logger LOGGER = LoggerFactory.getLogger(Constants.MOD_NAME);
    public static final int PROTOCOL_VERSION = 1;
    public static final ExecutorService WORKER_POOL = Executors.newSingleThreadExecutor(
            new ThreadFactoryBuilder()
                    .setNameFormat("Normandy Worker Thread - %d")
                    .setDaemon(true)
                    .setPriority(Thread.NORM_PRIORITY - 1)
                    .build()
    );
    public static final ScheduledExecutorService SCHEDULED_WORKER_POOL = Executors.newSingleThreadScheduledExecutor(
            new ThreadFactoryBuilder()
                    .setNameFormat("Normandy Scheduled Worker Thread - %d")
                    .setDaemon(true)
                    .setPriority(Thread.NORM_PRIORITY - 1)
                    .build()
    );
    public static void runTaskLaterSync(Runnable task, long delayMillis) {
        SCHEDULED_WORKER_POOL.schedule(() -> Minecraft.getInstance().execute(task), delayMillis, TimeUnit.MILLISECONDS);
    }
    public static void sendClientMessage(Component msg) {
        var client = Minecraft.getInstance();
        if (client.player != null) {
            client.player.displayClientMessage(msg, false);
        }
    }
}
