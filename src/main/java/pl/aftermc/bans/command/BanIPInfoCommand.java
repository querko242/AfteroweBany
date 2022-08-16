package pl.aftermc.bans.command;

import org.bukkit.command.CommandSender;
import pl.aftermc.bans.AfteroweBany;
import pl.aftermc.bans.lib.mf.annotations.Command;
import pl.aftermc.bans.lib.mf.annotations.Completion;
import pl.aftermc.bans.lib.mf.annotations.Default;
import pl.aftermc.bans.lib.mf.annotations.Permission;
import pl.aftermc.bans.lib.mf.base.CommandBase;
import pl.aftermc.bans.object.BanIP;
import pl.aftermc.bans.util.ChatUtil;

@Command("banipinfo")
public class BanIPInfoCommand extends CommandBase {

    public BanIPInfoCommand(final AfteroweBany plugin){
        plugin.getCommandManager().register(this);
        this.plugin = plugin;
    }
    private final AfteroweBany plugin;

    @Default
    @Permission("ban-ipinfo")
    @Completion("#bannedips")
    public void banInfoCommand(final CommandSender sender, final String[] args) {
        if(args.length < 1) {
            ChatUtil.sendMessage(sender, this.plugin.getMessagesConfiguration().usageCommand.replace("%usage%", "/banipinfo <ip>"));
            return;
        }
        BanIP ban = this.plugin.getPluginData().getBanIP(args[0]);
        if(ban == null) {
            ChatUtil.sendMessage(sender, this.plugin.getMessagesConfiguration().ipNotBanned);
            return;
        }

        ChatUtil.sendMessage(sender, this.plugin.getMessagesConfiguration().banIPInfo
                .replace("%ip%", ban.getIP())
                .replace("%banDate%", ban.getBanDate())
                .replace("%banAdmin%", ban.getBanAdmin())
                .replace("%reason%", ban.getReason())
                .replace("%expire%", ban.isPerm() ? "nigdy" : ban.getBanDuration()));
    }
}
