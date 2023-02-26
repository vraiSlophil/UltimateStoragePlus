package fr.slophil.ultimatestorageplus.utils;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

public class ItemBuilder {

    private ItemStack stack;

    /**
     * Constructor
     *
     * @param mat Material of the item
     */
    public ItemBuilder(Material mat) {
        stack = new ItemStack(mat);
    }

    /**
     * This method is used to get the ItemMeta of the item
     *
     * @return ItemMeta of the item
     */
    public ItemMeta getItemMeta() {
        return stack.getItemMeta();
    }

    /**
     * This method is used to add a glow effect to the item
     *
     * @param glow true if you want to add a glow effect, false if you want to remove it
     * @return ItemBuilder
     */
    public ItemBuilder setGlow(boolean glow) {
        if (glow) {
            addEnchant(Enchantment.FROST_WALKER, 1);
            addItemFlag(ItemFlag.HIDE_ENCHANTS);
        } else {
            ItemMeta meta = getItemMeta();
            for (Enchantment enchantment : meta.getEnchants().keySet()) {
                meta.removeEnchant(enchantment);
            }
        }
        return this;
    }

    /**
     * This method is used to set the ItemMeta of the item
     *
     * @param meta new ItemMeta of item
     * @return ItemBuilder
     */
    public ItemBuilder setItemMeta(ItemMeta meta) {
        stack.setItemMeta(meta);
        return this;
    }

    /**
     * This method is used to set the display name of the item
     *
     * @param displayname new displayed name of the item
     * @return ItemBuilder
     */
    public ItemBuilder setDisplayName(String displayname) {
        ItemMeta meta = getItemMeta();
        meta.setDisplayName(displayname);
        setItemMeta(meta);
        return this;
    }

    /**
     * This method is used to set the lore of the item
     *
     * @param lore new lore of the item
     * @return ItemBuilder
     */
    public ItemBuilder setLore(ArrayList<String> lore) {
        ItemMeta meta = getItemMeta();
        meta.setLore(lore);
        setItemMeta(meta);
        return this;
    }

    /**
     * This method is used to add an enchantment to the item
     *
     * @param enchantment enchantment to add
     * @param level       level of the enchantment
     * @return ItemBuilder
     */
    public ItemBuilder addEnchant(Enchantment enchantment, int level) {
        ItemMeta meta = getItemMeta();
        meta.addEnchant(enchantment, level, true);
        setItemMeta(meta);
        return this;
    }

    /**
     * This method is used to add an item flag to the item
     *
     * @param flag item flag to add
     * @return ItemBuilder
     */
    public ItemBuilder addItemFlag(ItemFlag flag) {
        ItemMeta meta = getItemMeta();
        meta.addItemFlags(flag);
        setItemMeta(meta);
        return this;
    }

    /**
     * Thismethod is used to add a PersistentDataContainer to the item
     *
     * @param namespacedKey      NamespacedKey of the PersistentDataContainer
     * @param persistentDataType PersistentDataType of the PersistentDataContainer
     * @param value              value of the PersistentDataContainer
     * @return
     */
    public ItemBuilder setPersistentDataContainer(NamespacedKey namespacedKey, PersistentDataType persistentDataType, Integer value) {

        ItemMeta meta = getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        if (!data.has(namespacedKey, PersistentDataType.INTEGER)) {
            data.set(namespacedKey, persistentDataType, value);
        } else {
            return null;
        }
        setItemMeta(meta);
        return this;
    }

    /**
     * This method is used to build the item
     * You have to use this method at the end of the creation of your ItemBuilder
     *
     * @return ItemStack
     */
    public ItemStack build() {
        return stack;
    }

}