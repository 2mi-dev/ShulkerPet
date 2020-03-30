package navy.otter.shulkerpet.listener;

import static navy.otter.shulkerpet.worker.ShulkerPetManager.teleportShulker;

import java.util.HashMap;
import java.util.UUID;
import navy.otter.shulkerpet.config.Configuration;
import navy.otter.shulkerpet.ShulkerPetPlugin;
import navy.otter.shulkerpet.entities.ControlItem;
import navy.otter.shulkerpet.entities.ShulkerPet;
import navy.otter.shulkerpet.entities.ShulkerPetSpawnEgg;
import navy.otter.shulkerpet.worker.ShulkerPetManager;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Shulker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class BlockRightClickListener implements Listener {

  Configuration config = ShulkerPetPlugin.getConfiguration();
  HashMap<UUID, ShulkerPet> shulkerMap = ShulkerPetManager.getShulkerMap();

  @EventHandler
  public void onPlayerInteractEvent(PlayerInteractEvent e) {
    Block clickedBlock = e.getClickedBlock();
    ItemStack handItem = e.getPlayer().getInventory().getItemInMainHand();
    if (ControlItem.createControlItem().isSimilar(handItem)) {
      Player p = e.getPlayer();
      for (UUID shulkerUuid : shulkerMap.keySet()) {
        ShulkerPet sp = shulkerMap.get(shulkerUuid);
        Shulker shulker = (Shulker) Bukkit.getEntity(sp.getUuid());

        if (shulker != null && p.getUniqueId().equals(sp.getOwnerUuid()) && !shulker.isDead()) {
          teleportShulker(shulker, p, clickedBlock, e.getBlockFace());
          e.setCancelled(true);
          return;
        }
      }
      p.sendMessage(config.getInvalidLocationMsg());
      e.setCancelled(true);
    } else if (ShulkerPetSpawnEgg.createSpawnEgg().isSimilar(handItem) && e.getAction().equals(
        Action.RIGHT_CLICK_BLOCK)) { //todo check if player has shulker
      ShulkerPetManager spManager = ShulkerPetPlugin.getMainInstance().getSpManager();
      boolean created = spManager.createShulker(e.getPlayer());
      if(created) {
        handItem.setAmount(handItem.getAmount() - 1);
      }
      e.setCancelled(true);
    }
  }
}
