package site.revanilla.globallocalchatplugin;

import java.util.HashMap;

public class PlayerData {
    private HashMap<String, String> playerPrefixes;
    private HashMap<String, String> playerSuffixes;

    public PlayerData(HashMap<String, String> playerPrefixes, HashMap<String, String> playerSuffixes) {
        this.playerPrefixes = playerPrefixes;
        this.playerSuffixes = playerSuffixes;
    }

    public HashMap<String, String> getPlayerPrefixes() {
        return playerPrefixes;
    }

    public HashMap<String, String> getPlayerSuffixes() {
        return playerSuffixes;
    }
}
