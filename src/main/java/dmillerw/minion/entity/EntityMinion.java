package dmillerw.minion.entity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

/**
 * @author dmillerw
 */
public class EntityMinion extends EntityLiving {


	public static final int DATA_OWNER = 20;
	public static final int DATA_SKIN = 21;

	private Vec3 locationTarget;

	private String owner = "";
	private String skin = "";

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
	public boolean isAIEnabled() {
		return true;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();

		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(256.0D);
	}

	public Vec3 getLocationTarget() {
		return locationTarget;
	}

	public void setLocationTarget(Vec3 locationTarget) {
		this.locationTarget = locationTarget;
		if (locationTarget != null) {
			getNavigator().tryMoveToXYZ(locationTarget.xCoord, locationTarget.yCoord, locationTarget.zCoord, 1F);
		} else {
			if (getNavigator().getPath() != null) {
				getNavigator().clearPathEntity();
			}
		}
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
