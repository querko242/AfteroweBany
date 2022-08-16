package pl.aftermc.bans.command;

import pl.aftermc.bans.lib.mf.annotations.Command;
import pl.aftermc.bans.lib.mf.annotations.Completion;
import pl.aftermc.bans.lib.mf.annotations.Default;
import pl.aftermc.bans.lib.mf.annotations.Permission;
import pl.aftermc.bans.lib.mf.base.CommandBase;
import org.bukkit.command.CommandSender;
import pl.aftermc.bans.AfteroweBany;
import pl.aftermc.bans.object.Mute;
import pl.aftermc.bans.util.ChatUtil;

@Command("muteinfo")
public class MuteInfoCommand extends CommandBase {

    public MuteInfoCommand(final AfteroweBany plugin){
        plugin.getCommandManager().register(this);
        this.plugin = plugin;
    }
    private final AfteroweBany plugin;

    @Default
    @Permission("muteinfo")
    @Completion("#mutedplayers")
    public void muteInfoCommand(final CommandSender sender, final String[] args) {
        if(args.length < 1) {
            ChatUtil.sendMessage(sender, this.plugin.getMessagesConfiguration().usageCommand.replace("%usage%", "/muteinfo <gracz>"));
            return;
        }
        Mute mute = this.plugin.getPluginData().getMute(args[0]);
        if(mute == null) {
            ChatUtil.sendMessage(sender, this.plugin.getMessagesConfiguration().playerNotMuted);
            return;
        }

        ChatUtil.sendMessage(sender, this.plugin.getMessagesConfiguration().muteInfo
                .replace("%player%", mute.getPlayerName())
                .replace("%banDate%", mute.getMuteDate())
                .replace("%banAdmin%", mute.getMuteAdmin())
                .replace("%reason%", mute.getReason())
                .replace("%expire%", mute.isPerm() ? "nigdy" : mute.getMuteDuration()));
    }
}
