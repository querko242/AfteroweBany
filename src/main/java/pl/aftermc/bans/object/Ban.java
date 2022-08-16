package pl.aftermc.bans.object;

import org.bukkit.configuration.ConfigurationSection;
import pl.aftermc.bans.AfteroweBany;
import pl.aftermc.bans.util.TimeUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class Ban {

    private final String playerName;
    private final String reason;
    private long banDuration;
    private final long banDate;
    private final String banAdmin;
    private boolean changes;

    public Ban(final String playerName, final String reason, final long banDuration, final String banAdmin) {
        this.playerName = playerName;
        this.reason = reason;
        this.banDuration = banDuration;
        if(banDuration != -1) {
            this.banDuration = banDuration + System.currentTimeMillis();
        }
        this.banDate = System.currentTimeMillis();
        this.banAdmin = banAdmin;
        this.changes = true;
    }

    public Ban(ResultSet result) throws SQLException {
        this.playerName = result.getString("playerName");
        this.reason = result.getString("reason");
        this.banDuration = result.getLong("duration");
        this.banDate = result.getLong("date");
        this.banAdmin = result.getString("admin");
        this.changes = false;
    }

    public Ban(final String playerName, ConfigurationSection cs) {
        this.playerName = playerName;
        this.reason = cs.getString("reason");
        this.banDuration = cs.getLong("banDuration");
        this.banDate = cs.getLong("banDate");
        this.banAdmin = cs.getString("banAdmin");
        this.changes = false;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public String getReason() {
        return this.reason;
    }

    public long getLongBanDuration() {
        return this.banDuration;
    }

    public String getBanDuration() {
        return TimeUtil.getDate(this.banDuration);
    }

    public String getBanDate() {
        return TimeUtil.getDate(this.banDate);
    }

    public long getLongBanDate() {
        return this.banDate;
    }

    public String getBanAdmin() {
        return this.banAdmin;
    }

    public boolean isPerm() {
        return this.banDuration == -1;
    }

    public boolean isExpire() {
        if(this.isPerm()) return false;
        return System.currentTimeMillis() >= this.banDuration;
    }

    public boolean isChanges() {
        return this.changes;
    }

    public void setChanges(boolean changes) {
        this.changes = changes;
    }

    public String replaceBan() {
        if(this.isPerm()) {
            String permBan = AfteroweBany.getPlugin().getMessagesConfiguration().permBan;
            permBan = permBan.replace("%admin%", this.banAdmin);
            permBan = permBan.replace("%data%", this.getBanDate());
            permBan = permBan.replace("%reason%", this.reason);
            return permBan;
        }
        String tempBan = AfteroweBany.getPlugin().getMessagesConfiguration().tempBan;
        tempBan = tempBan.replace("%admin%", this.banAdmin);
        tempBan = tempBan.replace("%data%", this.getBanDate());
        tempBan = tempBan.replace("%reason%", this.reason);
        tempBan = tempBan.replace("%expire%", this.getBanDuration());
        return tempBan;
    }
}
