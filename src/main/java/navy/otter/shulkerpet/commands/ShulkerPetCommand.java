package navy.otter.shulkerpet.commands;

import java.util.Arrays;
import java.util.Iterator;
import navy.otter.shulkerpet.config.Configuration;
import navy.otter.shulkerpet.entities.ControlItem;
import navy.otter.shulkerpet.ShulkerPetPlugin;
import navy.otter.shulkerpet.entities.ShulkerPetSpawnEgg;
import navy.otter.shulkerpet.worker.ShulkerPetManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ShulkerPetCommand implements CommandExecutor {

  Configuration config = ShulkerPetPlugin.getConfiguration();

  @Override
  public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command,
      @NotNull String s, @NotNull String[] strings) {
    if(!(commandSender instanceof Player)) {
      return false;
    }

    Player player = (Player) commandSender;
    Iterator<String> arg = Arrays.asList(strings).iterator();
    String option = arg.hasNext() ? arg.next() : "";
    String verifier = arg.hasNext() ? arg.next() : "";

    switch(option.toLowerCase()) {
      case "":
        if(player.hasPermission("shulkerpet.help")) {
          displayHelpMessage(player);
        }
        break;
      case "pack":
        if(player.hasPermission("shulkerpet.pack")) {
          packShulker(player);
        }
        break;
      case "ci":
        if(player.hasPermission("shulkerpet.ci")) {
          giveControlItem(player);
        }
        break;
      case "help":
        displayHelpMessage(player);
        break;
      case "create":
        if(player.hasPermission("shulkerpet.create")) {
          if(verifier.equalsIgnoreCase("egg")) {
            giveShulkerEgg(player);
          } else {
            createShulker(player);
          }
        }
        break;
      case "delete":
        if(player.hasPermission("shulkerpet.delete")) {
          deleteShulker(player);
        }
        break;
      default:
        player.sendMessage(config.getUnknownCmdMsg());
        break;
    }
    return false;
  }

  public void displayHelpMessage(Player player) {
    player.sendMessage("Help Info for ShulkerPet");
  }

  public void createShulker(Player player) {
    ShulkerPetManager spManager = ShulkerPetPlugin.getMainInstance().getSpManager();
    spManager.createShulker(player);
  }

  public void deleteShulker(Player player) {
    ShulkerPetManager spManager = ShulkerPetPlugin.getMainInstance().getSpManager();
    spManager.deleteShulkerPet(player);
  }

  public void giveShulkerEgg(Player player) {
    ItemStack spawnEgg = ShulkerPetSpawnEgg.createSpawnEgg();
    if(player.getInventory().firstEmpty() != -1) {
      player.getInventory().addItem(spawnEgg);
    } else {
      player.sendMessage(config.getInventoryFullMsg());
    }
  }

  public void packShulker(Player player) {
    ShulkerPetManager.packShulkerPet(player);
  }

  public void giveControlItem(Player player) {
    ItemStack ciItem = ControlItem.createControlItem();
    if(player.getInventory().firstEmpty() != -1) {
      player.getInventory().addItem(ciItem);
    } else {
      player.sendMessage(config.getInventoryFullMsg());
    }
  }
}
