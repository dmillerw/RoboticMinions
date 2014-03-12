package dmillerw.minion.client.helper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dmillerw.minion.client.entity.EntityCamera;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * @author dmillerw
 */
public class RenderHelper {

	public static Vec3 close;
	public static Vec3 far;

	private static FloatBuffer modelviewF = GLAllocation.createDirectFloatBuffer(16);
	private static FloatBuffer projectionF = GLAllocation.createDirectFloatBuffer(16);
	private static IntBuffer viewport = GLAllocation.createDirectIntBuffer(16);
	private static FloatBuffer posClose = ByteBuffer.allocateDirect(3 * 4).asFloatBuffer();
	private static FloatBuffer posFar = ByteBuffer.allocateDirect(3 * 4).asFloatBuffer();

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void renderLast(RenderWorldLastEvent evt) {
		if (EntityCamera.isActive()) {
			GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, modelviewF);
			GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, projectionF);
			GL11.glGetInteger(GL11.GL_VIEWPORT, viewport);

			float x = Mouse.getX();
			float y = Mouse.getY();

			FloatBuffer winZ = GLAllocation.createDirectFloatBuffer(1);

			GL11.glReadPixels((int) x, (int) y, 1, 1, GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, winZ);
			GLU.gluUnProject(x, y, 0F, modelviewF, projectionF, viewport, posClose);
			GLU.gluUnProject(x, y, 1F, modelviewF, projectionF, viewport, posFar);

			close = Vec3.createVectorHelper(posClose.get(0), posClose.get(1), posClose.get(2));
			far = Vec3.createVectorHelper(posFar.get(0), posFar.get(1), posFar.get(2));

			// Draw selection box
			if (EntityCamera.mouseover != null) {
				drawBlockBounds(EntityCamera.activeCamera, EntityCamera.mouseover);
			}
		}
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void renderHand(RenderHandEvent event) {
		if (EntityCamera.isActive()) {
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void renderGUI(RenderGameOverlayEvent event) {
		if (EntityCamera.isActive()) {
			event.setCanceled(true);
		}
	}

	public static void drawBlockBounds(EntityLivingBase entity, MovingObjectPosition mob) {
		if (mob != null && mob.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
			Block block = entity.worldObj.getBlock(mob.blockX, mob.blockY, mob.blockZ);

			if (block.getMaterial() != Material.air) {
				block.setBlockBoundsBasedOnState(entity.worldObj, mob.blockX, mob.blockY, mob.blockZ);
				drawBoundingBox(entity, block.getCollisionBoundingBoxFromPool(entity.worldObj, mob.blockX, mob.blockY, mob.blockZ));
			}
		}
	}

	public static void drawEntityBounds(EntityLivingBase entity, Entity target) {
		if (target != null) {
			drawBoundingBox(entity, target.boundingBox);
		}
	}

	public static void drawBoundingBox(EntityLivingBase entity, AxisAlignedBB aabb) {
		if (aabb == null) {
			return;
		}

		GL11.glEnable(GL11.GL_BLEND);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.4F);
		GL11.glLineWidth(2.0F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDepthMask(false);
		float f1 = 0.002F;

		double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX);
		double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY);
		double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ);

		RenderGlobal.drawOutlinedBoundingBox(aabb.expand((double) f1, (double) f1, (double) f1).getOffsetBoundingBox(-d0, -d1, -d2), -1);

		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
	}

}
