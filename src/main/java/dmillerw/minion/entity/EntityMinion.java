package dmillerw.minion.entity;

import dmillerw.minion.RoboticMinions;
import dmillerw.minion.entity.ai.EntityAIMoveToTarget;
import dmillerw.minion.network.packet.client.PacketUpdateTarget;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

/**
 * @author dmillerw
 */
public class EntityMinion extends EntityLiving {

	private Vec3 target;

	private String owner = "";

	private EntityAIMoveToTarget targetAI;

	public EntityMinion(World world) {
		super(world);

		targetAI = new EntityAIMoveToTarget(this, 3F);
		this.tasks.addTask(0, targetAI);

		setSize(1F, 1F);
	}

	@Override
	public void entityInit() {

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
			targetAI.startExecuting();
		}

		if (!worldObj.isRemote && !(owner.isEmpty())) {
			EntityPlayer player = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(owner);
			PacketUpdateTarget packet = new PacketUpdateTarget(getEntityId(), target);
			RoboticMinions.pipeline.sendTo(packet, (EntityPlayerMP) player);
		}
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;

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
			setTarget(Vec3.createVectorHelper(targetNBT.getDouble("x"), targetNBT.getDouble("xy"), targetNBT.getDouble("z")));
		} else {
			setTarget(null);
		}

		owner = nbt.getString("owner");
	}

}
