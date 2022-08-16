package pl.aftermc.bans.command;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import pl.aftermc.bans.AfteroweBany;
import pl.aftermc.bans.lib.mf.annotations.Command;
import pl.aftermc.bans.lib.mf.annotations.Default;
import pl.aftermc.bans.lib.mf.annotations.Permission;
import pl.aftermc.bans.lib.mf.base.CommandBase;
import pl.aftermc.bans.util.ChatUtil;
import pl.aftermc.bans.util.ItemBuilder;

@Command("fastban")
public class FastBanCommand extends CommandBase {

    public FastBanCommand(final AfteroweBany plugin){
        plugin.getCommandManager().register(this);
        this.plugin = plugin;
    }
    private final AfteroweBany plugin;

    @Default
    @Permission("fastban")
    public void unmuteCommand(final Player player) {
        player.getInventory().addItem(new ItemBuilder(Material.STICK).setName("&4&lUKARAJ GRACZA").setLore("&8* &7Kliknij &6PPM &7na gracza którego chcesz ukarać",
                "&8* &7Wybierz z listy karę",
                "&8* &aGotowe, gracz został ukarany! :)").getItem());
        ChatUtil.sendBroadcast(this.plugin.getMessagesConfiguration().getFastBanStick);
    }
}
