package pl.aftermc.bans.data.database.flat;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.aftermc.bans.AfteroweBany;
import pl.aftermc.bans.data.database.DataModel;
import pl.aftermc.bans.object.Ban;
import pl.aftermc.bans.object.BanIP;
import pl.aftermc.bans.object.Blacklist;
import pl.aftermc.bans.object.Mute;

import java.io.File;
import java.io.IOException;

public class FlatDataModel implements DataModel {

    private final File databaseFile;
    private final YamlConfiguration databaseYaml;
    private final AfteroweBany plugin;

    public FlatDataModel(final AfteroweBany plugin) {
        this.plugin = plugin;
        this.databaseFile = new File(plugin.getDataFolder(), "database.yml");
        try {
            if(!this.databaseFile.exists()) this.databaseFile.createNewFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        this.databaseYaml = YamlConfiguration.loadConfiguration(this.databaseFile);
        this.plugin.getLogger().info("Korzystam z bazy danych: FLAT");
    }

    @Override
    public void load() {
        ConfigurationSection bans = this.databaseYaml.getConfigurationSection("bans");
        if(bans != null) {
            int intBans = 0;
            for(String string : bans.getKeys(false)) {
                ConfigurationSection cs = bans.getConfigurationSection(string);
                this.plugin.getPluginData().addBan(new Ban(string, cs));
                intBans++;
            }
            this.plugin.getLogger().info("Załadowano " + intBans + " banów!");
        } else {
            this.plugin.getLogger().info("Brak banów do załadowania!");
        }

        ConfigurationSection blacklists = this.databaseYaml.getConfigurationSection("blacklist");
        if(blacklists != null) {
            int intBlacklist = 0;
            for(String string : blacklists.getKeys(false)) {
                ConfigurationSection cs = blacklists.getConfigurationSection(string);
                this.plugin.getPluginData().addBlacklist(new Blacklist(string, cs));
                intBlacklist++;
            }
            this.plugin.getLogger().info("Załadowano " + intBlacklist + " blacklist!");
        } else {
            this.plugin.getLogger().info("Brak blacklist do załadowania!");
        }

        ConfigurationSection bansip = this.databaseYaml.getConfigurationSection("bansip");
        if(bansip != null) {
            int intBansIP = 0;
            for(String string : bansip.getKeys(false)) {
                ConfigurationSection cs = bansip.getConfigurationSection(string);
                this.plugin.getPluginData().addBanIP(new BanIP(string.replace(":", "."), cs));
                intBansIP++;
            }
            this.plugin.getLogger().info("Załadowano " + intBansIP + " banów IP!");
        } else {
            this.plugin.getLogger().info("Brak banów IP do załadowania!");
        }

        ConfigurationSection mutes = this.databaseYaml.getConfigurationSection("mutes");
        if(mutes != null) {
            int intMutes = 0;
            for(String string : mutes.getKeys(false)) {
                ConfigurationSection cs = mutes.getConfigurationSection(string);
                this.plugin.getPluginData().addMute(new Mute(string, cs));
                intMutes++;
            }
            this.plugin.getLogger().info("Załadowano " + intMutes + " wyciszonych graczy!");
        } else {
            this.plugin.getLogger().info("Brak wyciszonych graczy do załadowania!");
        }
    }

    @Override
    public void save() {
        int intBans = 0;
        for(Ban ban : this.plugin.getPluginData().getBans().values()) {
            if (ban.isExpire()) {
                this.delete(ban);
                this.plugin.getLogger().info("Ban gracza " + ban.getPlayerName() + " wygasł i zostanie usunięty!");
                continue;
            }
            if(!ban.isChanges()) continue;
            this.save(ban);
            intBans++;
        }
        this.plugin.getLogger().info("Zapisano " + intBans + " banów!");

        int intBlacklist = 0;
        for(Blacklist blacklist : this.plugin.getPluginData().getBlacklist().values()) {
            if (blacklist.isExpire()) {
                this.delete(blacklist);
                this.plugin.getLogger().info("Blacklista gracza " + blacklist.getPlayerName() + " wygasła i zostanie usunięta!");
                continue;
            }
            if(!blacklist.isChanges()) continue;
            this.save(blacklist);
            intBlacklist++;
        }
        this.plugin.getLogger().info("Zapisano " + intBlacklist + " blacklist!");

        int intBansIP = 0;
        for(BanIP banip : this.plugin.getPluginData().getBansIP().values()) {
            if (banip.isExpire()) {
                this.delete(banip);
                this.plugin.getLogger().info("Ban IP " + banip.getIP() + " wygasł i zostanie usunięty!");
                continue;
            }
            if(!banip.isChanges()) continue;
            this.save(banip);
            intBansIP++;
        }
        this.plugin.getLogger().info("Zapisano " + intBansIP + " banów IP!");

        int intMutes = 0;
        for(Mute mute : this.plugin.getPluginData().getMutes().values()) {
            if (mute.isExpire()) {
                this.delete(mute);
                this.plugin.getLogger().info("Wyciszenie gracza " + mute.getPlayerName() + " wygasło i zostanie usunięte!");
                continue;
            }
            if(!mute.isChanges()) continue;
            this.save(mute);
            intMutes++;
        }
        this.plugin.getLogger().info("Zapisano " + intMutes + " wyciszonych graczy!");
    }

    @Override
    public void save(final Ban ban) {
        this.databaseYaml.set("bans." + ban.getPlayerName() + ".reason", ban.getReason());
        this.databaseYaml.set("bans." + ban.getPlayerName() + ".banDuration", ban.getLongBanDuration());
        this.databaseYaml.set("bans." + ban.getPlayerName() + ".banDate", ban.getLongBanDate());
        this.databaseYaml.set("bans." + ban.getPlayerName() + ".banAdmin", ban.getBanAdmin());
        try {
            this.databaseYaml.save(this.databaseFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        ban.setChanges(false);
    }

    @Override
    public void save(Blacklist blacklist) {
        this.databaseYaml.set("blacklist." + blacklist.getPlayerName() + ".reason", blacklist.getReason());
        this.databaseYaml.set("blacklist." + blacklist.getPlayerName() + ".duration", blacklist.getLongBlacklistDuration());
        this.databaseYaml.set("blacklist." + blacklist.getPlayerName() + ".date", blacklist.getLongBanDate());
        this.databaseYaml.set("blacklist." + blacklist.getPlayerName() + ".admin", blacklist.getBanAdmin());
        try {
            this.databaseYaml.save(this.databaseFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        blacklist.setChanges(false);
    }

    @Override
    public void save(BanIP banip) {
        String parseIP = banip.getIP().replace(".", ":");
        this.databaseYaml.set("bansip." + parseIP + ".playerName", banip.getPlayerName());
        this.databaseYaml.set("bansip." + parseIP + ".reason", banip.getReason());
        this.databaseYaml.set("bansip." + parseIP + ".banDuration", banip.getLongBanDuration());
        this.databaseYaml.set("bansip." + parseIP + ".banDate", banip.getLongBanDate());
        this.databaseYaml.set("bansip." + parseIP + ".banAdmin", banip.getBanAdmin());
        try {
            this.databaseYaml.save(this.databaseFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        banip.setChanges(false);
    }

    @Override
    public void save(final Mute mute) {
        this.databaseYaml.set("mutes." + mute.getPlayerName() + ".reason", mute.getReason());
        this.databaseYaml.set("mutes." + mute.getPlayerName() + ".banDuration", mute.getLongMuteDuration());
        this.databaseYaml.set("mutes." + mute.getPlayerName() + ".banDate", mute.getLongMuteDate());
        this.databaseYaml.set("mutes." + mute.getPlayerName() + ".banAdmin", mute.getMuteAdmin());
        try {
            this.databaseYaml.save(this.databaseFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        mute.setChanges(false);
    }

    @Override
    public void deleteAllBans() {
        this.databaseYaml.set("bans", null);
        try {
            this.databaseYaml.save(this.databaseFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public void deleteAllMutes() {
        this.databaseYaml.set("mutes", null);
        try {
            this.databaseYaml.save(this.databaseFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(final Ban ban) {
        this.databaseYaml.set("bans." + ban.getPlayerName(), null);
        try {
            this.databaseYaml.save(this.databaseFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(Blacklist blacklist) {
        this.databaseYaml.set("blacklist." + blacklist.getPlayerName(), null);
        try {
            this.databaseYaml.save(this.databaseFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(BanIP banip) {
        this.databaseYaml.set("bansip." + banip.getIP().replace(".", ":"), null);
        try {
            this.databaseYaml.save(this.databaseFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(final Mute mute) {
        this.databaseYaml.set("mutes." + mute.getPlayerName(), null);
        try {
            this.databaseYaml.save(this.databaseFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void shutdown() {
        this.save();
    }
}
