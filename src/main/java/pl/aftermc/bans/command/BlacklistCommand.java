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

@Command("blacklist")
public class BlacklistCommand extends CommandBase {

    public BlacklistCommand(final AfteroweBany plugin){
        plugin.getCommandManager().register(this);
        this.plugin = plugin;
    }
    private final AfteroweBany plugin;

    @Default
    @Permission("blacklist")
    @Completion("#players")
    public void blacklistCommand(final CommandSender sender, final String[] args) {
        if(args.length < 1) {
            ChatUtil.sendMessage(sender, this.plugin.getMessagesConfiguration().usageCommand.replace("%usage%", "/blacklist <gracz> <powód>"));
            return;
        }
        if(this.plugin.getPluginData().getBlacklist(args[0]) != null) {
            ChatUtil.sendMessage(sender, this.plugin.getMessagesConfiguration().playerAlreadyBlacklist);
            return;
        }
        String reason = this.plugin.getPluginConfiguration().noReasonBan;
        if(args.length > 1) {
            reason = StringUtil.stringBuilder(args, 1);
        }
        String banAdmin = sender.getName();
        if(sender.getName().equalsIgnoreCase("console")) {
            banAdmin = "konsola";
        }
        Blacklist blacklist = new Blacklist(args[0], reason, -1, banAdmin);
        this.plugin.getPluginData().addBlacklist(blacklist);
        this.plugin.getDatabase().save(blacklist);
        Player banned = Bukkit.getPlayer(args[0]);
        if(banned != null) {
            banned.kickPlayer(ChatUtil.fixColor(blacklist.replaceBlacklist()));
        }
        ChatUtil.sendBroadcast(this.plugin.getMessagesConfiguration().permBlacklistBroadcast.replace("%player%", args[0]).replace("%admin%", banAdmin).replace("%reason%", reason));
    }
}
