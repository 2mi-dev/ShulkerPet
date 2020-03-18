package navy.otter.shulkerpet.worker;

import static navy.otter.shulkerpet.util.LocationCheck.teleportToPlayer;

import java.util.HashMap;
import java.util.UUID;
import navy.otter.shulkerpet.ShulkerPetPlugin;
import navy.otter.shulkerpet.entities.ShulkerPet;
import org.bukkit.entity.Player;
import org.bukkit.entity.Shulker;

public class ShulkerTeleportThread implements Runnable {

  HashMap<UUID, ShulkerPet> shulkerMap = ShulkerPetManager.getShulkerMap();
  double maxDistance = ShulkerPetPlugin.getConfiguration().getMaxDistToPlayer();

  @Override
  public void run() {

    for (UUID shulkerUuid : shulkerMap.keySet()) {
      ShulkerPet sp = shulkerMap.get(shulkerUuid);
      Shulker shulker = sp.getShulker();
      Player player = sp.getOwner();

      if (player == null || shulker == null || !player.isOnline()) {
        continue;
      }

      double distance = player.getLocation().distance(shulker.getLocation());

      if (distance > maxDistance) {
        if (sp.isFollowing()) {
          teleportToPlayer(player, shulker);
        }
      }
    }
  }
}