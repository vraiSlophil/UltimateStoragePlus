package fr.slophil.ultimatestorageplus.utils;

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
    );

    private final ItemStack item;
    private final ShapedRecipe recipe;
    private final NamespacedKey key;

    BlockType(ItemStack item, UnaryOperator<ShapedRecipe> recipeProvider) {
        this.item = item;
        this.key = new NamespacedKey("ultimatestorageplus", name().toLowerCase());
        this.recipe = recipeProvider.apply(new ShapedRecipe(key, item));
    }

    public static void registerRecipes() {
        for (BlockType recipe : values()) {
            Bukkit.addRecipe(recipe.recipe);
        }
    }

    public ItemStack getItem() {
        return item;
    }

    public NamespacedKey getKey() {
        return key;
    }

    public ShapedRecipe getRecipe() {
        return recipe;
    }

    /*public RecipeManager(UltimateStoragePlus ultimateStoragePlus) {

        //setup PersistentDataContainer key
        key = new NamespacedKey(ultimateStoragePlus, "BARRELSTORAGEPLUS");

        //setup Itemstacks for Big storage Barrel
        this.storage2k = new ItemBuilder(Material.BARREL)
                .setPersistentDataContainer(key, PersistentDataType.INTEGER, 2048)
                .setDisplayName(ChatColor.YELLOW + "Ultimate Storage - 2,048 capacity")
                .setGlow(true).build();
        this.storage4k = new ItemBuilder(Material.BARREL)
                .setPersistentDataContainer(key, PersistentDataType.INTEGER, 4096)
                .setDisplayName(ChatColor.YELLOW + "Ultimate Storage - 4,096 capacity")
                .setGlow(true).build();
        this.storage8k = new ItemBuilder(Material.BARREL)
                .setPersistentDataContainer(key, PersistentDataType.INTEGER, 8192)
                .setDisplayName(ChatColor.YELLOW + "Ultimate Storage - 8,192 capacity")
                .setGlow(true).build();
        this.storage16k = new ItemBuilder(Material.BARREL)
                .setPersistentDataContainer(key, PersistentDataType.INTEGER, 16384)
                .setDisplayName(ChatColor.YELLOW + "Ultimate Storage - 16,384 capacity")
                .setGlow(true).build();
        this.storage32k = new ItemBuilder(Material.BARREL)
                .setPersistentDataContainer(key, PersistentDataType.INTEGER, 32768)
                .setDisplayName(ChatColor.YELLOW + "Ultimate Storage - 32,768 capacity")
                .setGlow(true).build();
        this.storage64k = new ItemBuilder(Material.BARREL)
                .setPersistentDataContainer(key, PersistentDataType.INTEGER, 65536)
                .setDisplayName(ChatColor.YELLOW + "Ultimate Storage - 65,536 capacity")
                .setGlow(true).build();
        this.storage128k = new ItemBuilder(Material.BARREL)
                .setPersistentDataContainer(key, PersistentDataType.INTEGER, 131072)
                .setDisplayName(ChatColor.YELLOW + "Ultimate Storage - 131,072 capacity")
                .setGlow(true).build();
        this.storage256k = new ItemBuilder(Material.BARREL)
                .setPersistentDataContainer(key, PersistentDataType.INTEGER, 262144)
                .setDisplayName(ChatColor.YELLOW + "Ultimate Storage - 262,144 capacity")
                .setGlow(true).build();
        this.storage512k = new ItemBuilder(Material.BARREL)
                .setPersistentDataContainer(key, PersistentDataType.INTEGER, 524248)
                .setDisplayName(ChatColor.YELLOW + "Ultimate Storage - 524,248 capacity")
                .setGlow(true).build();
        this.storage1M = new ItemBuilder(Material.BARREL)
                .setPersistentDataContainer(key, PersistentDataType.INTEGER, 1048576)
                .setDisplayName(ChatColor.YELLOW + "Ultimate Storage - 1,048,576 capacity")
                .setGlow(true).build();
        this.storage2M = new ItemBuilder(Material.BARREL)
                .setPersistentDataContainer(key, PersistentDataType.INTEGER, 2097152)
                .setDisplayName(ChatColor.YELLOW + "Ultimate Storage - 2,097,152 capacity")
                .setGlow(true).build();
        this.storage4M = new ItemBuilder(Material.BARREL)
                .setPersistentDataContainer(key, PersistentDataType.INTEGER, 4194304)
                .setDisplayName(ChatColor.YELLOW + "Ultimate Storage - 4,194,304 capacity")
                .setGlow(true).build();
        this.storage8M = new ItemBuilder(Material.BARREL)
                .setPersistentDataContainer(key, PersistentDataType.INTEGER, 8388608)
                .setDisplayName(ChatColor.YELLOW + "Ultimate Storage - 8,388,608 capacity")
                .setGlow(true).build();
        this.storage16M = new ItemBuilder(Material.BARREL)
                .setPersistentDataContainer(key, PersistentDataType.INTEGER, 16777216)
                .setDisplayName(ChatColor.YELLOW + "Ultimate Storage - 16,777,216 capacity")
                .setGlow(true).build();

        //setup recipes to craft big storage Barrels
        this.storage1kRecipe = new ShapedRecipe(new NamespacedKey(ultimateStoragePlus, "storage1kRecipe"), getStorage1k())
                .shape("BEB", "IRI", "BEB")
                .setIngredient('B', Material.BARREL)
                .setIngredient('E', Material.EMERALD_BLOCK)
                .setIngredient('I', Material.IRON_BLOCK)
                .setIngredient('R', Material.REDSTONE_BLOCK);
        this.storage2kRecipe = new ShapedRecipe(new NamespacedKey(ultimateStoragePlus, "storage2kRecipe"), getStorage2k())
                .shape("BEB", "IRI", "BEB")
                .setIngredient('B', new RecipeChoice.MaterialChoice.ExactChoice(getStorage1k()))
                .setIngredient('E', Material.EMERALD_BLOCK)
                .setIngredient('I', Material.IRON_BLOCK)
                .setIngredient('R', Material.REDSTONE_BLOCK);
        this.storage4kRecipe = new ShapedRecipe(new NamespacedKey(ultimateStoragePlus, "storage4kRecipe"), getStorage4k())
                .shape("BEB", "IRI", "BEB")
                .setIngredient('B', new RecipeChoice.MaterialChoice.ExactChoice(getStorage2k()))
                .setIngredient('E', Material.EMERALD_BLOCK)
                .setIngredient('I', Material.IRON_BLOCK)
                .setIngredient('R', Material.REDSTONE_BLOCK);
        this.storage8kRecipe = new ShapedRecipe(new NamespacedKey(ultimateStoragePlus, "storage8kRecipe"), getStorage8k())
                .shape("BEB", "IRI", "BEB")
                .setIngredient('B', new RecipeChoice.MaterialChoice.ExactChoice(getStorage4k()))
                .setIngredient('E', Material.EMERALD_BLOCK)
                .setIngredient('I', Material.IRON_BLOCK)
                .setIngredient('R', Material.REDSTONE_BLOCK);
        this.storage16kRecipe = new ShapedRecipe(new NamespacedKey(ultimateStoragePlus, "storage16kRecipe"), getStorage16k())
                .shape("BEB", "IRI", "BEB")
                .setIngredient('B', new RecipeChoice.MaterialChoice.ExactChoice(getStorage8k()))
                .setIngredient('E', Material.EMERALD_BLOCK)
                .setIngredient('I', Material.IRON_BLOCK)
                .setIngredient('R', Material.REDSTONE_BLOCK);
        this.storage32kRecipe = new ShapedRecipe(new NamespacedKey(ultimateStoragePlus, "storage32kRecipe"), getStorage32k())
                .shape("BEB", "IRI", "BEB")
                .setIngredient('B', new RecipeChoice.MaterialChoice.ExactChoice(getStorage16k()))
                .setIngredient('E', Material.EMERALD_BLOCK)
                .setIngredient('I', Material.IRON_BLOCK)
                .setIngredient('R', Material.REDSTONE_BLOCK);
        this.storage64kRecipe = new ShapedRecipe(new NamespacedKey(ultimateStoragePlus, "storage64kRecipe"), getStorage64k())
                .shape("BEB", "IRI", "BEB")
                .setIngredient('B', new RecipeChoice.MaterialChoice.ExactChoice(getStorage32k()))
                .setIngredient('E', Material.EMERALD_BLOCK)
                .setIngredient('I', Material.IRON_BLOCK)
                .setIngredient('R', Material.REDSTONE_BLOCK);
        this.storage128kRecipe = new ShapedRecipe(new NamespacedKey(ultimateStoragePlus, "storage128kRecipe"), getStorage128k())
                .shape("BEB", "IRI", "BEB")
                .setIngredient('B', new RecipeChoice.MaterialChoice.ExactChoice(getStorage64k()))
                .setIngredient('E', Material.EMERALD_BLOCK)
                .setIngredient('I', Material.IRON_BLOCK)
                .setIngredient('R', Material.REDSTONE_BLOCK);
        this.storage256kRecipe = new ShapedRecipe(new NamespacedKey(ultimateStoragePlus, "storage256kRecipe"), getStorage256k())
                .shape("BEB", "IRI", "BEB")
                .setIngredient('B', new RecipeChoice.MaterialChoice.ExactChoice(getStorage128k()))
                .setIngredient('E', Material.EMERALD_BLOCK)
                .setIngredient('I', Material.IRON_BLOCK)
                .setIngredient('R', Material.REDSTONE_BLOCK);
        this.storage512kRecipe = new ShapedRecipe(new NamespacedKey(ultimateStoragePlus, "storage512kRecipe"), getStorage512k())
                .shape("BEB", "IRI", "BEB")
                .setIngredient('B', new RecipeChoice.MaterialChoice.ExactChoice(getStorage256k()))
                .setIngredient('E', Material.EMERALD_BLOCK)
                .setIngredient('I', Material.IRON_BLOCK)
                .setIngredient('R', Material.REDSTONE_BLOCK);
        this.storage1MRecipe = new ShapedRecipe(new NamespacedKey(ultimateStoragePlus, "storage1MRecipe"), getStorage1M())
                .shape("BEB", "IRI", "BEB")
                .setIngredient('B', new RecipeChoice.MaterialChoice.ExactChoice(getStorage512k()))
                .setIngredient('E', Material.EMERALD_BLOCK)
                .setIngredient('I', Material.IRON_BLOCK)
                .setIngredient('R', Material.REDSTONE_BLOCK);
        this.storage2MRecipe = new ShapedRecipe(new NamespacedKey(ultimateStoragePlus, "storage2MRecipe"), getStorage2M())
                .shape("BEB", "IRI", "BEB")
                .setIngredient('B', new RecipeChoice.MaterialChoice.ExactChoice(getStorage1M()))
                .setIngredient('E', Material.EMERALD_BLOCK)
                .setIngredient('I', Material.IRON_BLOCK)
                .setIngredient('R', Material.REDSTONE_BLOCK);
        this.storage4MRecipe = new ShapedRecipe(new NamespacedKey(ultimateStoragePlus, "storage4MRecipe"), getStorage4M())
                .shape("BEB", "IRI", "BEB")
                .setIngredient('B', new RecipeChoice.MaterialChoice.ExactChoice(getStorage2M()))
                .setIngredient('E', Material.EMERALD_BLOCK)
                .setIngredient('I', Material.IRON_BLOCK)
                .setIngredient('R', Material.REDSTONE_BLOCK);
        this.storage8MRecipe = new ShapedRecipe(new NamespacedKey(ultimateStoragePlus, "storage8MRecipe"), getStorage8M())
                .shape("BEB", "IRI", "BEB")
                .setIngredient('B', new RecipeChoice.MaterialChoice.ExactChoice(getStorage8M()))
                .setIngredient('E', Material.EMERALD_BLOCK)
                .setIngredient('I', Material.IRON_BLOCK)
                .setIngredient('R', Material.REDSTONE_BLOCK);
        this.storage16MRecipe = new ShapedRecipe(new NamespacedKey(ultimateStoragePlus, "storage16MRecipe"), getStorage16M())
                .shape("BEB", "IRI", "BEB")
                .setIngredient('B', new RecipeChoice.MaterialChoice.ExactChoice(getStorage8M()))
                .setIngredient('E', Material.EMERALD_BLOCK)
                .setIngredient('I', Material.IRON_BLOCK)
                .setIngredient('R', Material.REDSTONE_BLOCK);

        Bukkit.addRecipe(getStorage1kRecipe());
        Bukkit.addRecipe(getStorage2kRecipe());
        Bukkit.addRecipe(getStorage4kRecipe());
        Bukkit.addRecipe(getStorage8kRecipe());
        Bukkit.addRecipe(getStorage16kRecipe());
        Bukkit.addRecipe(getStorage32kRecipe());
        Bukkit.addRecipe(getStorage64kRecipe());
        Bukkit.addRecipe(getStorage128kRecipe());
        Bukkit.addRecipe(getStorage256kRecipe());
        Bukkit.addRecipe(getStorage512kRecipe());
        Bukkit.addRecipe(getStorage1MRecipe());
        Bukkit.addRecipe(getStorage2MRecipe());
        Bukkit.addRecipe(getStorage4MRecipe());
        Bukkit.addRecipe(getStorage8MRecipe());
        Bukkit.addRecipe(getStorage16MRecipe());
    }*/
}
