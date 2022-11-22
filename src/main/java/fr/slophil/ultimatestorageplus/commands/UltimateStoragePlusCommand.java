package fr.slophil.ultimatestorageplus.commands;

import fr.slophil.ultimatestorageplus.UltimateStoragePlus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

public class UltimateStoragePlusCommand implements TabExecutor {

    private final UltimateStoragePlus main;

    public UltimateStoragePlusCommand(UltimateStoragePlus ultimateStoragePlus) {
        this.main = ultimateStoragePlus;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (!player.hasPermission("ultimatestorageplus.command.use")){
                player.sendMessage(main.getPluginPrefix() + main.getPlayerNoPermission());
                return false;
            }

            if (!(label.equalsIgnoreCase("ultimatestorageplus")
                || label.equalsIgnoreCase("usp"))) {
                return false;
            }

            if (args.length < 1) {
                return false;
            }

            if (args[0].equalsIgnoreCase("give")) {

                if (args.length < 2) {
                    return false;
                }
                switch (args[1].toLowerCase()) {
                    case "1kstorageplus" -> {
                        player.getInventory().addItem(main.getRecipeManager().getStorage1k());
                        return true;
                    }
                    case "2kstorageplus" -> {
                        player.getInventory().addItem(main.getRecipeManager().getStorage2k());
                        return true;
                    }
                    case "4kstorageplus" -> {
                        player.getInventory().addItem(main.getRecipeManager().getStorage4k());
                        return true;
                    }
                    case "8kstorageplus" -> {
                        player.getInventory().addItem(main.getRecipeManager().getStorage8k());
                        return true;
                    }
                    case "16kstorageplus" -> {
                        player.getInventory().addItem(main.getRecipeManager().getStorage16k());
                        return true;
                    }
                    case "32kstorageplus" -> {
                        player.getInventory().addItem(main.getRecipeManager().getStorage32k());
                        return true;
                    }
                    case "64kstorageplus" -> {
                        player.getInventory().addItem(main.getRecipeManager().getStorage64k());
                        return true;
                    }
                    case "128kstorageplus" -> {
                        player.getInventory().addItem(main.getRecipeManager().getStorage128k());
                        return true;
                    }
                    case "256kstorageplus" -> {
                        player.getInventory().addItem(main.getRecipeManager().getStorage256k());
                        return true;
                    }
                    case "512kstorageplus" -> {
                        player.getInventory().addItem(main.getRecipeManager().getStorage512k());
                        return true;
                    }
                    case "1mstorageplus" -> {
                        player.getInventory().addItem(main.getRecipeManager().getStorage1M());
                        return true;
                    }
                    case "2mstorageplus" -> {
                        player.getInventory().addItem(main.getRecipeManager().getStorage2M());
                        return true;
                    }
                    case "4mstorageplus" -> {
                        player.getInventory().addItem(main.getRecipeManager().getStorage4M());
                        return true;
                    }
                    case "8mstorageplus" -> {
                        player.getInventory().addItem(main.getRecipeManager().getStorage8M());
                        return true;
                    }
                    case "16mstorageplus" -> {
                        player.getInventory().addItem(main.getRecipeManager().getStorage16M());
                        return true;
                    }
                }
                player.sendMessage(main.getPluginPrefix() + main.getCommandUsage());
                return false;
            }

        } else {
            main.getLogger().log(Level.INFO, main.getMustBePlayer());
            return false;
        }
        return false;
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> toReturn = new ArrayList<>();

        if (args.length == 1) {
            List<String> firstLevel = new ArrayList<>(List.of("give"));

            for (String element : firstLevel){
                if (element.startsWith(args[0])){
                    toReturn.add(element);
                }
            }
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("give")){
            List<String> secondLevel = new ArrayList<>(Arrays.asList("1kStoragePlus", "2kStoragePlus", "4kStoragePlus",
                    "8kStoragePlus", "16kStoragePlus", "32kStoragePlus", "64kStoragePlus", "128kStoragePlus",
                    "256kStoragePlus", "512kStoragePlus", "1MStoragePlus", "2MStoragePlus", "4MStoragePlus",
                    "8MStoragePlus", "16MStoragePlus"));
            for (String element : secondLevel){
                if (element.startsWith(args[1])){
                    toReturn.add(element);
                }
            }
        }

        if (args.length > 2) {
            return null;
        }

        return toReturn;
    }
}
