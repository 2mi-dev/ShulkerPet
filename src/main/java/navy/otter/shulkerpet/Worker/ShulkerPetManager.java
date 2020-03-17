package navy.otter.shulkerpet.Worker;

import java.util.HashMap;
import java.util.UUID;
import navy.otter.shulkerpet.Entities.ShulkerPet;
import org.bukkit.DyeColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Shulker;

public class ShulkerPetManager {

  static HashMap<UUID, ShulkerPet> shulkerMap = new HashMap<>();

  private void createShulker(Shulker shulker, Player player) {

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
