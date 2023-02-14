package fr.slophil.ultimatestorageplus.utils;

import fr.slophil.ultimatestorageplus.UltimateStoragePlus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Formatter;

public class GuiManager {

    private Storage owner;
    private Inventory inventory;
    private String state;

    private final ItemStack pullItemStack = new ItemBuilder(Material.ENDER_PEARL).setDisplayName(ChatColor.YELLOW + "Pull").setGlow(true).build();
    private final ItemStack dropItemStack = new ItemBuilder(Material.ENDER_EYE).setDisplayName(ChatColor.YELLOW + "Drop").setGlow(true).build();

    public GuiManager(UltimateStoragePlus ultimateStoragePlus) {
    }
    public GuiManager(Storage owner) {
        this.owner = owner;
        int rows = 4 * 9;
        Formatter cap = new Formatter();
        cap.format("%,d", owner.getCapacity());
        String title = "%sUltimate Storage - %s capacity".formatted(ChatColor.YELLOW, cap);

        this.inventory = Bukkit.createInventory(owner.getInventoryHolder(), rows, title);
        setup(this.inventory, owner);
    }

    private void setup(Inventory inventory, Storage owner) {
        for (int slot = 27; slot < 4*9; slot++) {
            inventory.setItem(slot, new ItemBuilder(Material.BARRIER).setDisplayName(ChatColor.BLACK + "").setGlow(true).build());
        }

        if (owner.getItemStack() != null) {
            Formatter amount = new Formatter();
            amount.format("%,d", owner.getAmount());
            String name = "%sContains %s of %s".formatted(ChatColor.YELLOW, amount, owner.getItemStack().getType());
            inventory.setItem(28, new ItemBuilder(Material.MAP).setDisplayName(name).setGlow(true).build());
        }
        this.setState("pull");

    }

    public void setState(String state) {
        this.state = state;
        if (state.equals("drop")) {
            this.inventory.setItem(27, dropItemStack);
            return;
        }
        this.inventory.setItem(27, pullItemStack);
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public Storage getStorage(){
        return this.owner;
    }

    public String getState() {
        return this.state;
    }
}
