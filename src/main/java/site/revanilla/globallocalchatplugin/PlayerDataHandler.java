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
    private final File playerSuffixFile;
    private final File playerPrefixFile;
    private final HashMap<String, String> playerPrefixes;
    private final HashMap<String, String> playerSuffixes;

    public PlayerDataHandler(JavaPlugin plugin) {
        this.plugin = plugin;
        this.gson = (new GsonBuilder()).setPrettyPrinting().create();
        this.dataFolder = plugin.getDataFolder();
        this.playerSuffixFile = new File(this.dataFolder, "suffixes.json");
        this.playerPrefixFile = new File(this.dataFolder, "prefixes.json");
        this.playerPrefixes = new HashMap();
        this.playerSuffixes = new HashMap();
    }

    public void loadPlayerPrefixes() {
        if (this.playerPrefixFile.exists()) {
            try {
                FileReader reader = new FileReader(this.playerPrefixFile);

                try {
                    this.playerPrefixes.clear();
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

    public void loadPlayerSuffixes() {
        if (this.playerSuffixFile.exists()) {
            try {
                FileReader reader = new FileReader(this.playerSuffixFile);

                try {
                    this.playerSuffixes.clear();
                    this.playerSuffixes.putAll((Map)this.gson.fromJson(reader, HashMap.class));
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


    public void savePlayerPrefixes() {
        try {
            FileWriter writer = new FileWriter(this.playerPrefixFile);

            try {
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

    public void savePlayerSuffixes() {
        try {
            FileWriter writer = new FileWriter(this.playerSuffixFile);

            try {
                this.gson.toJson(this.playerSuffixes, writer);
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

    public HashMap<String, String> getPlayerSuffixes() {
        return this.playerSuffixes;
    }
    public HashMap<String, String> getPlayerPrefixes() {
        return this.playerPrefixes;
    }
}
