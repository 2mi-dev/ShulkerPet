package navy.otter.shulkerpet.listener;

import java.util.HashMap;
import java.util.UUID;
import navy.otter.shulkerpet.config.Configuration;
import navy.otter.shulkerpet.ShulkerPetPlugin;
import navy.otter.shulkerpet.entities.ControlItem;
import navy.otter.shulkerpet.entities.ShulkerPet;
import navy.otter.shulkerpet.util.LocationCheck;
import navy.otter.shulkerpet.worker.ShulkerPetManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Shulker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlockRightClickListener implements Listener {

  Configuration config = ShulkerPetPlugin.getConfiguration();
  HashMap<UUID, ShulkerPet> shulkerMap = ShulkerPetManager.getShulkerMap();

  @EventHandler
  public void onPlayerInteractEvent(PlayerInteractEvent e) {
    Block clickedBlock = e.getClickedBlock();
    if (!ControlItem.createControlItem().isSimilar(e.getPlayer().getInventory().getItemInMainHand())
        || clickedBlock == null) {
      return;
    }

    Player p = e.getPlayer();
    Location target = clickedBlock.getLocation().add(0,1,0);
    if (LocationCheck.checkTeleportBlocks(e.getPlayer(), target)) {
      for (UUID shulkerUuid : shulkerMap.keySet()) {
        ShulkerPet sp = shulkerMap.get(shulkerUuid);
        Shulker shulker = (Shulker) Bukkit.getEntity(sp.getUuid());

        if (shulker != null && p.getUniqueId().equals(sp.getOwnerUuid()) && !shulker.isDead()) {
          shulker.teleport(target);
          break;
        }
      }
    } else {
      p.sendMessage(config.getInvalidLocationMsg());
    }
    e.setCancelled(true);
  }
}
