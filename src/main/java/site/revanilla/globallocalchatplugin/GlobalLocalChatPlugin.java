package site.revanilla.globallocalchatplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import site.revanilla.globallocalchatplugin.PlayerDataHandler;
import com.destroystokyo.paper.profile.PlayerProfile;

import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Logger;

public final class GlobalLocalChatPlugin extends JavaPlugin implements Listener {
    public static Logger log = Logger.getLogger("Minecraft");
    HashMap<String, Integer> map = new HashMap();
    HashMap<String, Integer> map2 = new HashMap();
    /*private HashMap<String, String> playerPrefixes = new HashMap();
    private HashMap<String, String> playerSuffixes = new HashMap();*/
    HashMap playerPrefixes = this.PlayerDataHandler.playerPrefixes;
    HashMap playerSuffixes = this.PlayerDataHandler.playerSuffixes;
    PlayerDataHandler playerDataHandler;

    public GlobalLocalChatPlugin() {
    }

    public void onEnable() {
        this.playerDataHandler = new PlayerDataHandler(this);
        this.playerDataHandler.loadPlayerPrefixes();
        this.playerDataHandler.loadPlayerSuffixes();
        FileConfiguration config = this.getConfig();
        this.saveDefaultConfig();
        this.getServer().getPluginManager().registerEvents(this, this);
        Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + "플러그인 활성화");
    }

    public void onDisable() {
        this.playerDataHandler.savePlayerPrefixes();
        this.playerDataHandler.savePlayerSuffixes();
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "플러그인 비활성화");
    }

    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "이 명령어는 플레이어만 사용할수있습니다");
            return true;
        } else {
            Player player = (Player)sender;
            String noPermission = this.getConfig().getString("pluginStuff.noPermissionMessage");
            String spyLocalOff;
            if (commandLabel.equalsIgnoreCase("local") || commandLabel.equalsIgnoreCase("l")) {
                if (sender instanceof Player) {
                    if (player.hasPermission("globallocalchat.local")) {
                        if ((Integer)this.map.get(player.getId()) != 1) {
                            spyLocalOff = this.getConfig().getString("globalLocalChat.localChat");
                            this.map.put(player.getId(), 1);
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', spyLocalOff));
                        } else {
                            spyLocalOff = this.getConfig().getString("globalLocalChat.alreadyInLocalChat");
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', spyLocalOff));
                        }

                        return true;
                    }

                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', noPermission));
                } else if (!(sender instanceof Player)) {
                    log.info("이 명령어는 플레이어만 사용할수있습니다");
                    Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "이 명령어는 플레이어만 사용할수있습니다");
                    return false;
                }
            }

            if (commandLabel.equalsIgnoreCase("global") || commandLabel.equalsIgnoreCase("g")) {
                if (sender instanceof Player) {
                    if (player.hasPermission("globallocalchat.global")) {
                        if ((Integer)this.map.get(player.getId()) != 0) {
                            spyLocalOff = this.getConfig().getString("globalLocalChat.globalChat");
                            this.map.put(player.getId(), 0);
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', spyLocalOff));
                        } else {
                            spyLocalOff = this.getConfig().getString("globalLocalChat.alreadyInGlobalChat");
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', spyLocalOff));
                        }

                        return true;
                    }

                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', noPermission));
                } else if (!(sender instanceof Player)) {
                    log.info("이 명령어는 플레이어만 사용할수있습니다");
                    Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "이 명령어는 플레이어만 사용할수있습니다");
                    return false;
                }
            }

            if (commandLabel.equalsIgnoreCase("staffc") || commandLabel.equalsIgnoreCase("sc")) {
                if (sender instanceof Player) {
                    if (player.hasPermission("globallocalchat.staff")) {
                        if ((Integer)this.map.get(player.getId()) != 2) {
                            spyLocalOff = this.getConfig().getString("globalLocalChat.staffChat");
                            this.map.put(player.getId(), 2);
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', spyLocalOff));
                        } else {
                            spyLocalOff = this.getConfig().getString("globalLocalChat.alreadyInStaffChat");
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', spyLocalOff));
                        }

                        return true;
                    }

                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', noPermission));
                } else if (!(sender instanceof Player)) {
                    log.info("이 명령어는 플레이어만 사용할수있습니다");
                    Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "이 명령어는 플레이어만 사용할수있습니다");
                    return false;
                }
            }

            if (commandLabel.equalsIgnoreCase("glcspy")) {
                if (sender instanceof Player) {
                    if (player.hasPermission("globallocalchat.localspy")) {
                        if ((Integer)this.map2.get(player.getId()) == 0) {
                            spyLocalOff = this.getConfig().getString("globalLocalChat.spyLocalOn");
                            this.map2.put(player.getId(), 1);
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', spyLocalOff));
                        } else if ((Integer)this.map2.get(player.getId()) == 1) {
                            spyLocalOff = this.getConfig().getString("globalLocalChat.spyLocalOff");
                            this.map2.put(player.getId(), 0);
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', spyLocalOff));
                        }

                        return true;
                    }

                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', noPermission));
                } else if (!(sender instanceof Player)) {
                    log.info("이 명령어는 플레이어만 사용할수있습니다");
                    Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "이 명령어는 플레이어만 사용할수있습니다");
                    return false;
                }
            }

            if (commandLabel.equalsIgnoreCase("glcreload")) {
                if (sender instanceof Player) {
                    if (player.hasPermission("globallocalchat.reload")) {
                        spyLocalOff = this.getConfig().getString("pluginStuff.reloadMessage");
                        this.reloadConfig();
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', spyLocalOff));
                        return true;
                    }

                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', noPermission));
                } else if (!(sender instanceof Player)) {
                    log.info("이 명령어는 플레이어만 사용할수있습니다");
                    Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "이 명령어는 플레이어만 사용할수있습니다");
                    return false;
                }
            }

            if (commandLabel.equalsIgnoreCase("glcl")) {
                if (args.length < 1) {
                    sender.sendMessage("사용법: /glcl <플레이어> [prefix/suffix] <텍스트>");
                    return true;
                }

                Player targetPlayer = this.getServer().getPlayer(args[0]);
                if (targetPlayer == null) {
                    sender.sendMessage("플레이어를 찾을 수 없습니다.");
                    return true;
                }

                if (args.length < 2) {
                    sender.sendMessage("사용법: /glcl <플레이어> [prefix/suffix] <텍스트>");
                    return true;
                }

                String prefixOrSuffix = args[1];
                if (args.length < 3) {
                    if (!prefixOrSuffix.equalsIgnoreCase("prefix") && !prefixOrSuffix.equalsIgnoreCase("suffix")) {
                        sender.sendMessage("올바른 prefix 또는 suffix를 지정하세요.");
                    } else {
                        sender.sendMessage(targetPlayer.getId() + "님의 " + prefixOrSuffix + "를 삭제했습니다.");
                        if (prefixOrSuffix.equalsIgnoreCase("prefix")) {
                            this.playerPrefixes.remove(targetPlayer.getId());
                        } else if (prefixOrSuffix.equalsIgnoreCase("suffix")) {
                            this.playerSuffixes.remove(targetPlayer.getId());
                        }
                    }
                } else {
                    String text = args[2];
                    String currentId;
                    if (prefixOrSuffix.equalsIgnoreCase("prefix")) {
                        text = ChatColor.translateAlternateColorCodes('&', text);
                        currentId = targetPlayer.getId();
                        currentUUID = targetPlayer.getId();
                        sender.sendMessage(currentId + "님의" + text + "를 설정했습니다.");
                        this.playerPrefixes.put(currentUUID, text);
                    } else if (prefixOrSuffix.equalsIgnoreCase("suffix")) {
                        text = ChatColor.translateAlternateColorCodes('&', text);
                        currentId = targetPlayer.getId();
                        currentUUID = targetPlayer.getId();
                        sender.sendMessage(currentId + "님의" + text + "를 설정했습니다.");
                        this.playerSuffixes.put(currentUUID, text);
                    } else {
                        sender.sendMessage("올바른 prefix 또는 suffix를 지정하세요.");
                    }
                }
            }

            return true;
        }
    }

    @EventHandler(
            priority = EventPriority.NORMAL
    )
    public void playerChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        String globalChatFormat;
        String noPermission;
        if ((Integer)this.map.get(p.getId()) == 1) {
            noPermission = this.getConfig().getString("globalLocalChat.localChatPrefix");
            Boolean permToSeeLocalChat = this.getConfig().getBoolean("globalLocalChat.permToSeeLocalChat");
            String prefix = this.playerPrefixes.get(p.getId());
            String suffix = this.playerSuffixes.get(p.getId());
            String prefixsuffix = "";
            String suffixprefix = "";

            if (prefix != null && !prefix.isEmpty()) {
                prefixsuffix += ChatColor.translateAlternateColorCodes('&', prefix);
            }
            if (suffix != null && !suffix.isEmpty()) {
                suffixprefix += ChatColor.translateAlternateColorCodes('&', suffix);
            }

            int radius = this.getConfig().getInt("globalLocalChat.localChatRadius");
            String spyLocalFormat = this.getConfig().getString("globalLocalChat.spyLocalPrefix");
            String localChatSpyFormat = String.format(ChatColor.translateAlternateColorCodes('&', spyLocalFormat) + e.getFormat(), p.getDisplayId(), e.getMessage());
            String localChatFormat;
            if (prefixsuffix.isEmpty() && suffixprefix.isEmpty()) {
                localChatFormat = String.format(ChatColor.translateAlternateColorCodes('&', noPermission) + "<%s> %s", p.getDisplayId(), e.getMessage());
            } else if (suffixprefix.isEmpty()) {
                localChatFormat = String.format(ChatColor.translateAlternateColorCodes('&', noPermission) + ChatColor.GRAY + "[ " + prefixsuffix + ChatColor.GRAY + " ]" + ChatColor.RESET + " <%s> %s", p.getDisplayId(), e.getMessage());
            } else {
                localChatFormat = String.format(ChatColor.translateAlternateColorCodes('&', noPermission) + ChatColor.GRAY + "[ " + prefixsuffix + ChatColor.GRAY + " ]" + ChatColor.RESET + " <%s>" + ChatColor.GRAY + " [ " + suffixprefix + ChatColor.GRAY + " ] " + ChatColor.RESET + "%s", p.getDisplayId(), e.getMessage());
            }

            Iterator var9 = Bukkit.getOnlinePlayers().iterator();
            if (p.hasPermission("globallocalchat.local")) {
                p.sendMessage(localChatFormat);
                Iterator var15 = e.getRecipients().iterator();

                while(var15.hasNext()) {
                    Player recipient = (Player)var15.next();
                    if (recipient != p && recipient.getWorld().equals(p.getWorld()) && recipient.getLocation().distance(p.getLocation()) <= (double)radius) {
                        double distance = recipient.getLocation().distance(p.getLocation());
                        String formattedMessage;
                        if (prefixsuffix.isEmpty()) {
                            formattedMessage = String.format(ChatColor.DARK_GREEN + "[" + Math.round(distance) + "m] " + ChatColor.RESET + "<%s> %s", p.getDisplayId(), e.getMessage());
                        } else {
                            formattedMessage = String.format(ChatColor.GRAY + "[ " + prefixsuffix + ChatColor.GRAY + " ] " + ChatColor.RESET + ChatColor.DARK_GREEN + "[" + Math.round(distance) + "m] " + ChatColor.RESET + " <%s>" + ChatColor.GRAY + "[ " + suffixprefix + ChatColor.GRAY + " ] " + ChatColor.RESET + "%s", p.getDisplayId(), e.getMessage());
                        }

                        recipient.sendMessage(formattedMessage);
                        boolean noPlayersInRange = false;
                        if (noPlayersInRange) {
                            p.sendMessage(ChatColor.RED + "대화를 전송하지 못했습니다. 반경 " + radius + " 블록 내에 플레이어를 찾을 수 없습니다");
                            p.sendMessage(ChatColor.AQUA + "팁: /chatmode 명령어를 이용해 대화 모드를 전환할 수 있습니다");
                        }
                    }
                }

                while(true) {
                    while(var9.hasNext()) {
                        Player p2 = (Player)var9.next();
                        if (p2.getWorld().equals(p.getWorld()) && p2.getLocation().distance(p.getLocation()) <= (double)radius) {
                            if (permToSeeLocalChat) {
                                if (p2.hasPermission("globallocalchat.local") && p2 != p) {
                                    p2.sendMessage(localChatFormat);
                                }
                            } else if (p2 != p) {
                                p2.sendMessage(localChatFormat);
                            }
                        } else if ((Integer)this.map2.get(p2.getId()) == 1 && p2.getPlayer() != p.getPlayer()) {
                            e.setFormat(localChatSpyFormat);
                            p2.sendRawMessage(e.getFormat());
                            e.setFormat(localChatFormat);
                        }
                    }

                    e.setCancelled(true);
                    break;
                }
            } else {
                this.map.put(p.getId(), 0);
                String globalChatPrefix = this.getConfig().getString("globalLocalChat.globalChatPrefix");
                if (prefixsuffix.isEmpty()) {
                    globalChatFormat = String.format(ChatColor.translateAlternateColorCodes('&', globalChatPrefix) + ChatColor.GRAY + "[ " + prefixsuffix + ChatColor.GRAY + " ]" + ChatColor.RESET + e.getFormat(), p.getDisplayId(), e.getMessage());
                } else {
                    globalChatFormat = String.format(ChatColor.translateAlternateColorCodes('&', globalChatPrefix) + ChatColor.GRAY + "[ " + prefixsuffix + ChatColor.GRAY + " ]" + ChatColor.RESET + e.getFormat(), p.getDisplayId(), e.getMessage());
                }

                e.setFormat(globalChatFormat);
            }
        } else {
            String globalChatPrefix;
            if ((Integer)this.map.get(p.getId()) == 2) {
                String staffChatPrefix = this.getConfig().getString("globalLocalChat.staffChatPrefix");
                noPermission = String.format(ChatColor.translateAlternateColorCodes('&', staffChatPrefix) + e.getFormat(), p.getDisplayId(), e.getMessage());
                if (p.hasPermission("globallocalchat.staff")) {
                    e.setFormat(noPermission);
                    Iterator var13 = Bukkit.getOnlinePlayers().iterator();

                    while(var13.hasNext()) {
                        Player p2 = (Player)var13.next();
                        if (p2.hasPermission("globallocalchat.staff")) {
                            p2.sendRawMessage(e.getFormat());
                        }
                    }

                    e.setCancelled(true);
                } else {
                    this.map.put(p.getId(), 0);
                    globalChatPrefix = this.getConfig().getString("globalLocalChat.globalChatPrefix");
                    globalChatFormat = String.format(ChatColor.translateAlternateColorCodes('&', globalChatPrefix) + e.getFormat(), p.getDisplayId(), e.getMessage());
                    e.setFormat(globalChatFormat);
                }
            } else {
                Boolean permToTypeInGlobalChat = this.getConfig().getBoolean("globalLocalChat.permToTypeInGlobalChat");
                noPermission = this.getConfig().getString("pluginStuff.noPermissionMessage");
                globalChatPrefix = this.getConfig().getString("globalLocalChat.globalChatPrefix");
                globalChatFormat = String.format(ChatColor.translateAlternateColorCodes('&', globalChatPrefix) + e.getFormat(), p.getDisplayId(), e.getMessage());
                if (permToTypeInGlobalChat) {
                    if (e.getPlayer().hasPermission("globallocalchat.global")) {
                        e.setFormat(globalChatFormat);
                    } else {
                        e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', noPermission));
                        e.setCancelled(true);
                    }
                } else {
                    e.setFormat(globalChatFormat);
                }
            }
        }

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        this.map.put(p.getId(), 1);
        this.map2.put(p.getId(), 0);
        String spyLocalOn = this.getConfig().getString("globalLocalChat.spyLocalOn");
        Boolean spyLocalOnLogin = this.getConfig().getBoolean("globalLocalChat.spyLocalOnLogin");
        if (spyLocalOnLogin && p.hasPermission("globallocalchat.localspy")) {
            this.map2.put(p.getId(), 1);
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', spyLocalOn));
        }
    }
}
