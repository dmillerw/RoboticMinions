package dmillerw.minion.client.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dmillerw.minion.client.helper.RenderHelper;
import dmillerw.minion.entity.EntityMinion;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.List;

/**
 * @author dmillerw
 */
@SideOnly(Side.CLIENT)
public class EntityCamera extends EntityLivingBase {

	public static final int SCROLL_ZONE_PADDING = 5;
	public static final float SCROLL_SPEED = 1F;
	public static final float ROTATION_SPEED = 2F;

	public static EntityCamera activeCamera;

	public static EntityLivingBase activePlayer;

	public static MovingObjectPosition mouseover;

	public static EntityMinion selectedMinion;

	private static boolean paused = false;

	public static boolean isActive() {
		return activeCamera != null && !paused;
	}

	/* SETTINGS CACHE */
	public static int thirdPerson = 0;

	public static void createCamera() {
		if (activeCamera == null) {
			activeCamera = new EntityCamera(Minecraft.getMinecraft().renderViewEntity.worldObj);
			activePlayer = Minecraft.getMinecraft().renderViewEntity;

			activeCamera.worldObj.spawnEntityInWorld(activeCamera);

			activeCamera.copyLocationAndAnglesFrom(activePlayer);

			activePlayer.rotationYaw = 0;
			activeCamera.rotationPitch = 0;

			Minecraft.getMinecraft().renderViewEntity = activeCamera;
			thirdPerson = Minecraft.getMinecraft().gameSettings.thirdPersonView;
			Minecraft.getMinecraft().gameSettings.thirdPersonView = 8;

			activeCamera.setPositionAndRotation(activeCamera.posX, activeCamera.posY + 10, activeCamera.posZ, 0, 50);
			activeCamera.setPosition(activeCamera.posX, activeCamera.posY + 10, activeCamera.posZ);
		}
	}

	public static void destroyCamera() {
		if (activeCamera != null) {
			Minecraft.getMinecraft().renderViewEntity = activePlayer;
			Minecraft.getMinecraft().gameSettings.thirdPersonView = thirdPerson;
			activeCamera.worldObj.removeEntity(activeCamera);
			activeCamera.setDead();
			activeCamera = null;

			mouseover = null;
			selectedMinion = null;
		}
	}

	public static void pause() {
		if (activeCamera != null && !paused) {
			Minecraft.getMinecraft().renderViewEntity = activePlayer;
			Minecraft.getMinecraft().gameSettings.thirdPersonView = thirdPerson;

			paused = true;
		}
	}

	public static void resume() {
		if (activeCamera != null && paused) {
			Minecraft.getMinecraft().renderViewEntity = activeCamera;
			thirdPerson = Minecraft.getMinecraft().gameSettings.thirdPersonView;
			Minecraft.getMinecraft().gameSettings.thirdPersonView = 8;

			paused = false;
		}
	}

	public EntityCamera(World world) {
		super(world);

		width = 0;
		height = 0;
	}

	@Override
	public void onUpdate() {
		Minecraft mc = Minecraft.getMinecraft();

		Vec3 look = getLook(1.0F).normalize();
		Vec3 up = worldObj.getWorldVec3Pool().getVecFromPool(0, 1, 0);
		Vec3 side = up.crossProduct(look).normalize();
		Vec3 forward = side.crossProduct(up).normalize();

		motionX = 0;
		motionY = 0;
		motionZ = 0;

		setAngles(0, 0);

		// Movement
		if ((Mouse.hasWheel() && Mouse.isButtonDown(2)) || Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.getKeyCode())) {
			int mouseDX = Mouse.getDX();
			int mouseDY = Mouse.getDY();

			setAngles(mouseDX * ROTATION_SPEED, mouseDY * ROTATION_SPEED);
		} else {
			// TODO
			// Handling proper mouse coordinates when the cursor isn't grabbed by the window
			// Currently breaks if the player moves the mouse too fast and its position in the
			// scroll zone isn't detected

			int mouseX = Mouse.getX();
			int mouseY = Mouse.getY();
			int scroll = Mouse.getDWheel();
			int width = mc.displayWidth;
			int height = mc.displayHeight;

			if (scroll != 0) {
				motionY = (SCROLL_SPEED * -scroll) / 100;
			} else {
				if (mouseX <= SCROLL_ZONE_PADDING || Keyboard.isKeyDown(mc.gameSettings.keyBindLeft.getKeyCode())) {
					motionX += (side.xCoord * SCROLL_SPEED);
					motionZ += (side.zCoord * SCROLL_SPEED);
				} else if (mouseX >= width - SCROLL_ZONE_PADDING || Keyboard.isKeyDown(mc.gameSettings.keyBindRight.getKeyCode())) {
					motionX -= (side.xCoord * SCROLL_SPEED);
					motionZ -= (side.zCoord * SCROLL_SPEED);
				}

				if (mouseY <= SCROLL_ZONE_PADDING || Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode())) {
					motionX -= (forward.xCoord * SCROLL_SPEED);
					motionZ -= (forward.zCoord * SCROLL_SPEED);
				} else if (mouseY >= height - SCROLL_ZONE_PADDING || Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode())) {
					motionX += (forward.xCoord * SCROLL_SPEED);
					motionZ += (forward.zCoord * SCROLL_SPEED);
				}
			}
		}

		// OTHER
		if (raytraceEntity() == null) {
			mouseover = raytraceBlock();
		} else {
			mouseover = null;
		}

		super.onUpdate();
	}

	private Vec3 getMousePosition() {
		Vec3 pos = this.getPosition(1.0F);
		pos.xCoord += RenderHelper.close.xCoord;
		pos.yCoord += RenderHelper.close.yCoord;
		pos.zCoord += RenderHelper.close.zCoord;
		return pos;
	}

	private Vec3 getFarPosition() {
		Vec3 pos = this.getPosition(1.0F);
		pos.xCoord += RenderHelper.far.xCoord;
		pos.yCoord += RenderHelper.far.yCoord;
		pos.zCoord += RenderHelper.far.zCoord;
		return pos;
	}

	public MovingObjectPosition raytraceBlock() {
		return this.worldObj.rayTraceBlocks(getMousePosition(), getFarPosition());
	}

	public Entity raytraceEntity() {
		Vec3 start = getMousePosition();
		Vec3 end = getFarPosition();

		float expand = 1F;
		List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(end.xCoord, end.yCoord, end.zCoord).expand(expand, expand, expand));

		for (int i = 0; i < list.size(); i++) {
			Entity entity = (Entity) list.get(i);
			if (entity != null && this.canEntityBeSeen(entity) && entity.canBeCollidedWith()) {
				float expand2 = Math.max(1F, entity.getCollisionBorderSize());
				AxisAlignedBB aabb = entity.boundingBox.expand(expand2, expand2 * 1.25, expand2);
				MovingObjectPosition mob = aabb.calculateIntercept(start, end);

				if (mob != null) {
					return entity;
				}
			}
		}

		return null;
	}

	/* IGNORE */
	@Override
	public ItemStack getHeldItem() {
		return null;
	}

	@Override
	public ItemStack getEquipmentInSlot(int var1) {
		return null;
	}

	@Override
	public void setCurrentItemOrArmor(int var1, ItemStack var2) {

	}

	@Override
	public ItemStack[] getLastActiveItems() {
		return new ItemStack[0];
	}

}
