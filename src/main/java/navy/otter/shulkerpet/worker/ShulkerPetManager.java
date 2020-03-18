package navy.otter.shulkerpet.worker;

import static navy.otter.shulkerpet.util.CardinalDirection.getCardinalDirection;

import java.util.HashMap;
import java.util.UUID;
import navy.otter.shulkerpet.config.Configuration;
import navy.otter.shulkerpet.entities.ShulkerPet;
import navy.otter.shulkerpet.ShulkerPetPlugin;
import navy.otter.shulkerpet.util.CardinalDirection;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Shulker;

public class ShulkerPetManager {

  static HashMap<UUID, ShulkerPet> shulkerMap = new HashMap<>();
  Configuration config = ShulkerPetPlugin.getMainInstance().getConfiguration();

  public void createShulker(Player player) {

    World world = player.getWorld();
    Location playerLocation = player.getLocation();
    CardinalDirection playerTarget = getCardinalDirection(player);

    switch(playerTarget) {
      case EAST:
        playerLocation.add(1.0, 0, 0);
        break;
      case WEST:
        playerLocation.add(-1.0, 0, 0);
        break;
      case NORTH:
        playerLocation.add(0, 0, -1.0);
        break;
      case SOUTH:
        playerLocation.add(0, 0, 1.0);
        break;
    }

    if(playerLocation.getBlock().getType() != Material.AIR) {
      player.sendMessage(config.getInvalidSpawningLocationMsg());
      return;
    }

    Entity entity = world.spawnEntity(playerLocation, EntityType.SHULKER);
    Shulker shulker = (Shulker) entity;

    UUID playerUuid = player.getUniqueId();
    UUID shulkerUuid = shulker.getUniqueId();

    ShulkerPet sp = new ShulkerPet();
    sp.setColor(DyeColor.LIME);
    sp.setUuid(shulkerUuid);
    sp.setOwnerUUID(playerUuid);
    sp.setCustomName(null); //todo

    shulkerMap.put(shulkerUuid, sp);
    //todo db
  }

  public void loadPersistentShulkerPets() {}

  public static HashMap<UUID, ShulkerPet> getShulkerMap() {
    return shulkerMap;
  }

  //todo delete edit
}
