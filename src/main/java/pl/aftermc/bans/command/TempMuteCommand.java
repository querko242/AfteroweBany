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
import pl.aftermc.bans.util.StringUtil;
import pl.aftermc.bans.util.TimeUtil;

@Command("tempmute")
public class TempMuteCommand extends CommandBase {

    public TempMuteCommand(final AfteroweBany plugin){
        plugin.getCommandManager().register(this);
        this.plugin = plugin;
    }
    private final AfteroweBany plugin;

    @Default
    @Permission("tempmute")
    @Completion("#players")
    public void tempMuteCommand(final CommandSender sender, final String[] args) {
        if(args.length < 2) {
            ChatUtil.sendMessage(sender, this.plugin.getMessagesConfiguration().usageCommand.replace("%usage%", "/tempmute <gracz> <czas np. 5min> <powód>"));
            return;
        }
        if(this.plugin.getPluginData().getMute(args[0]) != null) {
            ChatUtil.sendMessage(sender, this.plugin.getMessagesConfiguration().playerAlreadyMuted);
            return;
        }
        String reason = this.plugin.getPluginConfiguration().noReasonBan;
        if(args.length >= 3) {
            reason = StringUtil.stringBuilder(args, 2);
        }
        String admin = sender.getName();
        if(sender.getName().equalsIgnoreCase("console")) {
            admin = "konsola";
        }
        long muteDuration = TimeUtil.getTimeWithString(args[1]);
        Mute mute = new Mute(args[0], reason, muteDuration, admin);
        this.plugin.getPluginData().addMute(mute);
        this.plugin.getDatabase().save(mute);
        ChatUtil.sendBroadcast(this.plugin.getMessagesConfiguration().tempMuteBroadcast.replace("%player%", args[0]).replace("%admin%", admin).replace("%data%", mute.getMuteDuration()).replace("%reason%", reason));
        Player playerMuted = Bukkit.getPlayer(args[0]);
        if(playerMuted != null) {
            ChatUtil.sendMessage(playerMuted, mute.replaceMute());
        }
    }
}
