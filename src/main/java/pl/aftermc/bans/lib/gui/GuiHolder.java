package pl.aftermc.bans.lib.gui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class GuiHolder implements InventoryHolder {

    private final GuiWindow guiWindow;
    private final Map<Integer, Consumer<InventoryClickEvent>> actions;
    private Inventory inventory;

    public GuiHolder(GuiWindow guiWindow) {
        this.guiWindow = guiWindow;
        this.actions = new HashMap<>();
    }

    public void handleClick(InventoryClickEvent event) {
        actions.getOrDefault(event.getRawSlot(), e -> e.setCancelled(true)).accept(event);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public GuiWindow getGuiWindow() {
        return guiWindow;
    }

    public void setActionOnSlot(Integer slot, Consumer<InventoryClickEvent> consumer) {
        actions.put(slot, consumer != null ? consumer : event -> {});
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}