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

@Command("baninfo")
public class BanInfoCommand extends CommandBase {

    public BanInfoCommand(final AfteroweBany plugin){
        plugin.getCommandManager().register(this);
        this.plugin = plugin;
    }
    private final AfteroweBany plugin;

    @Default
    @Permission("baninfo")
    @Completion("#bannedplayers")
    public void banInfoCommand(final CommandSender sender, final String[] args) {
        if(args.length < 1) {
            ChatUtil.sendMessage(sender, this.plugin.getMessagesConfiguration().usageCommand.replace("%usage%", "/baninfo <gracz>"));
            return;
        }
        Ban ban = this.plugin.getPluginData().getBan(args[0]);
        if(ban == null) {
            ChatUtil.sendMessage(sender, this.plugin.getMessagesConfiguration().playerNotBanned);
            return;
        }

        ChatUtil.sendMessage(sender, this.plugin.getMessagesConfiguration().banInfo
                .replace("%player%", ban.getPlayerName())
                .replace("%banDate%", ban.getBanDate())
                .replace("%banAdmin%", ban.getBanAdmin())
                .replace("%reason%", ban.getReason())
                .replace("%expire%", ban.isPerm() ? "nigdy" : ban.getBanDuration()));
    }
}
