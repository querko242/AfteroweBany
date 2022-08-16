package pl.aftermc.bans.command;

import org.bukkit.command.CommandSender;
import pl.aftermc.bans.AfteroweBany;
import pl.aftermc.bans.lib.mf.annotations.*;
import pl.aftermc.bans.lib.mf.base.CommandBase;
import pl.aftermc.bans.object.BanIP;
import pl.aftermc.bans.util.ChatUtil;

@Command("unbanip")
public class UnBanIPCommand extends CommandBase {

    public UnBanIPCommand(final AfteroweBany plugin){
        plugin.getCommandManager().register(this);
        this.plugin = plugin;
    }
    private final AfteroweBany plugin;

    @Default
    @Permission("unbanip")
    @SubCommand("unban-ip")
    @Completion("#bannedips")
    public void unbanCommand(final CommandSender sender, final String[] args) {
        if(args.length < 1) {
            ChatUtil.sendMessage(sender, this.plugin.getMessagesConfiguration().usageCommand.replace("%usage%", "/unbanip <gracz/ip>"));
            return;
        }
        if(this.plugin.getPluginData().getBanIP(args[0]) == null) {
            ChatUtil.sendMessage(sender, this.plugin.getMessagesConfiguration().ipNotBanned);
            return;
        }

        BanIP ban = this.plugin.getPluginData().getBanIP(args[0]);
        ChatUtil.sendMessage(sender, this.plugin.getMessagesConfiguration().unbanIP.replace("%ip%", ban.getIP()));
        this.plugin.getDatabase().delete(ban);
        this.plugin.getPluginData().removeBan(ban);
    }
}
