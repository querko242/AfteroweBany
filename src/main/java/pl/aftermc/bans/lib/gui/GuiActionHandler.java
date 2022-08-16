package pl.aftermc.bans.lib.gui;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import pl.aftermc.bans.AfteroweBany;

public class GuiActionHandler implements Listener {

    public GuiActionHandler(final AfteroweBany plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onClick(final InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        if (inventory.getType() != InventoryType.CHEST) {
            return;
        }

        InventoryHolder inventoryHolder = inventory.getHolder();
        if (!(inventoryHolder instanceof GuiHolder)) {
            return;
        }

        GuiHolder funnyHolder = (GuiHolder) inventoryHolder;

        event.setCancelled(true);
        funnyHolder.handleClick(event);
    }

    @EventHandler
    public void onInteract(final InventoryInteractEvent event) {
        Inventory inventory = event.getInventory();
        if (inventory.getType() != InventoryType.CHEST) {
            return;
        }

        if (!(inventory.getHolder() instanceof GuiHolder)) {
            return;
        }

        event.setCancelled(true);
    }
}