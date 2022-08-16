package pl.aftermc.bans.command;

import pl.aftermc.bans.lib.mf.annotations.Command;
import pl.aftermc.bans.lib.mf.annotations.Default;
import pl.aftermc.bans.lib.mf.annotations.Permission;
import pl.aftermc.bans.lib.mf.base.CommandBase;
import org.bukkit.command.CommandSender;
import pl.aftermc.bans.AfteroweBany;
import pl.aftermc.bans.util.ChatUtil;

@Command("unmuteall")
public class UnMuteAllCommand extends CommandBase {

    public UnMuteAllCommand(final AfteroweBany plugin){
        plugin.getCommandManager().register(this);
        this.plugin = plugin;
    }
    private final AfteroweBany plugin;

    @Default
    @Permission("unmuteall")
    public void unmuteCommand(final CommandSender sender) {
        String admin = sender.getName();
        if(sender.getName().equalsIgnoreCase("console")) {
            admin = "konsola";
        }
        this.plugin.getPluginData().unmuteAll();
        ChatUtil.sendBroadcast(this.plugin.getMessagesConfiguration().unMuteAllBroadcast.replace("%admin%", admin));
    }
}
