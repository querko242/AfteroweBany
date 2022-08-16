package pl.aftermc.bans.command;

import org.bukkit.command.CommandSender;
import pl.aftermc.bans.AfteroweBany;
import pl.aftermc.bans.lib.mf.annotations.Command;
import pl.aftermc.bans.lib.mf.annotations.Completion;
import pl.aftermc.bans.lib.mf.annotations.Default;
import pl.aftermc.bans.lib.mf.annotations.Permission;
import pl.aftermc.bans.lib.mf.base.CommandBase;
import pl.aftermc.bans.object.Blacklist;
import pl.aftermc.bans.util.ChatUtil;

@Command("unblacklist")
public class UnBlacklistCommand extends CommandBase {

    public UnBlacklistCommand(final AfteroweBany plugin){
        plugin.getCommandManager().register(this);
        this.plugin = plugin;
    }
    private final AfteroweBany plugin;

    @Default
    @Permission("unblacklist")
    @Completion("#blacklistplayers")
    public void unbanCommand(final CommandSender sender, final String[] args) {
        if(args.length < 1) {
            ChatUtil.sendMessage(sender, this.plugin.getMessagesConfiguration().usageCommand.replace("%usage%", "/unblacklist <gracz>"));
            return;
        }
        if(this.plugin.getPluginData().getBlacklist(args[0]) == null) {
            ChatUtil.sendMessage(sender, this.plugin.getMessagesConfiguration().playerNotBlacklist);
            return;
        }
        String unbanAdmin = sender.getName();
        if(sender.getName().equalsIgnoreCase("console")) {
            unbanAdmin = "konsola";
        }
        Blacklist blacklist = this.plugin.getPluginData().getBlacklist(args[0]);
        ChatUtil.sendBroadcast(this.plugin.getMessagesConfiguration().unBlacklistBroadcast.replace("%player%", blacklist.getPlayerName()).replace("%admin%", unbanAdmin));
        this.plugin.getDatabase().delete(blacklist);
        this.plugin.getPluginData().removeBlacklist(blacklist);
    }
}
