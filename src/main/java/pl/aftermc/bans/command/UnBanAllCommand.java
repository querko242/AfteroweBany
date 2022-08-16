package pl.aftermc.bans.command;

import pl.aftermc.bans.lib.mf.annotations.Command;
import pl.aftermc.bans.lib.mf.annotations.Default;
import pl.aftermc.bans.lib.mf.annotations.Permission;
import pl.aftermc.bans.lib.mf.base.CommandBase;
import org.bukkit.command.CommandSender;
import pl.aftermc.bans.AfteroweBany;
import pl.aftermc.bans.util.ChatUtil;

@Command("unbanall")
public class UnBanAllCommand extends CommandBase {

    public UnBanAllCommand(final AfteroweBany plugin){
        plugin.getCommandManager().register(this);
        this.plugin = plugin;
    }
    private final AfteroweBany plugin;

    @Default
    @Permission("unbanall")
    public void unbanCommand(final CommandSender sender) {
        String unbanAdmin = sender.getName();
        if(sender.getName().equalsIgnoreCase("console")) {
            unbanAdmin = "konsola";
        }
        this.plugin.getPluginData().unbanAll();
        ChatUtil.sendBroadcast(this.plugin.getMessagesConfiguration().unBanAllBroadcast.replace("%admin%", unbanAdmin));
    }
}
