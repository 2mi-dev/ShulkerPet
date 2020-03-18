package navy.otter.shulkerpet.listener;

import navy.otter.shulkerpet.config.Configuration;
import navy.otter.shulkerpet.ShulkerPetPlugin;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlockRightClickListener implements Listener {

  @EventHandler
  public void onPlayerInteractEvent(PlayerInteractEvent e) {
    Block clickedBlock = e.getClickedBlock();
    Configuration config = ShulkerPetPlugin.getMainInstance().getConfiguration();
    if(e.getPlayer().getInventory().getItemInMainHand().getType() != config.getControlItemMaterial()
        || clickedBlock == null) {
      return;
    }

    Player p = e.getPlayer();

    if(!clickedBlock.getType().isSolid()) {
      p.sendMessage("Da kann dein ShulkerPet nicht stehen!");
    }


  }

}
