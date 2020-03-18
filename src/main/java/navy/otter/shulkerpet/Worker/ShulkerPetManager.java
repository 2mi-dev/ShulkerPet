package navy.otter.shulkerpet.Worker;

import static navy.otter.shulkerpet.Util.CardinalDirection.getCardinalDirection;

import java.util.HashMap;
import java.util.UUID;
import navy.otter.shulkerpet.Entities.ShulkerPet;
import navy.otter.shulkerpet.Util.CardinalDirection;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Shulker;
import org.bukkit.util.Vector;

public class ShulkerPetManager {

  static HashMap<UUID, ShulkerPet> shulkerMap = new HashMap<>();

  private void createShulker(Player player) {

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
