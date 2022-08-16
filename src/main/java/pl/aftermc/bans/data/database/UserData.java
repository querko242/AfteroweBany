package pl.aftermc.bans.data.database;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import pl.aftermc.bans.AfteroweBany;
import pl.aftermc.bans.object.User;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserData {

    private final Map<String, User> players;
    private File file;
    private YamlConfiguration yamlConfiguration;

    public UserData(final AfteroweBany plugin) {
        this.players = new HashMap<>();
        this.file = new File(plugin.getDataFolder(), "players.yml");
        if(!this.file.exists()) {
            try{
                this.file.createNewFile();
            }catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        this.yamlConfiguration = YamlConfiguration.loadConfiguration(this.file);
    }

    public void checkIfExist(final Player player) {
        if(this.players.containsKey(player.getName().toLowerCase())) {
            User user = this.get(player.getName());
            if(user.getIP() != player.getAddress().getAddress().getHostAddress()) {
                user.setIP(player.getAddress().getAddress().getHostAddress());
                this.yamlConfiguration.set("players." + player.getName().toLowerCase() + ".ip", user.getIP());
                this.saveFile();
            }
            return;
        }
        this.players.put(player.getName().toLowerCase(), new User(player));
    }

    public User get(final String playerName) {
        return this.players.get(playerName.toLowerCase());
    }

    public void load() {
        ConfigurationSection cs1 = this.yamlConfiguration.getConfigurationSection("players");
        if(cs1 == null) return;
        for(String playerName : cs1.getKeys(false)){
            ConfigurationSection configurationSection = cs1.getConfigurationSection(playerName);
            if(System.currentTimeMillis() >= configurationSection.getLong("expires")) {
                this.yamlConfiguration.set("players." + playerName, null);
                this.saveFile();
                continue;
            }
            this.players.put(playerName, new User(playerName, configurationSection));
        }
        Bukkit.getOnlinePlayers().forEach(this::checkIfExist);
    }

    public void saveFile() {
        try {
            this.yamlConfiguration.save(this.file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void save(){
        for(User user : this.players.values()) {
            if(!user.isChanges()) continue;
            if(user.isExpires()) {
                this.yamlConfiguration.set("players." + user.getPlayerName(), null);
                continue;
            }
            this.yamlConfiguration.set("players." + user.getPlayerName() + ".ip", user.getIP());
            this.yamlConfiguration.set("players." + user.getPlayerName() + ".playerName", user.getPlayerName());
            this.yamlConfiguration.set("players." + user.getPlayerName() + ".expires", user.getExpires());
        }
        this.saveFile();
    }
}
