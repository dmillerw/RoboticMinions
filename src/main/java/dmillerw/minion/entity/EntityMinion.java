package dmillerw.minion.entity;

import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import java.util.Random;

/**
 * @author dmillerw
 */
public class EntityMinion extends EntitySheep {

	private MovingObjectPosition target;

	public EntityMinion(World world) {
		super(world);

		setFleeceColor(0xFFFFFF);

		setSize(0.6F, 1.8F);
	}

	public MovingObjectPosition getTarget() {
		return target;
	}

	public void setTarget(MovingObjectPosition target) {
		this.target = target;
	}

	public void select() {
		this.setFleeceColor(EntitySheep.getRandomFleeceColor(new Random()));
	}

	public void deselect() {
		this.setFleeceColor(0xFFFFFF);
	}

}
