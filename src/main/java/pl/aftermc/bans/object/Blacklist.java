package pl.aftermc.bans.object;

import org.bukkit.configuration.ConfigurationSection;
import pl.aftermc.bans.AfteroweBany;
import pl.aftermc.bans.util.TimeUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class Blacklist {

    private final String playerName;
    private final String reason;
    private long duration;
    private final long date;
    private final String banAdmin;
    private boolean changes;

    public Blacklist(final String playerName, final String reason, final long blacklistDuration, final String blacklistAdmin) {
        this.playerName = playerName;
        this.reason = reason;
        this.duration = blacklistDuration;
        if(blacklistDuration != -1) {
            this.duration = blacklistDuration + System.currentTimeMillis();
        }
        this.date = System.currentTimeMillis();
        this.banAdmin = blacklistAdmin;
        this.changes = true;
    }

    public Blacklist(ResultSet result) throws SQLException {
        this.playerName = result.getString("playerName");
        this.reason = result.getString("reason");
        this.duration = result.getLong("duration");
        this.date = result.getLong("date");
        this.banAdmin = result.getString("admin");
        this.changes = false;
    }

    public Blacklist(final String playerName, ConfigurationSection cs) {
        this.playerName = playerName;
        this.reason = cs.getString("reason");
        this.duration = cs.getLong("duration");
        this.date = cs.getLong("date");
        this.banAdmin = cs.getString("admin");
        this.changes = false;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public String getReason() {
        return this.reason;
    }

    public long getLongBlacklistDuration() {
        return this.duration;
    }

    public String getBanDuration() {
        return TimeUtil.getDate(this.duration);
    }

    public String getBanDate() {
        return TimeUtil.getDate(this.date);
    }

    public long getLongBanDate() {
        return this.date;
    }

    public String getBanAdmin() {
        return this.banAdmin;
    }

    public boolean isPerm() {
        return this.duration == -1;
    }

    public boolean isExpire() {
        if(this.isPerm()) return false;
        return System.currentTimeMillis() >= this.duration;
    }

    public boolean isChanges() {
        return this.changes;
    }

    public void setChanges(boolean changes) {
        this.changes = changes;
    }

    public String replaceBlacklist() {
        if(this.isPerm()) {
            String permBan = AfteroweBany.getPlugin().getMessagesConfiguration().permBlacklist;
            permBan = permBan.replace("%admin%", this.banAdmin);
            permBan = permBan.replace("%data%", this.getBanDate());
            permBan = permBan.replace("%reason%", this.reason);
            return permBan;
        }
        String tempBan = AfteroweBany.getPlugin().getMessagesConfiguration().tempBlacklist;
        tempBan = tempBan.replace("%admin%", this.banAdmin);
        tempBan = tempBan.replace("%data%", this.getBanDate());
        tempBan = tempBan.replace("%reason%", this.reason);
        tempBan = tempBan.replace("%expire%", this.getBanDuration());
        return tempBan;
    }
}
