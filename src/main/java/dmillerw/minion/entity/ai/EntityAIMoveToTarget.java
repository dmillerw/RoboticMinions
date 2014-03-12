package dmillerw.minion.entity.ai;

import dmillerw.minion.entity.EntityMinion;
import net.minecraft.entity.ai.EntityAIBase;

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
		return minion.getTarget() != null && !minion.atTarget();
	}

	@Override
	public boolean continueExecuting() {
		return !minion.getNavigator().noPath();
	}

	@Override
	public void startExecuting() {
		minion.getNavigator().tryMoveToXYZ(minion.getTarget().blockX, minion.getTarget().blockY, minion.getTarget().blockZ, speed);
	}

}
