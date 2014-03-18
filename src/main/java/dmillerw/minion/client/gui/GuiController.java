package dmillerw.minion.client.gui;

import dmillerw.minion.client.entity.EntityCamera;
import dmillerw.minion.network.packet.server.PacketMouseClick;
import net.minecraft.client.gui.GuiScreen;

/**
 * @author dmillerw
 */
public class GuiController extends GuiScreen {

	public GuiController() {
		EntityCamera.createCamera();
	}

	@Override
	protected void mouseClicked(int x, int y, int button) {
		PacketMouseClick packet = new PacketMouseClick(button, EntityCamera.activeCamera.getClosePosition(), EntityCamera.activeCamera.getFarPosition());
		packet.sendToServer();
	}

	@Override
	public void onGuiClosed() {
		EntityCamera.destroyCamera();
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

}
