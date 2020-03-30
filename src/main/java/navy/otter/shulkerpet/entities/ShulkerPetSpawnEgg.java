package navy.otter.shulkerpet.entities;

import java.util.ArrayList;
import java.util.List;
import navy.otter.shulkerpet.ShulkerPetPlugin;
import navy.otter.shulkerpet.config.Configuration;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ShulkerPetSpawnEgg {

  private static Enchantment mending = Enchantment.MENDING;

  public static ItemStack createSpawnEgg() {
    ItemStack spawnEggItemStack = new ItemStack(Material.SHULKER_SPAWN_EGG, 1);
    ItemMeta spawnEggMeta = spawnEggItemStack.getItemMeta();
    List<String> spawnEggLore = new ArrayList<>();
    spawnEggMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "ShulkerPet-Spawn-Egg");
    spawnEggMeta.setLore(spawnEggLore);
    spawnEggMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    spawnEggItemStack.setItemMeta(spawnEggMeta);
    spawnEggItemStack.addUnsafeEnchantment(mending, 1);

    return spawnEggItemStack;
  }
}
