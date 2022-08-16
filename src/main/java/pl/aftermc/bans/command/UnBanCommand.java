package pl.aftermc.bans.command;

import pl.aftermc.bans.lib.mf.annotations.Command;
import pl.aftermc.bans.lib.mf.annotations.Completion;
import pl.aftermc.bans.lib.mf.annotations.Default;
import pl.aftermc.bans.lib.mf.annotations.Permission;
import pl.aftermc.bans.lib.mf.base.CommandBase;
import org.bukkit.command.CommandSender;
import pl.aftermc.bans.AfteroweBany;
import pl.aftermc.bans.object.Ban;
import pl.aftermc.bans.util.ChatUtil;

@Command("unban")
public class UnBanCommand extends CommandBase {

    public UnBanCommand(final AfteroweBany plugin){
        plugin.getCommandManager().register(this);
        this.plugin = plugin;
    }
    private final AfteroweBany plugin;

    @Default
    @Permission("unban")
    @Completion("#bannedplayers")
    public void unbanCommand(final CommandSender sender, final String[] args) {
        if(args.length < 1) {
            ChatUtil.sendMessage(sender, this.plugin.getMessagesConfiguration().usageCommand.replace("%usage%", "/unban <gracz>"));
            return;
        }
        if(this.plugin.getPluginData().getBan(args[0]) == null) {
            ChatUtil.sendMessage(sender, this.plugin.getMessagesConfiguration().playerNotBanned);
            return;
        }
        String unbanAdmin = sender.getName();
        if(sender.getName().equalsIgnoreCase("console")) {
            unbanAdmin = "konsola";
        }
        Ban ban = this.plugin.getPluginData().getBan(args[0]);
        ChatUtil.sendBroadcast(this.plugin.getMessagesConfiguration().unBanBroadcast.replace("%player%", ban.getPlayerName()).replace("%admin%", unbanAdmin));
        this.plugin.getDatabase().delete(ban);
        this.plugin.getPluginData().removeBan(ban);
    }
}
