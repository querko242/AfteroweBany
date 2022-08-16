package pl.aftermc.bans.data.database.mysql;

import com.zaxxer.hikari.HikariDataSource;
import pl.aftermc.bans.AfteroweBany;
import pl.aftermc.bans.data.database.DataModel;
import pl.aftermc.bans.data.PluginConfiguration;
import pl.aftermc.bans.object.Ban;
import pl.aftermc.bans.object.BanIP;
import pl.aftermc.bans.object.Blacklist;
import pl.aftermc.bans.object.Mute;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;

public final class MySQLDataModel implements DataModel {

    private final HikariDataSource dataSource;
    private final AfteroweBany plugin;

    public MySQLDataModel(final AfteroweBany plugin) {
        this.plugin = plugin;
        PluginConfiguration pluginConfiguration = plugin.getPluginConfiguration();

        this.dataSource = new HikariDataSource();
        int poolSize = pluginConfiguration.mysqlPoolSize;
        if(poolSize == -1){
            poolSize = Runtime.getRuntime().availableProcessors() * 2 + 1;
        }

        this.dataSource.setMaximumPoolSize(poolSize);
        this.dataSource.setConnectionTimeout(pluginConfiguration.mysqlTimeout);
        this.dataSource.setJdbcUrl("jdbc:mysql://" + pluginConfiguration.mysqlHost + ":" + pluginConfiguration.mysqlPort + "/" + pluginConfiguration.mysqlDatabase + "?useSSL=" + pluginConfiguration.mysqlUseSSL);
        this.dataSource.setUsername(pluginConfiguration.mysqlUser);
        if (pluginConfiguration.mysqlPassword != null) {
            this.dataSource.setPassword(pluginConfiguration.mysqlPassword);
        }
        this.dataSource.addDataSourceProperty("cachePrepStmts", true);
        this.dataSource.addDataSourceProperty("prepStmtCacheSize", 250);
        this.dataSource.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        this.dataSource.addDataSourceProperty("useServerPrepStmts", true);
        this.plugin.getLogger().info("Korzystam z bazy danych: MYSQL");
    }

    @Override
    public void load() {
        String tableBans = "CREATE TABLE IF NOT EXISTS `" + this.plugin.getPluginConfiguration().mysqlTableBans + "` (" +
                "playerName varchar(50) NOT NULL," +
                "reason TEXT NOT NULL," +
                "duration BIGINT NOT NULL," +
                "date BIGINT NOT NULL," +
                "admin varchar(50) NOT NULL," +
                "PRIMARY KEY(playerName));";
        this.executeUpdate(tableBans);
        String tableBlacklist = "CREATE TABLE IF NOT EXISTS `" + this.plugin.getPluginConfiguration().mysqlTableBlacklist + "` (" +
                "playerName varchar(50) NOT NULL," +
                "reason TEXT NOT NULL," +
                "duration BIGINT NOT NULL," +
                "date BIGINT NOT NULL," +
                "admin varchar(50) NOT NULL," +
                "PRIMARY KEY(playerName));";
        this.executeUpdate(tableBlacklist);
        String tableBansIP = "CREATE TABLE IF NOT EXISTS `" + this.plugin.getPluginConfiguration().mysqlTableBansIP + "` (" +
                "ip varchar(100) NOT NULL," +
                "playerName varchar(50) NOT NULL," +
                "reason TEXT NOT NULL," +
                "duration BIGINT NOT NULL," +
                "date BIGINT NOT NULL," +
                "admin varchar(50) NOT NULL," +
                "PRIMARY KEY(playerName));";
        this.executeUpdate(tableBansIP);
        String tableMutes = "CREATE TABLE IF NOT EXISTS `" + this.plugin.getPluginConfiguration().mysqlTableMutes + "` (" +
                "playerName varchar(50) NOT NULL," +
                "reason TEXT NOT NULL," +
                "duration BIGINT NOT NULL," +
                "date BIGINT NOT NULL," +
                "admin varchar(50) NOT NULL," +
                "PRIMARY KEY(playerName));";
        this.executeUpdate(tableMutes);

        this.executeQuery("SELECT * FROM `" + this.plugin.getPluginConfiguration().mysqlTableBans + "`", result -> {
            int i = 0;
            try {
                while (result.next()) {
                    this.plugin.getPluginData().addBan(new Ban(result));
                    i++;
                }
                this.plugin.getLogger().info("Załadowano " + i + " banów!");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        this.executeQuery("SELECT * FROM `" + this.plugin.getPluginConfiguration().mysqlTableBlacklist + "`", result -> {
            int i = 0;
            try {
                while (result.next()) {
                    this.plugin.getPluginData().addBlacklist(new Blacklist(result));
                    i++;
                }
                this.plugin.getLogger().info("Załadowano " + i + " blacklist!");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        this.executeQuery("SELECT * FROM `" + this.plugin.getPluginConfiguration().mysqlTableBansIP + "`", result -> {
            int i = 0;
            try {
                while (result.next()) {
                    this.plugin.getPluginData().addBanIP(new BanIP(result));
                    i++;
                }
                this.plugin.getLogger().info("Załadowano " + i + " banów IP!");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        this.executeQuery("SELECT * FROM `" + this.plugin.getPluginConfiguration().mysqlTableMutes + "`", result -> {
            int i = 0;
            try {
                while (result.next()) {
                    this.plugin.getPluginData().addMute(new Mute(result));
                    i++;
                }
                this.plugin.getLogger().info("Załadowano " + i + " wyciszonych graczy!");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
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
    public void save(Ban ban) {
        String insert = "INSERT INTO `" + this.plugin.getPluginConfiguration().mysqlTableBans + "` VALUES(" +
                "'%playerName%'," +
                "'%reason%'," +
                "'%duration%'," +
                "'%date%'," +
                "'%admin%'" +
                ") ON DUPLICATE KEY UPDATE " +
                "reason='%reason%'," +
                "duration='%duration%'," +
                "date='%date%'," +
                "admin='%admin%';";
        insert = insert.replace("%playerName%", ban.getPlayerName());
        insert = insert.replace("%reason%", ban.getReason());
        insert = insert.replace("%duration%", Long.toString(ban.getLongBanDuration()));
        insert = insert.replace("%date%", Long.toString(ban.getLongBanDate()));
        insert = insert.replace("%admin%", ban.getBanAdmin());
        this.executeUpdate(insert);
        ban.setChanges(false);
    }

    @Override
    public void save(Blacklist blacklist) {
        String insert = "INSERT INTO `" + this.plugin.getPluginConfiguration().mysqlTableBlacklist + "` VALUES(" +
                "'%playerName%'," +
                "'%reason%'," +
                "'%duration%'," +
                "'%date%'," +
                "'%admin%'" +
                ") ON DUPLICATE KEY UPDATE " +
                "reason='%reason%'," +
                "duration='%duration%'," +
                "date='%date%'," +
                "admin='%admin%';";
        insert = insert.replace("%playerName%", blacklist.getPlayerName());
        insert = insert.replace("%reason%", blacklist.getReason());
        insert = insert.replace("%duration%", Long.toString(blacklist.getLongBlacklistDuration()));
        insert = insert.replace("%date%", Long.toString(blacklist.getLongBanDate()));
        insert = insert.replace("%admin%", blacklist.getBanAdmin());
        this.executeUpdate(insert);
        blacklist.setChanges(false);
    }

    @Override
    public void save(BanIP banip) {
        String insert = "INSERT INTO `" + this.plugin.getPluginConfiguration().mysqlTableBansIP + "` VALUES(" +
                "'%ip%'," +
                "'%playername%'," +
                "'%reason%'," +
                "'%duration%'," +
                "'%date%'," +
                "'%admin%'" +
                ") ON DUPLICATE KEY UPDATE " +
                "playerName='%playername%'," +
                "reason='%reason%'," +
                "duration='%duration%'," +
                "date='%date%'," +
                "admin='%admin%';";
        insert = insert.replace("%ip%", banip.getIP());
        insert = insert.replace("%playername%", banip.getPlayerName());
        insert = insert.replace("%reason%", banip.getReason());
        insert = insert.replace("%duration%", Long.toString(banip.getLongBanDuration()));
        insert = insert.replace("%date%", Long.toString(banip.getLongBanDate()));
        insert = insert.replace("%admin%", banip.getBanAdmin());
        this.executeUpdate(insert);
        banip.setChanges(false);
    }

    @Override
    public void save(Mute mute) {
        String insert = "INSERT INTO `" + this.plugin.getPluginConfiguration().mysqlTableMutes + "` VALUES(" +
                "'%playerName%'," +
                "'%reason%'," +
                "'%duration%'," +
                "'%date%'," +
                "'%admin%'" +
                ") ON DUPLICATE KEY UPDATE " +
                "reason='%reason%'," +
                "duration='%duration%'," +
                "date='%date%'," +
                "admin='%admin%';";
        insert = insert.replace("%playerName%", mute.getPlayerName());
        insert = insert.replace("%reason%", mute.getReason());
        insert = insert.replace("%duration%", Long.toString(mute.getLongMuteDuration()));
        insert = insert.replace("%date%", Long.toString(mute.getLongMuteDate()));
        insert = insert.replace("%admin%", mute.getMuteAdmin());
        this.executeUpdate(insert);
        mute.setChanges(false);
    }

    @Override
    public void deleteAllBans() {
        this.executeUpdate("DELETE FROM `" + this.plugin.getPluginConfiguration().mysqlTableBans + "`");
    }
    @Override
    public void deleteAllMutes() {
        this.executeUpdate("DELETE FROM `" + this.plugin.getPluginConfiguration().mysqlTableMutes + "`");
    }

    @Override
    public void delete(Ban ban) {
        this.executeUpdate("DELETE FROM `" + this.plugin.getPluginConfiguration().mysqlTableBans + "` WHERE playerName='" + ban.getPlayerName() + "'");
    }

    @Override
    public void delete(Blacklist blacklist) {
        this.executeUpdate("DELETE FROM `" + this.plugin.getPluginConfiguration().mysqlTableBlacklist + "` WHERE playerName='" + blacklist.getPlayerName() + "'");
    }

    @Override
    public void delete(BanIP banip) {
        this.executeUpdate("DELETE FROM `" + this.plugin.getPluginConfiguration().mysqlTableBansIP + "` WHERE ip='" + banip.getIP() + "'");
    }

    @Override
    public void delete(Mute mute) {
        this.executeUpdate("DELETE FROM `" + this.plugin.getPluginConfiguration().mysqlTableMutes + "` WHERE playerName='" + mute.getPlayerName() + "'");
    }

    public void executeQuery(String query, Consumer<ResultSet> action) {
        try (Connection connection = this.dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet result = statement.executeQuery()) {
            action.accept(result);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public int executeUpdate(String query) {
        try (Connection connection = this.dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            if (statement == null) {
                return 0;
            }
            return statement.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public boolean isConnect() {
        try {
            if (this.dataSource != null && this.dataSource.getConnection() != null) {
                return true;
            }
        } catch (Exception ex) {
            return false;
        }
        return false;
    }

    @Override
    public void shutdown() {
        this.save();
        if(this.isConnect()) {
            this.dataSource.close();
        }
    }
}
