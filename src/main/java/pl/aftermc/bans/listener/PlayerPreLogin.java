package pl.aftermc.bans.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import pl.aftermc.bans.AfteroweBany;
import pl.aftermc.bans.data.MessagesConfiguration;
import pl.aftermc.bans.data.PluginConfiguration;
import pl.aftermc.bans.object.Ban;
import pl.aftermc.bans.object.BanIP;
import pl.aftermc.bans.object.Blacklist;
import pl.aftermc.bans.util.ChatUtil;

public final class PlayerPreLogin implements Listener {

    public PlayerPreLogin(final AfteroweBany plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    private final AfteroweBany plugin;

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerLogin(AsyncPlayerPreLoginEvent event){
        String name = event.getName();
        Ban ban = this.plugin.getPluginData().getBan(name);
        if(ban !=null) {
            MessagesConfiguration messageConfiguration = this.plugin.getMessagesConfiguration();
            PluginConfiguration pluginConfiguration = this.plugin.getPluginConfiguration();
            if(ban.isPerm()) {
                event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, ChatUtil.fixColor(ban.replaceBan()));
                if(pluginConfiguration.bannedPlayerTryJoin) {
                    ChatUtil.sendBroadcast(messageConfiguration.bannedPlayerLogin.replace("%player%", ban.getPlayerName()), this.plugin.getPluginConfiguration().permissionPrefix + ".bannedtryjoin");
                }
            } else {
                if(!ban.isExpire()) {
                    event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, ChatUtil.fixColor(ban.replaceBan()));
                    if(pluginConfiguration.bannedPlayerTryJoin) {
                        ChatUtil.sendBroadcast(messageConfiguration.bannedPlayerLogin.replace("%player%", ban.getPlayerName()), this.plugin.getPluginConfiguration().permissionPrefix + ".bannedtryjoin");
                    }
                } else {
                    this.plugin.getDatabase().delete(ban);
                    this.plugin.getPluginData().removeBan(ban);
                    if(pluginConfiguration.bannedPlayerTryJoin) {
                        ChatUtil.sendBroadcast(messageConfiguration.bannedPlayerLoginExpire.replace("%player%", ban.getPlayerName()), this.plugin.getPluginConfiguration().permissionPrefix + ".bannedtryjoin");
                    }
                }
            }
            return;
        }
        BanIP banIP = this.plugin.getPluginData().getBanIP(event.getAddress().getHostAddress());
        if(banIP != null) {
            MessagesConfiguration messageConfiguration = this.plugin.getMessagesConfiguration();
            PluginConfiguration pluginConfiguration = this.plugin.getPluginConfiguration();
            if(banIP.isPerm()) {
                event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, ChatUtil.fixColor(banIP.replaceBan()));
                if(pluginConfiguration.bannedPlayerTryJoin) {
                    ChatUtil.sendBroadcast(messageConfiguration.bannedIPLogin.replace("%ip%", banIP.getIP()), this.plugin.getPluginConfiguration().permissionPrefix + ".bannediptryjoin");
                }
            } else {
                if(!banIP.isExpire()) {
                    event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, ChatUtil.fixColor(banIP.replaceBan()));
                    if(pluginConfiguration.bannedPlayerTryJoin) {
                        ChatUtil.sendBroadcast(messageConfiguration.bannedIPLogin.replace("%ip%", banIP.getIP()), this.plugin.getPluginConfiguration().permissionPrefix + ".bannediptryjoin");
                    }
                } else {
                    this.plugin.getDatabase().delete(banIP);
                    this.plugin.getPluginData().removeBan(banIP);
                    if (pluginConfiguration.bannedPlayerTryJoin) {
                        ChatUtil.sendBroadcast(messageConfiguration.bannedIPLoginExpire.replace("%ip%", banIP.getIP()), this.plugin.getPluginConfiguration().permissionPrefix + ".bannediptryjoin");
                    }
                }
            }
            return;
        }
        Blacklist blacklist = this.plugin.getPluginData().getBlacklist(name);
        if(blacklist !=null) {
            MessagesConfiguration messageConfiguration = this.plugin.getMessagesConfiguration();
            PluginConfiguration pluginConfiguration = this.plugin.getPluginConfiguration();
            if(blacklist.isPerm()) {
                event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, ChatUtil.fixColor(blacklist.replaceBlacklist()));
                if(pluginConfiguration.blacklistPlayerTryJoin) {
                    ChatUtil.sendBroadcast(messageConfiguration.blacklistPlayerLogin.replace("%player%", blacklist.getPlayerName()), this.plugin.getPluginConfiguration().permissionPrefix + ".blacklisttryjoin");
                }
            } else {
                if(!blacklist.isExpire()) {
                    event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, ChatUtil.fixColor(blacklist.replaceBlacklist()));
                    if(pluginConfiguration.blacklistPlayerTryJoin) {
                        ChatUtil.sendBroadcast(messageConfiguration.blacklistPlayerLogin.replace("%player%", blacklist.getPlayerName()), this.plugin.getPluginConfiguration().permissionPrefix + ".blacklisttryjoin");
                    }
                } else {
                    this.plugin.getDatabase().delete(blacklist);
                    this.plugin.getPluginData().removeBlacklist(blacklist);
                    if(pluginConfiguration.bannedPlayerTryJoin) {
                        ChatUtil.sendBroadcast(messageConfiguration.blacklistPlayerLoginExpire.replace("%player%", blacklist.getPlayerName()), this.plugin.getPluginConfiguration().permissionPrefix + ".blacklisttryjoin");
                    }
                }
            }
        }
    }
}
