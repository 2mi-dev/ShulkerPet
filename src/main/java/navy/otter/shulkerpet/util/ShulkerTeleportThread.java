package navy.otter.shulkerpet.util;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import navy.otter.shulkerpet.ShulkerPetPlugin;
import navy.otter.shulkerpet.entities.ShulkerPet;
import navy.otter.shulkerpet.worker.ShulkerPetManager;
import org.bukkit.Location;
import org.bukkit.block.Block;
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

  private void teleportToPlayer(Player player, Shulker shulker) {
    /*
    any-solid-air-air-any
    min 1m zu spieler, max 5m
    todo: nicht in blickrichtung
    */

    Location playerFeet = player.getLocation().getBlock().getLocation();
    ThreadLocalRandom random = ThreadLocalRandom.current();

    Location[] locArr = new Location[]{
        playerFeet,
        playerFeet.add(0, -1, 0),
        playerFeet.add(0, 1, 0),
        playerFeet.add(0, -2, 0),
        playerFeet.add(0, 2, 0),
    };

    for (Location loc : locArr) {
      boolean success = false;
      for(int n = 0; n <= 5; n++) {
        Location target = loc.add(random.nextInt(1,4), 0, random.nextInt(1, 4));
        if(checkTeleportBlocks(player, target)) {
          shulker.teleport(target);
          success = true;
          break;
        }
      }
      if(success) {
        break;
      }
    }

  }

  // Check for air on air on solid block
  public boolean checkTeleportBlocks(Player player, Location location) {
    Block srcBlock = location.getBlock();
    if (srcBlock.isEmpty() && srcBlock.getRelative(0, 1, 0).isEmpty()) {
      if (srcBlock.getRelative(0, -1, 0).getType().isSolid()) {
        return checkDistance(player, location);
      }
    }
    return false;
  }

  // Check if location is > 1 and < 5 blocks from player
  public boolean checkDistance(Player player, Location location) {
    double distance = player.getLocation().distance(location);
    return distance > 1 && distance < 5;
  }
}