package pl.aftermc.bans.data.database;

import pl.aftermc.bans.AfteroweBany;
import pl.aftermc.bans.object.Ban;
import pl.aftermc.bans.object.BanIP;
import pl.aftermc.bans.object.Blacklist;
import pl.aftermc.bans.object.Mute;

import java.util.HashMap;
import java.util.Map;

public final class BanData {

    private final AfteroweBany plugin;
    private final Map<String, Ban> bans;
    private final Map<String, Blacklist> blacklist;
    private final Map<String, BanIP> bansip;
    private final Map<String, Mute> mutes;

    public BanData(final AfteroweBany plugin) {
        this.plugin = plugin;
        this.bans = new HashMap<>();
        this.blacklist = new HashMap<>();
        this.bansip = new HashMap<>();
        this.mutes = new HashMap<>();
    }

    public Map<String, Ban> getBans() {
        return this.bans;
    }
    public Map<String, Blacklist> getBlacklist() {
        return this.blacklist;
    }
    public Map<String, BanIP> getBansIP() {
        return this.bansip;
    }
    public Map<String, Mute> getMutes() {
        return this.mutes;
    }

    public void addBan(final Ban ban) {
        if(this.bans.containsKey(ban.getPlayerName()) || this.bans.containsValue(ban)) return;
        this.bans.put(ban.getPlayerName(), ban);
    }
    public void addBlacklist(final Blacklist blacklist) {
        if(this.blacklist.containsKey(blacklist.getPlayerName()) || this.blacklist.containsValue(blacklist)) return;
        this.blacklist.put(blacklist.getPlayerName(), blacklist);
    }
    public void addBanIP(final BanIP banip) {
        if(this.bansip.containsKey(banip.getIP()) || this.bansip.containsValue(banip)) return;
        this.bansip.put(banip.getIP(), banip);
    }
    public void addBanIP(final String ip, final BanIP banip) {
        if(this.bansip.containsKey(ip) || this.bansip.containsValue(banip)) return;
        this.bansip.put(ip, banip);
    }
    public void addMute(final Mute mute) {
        if(this.mutes.containsKey(mute.getPlayerName()) || this.mutes.containsValue(mute)) return;
        this.mutes.put(mute.getPlayerName(), mute);
    }

    public void removeBan(final Ban ban) {
        this.bans.remove(ban.getPlayerName());
    }
    public void removeBlacklist(final Blacklist blacklist) {
        this.blacklist.remove(blacklist.getPlayerName());
    }
    public void removeBan(final BanIP banip) {
        this.bansip.remove(banip.getIP());
    }
    public void removeMute(final Mute mute) {
        this.mutes.remove(mute.getPlayerName());
    }

    public Ban getBan(final String playerName){
        return this.bans.values().parallelStream().filter(user -> user.getPlayerName().equalsIgnoreCase(playerName)).findFirst().orElse(null);
    }
    public Blacklist getBlacklist(final String playerName){
        return this.blacklist.values().parallelStream().filter(user -> user.getPlayerName().equalsIgnoreCase(playerName)).findFirst().orElse(null);
    }
    public BanIP getBanIP(final String ip) {
        return this.bansip.values().parallelStream().filter(banip -> banip.getIP().equalsIgnoreCase(ip) || banip.getPlayerName().equalsIgnoreCase(ip)).findFirst().orElse(null);
    }
    public Mute getMute(final String playerName){
        return this.mutes.values().parallelStream().filter(user -> user.getPlayerName().equalsIgnoreCase(playerName)).findFirst().orElse(null);
    }

    public void unbanAll() {
        this.plugin.getDatabase().deleteAllBans();
        this.bans.clear();
    }
    public void unmuteAll() {
        this.plugin.getDatabase().deleteAllMutes();
        this.mutes.clear();
    }
}
