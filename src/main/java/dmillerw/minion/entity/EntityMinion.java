package dmillerw.minion.entity;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

/**
 * @author dmillerw
 */
public class EntityMinion extends EntityCreature {

	private static final int DATA_OWNER = 20;
	private static final int DATA_SKIN = 21;

	public Vec3 locationTarget;

	private EntityLivingBase attackTarget;

	public String owner = "";
	public String skin = "";

	public EntityMinion(World world) {
		super(world);

		setSize(0.75F, 1F);
	}

	@Override
	public void entityInit() {
		super.entityInit();

		dataWatcher.addObject(DATA_OWNER, "");
		dataWatcher.addObject(DATA_SKIN, "");
	}

	@Override
	public boolean isChild() {
		return true;
	}

	@Override
	public boolean canDespawn() {
		return true;
	}

	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();

		EntityPlayer closest = worldObj.getClosestPlayerToEntity(this, 16D);

		if (closest != null && closest.getCommandSenderName().equals(owner)) {
			getLookHelper().setLookPosition(closest.posX, closest.posY + (double) closest.getEyeHeight(), closest.posZ, 10.0F, (float) getVerticalFaceSpeed());
		}
	}

	public Vec3 getLocationTarget() {
		return locationTarget;
	}

	public void setLocationTarget(Vec3 locationTarget) {
		this.locationTarget = locationTarget;
	}

	public EntityLivingBase getAttackTarget() {
		return attackTarget;
	}

	public void setAttackTarget(EntityLivingBase attackTarget) {
		this.attackTarget = attackTarget;
	}

	public String getOwner() {
		return dataWatcher.getWatchableObjectString(DATA_OWNER);
	}

	public void setOwner(String owner) {
		this.owner = owner;
		dataWatcher.updateObject(DATA_OWNER, owner);
	}

	public String getSkin() {
		return dataWatcher.getWatchableObjectString(DATA_SKIN);
	}

	public void setSkin(String skin) {
		this.skin = skin;
		dataWatcher.updateObject(DATA_SKIN, skin);
	}

	public void select() {
		jump();
	}

	public void deselect() {

	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);

		if (locationTarget != null) {
			NBTTagCompound targetNBT = new NBTTagCompound();
			targetNBT.setDouble("x", locationTarget.xCoord);
			targetNBT.setDouble("y", locationTarget.yCoord);
			targetNBT.setDouble("z", locationTarget.zCoord);
			nbt.setTag("locationTarget", targetNBT);
		}

		nbt.setString("owner", owner);
		nbt.setString("skin", skin);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);

		if (nbt.hasKey("locationTarget")) {
			NBTTagCompound targetNBT = nbt.getCompoundTag("locationTarget");
			setLocationTarget(Vec3.createVectorHelper(targetNBT.getDouble("x"), targetNBT.getDouble("y"), targetNBT.getDouble("z")));
		} else {
			setLocationTarget(null);
		}

		setOwner(nbt.getString("owner"));
		setSkin(nbt.getString("skin"));
	}

}
