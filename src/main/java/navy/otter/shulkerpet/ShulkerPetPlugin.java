package navy.otter.shulkerpet;

import navy.otter.shulkerpet.Commands.ShulkerPetCommand;
import navy.otter.shulkerpet.Config.Configuration;
import navy.otter.shulkerpet.Listener.BlockRightClickListener;
import navy.otter.shulkerpet.Listener.ShulkerRightClickListener;
import navy.otter.shulkerpet.Listener.ShulkerTargetingListener;
import navy.otter.shulkerpet.Worker.ShulkerPetManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ShulkerPetPlugin extends JavaPlugin {

  private static ShulkerPetPlugin instance;
  public ShulkerPetManager spManager = new ShulkerPetManager();
  Configuration config;

  @Override
  public void onEnable() {
    instance = this;
    config = new Configuration(this);

    getCommand("shulkerpet").setExecutor(new ShulkerPetCommand());
    getServer().getPluginManager().registerEvents(new ShulkerRightClickListener(), this);
    getServer().getPluginManager().registerEvents(new BlockRightClickListener(), this);
    getServer().getPluginManager().registerEvents(new ShulkerTargetingListener(), this);
  }

  @Override
  public void onDisable() {
    instance = null;
  }

  public static ShulkerPetPlugin getMainInstance() {
    return instance;
  }

  public Configuration getConfiguration() {
    return config;
  }
}

