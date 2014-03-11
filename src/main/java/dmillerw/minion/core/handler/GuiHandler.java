package dmillerw.minion.core.handler;

import cpw.mods.fml.common.network.IGuiHandler;
import dmillerw.minion.client.gui.GuiController;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * @author dmillerw
 */
public class GuiHandler implements IGuiHandler {

	public static final int GUI_CONTROLLER = 0;

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		switch (id) {
			case GUI_CONTROLLER:
				return null;
		}

		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		switch (id) {
			case GUI_CONTROLLER:
				return new GuiController();
		}

		return null;
	}
}
