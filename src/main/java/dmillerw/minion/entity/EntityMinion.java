package dmillerw.minion.entity;

import dmillerw.minion.entity.ai.EntityAIPathfind;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

/**
 * @author dmillerw
 */
public class EntityMinion extends EntitySheep {

	public static final int TARGET_FUZZ = 2;

	private MovingObjectPosition target;

	public EntityMinion(World world) {
		super(world);

		this.tasks.addTask(9, new EntityAIPathfind(this, 1F));

		setFleeceColor(0);

		setSize(0.6F, 1.8F);
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
		this.setFleeceColor(15);
	}

	public void deselect() {
		this.setFleeceColor(0);
	}

}
