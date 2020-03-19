package navy.otter.shulkerpet.worker;

import static navy.otter.shulkerpet.util.CardinalDirection.getCardinalDirection;

import java.util.HashMap;
import java.util.UUID;
import navy.otter.shulkerpet.config.Configuration;
import navy.otter.shulkerpet.entities.ShulkerPet;
import navy.otter.shulkerpet.ShulkerPetPlugin;
import navy.otter.shulkerpet.util.CardinalDirection;
import org.bukkit.Bukkit;
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
  static Configuration config = ShulkerPetPlugin.getConfiguration();

  public void createShulker(Player player) {
    //todo: spawning auf gültigen blöcken
    World world = player.getWorld();
    Location playerLocation = player.getLocation();
    CardinalDirection playerTarget = getCardinalDirection(player);

    player.sendMessage("Llooool");

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
    shulker.setInvulnerable(true);

    UUID shulkerUuid = shulker.getUniqueId();

    ShulkerPet sp = new ShulkerPet(shulker, player);
    sp.setColor(DyeColor.LIME);
    sp.setFollowing(true);
    sp.setCustomName(null); //todo

    shulkerMap.put(shulkerUuid, sp);
    //todo db
  }

  public void deleteShulkerPet(Player player) {
    for(UUID shulkerUuid : ShulkerPetManager.getShulkerMap().keySet()) {
      ShulkerPet sp = shulkerMap.get(shulkerUuid);
      Shulker shulker = (Shulker) Bukkit.getEntity(shulkerUuid);

      if (player == null || shulker == null) {
        continue;
      }

      if(sp.getOwnerUuid().equals(player.getUniqueId())){
        sp.getShulker().remove();
        shulkerMap.remove(shulkerUuid);
      }
    }
  }

  public void loadPersistentShulkerPets() {}

  public static HashMap<UUID, ShulkerPet> getShulkerMap() {
    return shulkerMap;
  }

  //todo delete edit
}
