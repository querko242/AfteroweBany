package pl.aftermc.bans.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.aftermc.bans.AfteroweBany;
import pl.aftermc.bans.util.ChatUtil;

public class PlayerJoin implements Listener {

    public PlayerJoin(final AfteroweBany plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    private final AfteroweBany plugin;

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(final PlayerJoinEvent event) {
        Player player = event.getPlayer();
        this.plugin.getUserData().checkIfExist(player);

        if(player.hasPermission(this.plugin.getPluginConfiguration().permissionPrefix + ".notifyupdate")) {
            if(this.plugin.isNewPluginUpdate()) {
                ChatUtil.sendMessage(player, "&bAfteroweBany &8&l- &cDostępna jest nowa wersja pluginu!");
                ChatUtil.sendMessage(player, "&bAfteroweBany &8&l- &6Wejdź na serwer discord &b&lAFTERMC.PL &6i pobierz najnowszą wersję!");
                ChatUtil.sendURLMessage(player, "&bLink do Discorda: https://discord.com/invite/rXQMsRXYRc", "https://discord.com/invite/rXQMsRXYRc");
            } else {
                ChatUtil.sendMessage(player, "&bAfteroweBany &8&l- &aUżywasz aktualnej wersji pluginu! :)");
            }
        }
    }
}
