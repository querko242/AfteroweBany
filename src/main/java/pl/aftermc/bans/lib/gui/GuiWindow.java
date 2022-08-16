package pl.aftermc.bans.lib.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.aftermc.bans.util.ChatUtil;

import java.util.function.Consumer;

public class GuiWindow {

    private final Inventory   inv;
    private final GuiHolder holder;
    private ItemStack undoPage;
    private ItemStack nextPage;

    public GuiWindow(String name, int rows) {
        this.holder = new GuiHolder(this);
        this.inv = Bukkit.createInventory(this.holder, rows > 6 ? 6 * 9 : rows * 9, ChatUtil.fixColor(name));
        this.holder.setInventory(this.inv);

        //this.undoPage = new ItemBuilder(XMaterial.ACACIA_BUTTON).setName("&7<% &cWróc do poprzedniej strony").getItem();
        //this.nextPage = new ItemBuilder(XMaterial.WARPED_BUTTON).setName("&aNastępna strona &7%>").getItem();
    }

    public void setItem(int slot, ItemStack item) {
        this.inv.setItem(slot, item);
    }

    public void setItem(int slot, ItemStack item, Consumer<InventoryClickEvent> consumer) {
        this.holder.setActionOnSlot(slot, consumer);
        this.inv.setItem(slot, item);
    }

    public void setItem(int x, int y, ItemStack item, Consumer<InventoryClickEvent> customer) {
        setItem(x + y * 9, item, customer);
    }

    public void setToNextFree(ItemStack item, Consumer<InventoryClickEvent> consumer) {
        this.setToNextFree(0, item, consumer);
    }

    public void setToNextFree(int start, final ItemStack item, Consumer<InventoryClickEvent> consumer) {
        for (int slot = start; slot < inv.getSize(); slot++) {
            if (inv.getItem(slot) == null) {
                //this.inv.setItem(slot, item);
                this.setItem(slot, item, consumer);
                break;
            }
        }
    }

    public void open(HumanEntity entity) {
        entity.openInventory(inv);
    }

    //TODO: Use this method in the future. (Add ItemStack to configuration for fill inventory)
    public void fillEmpty(final ItemStack itemStack) {
        for (int slot = 0; slot < inv.getSize(); slot++) {
            if (inv.getItem(slot) == null) {
                inv.setItem(slot, itemStack);
            }
        }
    }

    public ItemStack getNextPage() {
        return nextPage;
    }

    public ItemStack getUndoPage() {
        return undoPage;
    }

}