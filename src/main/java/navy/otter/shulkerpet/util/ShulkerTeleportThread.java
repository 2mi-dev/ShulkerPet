package navy.otter.shulkerpet.util;

import java.util.HashMap;
import java.util.UUID;
import navy.otter.shulkerpet.ShulkerPetPlugin;
import navy.otter.shulkerpet.entities.ShulkerPet;
import navy.otter.shulkerpet.worker.ShulkerPetManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Shulker;

public class ShulkerTeleportThread implements Runnable {

  HashMap<UUID, ShulkerPet> shulkerMap = ShulkerPetManager.getShulkerMap();
  double maxDistance = ShulkerPetPlugin.getConfiguration().getMaxDistToPlayer();

  @Override
  public void run() {

    for(UUID shulkerUuid: shulkerMap.keySet()) {
      Entity shulker = Bukkit.getEntity(shulkerUuid);
      ShulkerPet sp = shulkerMap.get(shulkerUuid);
      Player player = Bukkit.getPlayer(sp.getOwnerUUID());

      if(player == null || shulker == null || !player.isOnline()) {
        continue;
      }

      double distance = player.getLocation().distance(shulker.getLocation());

      if(distance < maxDistance) {
        if(sp.isFollowing()) {
          teleportToPlayer(player, (Shulker) shulker);
        }
      }
    }
  }

  private void teleportToPlayer(Player player, Shulker shulker) {
    shulker.teleport(player);
  }
}
