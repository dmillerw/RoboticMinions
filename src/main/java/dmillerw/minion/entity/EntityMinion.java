package dmillerw.minion.entity;

import dmillerw.minion.entity.ai.EntityAIMoveToTarget;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

/**
 * @author dmillerw
 */
public class EntityMinion extends EntityLiving {

	public static final int TARGET_FUZZ = 2;

	private MovingObjectPosition target;

	public EntityMinion(World world) {
		super(world);

		this.tasks.addTask(0, new EntityAIMoveToTarget(this, 1F));

		setSize(1F, 1F);
	}

	public MovingObjectPosition getTarget() {
		return target;
	}

	public void setTarget(MovingObjectPosition target) {
		this.target = target;
	}

	public boolean atTarget() {
		if (target != null) {
			return posX <= target.blockX - TARGET_FUZZ && posX >= target.blockX + TARGET_FUZZ &&
					posY <= target.blockY - TARGET_FUZZ && posY >= target.blockY + TARGET_FUZZ &&
					posZ <= target.blockZ - TARGET_FUZZ && posZ >= target.blockZ + TARGET_FUZZ;
		}

		return false;
	}

	public void select() {

	}

	public void deselect() {

	}

}
