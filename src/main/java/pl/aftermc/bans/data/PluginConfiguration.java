package pl.aftermc.bans.data;

import com.google.common.collect.ImmutableMap;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Exclude;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.exception.OkaeriException;
import eu.okaeri.validator.annotation.Min;

import java.util.Map;

@Header("Konfiguracja pluginu AfteroweBany")
@Header("Serwer Discord na którym pomagamy z konfiguracją serwera: https://discord.com/invite/rXQMsRXYRc")
@Header("Dołącz na serwer już teraz! :)")
@Header(" ")
@Header("Prefix uprawnień można dowolnie zmieniać.")
@Header(" ")
@Header("Uprawnienia ogólne:")
@Header("aftermc.notifyupdate - Informacja o nowej wersji pluginu po wejściu na serwer")
@Header("aftermc.afterowebany - Dostęp do komendy /afterowebany")
@Header(" ")
@Header("Uprawnienia banów:")
@Header("aftermc.bannedtryjoin - Informacja gdy zbanowany gracz chce wejść na serwer")
@Header("aftermc.bannediptryjoin - Informacja gdy zbanowane ip chce wejść na serwer")
@Header("aftermc.ban - Dostęp do komendy /ban")
@Header("aftermc.banip - Dostęp do komendy /banip")
@Header("aftermc.tempban - Dostęp do komendy /tempban")
@Header("aftermc.tempbanip - Dostęp do komendy /tempbanip")
@Header("aftermc.unban - Dostęp do komendy /unban")
@Header("aftermc.unbanip - Dostęp do komendy /unbanip")
@Header("aftermc.unbanall - Dostęp do komendy /unbanall")
@Header("aftermc.baninfo - Dostęp do komendy /baninfo")
@Header("aftermc.fastban - Dostęp do komendy /fastban")
@Header(" ")
@Header("Uprawnienia blacklisty:")
@Header("aftermc.blacklisttryjoin - Informacja gdy zablokowany gracz chce wejść na serwer")
@Header("aftermc.blacklist - Dostęp do komendy /blacklist")
@Header("aftermc.tempblacklist - Dostęp do komendy /tempblacklist")
@Header("aftermc.unblacklist - Dostęp do komendy /unblacklist")
@Header(" ")
@Header("Uprawnienia wyciszeń:")
@Header("aftermc.mutetrychat - Informacja gdy wyciszony gracz chce pisać na czacie")
@Header("aftermc.mute - Dostęp do komendy /mute")
@Header("aftermc.tempmute - Dostęp do komendy /tempmute")
@Header("aftermc.unmute - Dostęp do komendy /unmute")
@Header("aftermc.unmuteall - Dostęp do komendy /unmuteall")
@Header("aftermc.muteinfo - Dostęp do komendy /muteinfo")
@Header("aftermc.mute.bypass - Gracz mimo wyciszenia może pisać na czacie")
@Header(" ")
@Header("Uprawnienia kicka:")
@Header("aftermc.kick - Dostęp do komendy /kick")
public final class PluginConfiguration extends OkaeriConfig {

    @Comment("Prefix uprawnień")
    public String permissionPrefix = "aftermc";
    @Comment("Powód bana/wyciszenia gdy jego powód nie jest podany")
    public String noReasonBan = "Złamanie regulaminu!";

    @Comment("Czy administracja ma zostać powiadomiona gdy zbanowany gracz/ip próbuje wejść na serwer?")
    public boolean bannedPlayerTryJoin = true;
    @Comment("Czy administracja ma zostać powiadomiona gdy gracz na czarnej liście próbuje wejść na serwer?")
    public boolean blacklistPlayerTryJoin = true;

    @Comment("Czy administracja ma zostać powiadomiona gdy wyciszony gracz próbuje pisać na czacie?")
    public boolean mutePlayerTryWrite = true;

    @Comment("Konfiguracja szybkiego zbanowania gracza")
    @Comment("Komendą /fastban można otrzymać patyk i kliknąć nim na gracza")
    @Comment("Otworzy nam się menu w którym można wybrać za co ma zostać ukarany gracz")
    @Comment("")
    @Comment("Ile slotów ma mieć GUI z karami?")
    @Min(1)
    public int guiSize = 3;
    @Comment("Lista kar")
    @Comment("_ to spacja")
    @Comment("command - Komenda do wykonania")
    @Comment("%player% - Zmienna z nickiem gracza")
    public Map<String, FastPlayerBan> fastPlayerBan = ImmutableMap.of(
            "Ban_za_KillAure", new FastPlayerBan("/tempban %player% 5d Zostałeś zbanowany za killaure!"),
            "Ban_za_Nukera", new FastPlayerBan("/tempban %player% 1d Zostałeś zbanowany za nukera!"),
            "Ban_za_BunnyHopa", new FastPlayerBan("/tempban %player% 3d Zostałeś zbanowany za bunnyhopa!"),
            "Wyciszenie_za_spam", new FastPlayerBan("/tempmute %player% 30min Zostałeś wyciszony za spam!")
    );

    @Comment("Typ bazy danych")
    @Comment("FLAT - Pliki YAML")
    @Comment("MYSQL - Baza danych (zalecane)")
    public String databaseType = "FLAT";

    @Comment("Co ile minut bany mają być zapisywane?")
    @Comment("Jeśli chcesz to wyłączyć wpisz -1")
    @Comment("Zalecana wartość 60 (w przypadku FLAT), 30 (w przypadku MYSQL)")
    public int autoSave = 60;
    @Exclude
    public int autoSaveTicks;

    @Comment("Konfiguracja bazy danych MySQL")
    @Comment("mysqlHost - Host bazy danych")
    @Comment("mysqlPort - Port bazy danych")
    @Comment("mysqlUser - Użytkownik bazy danych")
    @Comment("mysqlPassword - Hasło bazy danych")
    @Comment("mysqlDatabase - Nazwa bazy danych")
    @Comment("mysqlTable - Nazwa tabelki z danymi")
    @Comment("mysqlTimeout - Czas prób połączenia z bazą (zalecane minimum 30000)")
    @Comment("mysqlUseSSL - Czy połączenie ma być szyfrowane? (Jeśli localhost ustaw na false)")
    @Comment("mysqlPoolSize - Ile połączeń ma być otwartych do bazy? (Zalecane minimum 5, ustaw -1 aby plugin sam dobrał optymalną liczbę połączeń)")
    public String mysqlHost = "localhost";
    public int mysqlPort = 3306;
    public String mysqlUser = "root";
    public String mysqlPassword = "";
    public String mysqlDatabase = "minecraft";
    public String mysqlTableBans = "bans";
    public String mysqlTableBlacklist = "blacklist";
    public String mysqlTableBansIP = "bansip";
    public String mysqlTableMutes = "mutes";
    public int mysqlTimeout = 30000;
    public boolean mysqlUseSSL = false;
    public int mysqlPoolSize = 5;


    public static class FastPlayerBan extends OkaeriConfig {
        public String command;
        public FastPlayerBan(final String command) {
            this.command = command;
        }
        public String getCommand() {
            return this.command;
        }
    }

    @Override
    public OkaeriConfig load() throws OkaeriException {
        super.load();
        this.autoSaveTicks = this.autoSave * 60 * 20;
        return this;
    }
}
