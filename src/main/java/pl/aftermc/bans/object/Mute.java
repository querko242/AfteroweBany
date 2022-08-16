package pl.aftermc.bans.object;

import org.bukkit.configuration.ConfigurationSection;
import pl.aftermc.bans.AfteroweBany;
import pl.aftermc.bans.util.TimeUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class Mute {

    private final String playerName;
    private final String reason;
    private long muteDuration;
    private final long muteData;
    private final String muteAdmin;
    private boolean changes;

    public Mute(final String playerName, final String reason, final long banDuration, final String banAdmin) {
        this.playerName = playerName;
        this.reason = reason;
        this.muteDuration = banDuration;
        if(banDuration != -1) {
            this.muteDuration = banDuration + System.currentTimeMillis();
        }
        this.muteData = System.currentTimeMillis();
        this.muteAdmin = banAdmin;
        this.changes = true;
    }

    public Mute(ResultSet result) throws SQLException {
        this.playerName = result.getString("playerName");
        this.reason = result.getString("reason");
        this.muteDuration = result.getLong("duration");
        this.muteData = result.getLong("date");
        this.muteAdmin = result.getString("admin");
        this.changes = false;
    }

    public Mute(final String playerName, ConfigurationSection cs) {
        this.playerName = playerName;
        this.reason = cs.getString("reason");
        this.muteDuration = cs.getLong("banDuration");
        this.muteData = cs.getLong("banDate");
        this.muteAdmin = cs.getString("banAdmin");
        this.changes = false;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public String getReason() {
        return this.reason;
    }

    public long getLongMuteDuration() {
        return this.muteDuration;
    }

    public String getMuteDuration() {
        return TimeUtil.getDate(this.muteDuration);
    }

    public String getMuteDate() {
        return TimeUtil.getDate(this.muteData);
    }

    public long getLongMuteDate() {
        return this.muteData;
    }

    public String getMuteAdmin() {
        return this.muteAdmin;
    }

    public boolean isPerm() {
        return this.muteDuration == -1;
    }

    public boolean isExpire() {
        if(this.isPerm()) return false;
        return System.currentTimeMillis() >= this.muteDuration;
    }

    public boolean isChanges() {
        return this.changes;
    }

    public void setChanges(boolean changes) {
        this.changes = changes;
    }

    public String replaceMute() {
        if(this.isPerm()) {
            String permBan = AfteroweBany.getPlugin().getMessagesConfiguration().permMute;
            permBan = permBan.replace("%admin%", this.muteAdmin);
            permBan = permBan.replace("%data%", this.getMuteDate());
            permBan = permBan.replace("%reason%", this.reason);
            return permBan;
        }
        String tempBan = AfteroweBany.getPlugin().getMessagesConfiguration().tempMute;
        tempBan = tempBan.replace("%admin%", this.muteAdmin);
        tempBan = tempBan.replace("%data%", this.getMuteDate());
        tempBan = tempBan.replace("%reason%", this.reason);
        tempBan = tempBan.replace("%expire%", this.getMuteDuration());
        return tempBan;
    }
}
