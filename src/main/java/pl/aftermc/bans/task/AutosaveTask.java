package pl.aftermc.bans.task;

import org.bukkit.Bukkit;
import pl.aftermc.bans.AfteroweBany;

public final class AutosaveTask implements Runnable {

    public AutosaveTask(final AfteroweBany plugin) {
        this.plugin = plugin;
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, this, this.plugin.getPluginConfiguration().autoSaveTicks, this.plugin.getPluginConfiguration().autoSaveTicks);
    }
    private final AfteroweBany plugin;

    @Override
    public void run() {
        this.plugin.getLogger().info("Automatyczny zapis danych...");
        this.plugin.getDatabase().save();
    }
}
