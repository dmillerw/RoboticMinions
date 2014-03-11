package dmillerw.minion.client.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.lwjgl.input.Mouse;

/**
 * @author dmillerw
 */
@SideOnly(Side.CLIENT)
public class EntityCamera extends EntityLivingBase {

	public static final int SCROLL_ZONE_PADDING = 5;
	public static final float SCROLL_SPEED = 1F;

	public static EntityCamera activeCamera;

	public static EntityLivingBase activePlayer;

	/* SETTINGS CACHE */
	public static int thirdPerson = 0;

	public static void createCamera() {
		if (activeCamera == null) {
			activeCamera = new EntityCamera(Minecraft.getMinecraft().renderViewEntity.worldObj);
			activePlayer = Minecraft.getMinecraft().renderViewEntity;

			Minecraft.getMinecraft().renderViewEntity.worldObj.spawnEntityInWorld(activeCamera);

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
		motionX = 0;
		motionY = 0;
		motionZ = 0;

		setAngles(0, 0);

		// Maybe temporary
		int mouseX = Mouse.getX();
		int mouseY = Mouse.getY();
		int scroll = Mouse.getDWheel();
		int width = Minecraft.getMinecraft().displayWidth;
		int height = Minecraft.getMinecraft().displayHeight;

		if (mouseX <= SCROLL_ZONE_PADDING) {
			motionX = SCROLL_SPEED;
		} else if (mouseX >= width - SCROLL_ZONE_PADDING) {
			motionX = -SCROLL_SPEED;
		}

		if (mouseY <= SCROLL_ZONE_PADDING) {
			motionZ = -SCROLL_SPEED;
		} else if (mouseY >= height - SCROLL_ZONE_PADDING) {
			motionZ = SCROLL_SPEED;
		}

		motionY = (SCROLL_SPEED * -scroll) / 100;

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
