package dmillerw.minion.entity.ai;

import dmillerw.minion.entity.EntityMinion;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.Vec3;

/**
 * @author dmillerw
 */
public class EntityAIMoveToTarget extends EntityAIBase {

	private final EntityMinion minion;

	private final float speed;

	public EntityAIMoveToTarget(EntityMinion minion, float speed) {
		this.minion = minion;
		this.speed = speed;
	}

	@Override
	public boolean shouldExecute() {
		return minion.getTarget() != null;
	}

	@Override
	public boolean continueExecuting() {
		if (minion.getNavigator().noPath()) {
			minion.setTarget((Vec3) null);
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void startExecuting() {
		minion.getNavigator().tryMoveToXYZ(minion.getTarget().xCoord, minion.getTarget().yCoord, minion.getTarget().zCoord, speed);
	}

}
