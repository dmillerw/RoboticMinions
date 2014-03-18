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

	private Vec3 target;

	private String owner = "";

	public EntityMinion(World world) {
		super(world);

		setSize(1F, 1F);
	}

	@Override
	public void entityInit() {
		super.entityInit();

		dataWatcher.addObject(DATA_OWNER, "");
	}

	@Override
	protected boolean isAIEnabled() {
		return true;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();

		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(256.0D);
	}

	public Vec3 getTarget() {
		return target;
	}

	public void setTarget(Vec3 target) {
		this.target = target;
		if (target != null) {
			getNavigator().tryMoveToXYZ(target.xCoord, target.yCoord, target.zCoord, 1F);
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

	public void select() {
		jump();
	}

	public void deselect() {

	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);

		if (target != null) {
			NBTTagCompound targetNBT = new NBTTagCompound();
			targetNBT.setDouble("x", target.xCoord);
			targetNBT.setDouble("y", target.yCoord);
			targetNBT.setDouble("z", target.zCoord);
			nbt.setTag("target", targetNBT);
		}

		nbt.setString("owner", owner);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);

		if (nbt.hasKey("target")) {
			NBTTagCompound targetNBT = nbt.getCompoundTag("target");
			setTarget(Vec3.createVectorHelper(targetNBT.getDouble("x"), targetNBT.getDouble("y"), targetNBT.getDouble("z")));
		} else {
			setTarget(null);
		}

		owner = nbt.getString("owner");
	}

}
