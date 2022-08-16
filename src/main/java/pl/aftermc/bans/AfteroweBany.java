package pl.aftermc.bans;

import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.validator.okaeri.OkaeriValidator;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import org.bukkit.plugin.java.JavaPlugin;
import pl.aftermc.bans.command.*;
import pl.aftermc.bans.data.MessagesConfiguration;
import pl.aftermc.bans.data.PluginConfiguration;
import pl.aftermc.bans.data.database.BanData;
import pl.aftermc.bans.data.database.DataModel;
import pl.aftermc.bans.data.database.UserData;
import pl.aftermc.bans.data.database.flat.FlatDataModel;
import pl.aftermc.bans.data.database.mysql.MySQLDataModel;
import pl.aftermc.bans.lib.gui.GuiActionHandler;
import pl.aftermc.bans.lib.mf.base.CommandManager;
import pl.aftermc.bans.listener.PlayerChat;
import pl.aftermc.bans.listener.PlayerInteract;
import pl.aftermc.bans.listener.PlayerJoin;
import pl.aftermc.bans.listener.PlayerPreLogin;
import pl.aftermc.bans.task.AutosaveTask;
import pl.aftermc.bans.util.UpdatePlugin;

import java.io.File;
import java.util.ArrayList;

public class AfteroweBany extends JavaPlugin {

    private static AfteroweBany plugin;
    private boolean newPluginUpdate = false;
    private CommandManager commandManager;

    private PluginConfiguration pluginConfiguration;
    private MessagesConfiguration messagesConfiguration;

    private BanData pluginData;
    private UserData userData;

    private DataModel dataModel;

    @Override
    public void onDisable() {
        this.dataModel.shutdown();
        this.userData.save();
    }

    @Override
    public void onEnable() {
        plugin = this;
        this.checkPluginUpdate();
        this.loadConfiguration();
        this.registerCommands();
        this.registerListeners();

        this.pluginData = new BanData(this);
        this.userData = new UserData(this);

        this.dataModel = (this.pluginConfiguration.databaseType.equalsIgnoreCase("mysql") ? new MySQLDataModel(this) : new FlatDataModel(this));
        this.dataModel.load();
        this.userData.load();

        new AutosaveTask(this);
    }
    public void loadConfiguration() {
        if(!this.getDataFolder().exists()) this.getDataFolder().mkdir();
        this.pluginConfiguration = ConfigManager.create(PluginConfiguration.class, (it) -> {
            it.withConfigurer(new OkaeriValidator(new YamlBukkitConfigurer(), true));
            it.withBindFile(new File(this.getDataFolder(), "config.yml"));
            it.saveDefaults();
            it.load(true);
        });
        this.messagesConfiguration = ConfigManager.create(MessagesConfiguration.class, (it) -> {
            it.withConfigurer(new OkaeriValidator(new YamlBukkitConfigurer(), true));
            it.withBindFile(new File(this.getDataFolder(), "messages.yml"));
            it.saveDefaults();
            it.load(true);
        });
    }

    private void registerCommands() {
        this.commandManager = new CommandManager(this);
        this.commandManager.getCompletionHandler().register("#bannedplayers", input -> new ArrayList<>(this.pluginData.getBans().keySet()));
        this.commandManager.getCompletionHandler().register("#blacklistplayers", input -> new ArrayList<>(this.pluginData.getBlacklist().keySet()));
        this.commandManager.getCompletionHandler().register("#bannedips", input -> new ArrayList<>(this.pluginData.getBansIP().keySet()));
        this.commandManager.getCompletionHandler().register("#mutedplayers", input -> new ArrayList<>(this.pluginData.getMutes().keySet()));
        this.commandManager.hideTabComplete(true);

        new AfteroweBanyCommand(this);
        new BanCommand(this);
        new BanInfoCommand(this);
        new BanIPInfoCommand(this);
        new BlacklistCommand(this);
        new BlacklistInfoCommand(this);
        new BanIPCommand(this);
        new FastBanCommand(this);
        new KickCommand(this);
        new TempBanCommand(this);
        new TempBanIPCommand(this);
        new TempBlacklistCommand(this);
        new UnBanAllCommand(this);
        new UnBanCommand(this);
        new UnBanIPCommand(this);
        new UnBlacklistCommand(this);
        new MuteCommand(this);
        new MuteInfoCommand(this);
        new TempMuteCommand(this);
        new UnMuteAllCommand(this);
        new UnMuteCommand(this);
    }

    private void registerListeners() {
        new GuiActionHandler(this);
        new PlayerChat(this);
        new PlayerInteract(this);
        new PlayerJoin(this);
        new PlayerPreLogin(this);
    }

    public void checkPluginUpdate() {
        this.getLogger().info("Sprawdzam czy jest nowa wersja pluginu...");
        new UpdatePlugin(this).getVersion(version -> {
            if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                this.getLogger().info("Korzystasz z aktualnej wersji!");
            } else {
                this.newPluginUpdate = true;
                this.getLogger().info("Nowa wersja pluginu jest już dostępna!");
                this.getLogger().info("Wejdź na serwer discord https://aftermc.pl i pobierz najnowszą wersję!");
            }
        });
    }

    public static AfteroweBany getPlugin() {
        return plugin;
    }

    public boolean isNewPluginUpdate() {
        return this.newPluginUpdate;
    }

    public CommandManager getCommandManager() {
        return this.commandManager;
    }

    public PluginConfiguration getPluginConfiguration() {
        return this.pluginConfiguration;
    }

    public MessagesConfiguration getMessagesConfiguration() {
        return this.messagesConfiguration;
    }

    public BanData getPluginData() {
        return this.pluginData;
    }

    public UserData getUserData() {
        return this.userData;
    }

    public DataModel getDatabase() {
        return this.dataModel;
    }
}
