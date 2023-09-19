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

import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Logger;

public final class GlobalLocalChatPlugin extends JavaPlugin implements Listener {
    public static Logger log = Logger.getLogger("Minecraft");
    HashMap<String, Integer> map = new HashMap();
    HashMap<String, Integer> map2 = new HashMap();

    public GlobalLocalChatPlugin() {
    }

    public void onEnable() {
        FileConfiguration config = this.getConfig();
        this.saveDefaultConfig();
        this.getServer().getPluginManager().registerEvents(this, this);
        Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE + "플러그인 활성화");
    }

    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "플러그인 비활성화");
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void playerChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        String globalChatFormat;
        String staffChatPrefix;
        if ((Integer)this.map.get(p.getName()) == 1) {
            String localChatPrefix = this.getConfig().getString("globalLocalChat.localChatPrefix");
            Boolean permToSeeLocalChat = this.getConfig().getBoolean("globalLocalChat.permToSeeLocalChat");
            String prefix = String.format(ChatColor.translateAlternateColorCodes('&',"&7[ &a서버주인 &7]&f "));
            int radius = this.getConfig().getInt("globalLocalChat.localChatRadius");
            globalChatFormat = this.getConfig().getString("globalLocalChat.spyLocalPrefix");
            boolean noPlayersInRange;
            String localChatSpyFormat = String.format(ChatColor.translateAlternateColorCodes('&', globalChatFormat) + e.getFormat(), p.getDisplayName(), e.getMessage());
            String localChatFormat = String.format(ChatColor.translateAlternateColorCodes('&', localChatPrefix) + prefix + "<%s> %s", p.getDisplayName(), e.getMessage());
            Iterator var9 = Bukkit.getOnlinePlayers().iterator();
            if (p.hasPermission("globallocalchat.local")) {
                p.sendMessage(localChatFormat);

                for (Player recipient : e.getRecipients()) {
                    if (recipient != p && recipient.getWorld().equals(p.getWorld()) && recipient.getLocation().distance(p.getLocation()) <= radius) {
                        double distance = recipient.getLocation().distance(p.getLocation());
                        String formattedMessage = String.format(ChatColor.DARK_GREEN + "[" + Math.round(distance) + "m] " + ChatColor.RESET + "<%s> %s", p.getDisplayName(), e.getMessage());
                        recipient.sendMessage(formattedMessage);
                        noPlayersInRange = false;

                        if (noPlayersInRange) {
                            p.sendMessage(ChatColor.RED + "대화를 전송하지 못했습니다. 반경 " + radius + " 블록 내에 플레이어를 찾을 수 없습니다");
                            p.sendMessage(ChatColor.AQUA + "팁: /chatmode 명령어를 이용해 대화 모드를 전환할 수 있습니다");
                        }
                    }
                }

                while (true) {
                    while (var9.hasNext()) {
                        Player p2 = (Player) var9.next();
                        if (p2.getWorld().equals(p.getWorld()) && p2.getLocation().distance(p.getLocation()) <= (double) radius) {
                            if (permToSeeLocalChat) {
                                if (p2.hasPermission("globallocalchat.local") && p2 != p) {
                                    p2.sendMessage(localChatFormat);
                                }
                            } else {
                                if (p2 != p) {
                                    p2.sendMessage(localChatFormat);
                                }
                            }
                        } else if ((Integer) this.map2.get(p2.getName()) == 1 && p2.getPlayer() != p.getPlayer()) {
                            e.setFormat(localChatSpyFormat);
                            p2.sendRawMessage(e.getFormat());
                            e.setFormat(localChatFormat);
                        }
                    }
                    e.setCancelled(true);
                    break;
                }
            } else {
                this.map.put(p.getName(), 0);
                String globalChatPrefix = this.getConfig().getString("globalLocalChat.globalChatPrefix");
                globalChatFormat = String.format(ChatColor.translateAlternateColorCodes('&', globalChatPrefix) + e.getFormat(), p.getDisplayName(), e.getMessage());
                e.setFormat(globalChatFormat);
            }
        } else {
            String noPermission;
            String globalChatPrefix;
            if ((Integer)this.map.get(p.getName()) == 2) {
                staffChatPrefix = this.getConfig().getString("globalLocalChat.staffChatPrefix");
                noPermission = String.format(ChatColor.translateAlternateColorCodes('&', staffChatPrefix) + e.getFormat(), p.getDisplayName(), e.getMessage());
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
                    this.map.put(p.getName(), 0);
                    globalChatPrefix = this.getConfig().getString("globalLocalChat.globalChatPrefix");
                    globalChatFormat = String.format(ChatColor.translateAlternateColorCodes('&', globalChatPrefix) + e.getFormat(), p.getDisplayName(), e.getMessage());
                    e.setFormat(globalChatFormat);
                }
            } else {
                Boolean permToTypeInGlobalChat = this.getConfig().getBoolean("globalLocalChat.permToTypeInGlobalChat");
                noPermission = this.getConfig().getString("pluginStuff.noPermissionMessage");
                globalChatPrefix = this.getConfig().getString("globalLocalChat.globalChatPrefix");
                globalChatFormat = String.format(ChatColor.translateAlternateColorCodes('&', globalChatPrefix) + e.getFormat(), p.getDisplayName(), e.getMessage());
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
            this.map.put(p.getName(), 1);
            this.map2.put(p.getName(), 0);
            String spyLocalOn = this.getConfig().getString("globalLocalChat.spyLocalOn");
            Boolean spyLocalOnLogin = this.getConfig().getBoolean("globalLocalChat.spyLocalOnLogin");
            if (spyLocalOnLogin && p.hasPermission("globallocalchat.localspy")) {
                this.map2.put(p.getName(), 1);
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', spyLocalOn));
            }

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
                        if ((Integer)this.map.get(player.getName()) != 1) {
                            spyLocalOff = this.getConfig().getString("globalLocalChat.localChat");
                            this.map.put(player.getName(), 1);
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
                        if ((Integer)this.map.get(player.getName()) != 0) {
                            spyLocalOff = this.getConfig().getString("globalLocalChat.globalChat");
                            this.map.put(player.getName(), 0);
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
                        if ((Integer)this.map.get(player.getName()) != 2) {
                            spyLocalOff = this.getConfig().getString("globalLocalChat.staffChat");
                            this.map.put(player.getName(), 2);
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
                        if ((Integer)this.map2.get(player.getName()) == 0) {
                            spyLocalOff = this.getConfig().getString("globalLocalChat.spyLocalOn");
                            this.map2.put(player.getName(), 1);
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', spyLocalOff));
                        } else if ((Integer)this.map2.get(player.getName()) == 1) {
                            spyLocalOff = this.getConfig().getString("globalLocalChat.spyLocalOff");
                            this.map2.put(player.getName(), 0);
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

                // 플레이어 입력 확인
                Player targetPlayer = getServer().getPlayer(args[0]);
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
                    // 3번째 인수가 없으면 삭제 작업 수행
                    if (prefixOrSuffix.equalsIgnoreCase("prefix") || prefixOrSuffix.equalsIgnoreCase("suffix")) {
                        // 해당 플레이어의 prefix 또는 suffix 삭제
                        targetPlayer.setPlayerListName(targetPlayer.getName());
                        sender.sendMessage(targetPlayer.getName() + "님의 " + prefixOrSuffix + "를 삭제했습니다.");
                    } else {
                        sender.sendMessage("올바른 prefix 또는 suffix를 지정하세요.");
                    }
                } else {
                    // 3번째 인수가 있으면 설정 작업 수행
                    if (prefixOrSuffix.equalsIgnoreCase("prefix")) {
                        // Prefix 설정
                        String text = ChatColor.translateAlternateColorCodes('&', args[2]);
                        String currentName = targetPlayer.getName();
                        // Prefix를 플레이어 이름에 추가
                        targetPlayer.setPlayerListName(text + currentName);
                        sender.sendMessage(targetPlayer.getName() + "님의 Prefix를 설정했습니다.");
                    } else if (prefixOrSuffix.equalsIgnoreCase("suffix")) {
                        // Suffix 설정
                        String text = ChatColor.translateAlternateColorCodes('&', args[2]);
                        String currentName = targetPlayer.getName();
                        // Suffix를 플레이어 이름에 추가
                        targetPlayer.setPlayerListName(currentName + text);
                        sender.sendMessage(targetPlayer.getName() + "님의 Suffix를 설정했습니다.");
                    } else {
                        sender.sendMessage("올바른 prefix 또는 suffix를 지정하세요.");
                    }
                }
            }
            return true;
        }
    }
}
