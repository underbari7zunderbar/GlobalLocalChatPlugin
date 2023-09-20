//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package site.revanilla.globallocalchatplugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerDataHandler {
    private final JavaPlugin plugin;
    private final Gson gson;
    private final File dataFolder;
    private final File playerDataFile;
    private final HashMap<String, String> playerPrefixes;
    private final HashMap<String, String> playerSuffixes;

    public PlayerDataHandler(JavaPlugin plugin) {
        this.plugin = plugin;
        this.gson = (new GsonBuilder()).setPrettyPrinting().create();
        this.dataFolder = plugin.getDataFolder();
        this.playerDataFile = new File(this.dataFolder, "players.json");
        this.playerPrefixes = new HashMap();
        this.playerSuffixes = new HashMap();
    }

    public void loadPlayerData() {
        if (this.playerDataFile.exists()) {
            try {
                FileReader reader = new FileReader(this.playerDataFile);

                try {
                    this.playerSuffixes.clear();
                    this.playerPrefixes.clear();
                    this.playerSuffixes.putAll((Map)this.gson.fromJson(reader, HashMap.class));
                    this.playerPrefixes.putAll((Map)this.gson.fromJson(reader, HashMap.class));
                } catch (Throwable var5) {
                    try {
                        reader.close();
                    } catch (Throwable var4) {
                        var5.addSuppressed(var4);
                    }

                    throw var5;
                }

                reader.close();
            } catch (IOException var6) {
                var6.printStackTrace();
            }

        }
    }

    public void savePlayerData() {
        try {
            FileWriter writer = new FileWriter(this.playerDataFile);

            try {
                this.gson.toJson(this.playerSuffixes, writer);
                this.gson.toJson(this.playerPrefixes, writer);
            } catch (Throwable var5) {
                try {
                    writer.close();
                } catch (Throwable var4) {
                    var5.addSuppressed(var4);
                }

                throw var5;
            }

            writer.close();
        } catch (IOException var6) {
            var6.printStackTrace();
        }

    }

    public HashMap<String, String> getPlayerPrefixes() {
        return this.playerPrefixes;
    }
public HashMap<String, String> getPlayerSuffixes() {
    return this.playerSuffixes;
    }
}
