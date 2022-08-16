package pl.aftermc.bans.util;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;


public final class ChatUtil {

    public static String fixColor(String s){
        return ChatColor.translateAlternateColorCodes('&', s.replace("%>", "\u00BB").replace("<%", "\u00AB").replace("*", "\u2022"));
    }

    public static List<String> fixColor(final List<String> messages) {
        return messages.parallelStream().map(ChatUtil::fixColor).collect(Collectors.toList());
    }

    public static boolean sendMessage(CommandSender sender, String message){
        sender.sendMessage(fixColor(message));
        return true;
    }

    public static void sendURLMessage(Player player, String message, String url) {
        TextComponent component = new TextComponent(TextComponent.fromLegacyText(fixColor(message)));
        component.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
        player.spigot().sendMessage(component);
    }

    public static boolean sendBroadcast(String message, String permission){
        Bukkit.getOnlinePlayers().stream().filter(player -> player.hasPermission(permission)).forEach(player -> sendMessage(player, message));
        return true;
    }

    public static boolean sendBroadcast(String message){
        Bukkit.broadcastMessage(fixColor(message));
        return true;
    }
}
