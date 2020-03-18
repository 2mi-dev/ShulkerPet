package navy.otter.shulkerpet.listener;

import java.util.HashMap;
import java.util.UUID;
import navy.otter.shulkerpet.config.Configuration;
import navy.otter.shulkerpet.entities.ControlItem;
import navy.otter.shulkerpet.entities.ShulkerPet;
import navy.otter.shulkerpet.ShulkerPetPlugin;
import navy.otter.shulkerpet.worker.ShulkerPetManager;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Shulker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class ShulkerRightClickListener implements Listener {

  @EventHandler
  public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent e) {
    Entity entity = e.getRightClicked();
    Player player = e.getPlayer();

    if (!(entity instanceof Shulker)) {
      return;
    }

    Shulker shulker = (Shulker) entity;
    UUID shulkerUuid = shulker.getUniqueId();
    HashMap<UUID, ShulkerPet> shulkerMap = ShulkerPetManager.getShulkerMap();

    if (!checkShulker(shulkerMap, player, shulkerUuid)) {
      return;
    }

    ShulkerPet sp = shulkerMap.get(shulkerUuid);
    checkHandItem(sp, player);
  }

  private void checkHandItem(ShulkerPet sp, Player player) {
    Configuration config = ShulkerPetPlugin.getMainInstance().getConfiguration();
    ItemStack mainHandStack = player.getInventory().getItemInMainHand();
    Material mainHandStackMaterial = mainHandStack.getType();

    if(mainHandStack == ControlItem.createControlItem()) {
      sp.setFollowing(!sp.isFollowing());
      return;
    }

    switch (mainHandStackMaterial) {
      case WHITE_DYE:
        sp.setColor(DyeColor.WHITE);
        break;
      case ORANGE_DYE:
        sp.setColor(DyeColor.ORANGE);
        break;
      case MAGENTA_DYE:
        sp.setColor(DyeColor.MAGENTA);
        break;
      case LIGHT_BLUE_DYE:
        sp.setColor(DyeColor.LIGHT_BLUE);
        break;
      case YELLOW_DYE:
        sp.setColor(DyeColor.YELLOW);
        break;
      case LIME_DYE:
        sp.setColor(DyeColor.LIME);
        break;
      case PINK_DYE:
        sp.setColor(DyeColor.PINK);
        break;
      case GRAY_DYE:
        sp.setColor(DyeColor.GRAY);
        break;
      case LIGHT_GRAY_DYE:
        sp.setColor(DyeColor.LIGHT_GRAY);
        break;
      case CYAN_DYE:
        sp.setColor(DyeColor.CYAN);
        break;
      case PURPLE_DYE:
        sp.setColor(DyeColor.PURPLE);
        break;
      case BLUE_DYE:
        sp.setColor(DyeColor.BLUE);
        break;
      case BROWN_DYE:
        sp.setColor(DyeColor.BROWN);
        break;
      case GREEN_DYE:
        sp.setColor(DyeColor.GREEN);
        break;
      case RED_DYE:
        sp.setColor(DyeColor.RED);
        break;
      case BLACK_DYE:
        sp.setColor(DyeColor.BLACK);
        break;
      default:
        sp.openInventory(player);
        break;
    }
  }

  private boolean checkShulker(HashMap<UUID, ShulkerPet> shulkerMap, Player player,
      UUID shulkerUuid) {
    UUID playerUuid = player.getUniqueId();
    if (!shulkerMap.containsKey(shulkerUuid)) {
      player.sendMessage("Das ist kein ShulkerPet!");
      return false;
    } else if (shulkerMap.get(shulkerUuid).getOwnerUUID() != playerUuid) {
      player.sendMessage("Das ist nicht dein ShulkerPet!");
      return false;
    }
    return true;
  }
}
