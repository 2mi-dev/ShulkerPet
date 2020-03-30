package navy.otter.shulkerpet.worker;

import java.util.HashMap;
import java.util.UUID;
import navy.otter.shulkerpet.config.Configuration;
import navy.otter.shulkerpet.entities.ShulkerPet;
import navy.otter.shulkerpet.ShulkerPetPlugin;
import navy.otter.shulkerpet.entities.ShulkerPetSpawnEgg;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Shulker;
import org.bukkit.util.Vector;

public class ShulkerPetManager {

  static HashMap<UUID, ShulkerPet> shulkerMap = new HashMap<>();
  static HashMap<UUID, UUID> playerShulkerMap = new HashMap<>();
  static Configuration config = ShulkerPetPlugin.getConfiguration();

  public boolean createShulker(Player player) {

    Block lookingAtBlock = player.getTargetBlockExact(5);
    if (lookingAtBlock == null) {
      player.sendMessage(config.getNoTargetBlockMsg());
      return false;
    }
    Location location = lookingAtBlock.getLocation().add(0, 1, 0);

    if (location.getBlock().getType() != Material.AIR) {
      player.sendMessage(config.getInvalidSpawningLocationMsg());
      return false;
    }

    World world = player.getWorld();
    Entity entity = world.spawnEntity(location, EntityType.SHULKER);
    Shulker shulker = (Shulker) entity;
    shulker.setInvulnerable(true);

    UUID shulkerUuid = shulker.getUniqueId();

    if (playerShulkerMap.containsKey(player.getUniqueId())) {
      ShulkerPet spb = shulkerMap.get(playerShulkerMap.get(player.getUniqueId()));
      ShulkerPet sp = new ShulkerPet(shulker, player);
      sp.setColor(spb.getColor());
      sp.setFollowing(spb.isFollowing());
      sp.setInventory(spb.getInventory());

      playerShulkerMap.put(sp.getOwnerUuid(), shulkerUuid);
      shulkerMap.put(shulkerUuid, sp);
      return true;
    }

    ShulkerPet sp = new ShulkerPet(shulker, player);
    sp.setColor(DyeColor.LIME);
    sp.setFollowing(true);
    sp.setCustomName(null); //todo

    shulkerMap.put(shulkerUuid, sp);
    playerShulkerMap.put(player.getUniqueId(), shulkerUuid);
    //todo db
    return true;
  }

  public void deleteShulkerPet(Player player) {
    for (UUID shulkerUuid : ShulkerPetManager.getShulkerMap().keySet()) {
      ShulkerPet sp = shulkerMap.get(shulkerUuid);
      Shulker shulker = (Shulker) Bukkit.getEntity(shulkerUuid);

      if (player == null || shulker == null) {
        continue;
      }

      if (sp.getOwnerUuid().equals(player.getUniqueId())) {
        sp.getShulker().remove();
        shulkerMap.remove(shulkerUuid);
      }
    }
  }

  public static void teleportShulker(Shulker shulker, Player p, Block block, BlockFace blockFace) {

    if(block == null) {
      return;
    }

    Location target;
    Vector vec = new Vector(0, 0, 0);

    switch (blockFace) {
      case UP:
        vec = new Vector(0, 1, 0);
        break;
      case DOWN:
        vec = new Vector(0, -1, 0);
        break;
      case NORTH:
        vec = new Vector(0, 0, -1);
        break;
      case SOUTH:
        vec = new Vector(0, 0, 1);
        break;
      case WEST:
        vec = new Vector(-1, 0, 0);
        break;
      case EAST:
        vec = new Vector(1, 0, 0);
        break;
    }

    target = block.getLocation().add(vec);

    if (block.getType().isSolid()) {
      if (target.getBlock().isEmpty() && target.add(vec).getBlock().isEmpty()) {
        shulker.teleport(target.subtract(vec));
        return;
      }
    }

    p.sendMessage(config.getInvalidLocationMsg());
  }

  public static boolean packShulkerPet(Player player) {
    if (!playerShulkerMap.containsKey(player.getUniqueId())) {
      return false;
    }
    UUID shulkerUuid = playerShulkerMap.get(player.getUniqueId());
    ShulkerPet sp = shulkerMap.get(shulkerUuid);

    Shulker shulker = (Shulker) Bukkit.getEntity(shulkerUuid);
    if (shulker == null) {
      return false;
    }

    if (player.getInventory().firstEmpty() != -1) {
      player.getInventory().addItem(ShulkerPetSpawnEgg.createSpawnEgg());
      shulker.remove();
      sp.setShulker(null);
      return true;
    } else {
      player.sendMessage(config.getInventoryFullMsg());
    }
    return false;
  }

  public void loadPersistentShulkerPets() {
  }

  public static HashMap<UUID, ShulkerPet> getShulkerMap() {
    return shulkerMap;
  }

  public static HashMap<UUID, UUID> getPlayerShulkerMap() {
    return playerShulkerMap;
  }

  //todo delete edit
}
