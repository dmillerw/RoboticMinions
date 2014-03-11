package dmillerw.minion.client.gui;

import dmillerw.minion.client.entity.EntityCamera;
import dmillerw.minion.entity.EntityMinion;
import dmillerw.minion.network.packet.PacketMoveMinion;
import dmillerw.minion.network.packet.PacketSelectMinion;
import dmillerw.minion.network.packet.PacketSpawnMinion;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author dmillerw
 */
public class GuiController extends GuiScreen {

	private static final boolean DRAW_SCROLL_ZONES = true;

	public GuiController() {
		EntityCamera.createCamera();
	}

	@Override
	protected void mouseClicked(int x, int y, int button) {
		if (button == 0) {
			Entity entity = EntityCamera.activeCamera.raytraceEntity();

			if (entity != null && entity instanceof EntityMinion) {
				EntityCamera.selectedMinion = (EntityMinion) entity;
				PacketSelectMinion.select((EntityMinion) entity);
			} else {
				EntityCamera.selectedMinion = null;
				PacketSelectMinion.select(null);
			}
		} else if (button == 1) {
			if (EntityCamera.selectedMinion == null) {
				MovingObjectPosition block = EntityCamera.activeCamera.raytraceBlock();

				if (block != null && block.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
					ForgeDirection side = ForgeDirection.getOrientation(block.sideHit);
					PacketSpawnMinion.spawnMinion((block.blockX + 0.5) + side.offsetX, block.blockY + side.offsetY, (block.blockZ + 0.5) + side.offsetZ);
				}
			} else {
				MovingObjectPosition block = EntityCamera.activeCamera.raytraceBlock();

				if (block != null && block.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
					ForgeDirection side = ForgeDirection.getOrientation(block.sideHit);
					PacketMoveMinion.move((block.blockX + 0.5) + side.offsetX, block.blockY + side.offsetY, (block.blockZ + 0.5) + side.offsetZ);
				}
			}
		}
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
