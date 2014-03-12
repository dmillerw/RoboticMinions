package dmillerw.minion.entity;

import dmillerw.minion.entity.ai.EntityAIMoveToTarget;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

/**
 * @author dmillerw
 */
public class EntityMinion extends EntityLiving {

	private Vec3 target;

	private EntityAIMoveToTarget targetAI;

	public EntityMinion(World world) {
		super(world);

		targetAI = new EntityAIMoveToTarget(this, 3F);
		this.tasks.addTask(0, targetAI);

		setSize(1F, 1F);
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
	}

	public void select() {
		jump();
	}

	public void deselect() {

	}

}
