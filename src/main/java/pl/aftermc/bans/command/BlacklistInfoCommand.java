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

@Command("blacklistinfo")
public class BlacklistInfoCommand extends CommandBase {

    public BlacklistInfoCommand(final AfteroweBany plugin){
        plugin.getCommandManager().register(this);
        this.plugin = plugin;
    }
    private final AfteroweBany plugin;

    @Default
    @Permission("blacklistinfo")
    @Completion("#blacklistplayers")
    public void banInfoCommand(final CommandSender sender, final String[] args) {
        if(args.length < 1) {
            ChatUtil.sendMessage(sender, this.plugin.getMessagesConfiguration().usageCommand.replace("%usage%", "/blacklistinfo <gracz>"));
            return;
        }
        Blacklist blacklist = this.plugin.getPluginData().getBlacklist(args[0]);
        if(blacklist == null) {
            ChatUtil.sendMessage(sender, this.plugin.getMessagesConfiguration().playerNotBlacklist);
            return;
        }

        ChatUtil.sendMessage(sender, this.plugin.getMessagesConfiguration().blacklistInfo
                .replace("%player%", blacklist.getPlayerName())
                .replace("%banDate%", blacklist.getBanDate())
                .replace("%banAdmin%", blacklist.getBanAdmin())
                .replace("%reason%", blacklist.getReason())
                .replace("%expire%", blacklist.isPerm() ? "nigdy" : blacklist.getBanDuration()));
    }
}
