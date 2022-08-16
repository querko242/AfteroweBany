package pl.aftermc.bans.util;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

public class UpdatePlugin {

    private final JavaPlugin plugin;

    public UpdatePlugin(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void getVersion(final Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            try (InputStream inputStream = new URL("https://raw.githubusercontent.com/querko242/AfteroweBany/main/version.txt").openStream(); Scanner scanner = new Scanner(inputStream)) {
                consumer.accept(new BufferedReader(new InputStreamReader(inputStream)).readLine());
            } catch (IOException exception) {
                this.plugin.getLogger().info("Błąd podczas sprawdzania aktualizacji: " + exception.getMessage());
            }
        });
    }
}
