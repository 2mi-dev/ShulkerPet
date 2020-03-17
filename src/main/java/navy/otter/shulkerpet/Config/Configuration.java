package navy.otter.shulkerpet.Config;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import navy.otter.shulkerpet.ShulkerPetPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

public class Configuration {

  // Config keys
  private static class Key {

    private static final String MAX_DIST_TO_PLAYER = "max-dist-to-player";
    private static final String CHECK_DELAY = "check-delay";
    private static final String CONTROL_ITEM = "control-item";
    private static final String CONTROL_ITEM_LORE = "control-item-lore";
    private static final String UNSAFE_BLOCKS = "unsafe-blocks";

    private static final String MSG_PREFIX = "msg-prefix";
    private static final String UNKNOWN_CMD_MSG = "unknown-cmd-msg";
    private static final String NOT_YOUR_SHULKERPET_MSG = "not-your-shulker-message";
    private static final String NOT_A_SHULKERPET_MSG = "not-a-shulkerpet-msg";

  }

  private final int maxDistToPlayer;
  private final int checkDelay;
  private Material controlItemMaterial = Material.BAMBOO;
  private final List<String> controlItemLore;
  private final ArrayList<Material> unsafeBlocks;

  private final String msgPrefix;
  private final String unknownCmdMsg;
  private final String notYourShulkerPetMsg;
  private final String notAShulkerPetMsg;

  public Configuration(@NotNull ShulkerPetPlugin plugin) {
    FileConfiguration config = plugin.getConfig();
    config.options().copyDefaults(true);
    plugin.saveConfig();

    this.maxDistToPlayer = config.getInt(Key.MAX_DIST_TO_PLAYER);
    this.checkDelay = config.getInt(Key.CHECK_DELAY);
    String materialType = config.getString(Key.CONTROL_ITEM);
    if(materialType == null) {
      Bukkit.getLogger().info("Material not found: " + materialType + ", falling back to default"
          + " Bamboo Stick.");
    } else {
      this.controlItemMaterial = Material.getMaterial(materialType);
    }
    this.controlItemLore = new ArrayList<>();
    List<String> controlItemLores = plugin.getConfig().getStringList(Key.CONTROL_ITEM_LORE);
    for (String loreLine : controlItemLores) {
      if (loreLine == null) {
        Bukkit.getLogger().info(() -> "Lore invalid: " + loreLine);
        continue;
      }
      controlItemLore.add(ChatColor.GREEN + loreLine);
    }

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

    this.msgPrefix = config.getString(Key.MSG_PREFIX);
    this.unknownCmdMsg = msgPrefix + config.getString(Key.UNKNOWN_CMD_MSG);
    this.notYourShulkerPetMsg = msgPrefix + config.getString(Key.NOT_YOUR_SHULKERPET_MSG);
    this.notAShulkerPetMsg = msgPrefix + config.getString(Key.NOT_A_SHULKERPET_MSG);

  }

  public int getMaxDistToPlayer() {
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
}
