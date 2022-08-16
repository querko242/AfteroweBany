package pl.aftermc.bans.command;

import pl.aftermc.bans.lib.mf.annotations.Command;
import pl.aftermc.bans.lib.mf.annotations.Completion;
import pl.aftermc.bans.lib.mf.annotations.Default;
import pl.aftermc.bans.lib.mf.annotations.Permission;
import pl.aftermc.bans.lib.mf.base.CommandBase;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.aftermc.bans.AfteroweBany;
import pl.aftermc.bans.object.Ban;
import pl.aftermc.bans.util.ChatUtil;
import pl.aftermc.bans.util.StringUtil;
import pl.aftermc.bans.util.TimeUtil;

@Command("tempban")
public class TempBanCommand extends CommandBase {

    public TempBanCommand(final AfteroweBany plugin){
        plugin.getCommandManager().register(this);
        this.plugin = plugin;
    }
    private final AfteroweBany plugin;

    @Default
    @Permission("tempban")
    @Completion("#players")
    public void tempbanCommand(final CommandSender sender, final String[] args) {
        if(args.length < 2) {
            ChatUtil.sendMessage(sender, this.plugin.getMessagesConfiguration().usageCommand.replace("%usage%", "/tempban <gracz> <czas np. 5min> <powód>"));
            return;
        }
        if(this.plugin.getPluginData().getBan(args[0]) != null) {
            ChatUtil.sendMessage(sender, this.plugin.getMessagesConfiguration().playerAlreadyBanned);
            return;
        }
        String reason = this.plugin.getPluginConfiguration().noReasonBan;
        if(args.length >= 3) {
            reason = StringUtil.stringBuilder(args, 2);
        }
        String banAdmin = sender.getName();
        if(sender.getName().equalsIgnoreCase("console")) {
            banAdmin = "konsola";
        }
        long banDuration = TimeUtil.getTimeWithString(args[1]);
        Ban ban = new Ban(args[0], reason, banDuration, banAdmin);
        this.plugin.getPluginData().addBan(ban);
        this.plugin.getDatabase().save(ban);
        Player banned = Bukkit.getPlayer(args[0]);
        if(banned != null) {
            banned.kickPlayer(ChatUtil.fixColor(ban.replaceBan()));
        }
        ChatUtil.sendBroadcast(this.plugin.getMessagesConfiguration().tempBanBroadcast.replace("%player%", args[0]).replace("%admin%", banAdmin).replace("%data%", ban.getBanDuration()).replace("%reason%", reason));
    }
}
