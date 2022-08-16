package pl.aftermc.bans.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import pl.aftermc.bans.AfteroweBany;
import pl.aftermc.bans.data.PluginConfiguration;
import pl.aftermc.bans.lib.gui.GuiWindow;
import pl.aftermc.bans.util.ChatUtil;
import pl.aftermc.bans.util.ItemBuilder;

import java.util.Map;

public class PlayerInteract implements Listener {

    public PlayerInteract(final AfteroweBany plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    private final AfteroweBany plugin;

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteract(final PlayerInteractEntityEvent event) {
        if(!(event.getRightClicked() instanceof Player)) return;
        if(event.getPlayer().getItemInHand().getType() != Material.STICK ||
                event.getPlayer().getItemInHand().getItemMeta() == null ||
                event.getPlayer().getItemInHand().getItemMeta().getDisplayName() == null ||
                !event.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals(ChatUtil.fixColor("&4&lUKARAJ GRACZA"))) return;

        Player playerRightClicked = (Player)event.getRightClicked();

        GuiWindow gui = new GuiWindow("&4Ukaraj gracza &6" + playerRightClicked.getName(), this.plugin.getPluginConfiguration().guiSize);

        for(Map.Entry<String, PluginConfiguration.FastPlayerBan> bans : this.plugin.getPluginConfiguration().fastPlayerBan.entrySet()) {
            gui.setToNextFree(new ItemBuilder(Material.PAPER).setName("&8* &3" + bans.getKey().replace("_", " ") + " &8*").setLore(" &8* &7Komenda:",
                    "&6" + bans.getValue().getCommand().replace("%player%", playerRightClicked.getName()),
                    "&8",
                    "&a&nKliknij aby wykonać!").getItem(), clickEvent -> {
                if(!playerRightClicked.isOnline()) {
                    ChatUtil.sendMessage(event.getPlayer(), this.plugin.getMessagesConfiguration().playerNotOnline);
                    return;
                }
                event.getPlayer().chat(bans.getValue().getCommand().replace("%player%", playerRightClicked.getName()));
                event.getPlayer().closeInventory();
            });
        }

        gui.open(event.getPlayer());
    }
}
