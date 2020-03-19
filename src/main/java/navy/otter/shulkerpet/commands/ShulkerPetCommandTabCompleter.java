package navy.otter.shulkerpet.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ShulkerPetCommandTabCompleter implements TabCompleter {

  @Override
  public @Nullable List<String> onTabComplete(@NotNull CommandSender player,
      @NotNull Command command, @NotNull String s, @NotNull String[] args) {
    ArrayList<String> list = new ArrayList<>();
    if (!(player instanceof Player)) {
      return new ArrayList<>();
    }

    if (args.length <= 1) {
      if (player.hasPermission("shulkerpet.ci")) {
        list.add("ci");
      }
      if (player.hasPermission("shulkerpet.create")) {
        list.add("create");
      }
      if (player.hasPermission("shulkerpet.help")) {
        list.add("help");
      }
      if (player.hasPermission("UtilityUI.debugstick")) {
        list.add("debug");
      }
      if (player.hasPermission("UtilityUI.workbench")) {
        list.add("wb");
      }

      return list
          .stream()
          .filter((string) -> string.startsWith(args[0]))
          .collect(Collectors.toList());
    }

    if (args.length <= 2) {
      if (player.hasPermission("UtilityUI.chests")) {
        list.add("add");
        list.add("list");
        list.add("open");
      }

      return list
          .stream()
          .filter((string) -> string.startsWith(args[1]) && args[0].equals("chests"))
          .collect(Collectors.toList());
    }
    return new ArrayList<>();
  }
}
