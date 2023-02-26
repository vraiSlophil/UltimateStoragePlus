package fr.slophil.ultimatestorageplus.commands;

import fr.slophil.ultimatestorageplus.UltimateStoragePlus;
import fr.slophil.ultimatestorageplus.utils.BlockType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Stream;

public class UltimateStoragePlusCommand implements TabExecutor {

    private final UltimateStoragePlus main;

    public UltimateStoragePlusCommand(UltimateStoragePlus ultimateStoragePlus) {
        this.main = ultimateStoragePlus;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (!player.hasPermission("ultimatestorageplus.command.use")) {
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
                Stream.of(BlockType.values())
                        .filter(recipe -> recipe.name().equalsIgnoreCase(args[1]))
                        .findFirst()
                        .map(BlockType::getItem)
                        .ifPresentOrElse(
                                item -> player.getInventory().addItem(item),
                                () -> player.sendMessage(main.getPluginPrefix() + main.getCommandUsage())
                        );
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
            for (String element : firstLevel) {
                if (element.startsWith(args[0].toLowerCase())) {
                    toReturn.add(element);
                }
            }
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("give")) {
            List<String> secondLevel = Stream.of(BlockType.values())
                    .map(recipe -> recipe.name().toLowerCase())
                    .toList();
            for (String element : secondLevel) {
                if (element.startsWith(args[1].toLowerCase())) {
                    toReturn.add(element);
                }
            }
        }
        if (args.length > 2) {
            return Collections.emptyList();
        }
        return toReturn;
    }
}
