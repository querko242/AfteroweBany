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
import pl.aftermc.bans.object.Blacklist;
import pl.aftermc.bans.util.ChatUtil;
import pl.aftermc.bans.util.StringUtil;
import pl.aftermc.bans.util.TimeUtil;

@Command("tempblacklist")
public class TempBlacklistCommand extends CommandBase {

    public TempBlacklistCommand(final AfteroweBany plugin){
        plugin.getCommandManager().register(this);
        this.plugin = plugin;
    }
    private final AfteroweBany plugin;

    @Default
    @Permission("tempblacklist")
    @Completion("#players")
    public void tempBlacklistCommand(final CommandSender sender, final String[] args) {
        if(args.length < 2) {
            ChatUtil.sendMessage(sender, this.plugin.getMessagesConfiguration().usageCommand.replace("%usage%", "/tempblacklist <gracz> <czas np. 5min> <powód>"));
            return;
        }
        if(this.plugin.getPluginData().getBlacklist(args[0]) != null) {
            ChatUtil.sendMessage(sender, this.plugin.getMessagesConfiguration().playerAlreadyBlacklist);
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
        Blacklist blacklist = new Blacklist(args[0], reason, banDuration, banAdmin);
        this.plugin.getPluginData().addBlacklist(blacklist);
        this.plugin.getDatabase().save(blacklist);

        Player banned = Bukkit.getPlayer(args[0]);
        if(banned != null) {
            banned.kickPlayer(ChatUtil.fixColor(blacklist.replaceBlacklist()));
        }
        ChatUtil.sendBroadcast(this.plugin.getMessagesConfiguration().tempBlacklistBroadcast.replace("%player%", args[0]).replace("%admin%", banAdmin).replace("%data%", blacklist.getBanDuration()).replace("%reason%", reason));
    }
}
