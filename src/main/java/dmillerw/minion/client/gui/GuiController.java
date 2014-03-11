package dmillerw.minion.client.gui;

import dmillerw.minion.client.entity.EntityCamera;
import net.minecraft.client.gui.GuiScreen;

/**
 * @author dmillerw
 */
public class GuiController extends GuiScreen {

	private static final boolean DRAW_SCROLL_ZONES = true;

	// TODO Do something :P

	public GuiController() {
		EntityCamera.createCamera();
	}

	@Override
	protected void mouseClicked(int x, int y, int button) {

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
