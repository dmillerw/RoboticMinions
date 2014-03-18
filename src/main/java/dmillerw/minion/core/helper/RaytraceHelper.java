package dmillerw.minion.core.helper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.List;

/**
 * @author dmillerw
 */
public class RaytraceHelper {

	public static final float AABB_RANGE = 1F;

	public static Object raytrace(World world, Vec3 close, Vec3 far) {
		Object obj = raytraceEntity(world, close, far);
		if (obj == null) {
			obj = raytraceBlock(world, close, far);
		}
		return obj;
	}

	public static MovingObjectPosition raytraceBlock(World world, Vec3 close, Vec3 far) {
		return world.rayTraceBlocks(close, far);
	}

	public static EntityLivingBase raytraceEntity(World world, Vec3 close, Vec3 far) {
		MovingObjectPosition block = raytraceBlock(world, close, far);

		if (block != null) {
			List list = world.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(block.blockX, block.blockY, block.blockZ, block.blockX + 1, block.blockY + 1, block.blockZ + 1).expand(AABB_RANGE, AABB_RANGE, AABB_RANGE));

			for (int i = 0; i < list.size(); i++) {
				Entity entity = (Entity) list.get(i);
				if (entity != null && entity.canBeCollidedWith()) {
					AxisAlignedBB aabb = entity.boundingBox.expand(entity.getCollisionBorderSize(), entity.getCollisionBorderSize(), entity.getCollisionBorderSize());
					MovingObjectPosition mob = aabb.calculateIntercept(close, far);

					if (mob != null) {
						return (EntityLivingBase) entity;
					}
				}
			}
		}

		return null;
	}

}
