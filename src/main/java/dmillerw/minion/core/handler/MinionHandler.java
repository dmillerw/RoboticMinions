package dmillerw.minion.core.handler;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dmillerw.minion.entity.EntityMinion;
import net.minecraft.entity.player.EntityPlayer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dmillerw
 */
@SideOnly(Side.SERVER)
public class MinionHandler {

	public static Map<String, EntityMinion> minions = new HashMap<String, EntityMinion>();

	public static void selectMinion(EntityPlayer player, EntityMinion minion) {
		minion.select();
		minions.put(player.getCommandSenderName(), minion);
	}

	public static void deselectMinion(EntityPlayer player) {
		minions.get(player.getCommandSenderName()).deselect();
		minions.remove(player.getCommandSenderName());
	}

	public static EntityMinion getSelectedMinion(EntityPlayer player) {
		return minions.get(player.getCommandSenderName());
	}

}
