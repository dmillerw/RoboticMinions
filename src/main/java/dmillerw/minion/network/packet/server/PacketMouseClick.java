package dmillerw.minion.network.packet.server;

import dmillerw.minion.client.helper.SkinHelper;
import dmillerw.minion.core.handler.MinionHandler;
import dmillerw.minion.core.helper.RaytraceHelper;
import dmillerw.minion.entity.EntityMinion;
import dmillerw.minion.network.AbstractPacket;
import dmillerw.minion.network.VanillaPacketHelper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author dmillerw
 */
public class PacketMouseClick extends AbstractPacket {

	public static boolean DEBUG = false;

	public int button;

	public Vec3 locationClose;
	public Vec3 locationFar;

	public PacketMouseClick() {

	}

	public PacketMouseClick(int button, Vec3 locationClose, Vec3 locationFar) {
		this.button = button;
		this.locationClose = locationClose;
		this.locationFar = locationFar;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		buffer.writeInt(button);
		VanillaPacketHelper.writeVec3(locationClose, buffer);
		VanillaPacketHelper.writeVec3(locationFar, buffer);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		button = buffer.readInt();
		locationClose = VanillaPacketHelper.readVec3(buffer);
		locationFar = VanillaPacketHelper.readVec3(buffer);
	}

	@Override
	public void handleClientSide(EntityPlayer player) {

	}

	@Override
	public void handleServerSide(EntityPlayer player) {
		if (DEBUG) {
			System.out.println("Received mouse click from " + player.getCommandSenderName());

			Object raytraced = RaytraceHelper.raytrace(player.worldObj, locationClose, locationFar);
			if (raytraced != null) {
				if (raytraced instanceof EntityLivingBase) {
					System.out.println("Raytraced Entity!");
				} else if (raytraced instanceof MovingObjectPosition) {
					System.out.println("Raytraced Block!");
				}
				System.out.println(raytraced);
			} else {
				System.out.println("Raytraced null!");
			}
		}

		if (button == 0) { // Left click
			EntityLivingBase entity = RaytraceHelper.raytraceEntity(player.worldObj, locationClose, locationFar);

			if (entity != null && entity instanceof EntityMinion) {
				MinionHandler.selectMinion(player, (EntityMinion) entity);
			} else {
				MinionHandler.deselectMinion(player);
			}
		} else if (button == 1) { // Right click
			EntityMinion selected = MinionHandler.getSelectedMinion(player);

			if (selected == null) {
				MovingObjectPosition block = RaytraceHelper.raytraceBlock(player.worldObj, locationClose, locationFar);

				if (block != null && block.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
					ForgeDirection side = ForgeDirection.getOrientation(block.sideHit);
					EntityMinion minion = new EntityMinion(player.worldObj);
					minion.setOwner(player.getCommandSenderName());
					// Temporary
					minion.setSkin(SkinHelper.getRandomSkin());
					minion.setPosition((block.blockX + 0.5) + side.offsetX, block.blockY + side.offsetY, (block.blockZ + 0.5) + side.offsetZ);
					player.worldObj.spawnEntityInWorld(minion);
				}
			} else {
				MovingObjectPosition block = RaytraceHelper.raytraceBlock(player.worldObj, locationClose, locationFar);
				EntityLivingBase entity = RaytraceHelper.raytraceEntity(player.worldObj, locationClose, locationFar);

				if (entity == null && block != null && block.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
					ForgeDirection side = ForgeDirection.getOrientation(block.sideHit);
					selected.setLocationTarget(Vec3.createVectorHelper((block.blockX + 0.5) + side.offsetX, block.blockY + side.offsetY, (block.blockZ + 0.5) + side.offsetZ));

					if (selected.getAttackTarget() != null) {
						selected.setAttackTarget(null);
					}
				} else {
					if (entity != null) {
						selected.setAttackTarget(entity);
					}
				}
			}
		}
	}

}
