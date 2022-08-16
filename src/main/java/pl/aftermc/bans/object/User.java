package pl.aftermc.bans.object;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public final class User {

    private String playerName;

    private String ip;
    private long expires;
    private boolean changes;
    public User(final Player player) {
        this.playerName = player.getName().toLowerCase();
        this.ip = player.getAddress().getAddress().getHostAddress();
        this.expires = System.currentTimeMillis() + 432000000; // wygasa po 5 dniach
        this.changes = true;
    }
    public User(final String playerName, final ConfigurationSection cs) {
        this.ip = cs.getString("ip");
        this.playerName = playerName;
        this.expires = cs.getLong("expires");
        this.changes = false;
    }

    public String getPlayerName() {
        return this.playerName;
    }
    public String getIP() {
        return this.ip;
    }

    public long getExpires() {
        return expires;
    }

    public boolean isExpires() {
        return System.currentTimeMillis() >= this.expires;
    }

    public boolean isChanges() {
        return this.changes;
    }

    public void setIP(String ip) {
        this.ip = ip;
    }
}
