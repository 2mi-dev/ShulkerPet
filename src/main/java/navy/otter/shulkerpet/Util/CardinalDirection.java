package navy.otter.shulkerpet.Util;

import org.bukkit.entity.Player;

public enum CardinalDirection {
  SOUTH,
  WEST,
  NORTH,
  EAST;

  public static CardinalDirection getCardinalDirection (Player player) {
    float yaw = player.getLocation().getYaw();
    if (yaw < 0) {
      yaw += 360;
    }
    if (yaw >= 315 || yaw < 45) {
      return CardinalDirection.SOUTH;
    } else if (yaw < 135) {
      return CardinalDirection.WEST;
    } else if (yaw < 225) {
      return CardinalDirection.NORTH;
    } else if (yaw < 315) {
      return CardinalDirection.EAST;
    }
    return CardinalDirection.NORTH;
  }
}
