package fr.slophil.ultimatestorageplus.utils;

import fr.slophil.ultimatestorageplus.UltimateStoragePlus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.persistence.PersistentDataType;

import java.util.function.UnaryOperator;

import static fr.slophil.ultimatestorageplus.UltimateStoragePlus.STORAGE_KEY;

public enum BlockType {

    STORAGE_1K(
            new ItemBuilder(Material.BARREL)
                    .setPersistentDataContainer(STORAGE_KEY, PersistentDataType.INTEGER, 1024)
                    .setDisplayName(ChatColor.YELLOW + "Ultimate Storage - 1,024 capacity")
                    .setGlow(true).build(),
            (recipe) -> recipe
                    .shape("BEB", "IRI", "BEB")
                    .setIngredient('B', Material.BARREL)
                    .setIngredient('E', Material.EMERALD_BLOCK)
                    .setIngredient('I', Material.IRON_BLOCK)
                    .setIngredient('R', Material.REDSTONE_BLOCK)
    ),
    STORAGE_2K(
            new ItemBuilder(Material.BARREL)
                    .setPersistentDataContainer(STORAGE_KEY, PersistentDataType.INTEGER, 2048)
                    .setDisplayName(ChatColor.YELLOW + "Ultimate Storage - 2,048 capacity")
                    .setGlow(true).build(),
            (recipe) -> recipe
                    .shape("BEB", "IRI", "BEB")
                    .setIngredient('B', new RecipeChoice.MaterialChoice.ExactChoice(STORAGE_1K.item))
                    .setIngredient('E', Material.EMERALD_BLOCK)
                    .setIngredient('I', Material.IRON_BLOCK)
                    .setIngredient('R', Material.REDSTONE_BLOCK)
    ),
    STORAGE_4K(
            new ItemBuilder(Material.BARREL)
                    .setPersistentDataContainer(STORAGE_KEY, PersistentDataType.INTEGER, 4096)
                    .setDisplayName(ChatColor.YELLOW + "Ultimate Storage - 4,096 capacity")
                    .setGlow(true).build(),
            (recipe) -> recipe
                    .shape("BEB", "IRI", "BEB")
                    .setIngredient('B', new RecipeChoice.MaterialChoice.ExactChoice(STORAGE_2K.item))
                    .setIngredient('E', Material.EMERALD_BLOCK)
                    .setIngredient('I', Material.IRON_BLOCK)
                    .setIngredient('R', Material.REDSTONE_BLOCK)
    ),
    STORAGE_8K(
            new ItemBuilder(Material.BARREL)
                    .setPersistentDataContainer(STORAGE_KEY, PersistentDataType.INTEGER, 8192)
                    .setDisplayName(ChatColor.YELLOW + "Ultimate Storage - 8,192 capacity")
                    .setGlow(true).build(),
            (recipe) -> recipe
                    .shape("BEB", "IRI", "BEB")
                    .setIngredient('B', new RecipeChoice.MaterialChoice.ExactChoice(STORAGE_4K.item))
                    .setIngredient('E', Material.EMERALD_BLOCK)
                    .setIngredient('I', Material.IRON_BLOCK)
                    .setIngredient('R', Material.REDSTONE_BLOCK)
    ),
    STORAGE_16K(
            new ItemBuilder(Material.BARREL)
                    .setPersistentDataContainer(STORAGE_KEY, PersistentDataType.INTEGER, 16384)
                    .setDisplayName(ChatColor.YELLOW + "Ultimate Storage - 16,384 capacity")
                    .setGlow(true).build(),
            (recipe) -> recipe
                    .shape("BEB", "IRI", "BEB")
                    .setIngredient('B', new RecipeChoice.MaterialChoice.ExactChoice(STORAGE_8K.item))
                    .setIngredient('E', Material.EMERALD_BLOCK)
                    .setIngredient('I', Material.IRON_BLOCK)
                    .setIngredient('R', Material.REDSTONE_BLOCK)
    ),
    STORAGE_32K(
            new ItemBuilder(Material.BARREL)
                    .setPersistentDataContainer(STORAGE_KEY, PersistentDataType.INTEGER, 32768)
                    .setDisplayName(ChatColor.YELLOW + "Ultimate Storage - 32,768 capacity")
                    .setGlow(true).build(),
            (recipe) -> recipe
                    .shape("BEB", "IRI", "BEB")
                    .setIngredient('B', new RecipeChoice.MaterialChoice.ExactChoice(STORAGE_16K.item))
                    .setIngredient('E', Material.EMERALD_BLOCK)
                    .setIngredient('I', Material.IRON_BLOCK)
                    .setIngredient('R', Material.REDSTONE_BLOCK)
    ),
    STORAGE_64K(
            new ItemBuilder(Material.BARREL)
                    .setPersistentDataContainer(STORAGE_KEY, PersistentDataType.INTEGER, 65536)
                    .setDisplayName(ChatColor.YELLOW + "Ultimate Storage - 65,536 capacity")
                    .setGlow(true).build(),
            (recipe) -> recipe
                    .shape("BEB", "IRI", "BEB")
                    .setIngredient('B', new RecipeChoice.MaterialChoice.ExactChoice(STORAGE_32K.item))
                    .setIngredient('E', Material.EMERALD_BLOCK)
                    .setIngredient('I', Material.IRON_BLOCK)
                    .setIngredient('R', Material.REDSTONE_BLOCK)
    ),
    STORAGE_128K(
            new ItemBuilder(Material.BARREL)
                    .setPersistentDataContainer(STORAGE_KEY, PersistentDataType.INTEGER, 131072)
                    .setDisplayName(ChatColor.YELLOW + "Ultimate Storage - 131,072 capacity")
                    .setGlow(true).build(),
            (recipe) -> recipe
                    .shape("BEB", "IRI", "BEB")
                    .setIngredient('B', new RecipeChoice.MaterialChoice.ExactChoice(STORAGE_64K.item))
                    .setIngredient('E', Material.EMERALD_BLOCK)
                    .setIngredient('I', Material.IRON_BLOCK)
                    .setIngredient('R', Material.REDSTONE_BLOCK)
    ),
    STORAGE_256K(
            new ItemBuilder(Material.BARREL)
                    .setPersistentDataContainer(STORAGE_KEY, PersistentDataType.INTEGER, 262144)
                    .setDisplayName(ChatColor.YELLOW + "Ultimate Storage - 262,144 capacity")
                    .setGlow(true).build(),
            (recipe) -> recipe
                    .shape("BEB", "IRI", "BEB")
                    .setIngredient('B', new RecipeChoice.MaterialChoice.ExactChoice(STORAGE_128K.item))
                    .setIngredient('E', Material.EMERALD_BLOCK)
                    .setIngredient('I', Material.IRON_BLOCK)
                    .setIngredient('R', Material.REDSTONE_BLOCK)
    ),
    STORAGE_512K(
            new ItemBuilder(Material.BARREL)
                    .setPersistentDataContainer(STORAGE_KEY, PersistentDataType.INTEGER, 524288)
                    .setDisplayName(ChatColor.YELLOW + "Ultimate Storage - 524,288 capacity")
                    .setGlow(true).build(),
            (recipe) -> recipe
                    .shape("BEB", "IRI", "BEB")
                    .setIngredient('B', new RecipeChoice.MaterialChoice.ExactChoice(STORAGE_256K.item))
                    .setIngredient('E', Material.EMERALD_BLOCK)
                    .setIngredient('I', Material.IRON_BLOCK)
                    .setIngredient('R', Material.REDSTONE_BLOCK)
    ),
    STORAGE_1M(
            new ItemBuilder(Material.BARREL)
                    .setPersistentDataContainer(STORAGE_KEY, PersistentDataType.INTEGER, 1048576)
                    .setDisplayName(ChatColor.GOLD + "Ultimate Storage - 1,048,576 capacity")
                    .setGlow(true).build(),
            (recipe) -> recipe
                    .shape("BEB", "IRI", "BEB")
                    .setIngredient('B', new RecipeChoice.MaterialChoice.ExactChoice(STORAGE_512K.item))
                    .setIngredient('E', Material.EMERALD_BLOCK)
                    .setIngredient('I', Material.IRON_BLOCK)
                    .setIngredient('R', Material.REDSTONE_BLOCK)
    ),
    STORAGE_2M(
            new ItemBuilder(Material.BARREL)
                    .setPersistentDataContainer(STORAGE_KEY, PersistentDataType.INTEGER, 2097152)
                    .setDisplayName(ChatColor.GOLD + "Ultimate Storage - 2,097,152 capacity")
                    .setGlow(true).build(),
            (recipe) -> recipe
                    .shape("BEB", "IRI", "BEB")
                    .setIngredient('B', new RecipeChoice.MaterialChoice.ExactChoice(STORAGE_1M.item))
                    .setIngredient('E', Material.EMERALD_BLOCK)
                    .setIngredient('I', Material.IRON_BLOCK)
                    .setIngredient('R', Material.REDSTONE_BLOCK)
    ),
    STORAGE_4M(
            new ItemBuilder(Material.BARREL)
                    .setPersistentDataContainer(STORAGE_KEY, PersistentDataType.INTEGER, 4194304)
                    .setDisplayName(ChatColor.GOLD + "Ultimate Storage - 4,194,304 capacity")
                    .setGlow(true).build(),
            (recipe) -> recipe
                    .shape("BEB", "IRI", "BEB")
                    .setIngredient('B', new RecipeChoice.MaterialChoice.ExactChoice(STORAGE_2M.item))
                    .setIngredient('E', Material.EMERALD_BLOCK)
                    .setIngredient('I', Material.IRON_BLOCK)
                    .setIngredient('R', Material.REDSTONE_BLOCK)
    ),
    STORAGE_8M(
            new ItemBuilder(Material.BARREL)
                    .setPersistentDataContainer(STORAGE_KEY, PersistentDataType.INTEGER, 8388608)
                    .setDisplayName(ChatColor.GOLD + "Ultimate Storage - 8,388,608 capacity")
                    .setGlow(true).build(),
            (recipe) -> recipe
                    .shape("BEB", "IRI", "BEB")
                    .setIngredient('B', new RecipeChoice.MaterialChoice.ExactChoice(STORAGE_4M.item))
                    .setIngredient('E', Material.EMERALD_BLOCK)
                    .setIngredient('I', Material.IRON_BLOCK)
                    .setIngredient('R', Material.REDSTONE_BLOCK)
    ),
    STORAGE_16M(
            new ItemBuilder(Material.BARREL)
                    .setPersistentDataContainer(STORAGE_KEY, PersistentDataType.INTEGER, 16777216)
                    .setDisplayName(ChatColor.GOLD + "Ultimate Storage - 16,777,216 capacity")
                    .setGlow(true).build(),
            (recipe) -> recipe
                    .shape("BEB", "IRI", "BEB")
                    .setIngredient('B', new RecipeChoice.MaterialChoice.ExactChoice(STORAGE_8M.item))
                    .setIngredient('E', Material.EMERALD_BLOCK)
                    .setIngredient('I', Material.IRON_BLOCK)
                    .setIngredient('R', Material.REDSTONE_BLOCK)
    ),
    STORAGE_32M(
            new ItemBuilder(Material.BARREL)
                    .setPersistentDataContainer(STORAGE_KEY, PersistentDataType.INTEGER, 33554432)
                    .setDisplayName(ChatColor.GOLD + "Ultimate Storage - 33,554,432 capacity")
                    .setGlow(true).build(),
            (recipe) -> recipe
                    .shape("BEB", "IRI", "BEB")
                    .setIngredient('B', new RecipeChoice.MaterialChoice.ExactChoice(STORAGE_16M.item))
                    .setIngredient('E', Material.EMERALD_BLOCK)
                    .setIngredient('I', Material.IRON_BLOCK)
                    .setIngredient('R', Material.REDSTONE_BLOCK)
    ),
    STORAGE_64M(
            new ItemBuilder(Material.BARREL)
                    .setPersistentDataContainer(STORAGE_KEY, PersistentDataType.INTEGER, 67108864)
                    .setDisplayName(ChatColor.GOLD + "Ultimate Storage - 67,108,864 capacity")
                    .setGlow(true).build(),
            (recipe) -> recipe
                    .shape("BEB", "IRI", "BEB")
                    .setIngredient('B', new RecipeChoice.MaterialChoice.ExactChoice(STORAGE_32M.item))
                    .setIngredient('E', Material.EMERALD_BLOCK)
                    .setIngredient('I', Material.IRON_BLOCK)
                    .setIngredient('R', Material.REDSTONE_BLOCK)
    ),
    STORAGE_128M(
            new ItemBuilder(Material.BARREL)
                    .setPersistentDataContainer(STORAGE_KEY, PersistentDataType.INTEGER, 134217728)
                    .setDisplayName(ChatColor.GOLD + "Ultimate Storage - 134,217,728 capacity")
                    .setGlow(true).build(),
            (recipe) -> recipe
                    .shape("BEB", "IRI", "BEB")
                    .setIngredient('B', new RecipeChoice.MaterialChoice.ExactChoice(STORAGE_64M.item))
                    .setIngredient('E', Material.EMERALD_BLOCK)
                    .setIngredient('I', Material.IRON_BLOCK)
                    .setIngredient('R', Material.REDSTONE_BLOCK)
    ),
    STORAGE_256M(
            new ItemBuilder(Material.BARREL)
                    .setPersistentDataContainer(STORAGE_KEY, PersistentDataType.INTEGER, 268435456)
                    .setDisplayName(ChatColor.GOLD + "Ultimate Storage - 268,435,456 capacity")
                    .setGlow(true).build(),
            (recipe) -> recipe
                    .shape("BEB", "IRI", "BEB")
                    .setIngredient('B', new RecipeChoice.MaterialChoice.ExactChoice(STORAGE_128M.item))
                    .setIngredient('E', Material.EMERALD_BLOCK)
                    .setIngredient('I', Material.IRON_BLOCK)
                    .setIngredient('R', Material.REDSTONE_BLOCK)
    );

    private final ItemStack item;
    private final ShapedRecipe recipe;
    private final NamespacedKey key;

    /**
     * @param item           The item to be used in the recipe
     * @param recipeProvider A function that takes a ShapedRecipe and returns a ShapedRecipe
     */
    BlockType(ItemStack item, UnaryOperator<ShapedRecipe> recipeProvider) {
        this.item = item;
        this.key = new NamespacedKey(UltimateStoragePlus.getInstance(), name().toLowerCase());
        this.recipe = recipeProvider.apply(new ShapedRecipe(key, item));
    }

    /**
     * Registers all recipes
     */
    public static void registerRecipes() {
        for (BlockType recipe : values()) {
            Bukkit.addRecipe(recipe.recipe);
        }
    }

    /**
     * @return The ItemStack of the item
     */
    public ItemStack getItem() {
        return item;
    }

    /**
     * @param capacity The capacity of the item
     * @return
     */
    public static BlockType getBlockType(int capacity) {
        for (BlockType blockType : values()) {
            if (blockType.getItem().getItemMeta().getPersistentDataContainer().get(STORAGE_KEY, PersistentDataType.INTEGER) == capacity) {
                return blockType;
            }
        }
        return null;
    }

    /**
     * @return The NamespacedKey of the item
     */
    public NamespacedKey getKey() {
        return key;
    }

    /**
     * @return The ShapedRecipe of the item
     */
    public ShapedRecipe getRecipe() {
        return recipe;
    }

}
