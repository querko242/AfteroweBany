package pl.aftermc.bans.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.aftermc.bans.AfteroweBany;
import pl.aftermc.bans.lib.mf.annotations.Command;
import pl.aftermc.bans.lib.mf.annotations.Completion;
import pl.aftermc.bans.lib.mf.annotations.Default;
import pl.aftermc.bans.lib.mf.annotations.Permission;
import pl.aftermc.bans.lib.mf.base.CommandBase;
import pl.aftermc.bans.util.ChatUtil;
import pl.aftermc.bans.util.StringUtil;

@Command("kick")
public class KickCommand extends CommandBase {

    public KickCommand(final AfteroweBany plugin){
        plugin.getCommandManager().register(this);
        this.plugin = plugin;
    }
    private final AfteroweBany plugin;

    @Default
    @Permission("kick")
    @Completion("#players")
    public void kickCommand(final CommandSender sender, final String[] args) {
        if(args.length < 1) {
            ChatUtil.sendMessage(sender, this.plugin.getMessagesConfiguration().usageCommand.replace("%usage%", "/kick <gracz> <powód>"));
            return;
        }
        Player kicked = Bukkit.getPlayer(args[0]);
        if(kicked == null) {
            ChatUtil.sendMessage(sender, this.plugin.getMessagesConfiguration().playerNotOnline);
            return;
        }
        String kickAdmin = sender.getName();
        if(sender.getName().equalsIgnoreCase("console")) {
            kickAdmin = "konsola";
        }
        String reason = this.plugin.getPluginConfiguration().noReasonBan;
        if(args.length > 1) {
            reason = StringUtil.stringBuilder(args, 1);
        }
        kicked.kickPlayer(ChatUtil.fixColor(this.plugin.getMessagesConfiguration().kickPlayer.replace("%admin%", kickAdmin).replace("%reason%", reason)));
        ChatUtil.sendBroadcast(this.plugin.getMessagesConfiguration().kickPlayerBroadcast.replace("%player%", args[0]).replace("%admin%", kickAdmin).replace("%reason%", reason));
    }
}
