package pl.aftermc.bans.data;

import eu.okaeri.configs.OkaeriConfig;

public final class MessagesConfiguration extends OkaeriConfig {

    public String playerAlreadyBanned = "&8%> &cTen gracz jest już zbanowany!";
    public String playerAlreadyBlacklist = "&8%> &cTen gracz jest już dodany na czarną listę!";
    public String ipAlreadyBanned = "&8%> &cTo IP jest już zbanowane!";
    public String playerNotBanned = "&8%> &cTen gracz nie jest zbanowany!";
    public String playerNotBlacklist = "&8%> &cTen gracz nie jest na czarnej liście!";
    public String ipNotBanned = "&8%> &cTo IP nie jest zbanowane!";
    public String playerNotOnline = "&8%> &cTen gracz jest offline!";
    public String permBanBroadcast = "&8%> &cGracz &3%player% &czostał zbanowany przez &6%admin%&c! &cPowód: &6%reason%&c.";
    public String permBlacklistBroadcast = "&8%> &cGracz &3%player% &czostał dodany na czarną listę przez &6%admin%&c! &cPowód: &6%reason%&c.";
    public String permBannedIP = "&8%> &cZbanowałeś na zawsze IP &6%ip% &cz powodem: &6%reason%&c!";
    public String tempBanBroadcast = "&8%> &cGracz &3%player% &czostał zbanowany przez &6%admin%&c! &cPowód: &6%reason%&c. Ban wygasa &6%data%&c.";
    public String tempBlacklistBroadcast = "&8%> &cGracz &3%player% &czostał dodany na czarną listę przez &6%admin%&c! &cPowód: &6%reason%&c. Wygasa &6%data%&c.";
    public String tempBannedIP = "&8%> &cZbanowałeś IP &3%ip% &cz powodem: &6%reason%&c. Ban wygasa &6%data%&c.";
    public String unBanBroadcast = "&8%> &cGracz &3%player% &czostał odbanowany przez &6%admin%&c!";
    public String unBlacklistBroadcast = "&8%> &cGracz &3%player% &czostał usunięty z czarnej listy przez &6%admin%&c!";
    public String unbanIP = "&8%> &cOdbanowałeś IP &3%ip%&c!";
    public String unBanAllBroadcast = "&8%> &cAdministrator &6%admin% &codbanował wszystkich graczy!";
    public String bannedPlayerLogin = "&8%> &cGracz &3%player% &cpróbował wejść na serwer ale jest zbanowany. Informacje o banie &6/baninfo %player%";
    public String blacklistPlayerLogin = "&8%> &cGracz &3%player% &cpróbował wejść na serwer ale jest na czarnej liście. Informacje &6/blacklistinfo %player%";
    public String bannedIPLogin = "&8%> &cIP &3%ip% &cjest zbanowane ale ktoś próbował wejść na serwer! Informacje o banie &6/banipinfo %ip%";
    public String bannedPlayerLoginExpire = "&8%> &cGraczowi &3%player% &cwygasł tymczasowy ban!";
    public String blacklistPlayerLoginExpire = "&8%> &cGraczowi &3%player% &cwygasła tymczasowa czarna lista!";
    public String bannedIPLoginExpire = "&8%> &cTymczasowy ban na IP &3%ip% &cwygasł!";
    public String kickPlayerBroadcast = "&8%> &cGracz &3%player% &czostał wyrzucony z serwera przez &6%admin%&c! Powód: &6%reason%";

    public String playerAlreadyMuted = "&8%> &cTen gracz jest już wyciszony!";
    public String playerNotMuted = "&8%> &cTen gracz nie jest wyciszony!";
    public String permMuteBroadcast = "&8%> &cGracz &3%player% &czostał wyciszony przez &6%admin%&c! &cPowód: &6%reason%&c.";
    public String tempMuteBroadcast = "&8%> &cGracz &3%player% &czostał wyciszony przez &6%admin%&c! &cPowód: &6%reason%&c. Wygasa &6%data%&c.";
    public String unMuteBroadcast = "&8%> &cGracz &3%player% &czostał odciszony przez &6%admin%&c!";
    public String unMuteAllBroadcast = "&8%> &cAdministrator &6%admin% &codciszył wszystkich graczy!";
    public String mutedPlayerTryChat = "&8%> &cGracz &3%player% &csię odezwać ale jest wyciszony. Informacje o wyciszeniu &6/muteinfo %player%";
    public String mutedPlayerTryChatExpire = "&8%> &cGraczowi &3%player% &cwygasło tymczasowe wyciszenie!";
    public String usageCommand = "&8%> &aPrawidłowe użycie komendy: &7%usage%";
    public String getFastBanStick = "&8%> &aDostałeś patyk do banowania graczy! &bMiłej zabawy :D";

    public String reloadPluginSuccess = "&8%> &aPliki &6config.yml &ai &6messages.yml &azostały przeładowane!";
    public String saveAll = "&8%> &aZapisano &6bany&a, &6bany ip &ai &6mute!";

    public String banInfo = "&7Informacje o banie &3%player%\n" +
            " &8* &7Zbanowano dnia: &3%banDate%\n" +
            " &8* &7Przez: &3%banAdmin%\n" +
            " &8* &7Powód: &3%reason%\n" +
            " &8* &7Wygasa: &3%expire%\n" +
            "&8&m---------------------------------";
    public String blacklistInfo = "&7Informacje o blackliście &3%player%\n" +
            " &8* &7Nadano dnia: &3%banDate%\n" +
            " &8* &7Przez: &3%banAdmin%\n" +
            " &8* &7Powód: &3%reason%\n" +
            " &8* &7Wygasa: &3%expire%\n" +
            "&8&m---------------------------------";
    public String banIPInfo = "&7Informacje o banie IP &3%ip%\n" +
            " &8* &7Zbanowano dnia: &3%banDate%\n" +
            " &8* &7Przez: &3%banAdmin%\n" +
            " &8* &7Powód: &3%reason%\n" +
            " &8* &7Wygasa: &3%expire%\n" +
            "&8&m---------------------------------";

    public String permBan = "&7Zostałeś &4zbanowany &7przez &6%admin%\n" +
            "&7Nadano: &c%data%\n" +
            "&7Powód: &c%reason%\n" +
            "&7Blokada konta jest &cna zawsze\n" +
            "&f\n" +
            "&7Discord: &3https://dc.aftermc.pl\n" +
            "&7Itemshop: &3https://aftermc.pl\n" +
            "&f\n" +
            "&3&oTe wiadomości możesz zmienić w pliku: &bplugins/AfteroweBany/messages.yml";
    public String permBlacklist = "&7Zostałeś dodany na &4czarną listę &7przez &6%admin%\n" +
            "&7Nadano: &c%data%\n" +
            "&7Powód: &c%reason%\n" +
            "&f\n" +
            "&7Discord: &3https://dc.aftermc.pl\n" +
            "&7Itemshop: &3https://aftermc.pl\n" +
            "&f\n" +
            "&3&oTe wiadomości możesz zmienić w pliku: &bplugins/AfteroweBany/messages.yml";
    public String permBanIP = "&7Twoje IP zostało &4zbanowane &7przez &6%admin%\n" +
            "&7Nadano: &c%data%\n" +
            "&7Powód: &c%reason%\n" +
            "&7Blokada IP jest &cna zawsze\n" +
            "&f\n" +
            "&7Discord: &3https://dc.aftermc.pl\n" +
            "&7Itemshop: &3https://aftermc.pl\n" +
            "&f\n" +
            "&3&oTe wiadomości możesz zmienić w pliku: &bplugins/AfteroweBany/messages.yml";
    public String tempBan = "&7Zostałeś &4zbanowany &7przez &6%admin%\n" +
            "&7Nadano: &c%data%\n" +
            "&7Powód: &c%reason%\n" +
            "&7Blokada wygasa: &c%expire%\n" +
            "&f\n" +
            "&7Discord: &3https://dc.aftermc.pl\n" +
            "&7Itemshop: &3https://aftermc.pl\n" +
            "&f\n" +
            "&3&oTe wiadomości możesz zmienić w pliku: &bplugins/AfteroweBany/messages.yml";
    public String tempBlacklist = "&7Zostałeś dodany na &4czarną listę &7przez &6%admin%\n" +
            "&7Nadano: &c%data%\n" +
            "&7Powód: &c%reason%\n" +
            "&7Blokada wygasa: &c%expire%\n" +
            "&f\n" +
            "&7Discord: &3https://dc.aftermc.pl\n" +
            "&7Itemshop: &3https://aftermc.pl\n" +
            "&f\n" +
            "&3&oTe wiadomości możesz zmienić w pliku: &bplugins/AfteroweBany/messages.yml";
    public String tempBanIP = "&7Twoje IP zostało &4zbanowane &7przez &6%admin%\n" +
            "&7Nadano: &c%data%\n" +
            "&7Powód: &c%reason%\n" +
            "&7Blokada wygasa: &c%expire%\n" +
            "&f\n" +
            "&7Discord: &3https://dc.aftermc.pl\n" +
            "&7Itemshop: &3https://aftermc.pl\n" +
            "&f\n" +
            "&3&oTe wiadomości możesz zmienić w pliku: &bplugins/AfteroweBany/messages.yml";

    public String muteInfo = "&7Informacje o wyciszeniu &3%player%\n" +
            " &8* &7Wyciszony dnia: &3%banDate%\n" +
            " &8* &7Przez: &3%banAdmin%\n" +
            " &8* &7Powód: &3%reason%\n" +
            " &8* &7Wygasa: &3%expire%\n" +
            "&8&m---------------------------------";
    public String permMute = "&7Zostałeś &4wyciszony &7przez &6%admin%\n" +
            "&7Nadano: &c%data%\n" +
            "&7Powód: &c%reason%\n" +
            "&7Blokada jest &cna zawsze\n" +
            "&f\n" +
            "&7Discord: &3https://dc.aftermc.pl\n" +
            "&7Itemshop: &3https://aftermc.pl\n" +
            "&f\n" +
            "&3&oTe wiadomości możesz zmienić w pliku: &bplugins/AfteroweBany/messages.yml";
    public String tempMute = "&7Zostałeś &4wyciszony &7przez &6%admin%\n" +
            "&7Nadano: &c%data%\n" +
            "&7Powód: &c%reason%\n" +
            "&7Blokada wygasa: &c%expire%\n" +
            "&f\n" +
            "&7Discord: &3https://dc.aftermc.pl\n" +
            "&7Itemshop: &3https://aftermc.pl\n" +
            "&f\n" +
            "&3&oTe wiadomości możesz zmienić w pliku: &bplugins/AfteroweBany/messages.yml";
    public String unMute = "\n\n\n\n&a&lZnowu możesz pisać na czacie!";

    public String kickPlayer = "&cZostałeś &4wyrzucony &cz serwera przez &6%admin%\n" + "&7Powód: &c%reason%";
}
