package dmillerw.minion.network.packet.client;

import dmillerw.minion.entity.EntityMinion;
import dmillerw.minion.network.AbstractPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;

/**
 * @author dmillerw
 */
public class PacketUpdateTarget extends AbstractPacket {

	private int id;

	public boolean isNull;

	private double x;
	private double y;
	private double z;

	public PacketUpdateTarget() {

	}

	public PacketUpdateTarget(int id, Vec3 target) {
		this.id = id;
		this.isNull = target == null;
		this.x = target != null ? target.xCoord : 0;
		this.y = target != null ? target.yCoord : 0;
		this.z = target != null ? target.zCoord : 0;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		buffer.writeInt(id);
		buffer.writeDouble(x);
		buffer.writeDouble(y);
		buffer.writeDouble(z);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		id = buffer.readInt();
		x = buffer.readDouble();
		y = buffer.readDouble();
		z = buffer.readDouble();
	}

	@Override
	public void handleClientSide(EntityPlayer player) {
		Entity entity = player.worldObj.getEntityByID(id);

		if (entity instanceof EntityMinion) {
			if (isNull) {
				((EntityMinion) entity).setTarget(null);
			} else {
				((EntityMinion) entity).setTarget(Vec3.createVectorHelper(x, y, z));
			}
		}
	}

	@Override
	public void handleServerSide(EntityPlayer player) {

	}

}
