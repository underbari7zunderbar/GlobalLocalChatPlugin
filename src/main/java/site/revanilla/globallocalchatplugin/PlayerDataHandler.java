package site.revanilla.globallocalchatplugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class PlayerDataHandler {
    private final JavaPlugin plugin;
    private final Gson gson;
    private final File dataFolder;
    private final File playerDataFile;
    private final HashMap<String, String> playerPrefixes;

    public PlayerDataHandler(JavaPlugin plugin) {
        this.plugin = plugin;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.dataFolder = plugin.getDataFolder();
        this.playerDataFile = new File(dataFolder, "players.json");
        this.playerPrefixes = new HashMap<>();
    }

    public void loadPlayerData() {
        if (!playerDataFile.exists()) {
            return; // 파일이 없으면 아무것도 로드하지 않음
        }

        try (FileReader reader = new FileReader(playerDataFile)) {
            playerPrefixes.clear();
            playerPrefixes.putAll(gson.fromJson(reader, HashMap.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void savePlayerData() {
        try (FileWriter writer = new FileWriter(playerDataFile)) {
            gson.toJson(playerPrefixes, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, String> getPlayerPrefixes() {
        return playerPrefixes;
    }
}
