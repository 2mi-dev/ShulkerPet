package navy.otter.shulkerpet.entities;

import java.util.UUID;
import org.bukkit.DyeColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Shulker;

public class ShulkerPet {
  DyeColor color;
  final UUID uuid;
  final UUID ownerUuid;
  String customName;
  final Shulker shulker;
  final Player owner;
  boolean isFollowing = true;

  public ShulkerPet(Shulker shulker, Player owner) {
    this.shulker = shulker;
    this.uuid = shulker.getUniqueId();
    this.owner = owner;
    this.ownerUuid = owner.getUniqueId();
  }

  public void openInventory(Player player) {
    //todo
  }

  public DyeColor getColor() {
    return color;
  }

  public void setColor(DyeColor color) {
    this.color = color;
    this.getShulker().setColor(color);
  }

  public UUID getUuid() {
    return uuid;
  }

  public Player getOwner() {
    return owner;
  }

  public UUID getOwnerUuid() {
    return ownerUuid;
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

  public boolean isFollowing() {
    return isFollowing;
  }

  public void setFollowing(boolean following) {
    this.isFollowing = following;
  }

  public void toggleFollowing() {
    if(this.isFollowing) {
      this.setFollowing(false);
    } else{
      this.setFollowing(true);
    }
  }
}
