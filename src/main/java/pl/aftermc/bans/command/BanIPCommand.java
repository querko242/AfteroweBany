package pl.aftermc.bans.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.aftermc.bans.AfteroweBany;
import pl.aftermc.bans.lib.mf.annotations.*;
import pl.aftermc.bans.lib.mf.base.CommandBase;
import pl.aftermc.bans.object.BanIP;
import pl.aftermc.bans.object.User;
import pl.aftermc.bans.util.ChatUtil;
import pl.aftermc.bans.util.StringUtil;

@Command("banip")
public class BanIPCommand extends CommandBase {

    public BanIPCommand(final AfteroweBany plugin){
        plugin.getCommandManager().register(this);
        this.plugin = plugin;
    }
    private final AfteroweBany plugin;

    @Default
    @Permission("banip")
    @SubCommand("ban-ip")
    @Completion("#players")
    public void banIpCommand(final CommandSender sender, final String[] args) {
        if(args.length < 1) {
            ChatUtil.sendMessage(sender, this.plugin.getMessagesConfiguration().usageCommand.replace("%usage%", "/banip <gracz/ip> <powód>"));
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
        if(args.length > 1) {
            reason = StringUtil.stringBuilder(args, 1);
        }
        BanIP ban = new BanIP(IP, bannedPlayerName, reason, -1, sender.getName());
        this.plugin.getPluginData().addBanIP(IP, ban);
        this.plugin.getDatabase().save(ban);
        for(Player player : Bukkit.getOnlinePlayers()) {
            if(player.getAddress().getAddress().getHostAddress().equals(IP)) {
                player.kickPlayer(ChatUtil.fixColor(ban.replaceBan()));
            }
        }
        ChatUtil.sendMessage(sender, this.plugin.getMessagesConfiguration().permBannedIP.replace("%ip%", IP).replace("%reason%", reason));
    }
}
