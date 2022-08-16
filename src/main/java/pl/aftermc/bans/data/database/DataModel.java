package pl.aftermc.bans.data.database;

import pl.aftermc.bans.object.Ban;
import pl.aftermc.bans.object.BanIP;
import pl.aftermc.bans.object.Blacklist;
import pl.aftermc.bans.object.Mute;

public interface DataModel {

    void load();
    void save();
    void save(final Ban ban);
    void save(final Blacklist blacklist);
    void save(final BanIP banip);
    void save(final Mute mute);
    void deleteAllBans();
    void deleteAllMutes();
    void delete(final Ban ban);
    void delete(final Blacklist blacklist);
    void delete(final BanIP banip);
    void delete(final Mute mute);
    void shutdown();
}
