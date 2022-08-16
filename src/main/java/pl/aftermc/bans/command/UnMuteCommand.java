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
import pl.aftermc.bans.object.Mute;
import pl.aftermc.bans.util.ChatUtil;

@Command("unmute")
public class UnMuteCommand extends CommandBase {

    public UnMuteCommand(final AfteroweBany plugin){
        plugin.getCommandManager().register(this);
        this.plugin = plugin;
    }
    private final AfteroweBany plugin;

    @Default
    @Permission("unmute")
    @Completion("#mutedplayers")
    public void unmuteCommand(final CommandSender sender, final String[] args) {
        if(args.length < 1) {
            ChatUtil.sendMessage(sender, this.plugin.getMessagesConfiguration().usageCommand.replace("%usage%", "/unmute <gracz>"));
            return;
        }
        if(this.plugin.getPluginData().getMute(args[0]) == null) {
            ChatUtil.sendMessage(sender, this.plugin.getMessagesConfiguration().playerNotMuted);
            return;
        }
        String admin = sender.getName();
        if(sender.getName().equalsIgnoreCase("console")) {
            admin = "konsola";
        }
        Mute mute = this.plugin.getPluginData().getMute(args[0]);
        ChatUtil.sendBroadcast(this.plugin.getMessagesConfiguration().unMuteBroadcast.replace("%player%", mute.getPlayerName()).replace("%admin%", admin));
        Player playerMuted = Bukkit.getPlayer(mute.getPlayerName());
        if(playerMuted != null) {
            ChatUtil.sendMessage(playerMuted, this.plugin.getMessagesConfiguration().unMute);
        }
        this.plugin.getDatabase().delete(mute);
        this.plugin.getPluginData().removeMute(mute);
    }
}
