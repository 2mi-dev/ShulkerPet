package navy.otter.shulkerpet.config;

import java.util.ArrayList;
import java.util.List;
import navy.otter.shulkerpet.ShulkerPetPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

public class Configuration {

  // Config keys
  private static class Key {

    private static final String MAX_DIST_TO_PLAYER = "max-dist-to-player";
    private static final String CHECK_DELAY = "check-delay";
    private static final String CONTROL_ITEM = "control-item";
    private static final String CONTROL_ITEM_LORE = "control-item-lore";
    private static final String ENABLE_SHULKER_FOLLOW_TELEPORTS = "enable-shulker-follow-teleports";
    private static final String UNSAFE_BLOCKS = "unsafe-blocks";

    private static final String MSG_PREFIX = "msg-prefix";
    private static final String UNKNOWN_CMD_MSG = "unknown-cmd-msg";
    private static final String NOT_YOUR_SHULKERPET_MSG = "not-your-shulkerpet-msg";
    private static final String NOT_A_SHULKERPET_MSG = "not-a-shulkerpet-msg";
    private static final String INVENTORY_FULL_MSG = "inventory-full-msg";
    private static final String INVALID_SPAWNING_LOCATION_MSG = "invalid-spawning-location-msg";
    private static final String INVALID_LOCATION_MSG = "invalid-location-msg";
    private static final String SHULKERPET_FOLLOW_MSG = "shulkerpet-follow-msg";
    private static final String SHULKERPET_UNFOLLOW_MSG = "shulkerpet-unfollow-msg";
    private static final String SHULKER_DEATH_MSG = "shulker-death-msg";
    private static final String PLAYER_ALREADY_HAS_SHULKERPET_MSG = "player-already-has-shulkerpet-msg";
    private static final String NO_TARGET_BLOCK_MSG = "no-target-block-msg";
  }

  private final double maxDistToPlayer;
  private final int checkDelay;
  private Material controlItemMaterial = Material.BAMBOO;
  private final List<String> controlItemLore;
  private final boolean enableShulkerFollowTeleports;
  private final ArrayList<Material> unsafeBlocks;

  private final String msgPrefix;
  private final String unknownCmdMsg;
  private final String notYourShulkerPetMsg;
  private final String notAShulkerPetMsg;
  private final String inventoryFullMsg;
  private final String invalidSpawningLocationMsg;
  private final String invalidLocationMsg;
  private final String shulkerPetFollowMsg;
  private final String shulkerPetUnfollowMsg;
  private final String shulkerDeathMsg;
  private final String playerAlreadyHasShulkerPetMsg;
  private final String noTargetBlockMsg;


  public Configuration(@NotNull ShulkerPetPlugin plugin) {
    FileConfiguration config = plugin.getConfig();
    config.options().copyDefaults(true);
    plugin.saveConfig();

    this.maxDistToPlayer = config.getDouble(Key.MAX_DIST_TO_PLAYER);
    this.checkDelay = config.getInt(Key.CHECK_DELAY);
    String materialType = config.getString(Key.CONTROL_ITEM);
    if(materialType == null) {
      Bukkit.getLogger().info("Material not found, falling back to default:"
          + " Bamboo Stick.");
    } else {
      this.controlItemMaterial = Material.getMaterial(materialType);
    }
    this.controlItemLore = new ArrayList<>();
    List<String> controlItemLores = plugin.getConfig().getStringList(Key.CONTROL_ITEM_LORE);
    for (String loreLine : controlItemLores) {
      if (loreLine == null) {
        Bukkit.getLogger().info(() -> "Lore contains invalid line");
        continue;
      }
      controlItemLore.add(ChatColor.GREEN + loreLine);
    }
    this.enableShulkerFollowTeleports = config.getBoolean(Key.ENABLE_SHULKER_FOLLOW_TELEPORTS);
    this.unsafeBlocks = new ArrayList<>();
    List<String> unsafeBlockNames = plugin.getConfig().getStringList(Key.UNSAFE_BLOCKS);
    for (String unsafeBlockName : unsafeBlockNames) {
      Material material = Material.getMaterial(unsafeBlockName);
      if (material == null) {
        Bukkit.getLogger().info(() -> "Material not found: " + unsafeBlockName);
        continue;
      }
      unsafeBlocks.add(material);
    }

    String rawMsgPrefix = config.getString(Key.MSG_PREFIX);
    this.msgPrefix = ChatColor.translateAlternateColorCodes('&', rawMsgPrefix);
    this.unknownCmdMsg = msgPrefix + config.getString(Key.UNKNOWN_CMD_MSG);
    this.notYourShulkerPetMsg = msgPrefix + config.getString(Key.NOT_YOUR_SHULKERPET_MSG);
    this.notAShulkerPetMsg = msgPrefix + config.getString(Key.NOT_A_SHULKERPET_MSG);
    this.inventoryFullMsg = msgPrefix + config.getString(Key.INVENTORY_FULL_MSG);
    this.invalidSpawningLocationMsg = msgPrefix + config.getString(Key.INVALID_SPAWNING_LOCATION_MSG);
    this.invalidLocationMsg = msgPrefix + config.getString(Key.INVALID_LOCATION_MSG);
    this.shulkerPetFollowMsg = msgPrefix + config.getString(Key.SHULKERPET_FOLLOW_MSG);
    this.shulkerPetUnfollowMsg = msgPrefix + config.getString(Key.SHULKERPET_UNFOLLOW_MSG);
    this.shulkerDeathMsg = msgPrefix + config.getString(Key.SHULKER_DEATH_MSG);
    this.playerAlreadyHasShulkerPetMsg = msgPrefix + config.getString(Key.PLAYER_ALREADY_HAS_SHULKERPET_MSG);
    this.noTargetBlockMsg = msgPrefix + config.getString(Key.NO_TARGET_BLOCK_MSG);
  }

  public double getMaxDistToPlayer() {
    return maxDistToPlayer;
  }

  public int getCheckDelay() {
    return checkDelay;
  }

  public Material getControlItemMaterial() {
    return controlItemMaterial;
  }

  public List<String> getControlItemLore() {
    return controlItemLore;
  }

  public boolean isEnableShulkerFollowTeleports() {
    return enableShulkerFollowTeleports;
  }

  public ArrayList<Material> getUnsafeBlocks() {
    return unsafeBlocks;
  }

  public String getUnknownCmdMsg() {
    return unknownCmdMsg;
  }

  public String getNotYourShulkerPetMsg() {
    return notYourShulkerPetMsg;
  }

  public String getNotAShulkerPetMsg() {
    return notAShulkerPetMsg;
  }

  public String getInventoryFullMsg() {
    return inventoryFullMsg;
  }

  public String getInvalidSpawningLocationMsg() {
    return invalidSpawningLocationMsg;
  }

  public String getInvalidLocationMsg() {
    return invalidLocationMsg;
  }

  public String getShulkerPetFollowMsg() {
    return shulkerPetFollowMsg;
  }

  public String getShulkerPetUnfollowMsg() {
    return shulkerPetUnfollowMsg;
  }

  public String getShulkerDeathMsg() {
    return shulkerDeathMsg;
  }

  public String getPlayerAlreadyHasShulkerPetMsg() {
    return playerAlreadyHasShulkerPetMsg;
  }

  public String getNoTargetBlockMsg() {
    return noTargetBlockMsg;
  }
}
