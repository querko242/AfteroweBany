package pl.aftermc.bans.command;

import org.bukkit.command.CommandSender;
import pl.aftermc.bans.AfteroweBany;
import pl.aftermc.bans.lib.mf.annotations.Command;
import pl.aftermc.bans.lib.mf.annotations.Default;
import pl.aftermc.bans.lib.mf.annotations.Permission;
import pl.aftermc.bans.lib.mf.base.CommandBase;
import pl.aftermc.bans.util.ChatUtil;

@Command("afterowebany")
public class AfteroweBanyCommand extends CommandBase {

    public AfteroweBanyCommand(final AfteroweBany plugin){
        plugin.getCommandManager().register(this);
        this.plugin = plugin;
    }
    private final AfteroweBany plugin;

    @Default
    @Permission("afterowebany")
    public void banCommand(final CommandSender sender, final String[] args) {
        if(args.length < 1) {
            this.plugin.checkPluginUpdate();
            ChatUtil.sendMessage(sender, this.plugin.getMessagesConfiguration().usageCommand.replace("%usage%", "\n&a/afterowebany reload &8- &7Przeładowanie &6config.yml &7i &6messages.yml " +
                    "\n&a/afterowebany saveall &8- &7Zapisuje bany, bany ip, mute \n\n" +
                    "&bAfteroweBany v" + this.plugin.getDescription().getVersion() + "" +
                    "\n" + (this.plugin.isNewPluginUpdate() ? "&cDostępna jest nowa wersja pluginu!\n" +
            "&6Wejdź na serwer discord &b&lAFTERMC.PL &6i pobierz najnowszą wersję!\n" +
            "&bLink do Discorda: https://discord.com/invite/rXQMsRXYRc" : "&aKorzystasz z aktualnej wersji pluginu! :)")));
            return;
        }
        if(args[0].equalsIgnoreCase("reload")) {
            this.plugin.loadConfiguration();
            ChatUtil.sendMessage(sender, this.plugin.getMessagesConfiguration().reloadPluginSuccess);
            return;
        }
        if(args[0].equalsIgnoreCase("saveall")) {
            this.plugin.getDatabase().save();
            ChatUtil.sendMessage(sender, this.plugin.getMessagesConfiguration().saveAll);
        }
    }
}
