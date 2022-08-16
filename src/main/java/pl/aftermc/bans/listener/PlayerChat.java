package pl.aftermc.bans.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import pl.aftermc.bans.AfteroweBany;
import pl.aftermc.bans.data.MessagesConfiguration;
import pl.aftermc.bans.data.PluginConfiguration;
import pl.aftermc.bans.object.Mute;
import pl.aftermc.bans.util.ChatUtil;

public class PlayerChat implements Listener {

    public PlayerChat(final AfteroweBany plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    private final AfteroweBany plugin;

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerChat(final AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if(event.isCancelled()) return;
        if(!player.hasPermission(this.plugin.getPluginConfiguration().permissionPrefix + ".mute.bypass")) {
            Mute mute = this.plugin.getPluginData().getMute(player.getName());
            MessagesConfiguration messagesConfiguration = this.plugin.getMessagesConfiguration();
            PluginConfiguration pluginConfiguration = this.plugin.getPluginConfiguration();
            if(mute != null) {
                if(mute.isPerm()) {
                    event.setCancelled(true);
                    ChatUtil.sendMessage(player, mute.replaceMute());
                    if(pluginConfiguration.mutePlayerTryWrite) {
                        ChatUtil.sendBroadcast(messagesConfiguration.mutedPlayerTryChat.replace("%player%", mute.getPlayerName()), this.plugin.getPluginConfiguration().permissionPrefix + ".mutetrychat");
                    }
                } else {
                    if(!mute.isExpire()) {
                        event.setCancelled(true);
                        ChatUtil.sendMessage(player, mute.replaceMute());
                        if(pluginConfiguration.mutePlayerTryWrite) {
                            ChatUtil.sendBroadcast(messagesConfiguration.mutedPlayerTryChat.replace("%player%", mute.getPlayerName()), this.plugin.getPluginConfiguration().permissionPrefix + ".mutetrychat");
                        }
                    } else {
                        this.plugin.getDatabase().delete(mute);
                        this.plugin.getPluginData().removeMute(mute);
                        if(pluginConfiguration.mutePlayerTryWrite) {
                            ChatUtil.sendBroadcast(messagesConfiguration.mutedPlayerTryChatExpire.replace("%player%", mute.getPlayerName()), this.plugin.getPluginConfiguration().permissionPrefix + ".mutetrychat");
                        }
                    }

                }
            }
        }
    }
}
