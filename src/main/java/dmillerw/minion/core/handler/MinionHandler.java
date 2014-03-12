package dmillerw.minion.core.handler;

import dmillerw.minion.entity.EntityMinion;
import net.minecraft.entity.player.EntityPlayer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dmillerw
 */
public class MinionHandler {

	public static Map<String, EntityMinion> minions = new HashMap<String, EntityMinion>();

	public static void selectMinion(EntityPlayer player, EntityMinion minion) {
		if (getSelectedMinion(player) != null) {
			deselectMinion(player);
		}

		if (minion.getOwner().equals(player.getCommandSenderName())) {
			minion.select();
			minions.put(player.getCommandSenderName(), minion);
		}
	}

	public static void deselectMinion(EntityPlayer player) {
		if (getSelectedMinion(player) != null) {
			getSelectedMinion(player).deselect();
			minions.remove(player.getCommandSenderName());
		}
	}

	public static EntityMinion getSelectedMinion(EntityPlayer player) {
		return minions.get(player.getCommandSenderName());
	}

}
