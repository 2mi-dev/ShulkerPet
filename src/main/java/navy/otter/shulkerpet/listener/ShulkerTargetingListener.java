package navy.otter.shulkerpet.listener;

import java.util.HashMap;
import java.util.UUID;
import navy.otter.shulkerpet.entities.ShulkerPet;
import navy.otter.shulkerpet.worker.ShulkerPetManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Shulker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;

public class ShulkerTargetingListener implements Listener {

  HashMap<UUID, ShulkerPet> shulkerMap = ShulkerPetManager.getShulkerMap();

  @EventHandler
  public void onEntityTargetEvent(EntityTargetEvent e) {
    if(shulkerMap.containsKey(e.getEntity().getUniqueId())) {
      e.setCancelled(true);
    }
  }
}
