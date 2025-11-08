package io.wdsj.normandy.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.wdsj.normandy.Constants;
import io.wdsj.normandy.config.NormandyCommonConfig;
import io.wdsj.normandy.core.KeyData;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.UUID;

public final class KeyManager {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final boolean STORE_TOKEN_IN_USER_HOME = Boolean.getBoolean("normandy.storeTokenInUserHome");

    private static Path getPlayerKeyFile(File gameDir, String serverAddress, UUID playerUuid) {
        String safeServerName = serverAddress.replace(":", "_").replace("/", "_");
        return NormandyCommonConfig.common().storeTokenInUserHome.get() || STORE_TOKEN_IN_USER_HOME ? Path.of(System.getProperty("user.home"))
                .resolve(".normandy-login")
                .resolve("servers")
                .resolve(safeServerName)
                .resolve(playerUuid.toString() + ".json") :
        gameDir.toPath()
                .resolve(".auth-token")
                .resolve("servers")
                .resolve(safeServerName)
                .resolve(playerUuid.toString() + ".json");
    }

    public static KeyPair loadKeyPair(File gameDir, String serverAddress, UUID playerUuid) {
        try {
            File keyFile = getPlayerKeyFile(gameDir, serverAddress, playerUuid).toFile();
            if (!keyFile.exists()) return null;

            try (FileReader reader = new FileReader(keyFile)) {
                KeyData data = GSON.fromJson(reader, KeyData.class);
                PrivateKey privateKey = CryptoUtils.stringToPrivateKey(data.privateKey());
                PublicKey publicKey = CryptoUtils.stringToPublicKey(data.publicKey());
                return new KeyPair(publicKey, privateKey);
            }
        } catch (Exception e) {
            Constants.LOGGER.error("Failed to load key pair.", e);
            return null;
        }
    }

    public static void saveKeyPair(File gameDir, String serverAddress, UUID playerUuid, KeyPair keyPair) throws IOException {
        Path keyFilePath = getPlayerKeyFile(gameDir, serverAddress, playerUuid);
        keyFilePath.getParent().toFile().mkdirs();

        KeyData data = new KeyData(
                CryptoUtils.keyToString(keyPair.getPrivate()),
                CryptoUtils.keyToString(keyPair.getPublic())
        );

        try (FileWriter writer = new FileWriter(keyFilePath.toFile())) {
            GSON.toJson(data, writer);
        }
    }
}