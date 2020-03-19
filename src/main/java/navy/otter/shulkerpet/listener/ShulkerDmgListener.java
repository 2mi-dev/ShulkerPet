package navy.otter.shulkerpet.listener;

import java.util.HashMap;
import java.util.UUID;
import navy.otter.shulkerpet.entities.ShulkerPet;
import navy.otter.shulkerpet.worker.ShulkerPetManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class ShulkerDmgListener implements Listener {

  HashMap<UUID, ShulkerPet> shulkerMap = ShulkerPetManager.getShulkerMap();

  @EventHandler
  public void onEntityDamageEvent(EntityDamageEvent e) {
    if(!shulkerMap.containsKey(e.getEntity().getUniqueId())) {
      e.setCancelled(true);
    }
  }
}
