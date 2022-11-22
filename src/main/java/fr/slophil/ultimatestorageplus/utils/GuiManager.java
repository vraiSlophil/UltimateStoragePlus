package fr.slophil.ultimatestorageplus.utils;

import fr.slophil.ultimatestorageplus.UltimateStoragePlus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Barrel;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.BlockInventoryHolder;
import org.bukkit.inventory.Inventory;

import java.util.Formatter;

public class GuiManager {

    private Storage owner;
    private int rows;
    private String title;
    private Inventory inventory;

    private UltimateStoragePlus ultimateStoragePlus;

    public GuiManager(UltimateStoragePlus ultimateStoragePlus) {
        this.ultimateStoragePlus = ultimateStoragePlus;
    }
    public GuiManager(Storage owner) {
        this.owner = owner;
        this.rows = 4*9;
        Formatter cap = new Formatter();
        cap.format("%,d", owner.getCapacity());
        this.title = "%sUltimate Storage - %s capacity".formatted(ChatColor.YELLOW, cap);

        this.inventory = Bukkit.createInventory(owner.getInventoryHolder(), this.rows, this.title);
        setup(this.inventory, owner);
    }

    private void setup(Inventory inventory, Storage owner) {
        for (int slot = 27; slot < 26+9; slot++) {
            inventory.setItem(slot, new ItemBuilder(Material.BARRIER).setDisplayName(ChatColor.BLACK + "").setGlow(true).build());
        }

        if (owner.getItemStack() != null) {
            Formatter amount = new Formatter();
            amount.format("%,d", owner.getAmount());
            String name = "%sContains %s of %s".formatted(ChatColor.YELLOW, amount, owner.getItemStack().getType());
            inventory.setItem(27, new ItemBuilder(Material.ENDER_EYE).setDisplayName(ChatColor.YELLOW + "Drop").setGlow(true).build());
            inventory.setItem(28, new ItemBuilder(Material.MAP).setDisplayName(name).setGlow(true).build());
        }

    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public Storage getStorage(){
        return this.owner;
    }
}
