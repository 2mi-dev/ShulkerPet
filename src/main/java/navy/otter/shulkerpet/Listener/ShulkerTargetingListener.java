package navy.otter.shulkerpet.Listener;

import java.util.UUID;
import navy.otter.shulkerpet.Worker.ShulkerPetManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Shulker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;

public class ShulkerTargetingListener implements Listener {

  @EventHandler
  public void onEntityTargetEvent(EntityTargetEvent e) {
    Entity entity = e.getEntity();
    if(!(entity instanceof Shulker)) {
      return;
    }
    Shulker shulker = (Shulker) entity;
    UUID shulkerUuid = shulker.getUniqueId();

    if(ShulkerPetManager.getShulkerMap().containsKey(shulkerUuid)){
      e.setCancelled(true);
    }
  }
}
