package pl.aftermc.bans.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import pl.aftermc.bans.AfteroweBany;
import pl.aftermc.bans.lib.mf.annotations.Command;
import pl.aftermc.bans.lib.mf.annotations.Default;
import pl.aftermc.bans.lib.mf.annotations.Permission;
import pl.aftermc.bans.lib.mf.annotations.SubCommand;
import pl.aftermc.bans.lib.mf.base.CommandBase;
import pl.aftermc.bans.object.BanIP;
import pl.aftermc.bans.object.User;
import pl.aftermc.bans.util.ChatUtil;
import pl.aftermc.bans.util.StringUtil;
import pl.aftermc.bans.util.TimeUtil;

@Command("tempbanip")
public class TempBanIPCommand extends CommandBase {

    public TempBanIPCommand(final AfteroweBany plugin){
        plugin.getCommandManager().register(this);
        this.plugin = plugin;
    }
    private final AfteroweBany plugin;

    @Default
    @SubCommand("tempban-ip")
    @Permission("tempbanip")
    public void tempbanCommand(final CommandSender sender, final String[] args) {
        if(args.length < 2) {
            ChatUtil.sendMessage(sender, this.plugin.getMessagesConfiguration().usageCommand.replace("%usage%", "/tempbanip <gracz/ip> <czas np. 5min> <powód>"));
            return;
        }
        String IP = args[0];
        String bannedPlayerName = args[0];
        if(this.plugin.getUserData().get(args[0]) != null) {
            User bannedUser = this.plugin.getUserData().get(args[0]);
            IP = bannedUser.getIP();
            bannedPlayerName = bannedUser.getPlayerName();
        }

        if(this.plugin.getPluginData().getBanIP(IP) != null) {
            ChatUtil.sendMessage(sender, this.plugin.getMessagesConfiguration().ipAlreadyBanned);
            return;
        }
        String reason = this.plugin.getPluginConfiguration().noReasonBan;
        if(args.length >= 3) {
            reason = StringUtil.stringBuilder(args, 2);
        }

        long banDuration = TimeUtil.getTimeWithString(args[1]);
        BanIP ban = new BanIP(IP, bannedPlayerName, reason, banDuration, sender.getName());
        this.plugin.getPluginData().addBanIP(IP, ban);
        this.plugin.getDatabase().save(ban);
        String finalIP = IP;
        Bukkit.getOnlinePlayers().stream().filter(player -> player.getAddress().getAddress().getHostAddress().equals(finalIP)).forEach(banned -> banned.kickPlayer(ChatUtil.fixColor(ban.replaceBan())));
        ChatUtil.sendMessage(sender, this.plugin.getMessagesConfiguration().tempBannedIP.replace("%ip%", finalIP).replace("%reason%", reason).replace("%data%", ban.getBanDate()));
    }
}
