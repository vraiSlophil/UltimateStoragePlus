package fr.slophil.ultimatestorageplus.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Map;

public class GuiManager implements Listener, InventoryHolder {
    private final InventoryHolder inventoryHolder;
    private final Inventory inventory;
    private final Storage storage;
    private boolean dropMode;
    private boolean pullMode;
    private final int inventorySize = 4 * 9;

    public GuiManager(InventoryHolder inventoryHolder, Storage storage) {
        this.inventoryHolder = inventoryHolder;
        this.storage = storage;
        this.dropMode = true;
        this.pullMode = false;
        this.inventory = Bukkit.createInventory(this, this.inventorySize, ChatColor.YELLOW + "Ultimate Storage - " + storage.getMaxQuantity() + "capacity");
        // On crée un nouvel inventaire custom avec 4 lignes de 9 cases chacune
    }

    public void openInventory(Player player) {
        updateInventory(); // On met à jour l'inventaire custom avant de l'afficher
        player.openInventory(this.inventory);
    }

    public void updateInventory() {
        ItemStack storedItem = storage.getStoredItem();
        int storedQuantity = storage.getStoredQuantity();
        int maxQuantity = storage.getMaxQuantity();

        // On ajoute les items stockés dans l'inventaire custom en mode "drop"
        if (this.dropMode) {
            int slot = 0; // On commence à la première case de la première ligne
            while (storedQuantity > 0 && slot < 27) { // Tant qu'on a des items à ajouter et qu'on est dans l'inventaire custom
                ItemStack itemStack = new ItemStack(storedItem);
                int quantityToAdd = Math.min(storedQuantity, storedItem.getMaxStackSize()); // On ajoute un stack complet si possible, sinon on ajoute ce qu'il reste
                itemStack.setAmount(quantityToAdd);
                this.inventory.setItem(slot, itemStack);
                storedQuantity -= quantityToAdd;
                slot++;
            }
        }

        // On ajoute les paramètres de l'inventaire custom dans la dernière ligne
        for (int i = 27; i < 36; i++) {
            this.inventory.setItem(i, new ItemBuilder(Material.BARRIER).setDisplayName(ChatColor.BLACK + "").setGlow(true).build());
        }
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Stocked item : " + (storedItem == null ? "anything" : storedItem.getType().toString()));
        lore.add(ChatColor.GRAY + "Quantity : " + storedQuantity + " / " + maxQuantity);
        ItemStack barrel = new ItemBuilder(Material.BARREL).setDisplayName(ChatColor.YELLOW + "Ultimate Storage - " + maxQuantity + "capacity").setLore(lore).build();
        this.inventory.setItem(30, barrel); // On affiche le tonneau de stockage
        if (this.pullMode) { // Si on est en mode "pull"
            ItemStack pullItem = new ItemBuilder(Material.CHEST).setDisplayName(ChatColor.YELLOW + "Mode pull").build();
            this.inventory.setItem(32, pullItem); // On affiche le bouton pour activer le mode "pull"
        } else { // Si on est en mode "drop"
            ItemStack dropItem = new ItemBuilder(Material.HOPPER).setDisplayName(ChatColor.YELLOW + "Mode drop").build();
            this.inventory.setItem(32, dropItem); // On affiche le bouton pour activer le mode "drop"
        }
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getInventory().equals(this.inventory)) { // Si l'événement concerne l'inventaire custom
            return;
        }
        event.setCancelled(true); // On empêche l'interaction avec l'inventaire normal du joueur
        ItemStack currentItem = event.getCurrentItem();
        if (currentItem == null || currentItem.getType() == Material.AIR) {
            return; // Si l'item cliqué est vide, on ne fait rien
        }
        if (event.getRawSlot() >= 27) { // Si l'item cliqué est dans la dernière ligne
            if (event.getRawSlot() == 32) { // Si on a cliqué sur le bouton pour changer de mode
                dropMode = !dropMode;
                pullMode = !pullMode;
                updateInventory(); // On met à jour l'inventaire custom pour afficher le nouveau mode
            }
            return; // On ne fait rien d'autre si on a cliqué sur un bouton de la dernière ligne
        }
        // On ajoute ou on retire des items du stockage selon le mode et l'action effectuée
        if (this.dropMode && currentItem.isSimilar(this.storage.getStoredItem())) {
            int quantityToAdd = currentItem.getAmount();
            int quantityAdded = storage.addItems(quantityToAdd, currentItem);
            currentItem.setAmount(quantityToAdd - quantityAdded);
        } else if (pullMode) {
            int quantityToRemove = currentItem.getAmount();
            int quantityRemoved = storage.removeItems(quantityToRemove);
            if (quantityRemoved > 0) {
                ItemStack itemStack = storage.getStoredItem();
                itemStack.setAmount(quantityRemoved);
                Player player = (Player) event.getWhoClicked();
                player.getInventory().addItem(itemStack);
            }
        }
        updateInventory(); // On met à jour l'inventaire custom après l'interaction.
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (!event.getInventory().equals(this.inventory)) { // Si l'événement concerne l'inventaire custom
            return;
        }
        event.setCancelled(true); // On empêche l'interaction avec l'inventaire normal du joueur
        Map<Integer, ItemStack> draggedItems = event.getNewItems();
        if (draggedItems.isEmpty()) {
            return; // Si aucun item n'a été déplacé, on ne fait rien
        }
        int quantityMoved = 0;
        for (ItemStack item : draggedItems.values()) {
            if (item.isSimilar(storage.getStoredItem())) {
                quantityMoved += item.getAmount();
            }
        }
        if (dropMode) {
            int quantityAdded = storage.addItems(quantityMoved, draggedItems.get(0));
            if (quantityAdded < quantityMoved) {
                quantityMoved = quantityAdded;
            }
        } else if (pullMode) {
            int quantityRemoved = storage.removeItems(quantityMoved);
            if (quantityRemoved < quantityMoved) {
                quantityMoved = quantityRemoved;
            }
            ItemStack itemStack = new ItemStack(storage.getStoredItem());
            itemStack.setAmount(quantityMoved);
            Player player = (Player) event.getWhoClicked();
            player.getInventory().addItem(itemStack);
        }
        updateInventory(); // On met à jour l'inventaire custom après le déplacement d'items
    }

}











//public class GuiManager {
//
//    private Storage owner;
//    private Inventory inventory;
//    private String state;
//
//    private final ItemStack pullItemStack = new ItemBuilder(Material.ENDER_PEARL).setDisplayName(ChatColor.YELLOW + "Pull").setGlow(true).build();
//    private final ItemStack dropItemStack = new ItemBuilder(Material.ENDER_EYE).setDisplayName(ChatColor.YELLOW + "Drop").setGlow(true).build();
//
//    public GuiManager(UltimateStoragePlus ultimateStoragePlus) {
//    }
//    public GuiManager(Storage owner) {
//        this.owner = owner;
//        int rows = 4 * 9;
//        Formatter cap = new Formatter();
//        cap.format("%,d", owner.getCapacity());
//        String title = "%sUltimate Storage - %s capacity".formatted(ChatColor.YELLOW, cap);
//
//        this.inventory = Bukkit.createInventory(owner.getInventoryHolder(), rows, title);
//        setup(this.inventory, owner);
//    }
//
//    private void setup(Inventory inventory, Storage owner) {
//        for (int slot = 27; slot < 4*9; slot++) {
//            inventory.setItem(slot, new ItemBuilder(Material.BARRIER).setDisplayName(ChatColor.BLACK + "").setGlow(true).build());
//        }
//
//        if (owner.getItemStack() != null) {
//            Formatter amount = new Formatter();
//            amount.format("%,d", owner.getAmount());
//            String name = "%sContains %s of %s".formatted(ChatColor.YELLOW, amount, owner.getItemStack().getType());
//            inventory.setItem(28, new ItemBuilder(Material.MAP).setDisplayName(name).setGlow(true).build());
//        }
//        this.setState("pull");
//
//    }
//
//    public void setState(String state) {
//        this.state = state;
//        if (state.equals("drop")) {
//            this.inventory.setItem(27, dropItemStack);
//            return;
//        }
//        this.inventory.setItem(27, pullItemStack);
//    }
//
//    public Inventory getInventory() {
//        return this.inventory;
//    }
//
//    public Storage getStorage(){
//        return this.owner;
//    }
//
//    public String getState() {
//        return this.state;
//    }
//}
