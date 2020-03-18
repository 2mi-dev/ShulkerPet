package navy.otter.shulkerpet.entities;

import java.util.UUID;
import org.bukkit.DyeColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Shulker;

public class ShulkerPet {
  DyeColor color;
  UUID uuid;
  UUID ownerUUID;
  String customName;
  Shulker shulker;
  boolean isFollowing = true;

  public void openInventory(Player player) {
    //todo
  }

  public DyeColor getColor() {
    return color;
  }

  public void setColor(DyeColor color) {
    this.color = color;
    //shulker.setColor(color); todo
  }

  public UUID getUuid() {
    return uuid;
  }

  public void setUuid(UUID uuid) {
    this.uuid = uuid;
  }

  public UUID getOwnerUUID() {
    return ownerUUID;
  }

  public void setOwnerUUID(UUID ownerUUID) {
    this.ownerUUID = ownerUUID;
  }

  public String getCustomName() {
    return customName;
  }

  public void setCustomName(String customName) {
    if(customName != null) {
      this.customName = customName;
    }
  }

  public Shulker getShulker() {
    return shulker;
  }

  public void setShulker(Shulker shulker) {
    this.shulker = shulker;
  }

  public boolean isFollowing() {
    return isFollowing;
  }

  public void setFollowing(boolean following) {
    isFollowing = following;
  }
}
