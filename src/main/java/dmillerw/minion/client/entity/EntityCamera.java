package dmillerw.minion.client.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.lwjgl.input.Mouse;

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
		}
	}

	public EntityCamera(World world) {
		super(world);

		width = 0;
		height = 0;
	}

	@Override
	public void onUpdate() {
		Vec3 look = getLook(1.0F).normalize();

		Vec3 up = worldObj.getWorldVec3Pool().getVecFromPool(0, 1, 0);
		Vec3 side = up.crossProduct(look).normalize();
		Vec3 forward = side.crossProduct(up).normalize();

		motionX = 0;
		motionY = 0;
		motionZ = 0;

		setAngles(0, 0);

		if (Mouse.isButtonDown(2)) {
			int mouseDX = Mouse.getDX();
			int mouseDY = Mouse.getDY();

			setAngles(mouseDX * ROTATION_SPEED, mouseDY * ROTATION_SPEED);
		} else {
			int mouseX = Mouse.getX();
			int mouseY = Mouse.getY();
			int scroll = Mouse.getDWheel();
			int width = Minecraft.getMinecraft().displayWidth;
			int height = Minecraft.getMinecraft().displayHeight;

			if (scroll != 0) {
				motionY = (SCROLL_SPEED * -scroll) / 100;
			} else {
				if (mouseX <= SCROLL_ZONE_PADDING) {
					motionX += (side.xCoord * SCROLL_SPEED);
					motionZ += (side.zCoord * SCROLL_SPEED);
				} else if (mouseX >= width - SCROLL_ZONE_PADDING) {
					motionX -= (side.xCoord * SCROLL_SPEED);
					motionZ -= (side.zCoord * SCROLL_SPEED);
				}

				if (mouseY <= SCROLL_ZONE_PADDING) {
					motionX -= (forward.xCoord * SCROLL_SPEED);
					motionZ -= (forward.zCoord * SCROLL_SPEED);
				} else if (mouseY >= height - SCROLL_ZONE_PADDING) {
					motionX += (forward.xCoord * SCROLL_SPEED);
					motionZ += (forward.zCoord * SCROLL_SPEED);
				}
			}
		}

		super.onUpdate();
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
